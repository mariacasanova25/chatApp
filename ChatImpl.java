
import java.rmi.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public  class ChatImpl implements ChatService {

	HashMap<Integer, InfoClient> registrationTable;
    ArrayList<String> history;
    FileWriter writer;

 
	public ChatImpl() {
		registrationTable= new HashMap<Integer, InfoClient>();
       this.history = new ArrayList<String>();
        fillHistory();      
	}

    private void fillHistory(){
      try {
            BufferedReader reader = new BufferedReader(new FileReader("history.txt")) ;
            String line = reader.readLine();
            history.add(line);

            while (line != null) {
                line = reader.readLine();
                history.add(line);
            }
            reader.close();
      }catch(IOException e){
       e.printStackTrace(); 
      }
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

    public int send(String message, InfoClient sender) throws RemoteException{
        if(!registrationTable.containsKey(sender.getId())) return -1;
        try{
            writer= new FileWriter("history.txt", true);
            String newMessage="Sender: "+Integer.toString(sender.getId())+" plaintext: " +message;
            writer.append(newMessage);
            history.add(newMessage);
            writer.close();
            return 0;
        }catch (IOException e){
            System.out.println("Unable to open history");
            System.exit(-1);
            return -1;
        }
    }

    public ArrayList<String> getHistory(InfoClient client) throws RemoteException, Exception{
        if(!registrationTable.containsKey(client.getId())) throw new Exception("error: you're not in the group"); 
        return history;
    }
}

