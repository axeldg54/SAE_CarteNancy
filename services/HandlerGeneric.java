import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.json.*;

abstract class HandlerGeneric implements  HttpHandler {    
    public ServiceConverter converter;
    public ServiceBridge bridge;
    
    @Override
    public abstract void handle(HttpExchange exchange) throws IOException;
    public Map<String, String> queryToMap(String query) {
        if(query == null) {
            return null;
        }

        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }

    protected void sendResponse(HttpExchange exchange, int statusCode, byte[] responseBytes) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
            os.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi de la réponse: " + e.getMessage());
        }
    }

    protected void sendResponse(HttpExchange exchange, int statusCode, String responseMessage) throws IOException {
        byte[] responseBytes = responseMessage.getBytes(StandardCharsets.UTF_8);
        sendResponse(exchange, statusCode, responseBytes);
    }


    protected String extractString(JSONObject json, String key, String defaultValue){
        try{
            String res=json.getString(key);
            return res;
        }
        catch(Exception e){
            return defaultValue;
        }
    }
    
    protected int extractInt(JSONObject json, String key, int defaultValue){
        try{
            int res=json.getInt(key);
            return res;
        }
        catch(Exception e){
            return defaultValue;
        }
    }
}