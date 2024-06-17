import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface ServiceConverter extends Remote{
    public String getAllRestaurantData() throws RemoteException, ClassNotFoundException, SQLException;
    
    public String getAvailable(String id, String date) throws RemoteException, ClassNotFoundException, SQLException;

    public String getAllReservationData() throws RemoteException, ClassNotFoundException, SQLException;

    public void reserve(int idRes, String dateRes, String nom, String prenom, String numTel, int nbPersonnes) throws RemoteException, ClassNotFoundException, SQLException;

    public void addRestaurant(String nom, String adresse, String latitude, String longitude,  String note, String telephone, int nbResMax, String image) throws RemoteException, ClassNotFoundException, SQLException;
}