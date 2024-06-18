import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

class HandlerIncident extends HandlerGeneric {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Requete à " + exchange.getLocalAddress());

        HttpClient client = //HttpClient.newHttpClient();
        HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://carto.g-ny.org/data/cifs/cifs_waze_v2.json"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> r = client.send(request, HttpResponse.BodyHandlers.ofString());

            String reponse = r.body();
            
            sendResponse(exchange, 200, reponse);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            sendResponse(exchange, 500, "Interruption error");
        }
    }
}