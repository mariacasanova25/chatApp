
import java.rmi.*;
import java.util.HashMap;

public  class ChatImpl implements ChatService {

	HashMap<Integer, InfoClient> registrationTable;

 
	public ChatImpl() {
		registrationTable= new HashMap<Integer, InfoClient>();
	}
   
	public int join(InfoClient client)  throws RemoteException{
        if(registrationTable.containsKey(client.getId())) return -1; //user already exists

        registrationTable.put(client.getId(),client);
        return 0; 
    }
   
    public int leave(InfoClient client) throws RemoteException{
        if(!registrationTable.containsKey(client.getId())) return -1; //user doesn't exist

        registrationTable.remove(client.getId());
        return 0; 
    }

    /*public int send(String message, InfoClient sender, InfoClient receiver) throws RemoteException;

    public String receive(InfoClient sender) throws RemoteException;*/
}

