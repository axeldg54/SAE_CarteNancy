import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceBridge extends Remote{
    public String getIncidents() throws RemoteException, InterruptedException, IOException;

    public String getSup() throws RemoteException, InterruptedException, IOException;
}
