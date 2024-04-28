import java.net.*;
import java.io.*;
import java.util.*;

public class ServerSide {
    private static final int PORT = 12346;
    private static List<Member> members;
    private static List<Item> catalog;

    public static boolean updates = false;

    public static void main(String[] args) {
        members = new ArrayList<>();
        catalog = new ArrayList<>();
        MongoDbPojo.initialize();
        // Initialize catalog and members from storage

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        private BufferedReader reader;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while(true) {
                    String request = null;
                    try {
                        request = reader.readLine();
                    }
                    catch (SocketException s) {
                        clientSocket.close();
                    }
                    if (request == null) {

                    }
                    else if (request != null && request.contains("getItems")) {
                        List<Item> items = MongoDbPojo.retrieveItems(); // Implement this method to fetch items from MongoDB
                        out.writeObject(items);
                        out.flush();// Send items to client
                    }
                    else if (request != null && request.contains("checkout")) {
                        String checked = reader.readLine();
                        String user1 = reader.readLine();
                        boolean valid = MongoDbPojo.borrow(checked, user1);
                        if (valid) {
                            out.writeObject("success");
                            List<Item> items = MongoDbPojo.retrieveItems();
                            out.writeObject(items);
                            out.flush();
                            updates = true;
                        }
                        else {
                            out.writeObject("failure");
                            List<Item> items = MongoDbPojo.retrieveItems();
                            out.writeObject(items);
                            out.flush();
                            updates = true;
                        }

                    }
                    else if (request != null && request.contains("holdrequest")) {
                        String checked = reader.readLine();
                        String user1 = reader.readLine();
                        boolean valid = MongoDbPojo.hold(checked, user1);
                        if (valid) {
                            out.writeObject("success");
                            List<Item> items = MongoDbPojo.retrieveItems();
                            out.writeObject(items);
                            out.flush();
                            updates = true;
                        }
                        else {
                            out.writeObject("failure");
                            List<Item> items = MongoDbPojo.retrieveItems();
                            out.writeObject(items);
                            out.flush();
                            updates = true;
                        }

                    }
                    else if (request != null && request.contains("held")) {
                        String checked = reader.readLine();
                        String user1 = reader.readLine();
                        boolean held = MongoDbPojo.held(checked, user1);
                        if (held) {
                            out.writeObject("success");
                            out.flush();
                        }
                        else {
                            out.writeObject("failure");
                            out.flush();
                        }

                    }
                    else if (request != null && request.contains("return")) {
                        String checked = reader.readLine();
                        String user1 = reader.readLine();
                        boolean valid = MongoDbPojo.returnItem(checked, user1);
                        if (valid) {
                            out.writeObject("success");
                            List<Item> items = MongoDbPojo.retrieveItems();
                            out.writeObject(items);
                            out.flush();
                            updates = true;
                        }
                        else {
                            out.writeObject("failure");
                            out.flush();
                        }
                    }
                    else if (request != null && request.contains("updates")) {
                        out.writeObject(updates);
                        if (updates) {
                            List<Item> items = MongoDbPojo.retrieveItems();// issue here
                            out.writeObject(items);
                        }
                        out.flush();
                        updates = false;
                    }
                    else if (request != null && request.contains("init")) {
                        String user = reader.readLine();
                        List<Item> items = MongoDbPojo.retrieveUserList(user);// issue here
                        out.writeObject(items);
                        String profilePic = MongoDbPojo.retrievePfp(user);
                        out.writeObject(profilePic);
                        out.flush();
                    }
                    else if (request != null && request.contains("holdcheck")) {
                        String user = reader.readLine();
                        List<Item> items = MongoDbPojo.retrieveUserList(user);// issue here
                        out.writeObject(items);
                        out.flush();
                    }
                    else if (request != null && request.contains("newpassword")) {
                        String username = reader.readLine();
                        String password = reader.readLine();
                        Boolean changepw = MongoDbPojo.changepw(username, password);
                        if (changepw) {
                            out.writeObject("success");

                        } else {
                            out.writeObject("failure");
                        }
                        out.flush();
                    }
                    else if (request != null && request.contains("newpfp")) {
                        String username = reader.readLine();
                        String newpfp = reader.readLine();
                        Boolean newpic = MongoDbPojo.newpfp(username, newpfp);
                        if (newpic) {
                            out.writeObject("success");

                        } else {
                            out.writeObject("failure");
                        }
                        out.flush();
                    }
                    else if (request != null && request.contains("createaccount")){
                        // Read username from the client
                        String username = reader.readLine();
                        String password = reader.readLine();

                        // Authenticate the username
                        boolean isAuthenticated = MongoDbPojo.createaccount(username, password);

                        // Send authentication result back to the client
                        if (isAuthenticated) {
                            out.writeObject("success");

                        } else {
                            out.writeObject("failure");
                        }
                        out.flush();
                    }

                    else if (request != null && request.contains("login")){
                        // Read username from the client
                        String username = reader.readLine();
                        String password = reader.readLine();

                        // Authenticate the username
                        boolean isAuthenticated = MongoDbPojo.authenticate(username, password);

                        // Send authentication result back to the client
                        if (isAuthenticated) {
                            out.writeObject("success");

                        } else {
                            out.writeObject("failure");
                        }
                        out.flush();
                    }
                }



                    // Add more handlers for other types of requests as needed


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}