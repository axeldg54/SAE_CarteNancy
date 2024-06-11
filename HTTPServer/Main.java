import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer serveur = HttpServer.create(new InetSocketAddress(8000), 0);
        serveur.createContext("/test", new HandlerServeur());
        serveur.setExecutor(null); // creates a default executor
        serveur.start();

        System.out.println("Serveur lancé");
    }
}