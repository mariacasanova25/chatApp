
import java.rmi.*;
import java.util.*;

public interface ChatService extends Remote {
	public int join(InfoClient client)  throws RemoteException;

    public int leave(InfoClient client) throws RemoteException;

   
    public int send(String message,InfoClient sender) throws RemoteException;

    public ArrayList<String> getHistory(InfoClient client) throws RemoteException, Exception; 

    //public String receive(InfoClient sender) throws RemoteException;*/
}
