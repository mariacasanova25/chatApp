
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.nio.*;
import java.io.*;

public class ChatImpl implements ChatService {

    // HashMap<Integer, InfoClient> registrationTable;
    File users;
    File history;
    ArrayList<String> historyArray;
    FileWriter writer;
    FileWriter writerUsers;
    BufferedReader readerHistory;
    BufferedReader readerUsers;

    public ChatImpl() {
        // registrationTable= new HashMap<Integer, InfoClient>();
        historyArray = new ArrayList<String>();
        users = new File("users.txt");
        history = new File("history.txt");

        fillHistory();
    }

    private void fillHistory() {

        try {
            readerHistory = new BufferedReader(new FileReader(history));
            String line = readerHistory.readLine();
            if (line != null)
                historyArray.add(line);
            while (line != null) {
                line = readerHistory.readLine();
                historyArray.add(line);
            }
            readerHistory.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void join(InfoClient client) throws RemoteException, Exception {

        if (isUserJoined(client))
            throw new Exception("user already exists"); // user already exists

        try {
            writerUsers = new FileWriter(users, true);
            // TODO: fix \n on first join
            writerUsers.append("\n" + Integer.toString(client.getId()));
            writerUsers.close();

        } catch (IOException e) {
            System.out.println("Unable to open history");
            System.exit(-1);
        }

    }

    public void leave(InfoClient client) throws RemoteException, Exception {
        System.out.println("entrou em leave");
        if (!isUserJoined(client))
            throw new Exception("This user doesn't exist"); // user doesn't exist

        try {
            removeUser(Integer.toString(client.getId()));
            System.out.println("user removed");
        } catch (IOException e) {
            System.out.println("Unable to open history");
            System.exit(-1);
        }
    }

    public void send(String message, InfoClient sender) throws RemoteException, Exception {
        if (!isUserJoined(sender))
            throw new Exception("You are not in the chatgroup");

        try {
            writer = new FileWriter(history, true);
            String newMessage = "Sender: " + Integer.toString(sender.getId()) + " plaintext: " + message;
            writer.append("\n" + newMessage);
            historyArray.add(newMessage);
            writer.close();

        } catch (IOException e) {
            System.out.println("Unable to open history");
            System.exit(-1);
        }
    }

    // TODO:fix array null lines
    public ArrayList<String> getHistory(InfoClient client) throws RemoteException, Exception {
        if (!isUserJoined(client))
            throw new Exception("error: you're not in the group");
        return historyArray;
    }

    public boolean isUserJoined(InfoClient client) {
        String line;
        try {
            readerUsers = new BufferedReader(new FileReader(users));

            while ((line = readerUsers.readLine()) != null) {
                if (line.equals(Integer.toString(client.getId()))) {

                    System.out.println("encontrou user");
                    return true;
                }
            }
            readerUsers.close();
            return false;

        } catch (IOException e) {
            System.out.println("isuserjoin" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public void removeUser(String lineContent) throws IOException {
        File updatedUsersFile = new File("updatedUsers.txt");

        readerUsers = new BufferedReader(new FileReader(users));
        BufferedWriter w = new BufferedWriter(new FileWriter(updatedUsersFile));

        String currentLine;

        while ((currentLine = readerUsers.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if (!trimmedLine.equals(lineContent)) {
                w.write(currentLine + System.getProperty("line.separator"));
            }
        }

        w.close();
        readerUsers.close();
        updatedUsersFile.renameTo(users);
    }

}
