import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;

class HandlerAllReservation extends  HandlerGeneric {
    private ServiceConverter converter;

    HandlerAllReservation(ServiceConverter conv){
        super();
        this.converter=conv;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
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