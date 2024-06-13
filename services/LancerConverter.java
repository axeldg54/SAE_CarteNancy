import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerConverter {
    public static void main(String[] args) {
        int port = 1090;
        if (args.length > 0) port = Integer.parseInt(args[0]);

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
            reg = LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            System.out.println("service : Erreur lors de la recuperation de l'annuaire.");
            System.exit(1);
        }

        try {
            reg.rebind("converter", rdServiceConverter);
        } catch (RemoteException e) {
            System.out.println("service : probleme enregistrement proxy");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        
        System.out.print("Service lancé");
    }
}