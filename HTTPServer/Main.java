import com.sun.net.httpserver.HttpServer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpServer serveur = HttpServer.create(new InetSocketAddress(8000), 0);
        serveur.createContext("/test", new MyHandler());
        serveur.setExecutor(null); // creates a default executor
        serveur.start();

        System.out.println("Serveur lancé");
    }
}