import java.net.*;
import java.io.*;
import java.util.*;

public class ServerSide {
    private static final int PORT = 12345;
    private static List<Member> members;
    private static List<Item> catalog;

    public static void main(String[] args) {
        members = new ArrayList<>();
        catalog = new ArrayList<>();
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

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());

                // Read username from the client
                String username = (String) in.readObject();
                String password = (String) in.readObject();

                // Authenticate the username
                boolean isAuthenticated = authenticate(username, password);

                // Send authentication result back to the client
                if (isAuthenticated) {
                    out.writeObject("success");
                } else {
                    out.writeObject("failure");
                }
                out.flush();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean authenticate(String username, String password) {
        // Replace this with your actual authentication logic (e.g., check against a database)
        // For demonstration purposes, always return true
        return true;
    }
}