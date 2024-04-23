import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientListener {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String user;

    public static Socket socket;

    public void loginButtonClicked(ActionEvent event) {
        String enteredUsername = usernameField.getText();
        user = enteredUsername;
        String enteredPassword = passwordField.getText();
        if (!enteredUsername.isEmpty() && !enteredPassword.isEmpty()) {
            try (Socket socket = new Socket("localhost", 12345);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                this.socket = socket;

                // Send the username to the server
                out.writeObject(enteredUsername);
                out.writeObject(enteredPassword);
                out.flush();

                // Receive response from the server
                String response = (String) in.readObject();

                // Check if login was successful
                if (response.equals("success")) {
                    showAlert("Login successful!");
                    switchToCatalog(event);

                    // Proceed to next screen or perform other actions
                } else {
                    showAlert("Login failed. Please try again.");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                showAlert("Error connecting to server.");
            }
        } else {
            showAlert("Please enter a username.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void switchToCatalog(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LibraryCatalog.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FrontEnd.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}