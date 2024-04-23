import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class CatalogListener implements Initializable {
    @FXML
    private TextField usernameField;

    @FXML
    private ChoiceBox search;

    public void initialize(URL url, ResourceBundle resource) {
        String[] choices = {"Title", "Author"};
        search.getItems().addAll(choices);
        search.setStyle("-fx-font: 14px \"Droid Sans\";");
    }


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
                    Stage primaryStage = new Stage();
                    Stage stage = (Stage) primaryStage.getScene().getWindow();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("LibraryCatalog.fxml"));
                        Parent root = loader.load();
                        stage.setTitle("Library Catalog");
                        stage.setScene(new Scene(root, 600, 400));
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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