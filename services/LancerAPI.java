import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerAPI {
    public static void main(String[] args) throws IOException {
        int port=1090;
        String ip="localhost";

        if(args.length>0){
            port=Integer.parseInt(args[0]); 
        }

        Registry reg = null;
        try {
            reg = LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            System.out.println("service : Erreur lors de la création de l'annuaire.");
            System.exit(1);
        }

        API api=new API(8000);

        HandlerRestaurant handlerAll=new HandlerRestaurant();
        api.addHandler("/restaurants", handlerAll);

        HandlerRemaining handlerRemaining=new HandlerRemaining();
        api.addHandler("/remaining", handlerRemaining);

        HandlerReservation handlerReserve=new HandlerReservation();
        api.addHandler("/reservations", handlerReserve);

        HandlerIncident handlerIncident = new HandlerIncident();
        api.addHandler("/incidents", handlerIncident);

        HandlerSup handlerSup = new HandlerSup();
        api.addHandler("/sup", handlerSup);


        ServiceAPI rdServiceAPI=null;

        try {
            rdServiceAPI = (ServiceAPI) UnicastRemoteObject.exportObject(api, 0);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
            System.out.println("service : L'exportation n'a pas fonctionné");
            System.exit(1);
        }

        try {
            rdServiceAPI.start();
            reg.rebind("api", rdServiceAPI);
        } catch (RemoteException e) {
            System.out.println("service : probleme enregistrement proxy");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("API BD lancée");
    }
}
