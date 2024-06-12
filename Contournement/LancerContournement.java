import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Main
 */
public class LancerContournement {
    public static void main(String[] args) throws IOException {
        HttpServer serveur = HttpServer.create(new InetSocketAddress(8001), 0);
        serveur.createContext("/incidents.json", new HandlerServeur());
        serveur.setExecutor(null); 
        serveur.start();

        System.out.println("Serveur lancé");
    }
}