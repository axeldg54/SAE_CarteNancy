import java.io.IOException;
import java.net.InetSocketAddress;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpServer;

public class API implements ServiceAPI {
    private ArrayList<HandlerGeneric> handlers;

    private HttpServer server;

    API(int port){
        try{
            server = HttpServer.create(new InetSocketAddress(port), 0);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        handlers=new ArrayList<>();
    }

    public void addHandler(String endpoint, HandlerGeneric handler){
        handlers.add(handler);
        server.createContext(endpoint, handler);
    }

    public void start() throws RemoteException{
        server.start();
    }

    @Override
    public void addConverter(ServiceConverter converter) throws RemoteException {
        for (HandlerGeneric handler : handlers) {
            handler.converter=converter;
        }
    }

    @Override
    public void addBridge(ServiceBridge bridge) throws RemoteException {
        for (HandlerGeneric handler : handlers) {
            handler.bridge=bridge;
        }
    }
}
