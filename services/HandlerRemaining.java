import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

class HandlerRemaining implements  HttpHandler {
    public ServiceConverter converter;
    
    HandlerRemaining(ServiceConverter conv){
        super();
        this.converter=conv;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Requete de " + exchange.getLocalAddress());

        //Obtention des params donnés dans le query string
        Map<String, String> parameters=queryToMap(exchange.getRequestURI().getQuery()); 

        String jsonString = null;
        try {
            jsonString = converter.getAvailable(parameters.get("id"), parameters.get("date"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String encoding = "UTF-8"; // Peut-être inutile pour du JSON

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=" + encoding);
        // C'est cette ligne qui permet d'autoriser le CORS
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

        byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length); // 200 = OK

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    public Map<String, String> queryToMap(String query) {
        if(query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }
}