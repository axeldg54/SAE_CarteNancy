import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;

class HandlerAllRestaurant extends HandlerGeneric {
    private ServiceConverter converter;

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
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur class");
        } catch (SQLException e) {
            System.out.println("Erreur SQL");
        }

        System.out.println(jsonString);
        sendResponse(exchange, 200, jsonString);
    }
}