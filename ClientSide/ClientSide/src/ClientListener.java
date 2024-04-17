import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientListener {
    @FXML
    private TextField usernameField;

    public void loginButtonClicked(ActionEvent event) {
        String enteredUsername = usernameField.getText().trim();
        if (!enteredUsername.isEmpty()) {
            try (Socket socket = new Socket("localhost", 12345);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                // Send the username to the server
                out.writeObject(enteredUsername);
                out.flush();

                // Receive response from the server
                String response = (String) in.readObject();

                // Check if login was successful
                if (response.equals("success")) {
                    showAlert("Login successful!");
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
}