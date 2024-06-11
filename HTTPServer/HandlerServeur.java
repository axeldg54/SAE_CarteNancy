import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

class HandlerServeur implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Requete à " + exchange.getLocalAddress());
        // String requestMethod = exchange.getRequestMethod();
        // String query = exchange.getRequestURI().getQuery();

        // InputStream is = exchange.getRequestBody();
        // os.read(is); // .. read the request body

        HttpClient client = //HttpClient.newHttpClient();
        HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            //.proxy(ProxySelector.of(new InetSocketAddress("www-proxy.com", 80))) // Pour depuis l'IUT ?
            //.authenticator(Authenticator.getDefault())
            .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://carto.g-ny.org/data/cifs/cifs_waze_v2.json"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                //.GET()
                .build();

        try {
            HttpResponse<String> r = client.send(request, HttpResponse.BodyHandlers.ofString());
            // System.out.println(r.statusCode());
            // System.out.println(r.body());
            String encoding = "UTF-8"; // Peut-être inutile pour du JSON
            String reponse = r.body();
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=" + encoding);
            // C'est cette ligne qui permet d'autoriser le CORS
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

            byte[] bytes = reponse.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length); // 200 = OK
            System.out.println(reponse);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}