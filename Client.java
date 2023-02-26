import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        // Check the number of arguments
        try {
            if (args.length < 2) {
                System.out.println("Usage: java HelloClient <rmiregistry host>");
                return;
            }

            //Casting arguments
            String host = args[0];

            InfoClientImpl client = new InfoClientImpl(Integer.parseInt(args[1]));
            InfoClient infoClient = (InfoClient) UnicastRemoteObject.exportObject(client, 0);
            Registry registry = LocateRegistry.getRegistry(host);

            //Lookup in the RMI tregistery the chat service of the server side
            ChatService chatService = (ChatService) registry.lookup("ChatService");

            //Defining vars for the clients commands processing
            Scanner scanner = new Scanner(System.in);
            String line;
            String[] command;
            int res;

            //Looping on clients commands
            while (true) {

                line = scanner.nextLine();
                command = line.split(" ");
                //Note : guards (user has joined the group, etc, are checked on the backend part and throws exceptions)
                try {
                    switch (command[0]) {

                        case "send":
                            chatService.send(command[1], infoClient);
                            System.out.println("success: message sent");
                            break;

                        case "join":
                            chatService.join(infoClient);
                            System.out.println("success: you joined the group");
                            break;

                        case "leave":
                            chatService.leave(infoClient);
                            System.out.println("success: you left the group");
                            break;

                        case "history":
                            ArrayList<String> history = chatService.getHistory(infoClient);
                            if (history.size() != 0) {
                                for (int i = 0; i < history.size(); i++) {
                                    System.out.println(history.get(i));
                                }
                            } else {
                                System.out.println("no messages yet");
                            }
                            break;

                    }
                } catch (Exception e) {
                    System.out.println("SWITCH ERROR");
                    System.out.println(e.getMessage());
                }
            }

        } catch (Exception e) {
            System.err.println("Error on client: " + e);
        }
    }
}