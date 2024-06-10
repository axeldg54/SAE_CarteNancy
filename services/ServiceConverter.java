import org.json.JSONArray;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface ServiceConverter extends Remote{
    public JSONArray getAllRestaurantData() throws RemoteException, ClassNotFoundException, SQLException;
}