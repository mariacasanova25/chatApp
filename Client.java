import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*; 
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
  public static void main(String [] args) {
	
	try {
	  if (args.length < 2) {
	   System.out.println("Usage: java HelloClient <rmiregistry host>");
	   return;}

	String host = args[0];
	
	InfoClientImpl client= new InfoClientImpl(Integer.parseInt(args[1]));
	InfoClient infoClient= (InfoClient) UnicastRemoteObject.exportObject(client, 0);
	Registry registry = LocateRegistry.getRegistry(host); 

	ChatService chatService = (ChatService) registry.lookup("ChatService");

	Scanner scanner = new Scanner(System.in);
	String line;
	String[] command;
	int res;
	
while(true){

	line = scanner.nextLine();
	command = line.split(" ");
	
	switch(command[0]){
			case "send":
				res = chatService.send(command[1],infoClient);
				if(res==-1) System.out.println("you need to join the chatgroup first");
					else{
					System.out.println("success: message sent");
				}
				break;

			case "join":
				res = chatService.join(infoClient);

				if(res==-1) System.out.println("you are already joined in chatgroup");
				else{
					System.out.println("success: you joined the group");
				}
				break;

			case "leave":
				res = chatService.leave(infoClient);

				if(res==-1) System.out.println("you are not in the chatgroup");
				else{
					System.out.println("success: you left the group");
				}
				break;

			case "history":
				try{
					ArrayList<String> history= chatService.getHistory(infoClient);
					System.out.println(history);

				}catch(Exception e){
					
					System.out.println(e.getMessage());
				}
				
				break;
 
		}
}
	

	} catch (Exception e)  {
		System.err.println("Error on client: " + e);
	}
  }
}