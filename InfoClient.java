import java.rmi.*;

public interface InfoClient extends Remote {
    public Integer getId() throws RemoteException;
}