import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceConverter extends Remote{
    public String getData() throws RemoteException;
}