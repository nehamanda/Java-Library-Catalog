import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CatalogListener implements Initializable {
    @FXML
    private TextField usernameField;

    @FXML
    private ChoiceBox search;

    @FXML
    private ListView itemListView;




    public void initialize(URL url, ResourceBundle resource) {
        String[] choices = {"Title", "Author"};
        search.getItems().addAll(choices);
        search.setStyle("-fx-font: 14px \"Droid Sans\";");
        /*ObservableList<Item> items = FXCollections.observableArrayList(
                new Item("Book", "Anxious People", "Fredrik Backman", "Description 1", "Images/anxious people.jpg"),
                new Item("Book", "The Poppy War", "R. F. Kuang", "Description 2", "Images/the poppy war.jpg"),
                new Item("DVD", "Wonder", "R.J. Palacio", "Description 3", "Images/Wonder_Cover_Art.png")
        );*/
        try (ObjectOutputStream out = new ObjectOutputStream(ClientListener.socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(ClientListener.socket.getInputStream())) {

            // Send request to server
            out.writeObject("getItems");

            // Receive items from server
            List<Item> items = (List<Item>) in.readObject();
            ObservableList<Item> observableList = FXCollections.observableArrayList(items);
            itemListView.setItems(observableList);
            itemListView.setCellFactory(itemListView -> new ItemListCell());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}