import java.rmi.RemoteException;

public class Converter implements ServiceConverter {
    private String password;
    private String username;

    private final String url="charlemagne.iutnc.univ-lorraine.fr";

    Converter(String u, String p){
        this.password=p;
        this.username=u;
    }
    
    @Override
    public String getData() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getData'");
    }
}
