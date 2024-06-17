import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

class HandlerRemaining extends HandlerGeneric {   
    HandlerRemaining(ServiceConverter conv){
        super();
        this.converter=conv;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        
        System.out.println("Requete de " + exchange.getLocalAddress());

        //Obtention des params donnés dans le query string
        Map<String, String> parameters=queryToMap(exchange.getRequestURI().getQuery()); 

        String jsonString = null;
        try {
            jsonString = converter.getAvailable(parameters.get("id"), parameters.get("date"));
            sendResponse(exchange, 200, jsonString);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur interne");
            sendResponse(exchange, 500, "Internal server error");    
        }
    }
}