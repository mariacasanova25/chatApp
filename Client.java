import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*; 

public class Client {
  public static void main(String [] args) {
	
	try {
	  if (args.length < 2) {
	   System.out.println("Usage: java HelloClient <rmiregistry host>");
	   return;}

	String host = args[0];
	
	InfoClientImpl client= new InfoClientImpl(Integer.parseInt(args[1]));
	InfoClient infoClient= (InfoClient) UnicastRemoteObject.exportObject(client, 0);

	// Get remote object reference
	Registry registry = LocateRegistry.getRegistry(host); 

	ChatService chatService = (ChatService) registry.lookup("ChatService");

	// Remote method invocation
	int res = chatService.join(infoClient);
	System.out.println(res);

	} catch (Exception e)  {
		System.err.println("Error on client: " + e);
	}
  }
}