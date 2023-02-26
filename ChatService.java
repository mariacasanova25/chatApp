import java.rmi.*;
import java.util.*;

public interface ChatService extends Remote {
	public void join(InfoClient client)  throws RemoteException, Exception;

    public void leave(InfoClient client) throws RemoteException, Exception;
   
    public void send(String message,InfoClient sender) throws RemoteException,Exception;

    public ArrayList<String> getHistory(InfoClient client) throws RemoteException, Exception;
}
