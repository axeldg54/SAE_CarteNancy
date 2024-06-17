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
        connection.setAutoCommit(true);

        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM RESTAURANT order by id");

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
        connection.setAutoCommit(true);

        String requestString=
        "SELECT nom, nbResMax, nbResMax - COALESCE((SELECT SUM(nbPersonnes) FROM RESERVATION WHERE IDRESTAURANT = ? AND DATERES = ?), 0) AS restant FROM RESTAURANT WHERE id = ?";

        PreparedStatement st=connection.prepareStatement(requestString);
        st.setString(1, id);
        st.setString(2, date);
        st.setString(3, id);

        ResultSet rs = st.executeQuery();

        JSONObject json=new JSONObject();

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

    @Override
    public String getAllReservationData() throws RemoteException, ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(true);

        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM Reservation ORDER BY id");

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
    public void reserve(int idRes, String dateRes, String nom, String prenom, String numTel, int nbPersonnes) throws RemoteException, ClassNotFoundException, SQLException {        
        Class.forName("oracle.jdbc.driver.OracleDriver");

        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(true);
        
        String requestString=
        "INSERT INTO RESERVATION(idRestaurant, dateres, nom, prenom, numtel, nbPersonnes) "+
        "VALUES(?,To_Date(?, 'DD/MM/YYYY'),?,?,?,?)";
        
        PreparedStatement st=connection.prepareStatement(requestString);
        st.setInt(1, idRes);
        st.setString(2, dateRes);
        st.setString(3, nom);
        st.setString(4, prenom);
        st.setString(5, numTel);
        st.setInt(6, nbPersonnes);
        
        st.executeUpdate();
    }

    @Override
    public int addRestaurant(String nom, String adresse, String latitude, String longitude,  String note, String telephone, int nbResMax, String image) throws RemoteException, ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(true);
        
        //insert_restaurant insert un nouveau restaurant et retourne en parametre inout l'id du restaurant inseré
        CallableStatement st = connection.prepareCall("{call insert_restaurant(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

        st.setString(1, nom);
        st.setString(2, adresse);
        st.setString(3, latitude);
        st.setString(4, longitude);
        st.setString(5, note);
        st.setString(6, telephone);
        st.setInt(7, nbResMax);
        st.setString(8, image);

        st.registerOutParameter(9, java.sql.Types.NUMERIC);

        st.executeUpdate();

        int newId = st.getInt(9);

        return newId;
    }
}