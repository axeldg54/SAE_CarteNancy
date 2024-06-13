import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class HandlerReserve extends  HandlerGeneric { 
    private ServiceConverter converter;
    HandlerReserve(ServiceConverter conv){
        super();
        this.converter=conv;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Requete de " + exchange.getLocalAddress());

        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) { //Methode post
            boolean res = true;
    
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JSONObject jsonRequest = new JSONObject(body);
            
            // Extraire les paramètres du JSON
            int idRestaurant = jsonRequest.getInt("idRestaurant");
            String dateRes = jsonRequest.getString("dateRes");
            String nom = jsonRequest.getString("nom");
            String prenom = jsonRequest.getString("prenom");
            String numTel = jsonRequest.getString("numTel");
            int nbPersonnes = jsonRequest.getInt("nbPersonnes");

            System.out.println(idRestaurant+" ; "+dateRes+" ; "+nom+" ; "+prenom+" ; "+numTel+" ; "+ nbPersonnes);
    
            try {
                converter.reserve(idRestaurant, dateRes, nom, prenom, numTel, nbPersonnes);
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
                res = false;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                res = false;
            }

            JSONObject json=new JSONObject();
            json.put("result", res);
            
            sendResponse(exchange, 200, json.toString().getBytes(StandardCharsets.UTF_8));
        }
        else{ //GET method isn't authorized
            sendResponse(exchange, 405, "Unauthorized method");
        }
    }
}