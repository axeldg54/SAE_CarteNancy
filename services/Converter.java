import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.*;

public class Converter implements ServiceConverter {
    private String password;
    private String username;

    private final String url = "jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb";

    Converter(String u, String p) {
        this.password = p;
        this.username = u;
    }

    @Override
    public JSONArray getAllRestaurantData() throws RemoteException, ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        Connection connection = DriverManager.getConnection(url, username, password);

        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM RESTAURANT");

        while (rs.next()) {

        }

        return null;
    }
}
