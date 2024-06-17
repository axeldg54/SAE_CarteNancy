import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;

class HandlerAllReservation extends  HandlerGeneric {
    HandlerAllReservation(ServiceConverter conv){
        super();
        this.converter=conv;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        
        System.out.println("Requete de " + exchange.getLocalAddress());

        String jsonString = null;
        try {
            jsonString = converter.getAllReservationData();
            sendResponse(exchange, 200, jsonString);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur interne");
            sendResponse(exchange, 500, "Internal server error");
        } 
    }
}