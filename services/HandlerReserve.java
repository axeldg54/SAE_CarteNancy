import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import org.json.JSONObject;

class HandlerReserve extends HandlerGeneric {
    HandlerReserve(ServiceConverter conv) {
        super();
        this.converter = conv;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Requete de " + exchange.getLocalAddress());

        // Add CORS headers
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type");

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1); // No content for preflight request
            return;
        }

        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) { // Method post
            boolean res = true;

            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JSONObject jsonRequest = new JSONObject(body);

            // Extract parameters from JSON
            int idRestaurant = jsonRequest.getInt("idRestaurant");
            String dateRes = jsonRequest.getString("dateRes");
            String nom = jsonRequest.getString("nom");
            String prenom = jsonRequest.getString("prenom");
            String numTel = jsonRequest.getString("numTel");
            int nbPersonnes = jsonRequest.getInt("nbPersonnes");

            System.out.println(idRestaurant + " ; " + dateRes + " ; " + nom + " ; " + prenom + " ; " + numTel + " ; " + nbPersonnes);

            try {
                converter.reserve(idRestaurant, dateRes, nom, prenom, numTel, nbPersonnes);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());
                res = false;
            }

            JSONObject json = new JSONObject();
            json.put("result", res);

            sendResponse(exchange, 200, json.toString().getBytes(StandardCharsets.UTF_8));
        } else { // GET method isn't authorized
            sendResponse(exchange, 405, "Unauthorized method");
        }
    }
}
