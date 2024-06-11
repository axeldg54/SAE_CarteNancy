import java.rmi.RemoteException;
import java.sql.*;

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

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount=metaData.getColumnCount();

        JSONArray json=new JSONArray();

        while (rs.next()) {
            JSONObject jsonObject=new JSONObject();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = rs.getObject(i);
                jsonObject.put(columnName, value);
            }
        }
        return json;
    }
}