import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

class HandlerSup extends HandlerGeneric {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Requete à " + exchange.getLocalAddress());

        try {
            String json;
            json=bridge.getSup();
            
            sendResponse(exchange, 200, json);
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
            sendResponse(exchange, 500, "Internal error");
        }
    }
}