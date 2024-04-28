import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;


public class ClientListener {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ChoiceBox search;

    @FXML
    private ListView itemListView;


    public Socket socket;

    public ObjectOutputStream out;

    public ObjectInputStream in;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String user;

    private boolean init = false;

    private PrintWriter writer;

    public void initializeSocket() throws IOException {
        if (!init) {
            socket = new Socket("localhost", 12346);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream());
            init = true;
        }


    }



    public void loginButtonClicked(ActionEvent event) throws IOException {
        initializeSocket();
        String enteredUsername = usernameField.getText();
        user = enteredUsername;
        String enteredPassword = passwordField.getText();
        if (!enteredUsername.isEmpty() && !enteredPassword.isEmpty()) {
            try {
                // Send the username to the server
                writer.println("login");
                writer.println(enteredUsername);
                writer.println(enteredPassword);
                writer.flush();

                // Receive response from the server
                String response = (String) in.readObject();

                // Check if login was successful
                if (response.equals("success")) {
                    Thread thread = new Thread(() -> {
                        showAlert("Login successful!");
                    });
                    thread.start();
                    socket.close();
                    Platform.runLater(() -> {
                        try {
                            switchToCatalog(event);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });


                    // Proceed to next screen or perform other actions
                } else {
                    showAlert("Login failed. Please try again.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error connecting to server.");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("Please enter a username.");
        }
    }

    public void createAccount(ActionEvent event) throws IOException {
        initializeSocket();
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();
        user = enteredUsername;
        if (!enteredUsername.isEmpty() && !enteredPassword.isEmpty()) {
            try {
                // Send the username to the server
                writer.println("createaccount");
                writer.println(enteredUsername);
                writer.println(enteredPassword);
                writer.flush();

                // Receive response from the server
                String response = (String) in.readObject();

                // Check if login was successful
                if (response.equals("success")) {
                    showAlert("Account created successfully!");
                    socket.close();
                    switchToCatalog(event);

                    // Proceed to next screen or perform other actions
                } else {
                    showAlert("Username taken. Please try again.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error connecting to server.");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("Please enter a username.");
        }
    }

    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(message);
            alert.showAndWait();
        });

    }

    public void switchToCatalog(ActionEvent event) throws IOException, ClassNotFoundException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("LibraryCatalog.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxml.load());
        CatalogListener listener = fxml.getController();
        listener.initializeUser(user);
        stage.setScene(scene);
        stage.show();
    }
}