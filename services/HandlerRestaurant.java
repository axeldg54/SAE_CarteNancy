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
            boolean res;

            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JSONObject jsonRequest = new JSONObject(body);
            System.out.println(jsonRequest);

            // Extract parameters from JSON
            String nom = jsonRequest.getString("nom");
            String adresse = jsonRequest.getString("adresse");
            String latitude = jsonRequest.getString("latitude");
            String longitude = jsonRequest.getString("longitude");
            String note = jsonRequest.getString("note");
            String telephone = jsonRequest.getString("telephone");
            int nbResMax = jsonRequest.getInt("nbResMax");
            String image = jsonRequest.getString("image");

            System.out.println(nom+ "; "+adresse+" ; "+latitude+" ; "+longitude+" ; "+note+" ; "+telephone+" ; "+nbResMax+" ; "+image);


            int code;
            JSONObject json = new JSONObject();

            try {
                int newId=converter.addRestaurant(nom, adresse,latitude,longitude, note, telephone, nbResMax,image);

                if(newId==-1){
                    code=500;
                    res=false;
                    System.out.println("NEW ID INTROUVABLE");
                }
                else{
                    res=true;
                    code=200;
                    json.put("id", newId);
                }
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());
                res = false;
                code=500;
            }

            json.put("result", res);

            sendResponse(exchange, code, json.toString().getBytes(StandardCharsets.UTF_8));     
        }
    }
}