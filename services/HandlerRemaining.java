import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

class HandlerRemaining extends HandlerGeneric {   
    private ServiceConverter converter; 
    HandlerRemaining(ServiceConverter conv){
        super();
        this.converter=conv;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Requete de " + exchange.getLocalAddress());

        //Obtention des params donnés dans le query string
        Map<String, String> parameters=queryToMap(exchange.getRequestURI().getQuery()); 

        String jsonString = null;
        try {
            jsonString = converter.getAvailable(parameters.get("id"), parameters.get("date"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sendResponse(exchange, 200, jsonString);
    }
}