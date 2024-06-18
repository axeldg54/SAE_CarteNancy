import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceAPI extends Remote{
    public void addConverter(ServiceConverter converter) throws RemoteException;

    public void addBridge(ServiceBridge bridge) throws RemoteException;
}
