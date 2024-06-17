import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import org.json.*;

class HandlerRestaurant extends HandlerGeneric {
    HandlerRestaurant(ServiceConverter conv){
        super();
        this.converter=conv;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Requete de " + exchange.getLocalAddress());

        if(exchange.getRequestMethod().equalsIgnoreCase("GET")){
            String jsonString = null;
            try {
                jsonString = converter.getAllRestaurantData();
                sendResponse(exchange, 200, jsonString);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur interne");
                sendResponse(exchange, 500, "Internal server error");
            } 
        }
        else{
            boolean res = true;

            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JSONObject jsonRequest = new JSONObject(body);

            // Extract parameters from JSON
            String nom = jsonRequest.getString("nom");
            String adresse = jsonRequest.getString("adresse");
            String latitude = jsonRequest.getString("latitude");
            String longitude = jsonRequest.getString("longitude");
            String note = jsonRequest.getString("note");
            String telephone = jsonRequest.getString("numTel");
            int nbResMax = jsonRequest.getInt("nbResMax");
            String image = jsonRequest.getString("image");


            int code;
            try {
                converter.addRestaurant(nom, adresse,latitude,longitude, note, telephone, nbResMax,image);
                code=200;
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());
                res = false;
                code=500;
            }

            JSONObject json = new JSONObject();
            json.put("result", res);

            sendResponse(exchange, code, json.toString().getBytes(StandardCharsets.UTF_8));     
        }
    }
}