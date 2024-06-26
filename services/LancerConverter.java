import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerConverter {
    public static void main(String[] args) {
        String ip="localhost";
        int port = 1090;

        if (args.length > 0){ 
            ip=args[0];
            if(args.length>1){
                port = Integer.parseInt(args[1]);
            }
        }

        ServiceConverter serviceConverter = new Converter("baudson4u", "rootroot");

        ServiceConverter rdServiceConverter = null;

        try {
            rdServiceConverter = (ServiceConverter) UnicastRemoteObject.exportObject(serviceConverter, 0);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
            System.out.println("service : L'exportation n'a pas fonctionné");
            System.exit(1);
        }

        Registry reg = null;
        try {
            reg = LocateRegistry.getRegistry(ip, port);
        } catch (RemoteException e) {
            System.out.println("service : Erreur lors de la recuperation de l'annuaire.");
            System.exit(1);
        }

        ServiceAPI api=null;
        try {
            api=(ServiceAPI) reg.lookup("api");
        } catch (RemoteException e) {
            System.out.println("service : probleme enregistrement proxy");
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("service : probleme enregistrement proxy");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        try {
            api.addConverter(rdServiceConverter);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        System.out.print("Service lancé");
    }
}