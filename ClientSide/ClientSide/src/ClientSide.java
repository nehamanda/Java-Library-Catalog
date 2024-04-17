import javafx.application.Application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSide extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FrontEnd.fxml"));
        primaryStage.setScene(new Scene(root, 761, 615));
        primaryStage.setTitle("Library Client");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static boolean login(String username) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Send the username to the server
            out.writeObject(username);
            out.flush();

            // Receive response from the server
            String response = (String) in.readObject();

            // Check if login was successful
            return response.equals("success");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}