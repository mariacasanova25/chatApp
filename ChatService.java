
import java.rmi.*;

public interface ChatService extends Remote {
	public int join(InfoClient client)  throws RemoteException;

    public int leave(InfoClient client) throws RemoteException;

   
    /*public int send(String message,InfoClient sender,InfoClient receiver) throws RemoteException;

    public String receive(InfoClient sender) throws RemoteException;*/
}
