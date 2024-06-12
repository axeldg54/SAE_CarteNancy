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
    public String getAllRestaurantData() throws RemoteException, ClassNotFoundException, SQLException {
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
            json.put(jsonObject);
        }
        return json.toString();
    }


    @Override
    public String getAvailable(String id, String date) throws RemoteException, ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        Connection connection = DriverManager.getConnection(url, username, password);

        String requestString=
        "select nbPlaces-(select count(id) restant, nbResMax, nom from RESERVATION "+
        "where IDRESTAURANT = ? "+
        "and DATERES = ?) "+
        "as nbPlacesRestantes "+
        "from RESTAURANT "+
        "where id = ?";

        PreparedStatement st=connection.prepareStatement(requestString);
        st.setString(1, id);
        st.setString(2, date);
        st.setString(3, id);

        ResultSet rs = st.executeQuery();

        JSONObject json=new JSONObhect();

        rs.next();
        
        ResultSetMetaData metaData=rs.getMetaData();
        int columnCount=metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            Object value = rs.getObject(i);
            json.put(columnName, value);
        }
        return json.toString();
    }
}