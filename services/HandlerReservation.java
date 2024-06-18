import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.sql.SQLException;
import org.json.*;

class HandlerReservation extends  HandlerGeneric {
    public ServiceConverter converter;

    HandlerReservation(){
        super();
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Requete de " + exchange.getLocalAddress());

        // Add CORS headers
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type");

        if(exchange.getRequestMethod().equalsIgnoreCase("GET")){  //GET
            String jsonString = null;

            try {
                jsonString = converter.getAllReservationData();
                sendResponse(exchange, 200, jsonString);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur interne");
                sendResponse(exchange, 500, "Internal server error");
            } 
        }
        else{    //POST
            int code;
            boolean res;

            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JSONObject jsonRequest = new JSONObject(body);

            // Extract parameters from JSON
            int idRestaurant = extractInt(jsonRequest, "idRestaurant", 0);
            String dateRes = extractString(jsonRequest,"dateRes", null);
            String nom = extractString(jsonRequest,"nom", null);
            String prenom = extractString(jsonRequest,"prenom", null);
            String numTel = extractString(jsonRequest,"numTel", null);
            int nbPersonnes = extractInt(jsonRequest,"nbPersonnes", 1);

            System.out.println(idRestaurant + " ; " + dateRes + " ; " + nom + " ; " + prenom + " ; " + numTel + " ; " + nbPersonnes);

            try {
                converter.reserve(idRestaurant, dateRes, nom, prenom, numTel, nbPersonnes);
                code=200;
                res=true;
            } catch (ClassNotFoundException | SQLException | RemoteException e) {
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