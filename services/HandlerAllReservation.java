import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String encoding = "UTF-8"; // Peut-être inutile pour du JSON

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=" + encoding);
        // C'est cette ligne qui permet d'autoriser le CORS
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

        byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length); // 200 = OK

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}