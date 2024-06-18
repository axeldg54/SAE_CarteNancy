import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LancerAPI {
    public static void main(String[] args) throws IOException {
        int port=1090;
        String ip="localhost";

        if(args.length>0){
            ip=args[0];
            if(args.length>1){
                port=Integer.parseInt(args[1]);
            }
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        Registry reg=LocateRegistry.getRegistry(ip, port);


        //Recuperation du proxy
        ServiceConverter converter=null;
        try {
            converter=(ServiceConverter) reg.lookup("converter");
        } catch (NotBoundException e) {
            System.out.println("Service introuvable");
            System.exit(1);
        }

        HandlerRestaurant handlerAll=new HandlerRestaurant(converter);
        HandlerRemaining handlerRemaining=new HandlerRemaining(converter);
        HandlerReservation handlerReserve=new HandlerReservation(converter);
        HandlerIncident handlerIncident = new HandlerIncident();

        server.createContext("/restaurants", handlerAll);
        server.createContext("/remaining", handlerRemaining);
        server.createContext("/reservations", handlerReserve);
        server.createContext("/incidents", handlerIncident);


        server.setExecutor(null); // creates a default executor
        server.start();

        System.out.println("API BD lancée");
    }
}
