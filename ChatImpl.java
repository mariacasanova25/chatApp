
import java.rmi.*;
import java.util.ArrayList;
import java.io.*;

public class ChatImpl implements ChatService {

    private File users; // Store users registered in the group(Since there is only one, there is no need
    // to store couples (user/groups)
    private File history;
    private ArrayList<String> historyArray; // Object sent to the client (instead of passing a file)
    private FileWriter writer;
    private FileWriter writerUsers;
    private BufferedReader readerHistory;
    private BufferedReader readerUsers;

    public ChatImpl() {
        historyArray = new ArrayList<String>();
        users = new File("users.txt");
        history = new File("history.txt");
        fillHistory(); // Loading message history from previous session (percistency)
    }

    /*
     * Fill the history with the content of the history file
     */
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

    /*
     * Register the user inside groupe (write in user.txt)
     */
    public void join(InfoClient client) throws RemoteException, Exception {

        if (isUserJoined(client))
            throw new Exception("user already exists"); // user already exists

        try {
            writerUsers = new FileWriter(users, true);
            writerUsers.append("\n" + Integer.toString(client.getId()));
            writerUsers.close();

        } catch (IOException e) {
            System.out.println("Unable to open history");
            System.exit(-1);
        }

    }

    /*
     * Unregister the user of the group (delete inside user.txt)
     */
    public void leave(InfoClient client) throws RemoteException, Exception {
        if (!isUserJoined(client))
            throw new Exception("This user doesn't exist"); // user doesn't exist

        try {
            removeUser(Integer.toString(client.getId()));
        } catch (IOException e) {
            System.out.println("Unable to open history");
            System.exit(-1);
        }
    }

    /*
     * Send a message (add it both to history.txt and to the list)
     */
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

    public ArrayList<String> getHistory(InfoClient client) throws RemoteException, Exception {
        if (!isUserJoined(client))
            throw new Exception("error: you're not in the group");
        return historyArray;
    }

    /*
     * Return true if the used is inside the group (inside user.txt)
     */
    public boolean isUserJoined(InfoClient client) {
        String line;
        try {
            readerUsers = new BufferedReader(new FileReader(users));

            while ((line = readerUsers.readLine()) != null) {
                if (line.equals(Integer.toString(client.getId()))) {
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

    /*
     * Delete the specified user from the group (users.txt)
     */
    public void removeUser(String lineContent) throws IOException {
        File updatedUsersFile = new File("updatedUsers.txt");

        readerUsers = new BufferedReader(new FileReader(users));
        BufferedWriter w = new BufferedWriter(new FileWriter(updatedUsersFile));

        String currentLine;

        while ((currentLine = readerUsers.readLine()) != null) {

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
