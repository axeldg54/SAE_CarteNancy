import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class HandlerReserve implements  HttpHandler {
    public ServiceConverter converter;
    
    HandlerReserve(ServiceConverter conv){
        super();
        this.converter=conv;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Requete de " + exchange.getLocalAddress());

        boolean res=true;

        //Obtention des params donnés dans le query string
        Map<String, String> parameters=queryToMap(exchange.getRequestURI().getQuery()); 

        String jsonString = null;
        try {
            converter.reserve(parameters.get("idRestaurant"), parameters.get("dateRes"), parameters.get("nom"), parameters.get("prenom"), parameters.get("numTel"), parameters.get("nbPersonnes"));
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            res=false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            res=false;
        }
        String encoding = "UTF-8"; // Peut-être inutile pour du JSON

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=" + encoding);
        // C'est cette ligne qui permet d'autoriser le CORS
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

        JSONObject json=new JSONObject();
        json.put("result", res);

        byte[] bytes = json.toString().getBytes(StandardCharsets.UTF_8);
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