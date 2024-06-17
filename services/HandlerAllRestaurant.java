import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;

class HandlerAllRestaurant extends HandlerGeneric {
    HandlerAllRestaurant(ServiceConverter conv){
        super();
        this.converter=conv;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Requete de " + exchange.getLocalAddress());

        String jsonString = null;
        try {
            jsonString = converter.getAllRestaurantData();
            sendResponse(exchange, 200, jsonString);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur interne");
            sendResponse(exchange, 500, "Internal server error");
        } 
    }
}