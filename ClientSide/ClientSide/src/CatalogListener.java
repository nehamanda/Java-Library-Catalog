import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.swing.text.Document;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
    private TextField searchbar;

    @FXML
    private ListView itemListView;

    @FXML
    private Button checkout;

    @FXML
    private Label logout;

    @FXML
    private CheckBox bookchk;

    @FXML
    private CheckBox abookchk;

    @FXML
    private CheckBox gamechk;

    @FXML
    private CheckBox moviechk;

    @FXML
    private CheckBox availablechk;

    @FXML
    private CheckBox hasitemchk;

    @FXML
    private CheckBox holdchk;

    @FXML
    private Label myaccount;

    @FXML
    private Rectangle account;

    @FXML
    private ImageView pfp;

    @FXML
    private Label accounttxt;

    @FXML
    private Label resetpassword;

    @FXML
    private Label changepfp;

    @FXML
    private TextField enternewpassword;

    @FXML
    private TextField enterimageaddress;

    @FXML
    private Button submitnewpassword;

    @FXML
    private Button submitpfp;

    @FXML
    private Label librarycatalog;



    public Socket socket;

    public ObjectOutputStream out;

    public ObjectInputStream in;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean init = false;

    public PrintWriter writer;

    public String username;

    public Member member;

    public enum Strength {
        WEAK, MEDIUM, STRONG
    }

    public static Strength checkPasswordStrength(String password) {
        int length = password.length();
        boolean hasLowerCase = !password.equals(password.toUpperCase());
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = !password.matches("[A-Za-z0-9]*");

        // Check length
        if (length < 4) {
            return Strength.WEAK;
        }

        // Check complexity
        if (!hasLowerCase || !hasUpperCase || !hasDigit || !hasSpecialChar) {
            return Strength.MEDIUM;
        }

        return Strength.STRONG;
    }

    public void initializeSocket() throws IOException {
        if (!init) {
            socket = new Socket("localhost", 12346);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream());
            init = true;
        }
    }

    public void initializeUser(String user) throws IOException, ClassNotFoundException {
        initializeSocket();
        username = user;
        member = new Member(username);
        writer.println("init");
        writer.println(username);
        writer.flush();
        List<Item> items = (List<Item>) in.readObject();
        String imageLink = (String) in.readObject();
        member.borrowedItems = items;
        Image image = new Image(imageLink);
        pfp.setImage(image);
    }




    public void initialize(URL url, ResourceBundle resource) {
        try {
            initializeSocket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] choices = {"Title", "Author"};
        search.getItems().addAll(choices);
        search.setStyle("-fx-font: 14px \"Droid Sans\";");

        try  {
            writer.println("getItems");
            writer.flush();


            List<Item> items = (List<Item>) in.readObject();
            ObservableList<Item> observableList = FXCollections.observableArrayList(items);
            itemListView.setItems(observableList);
            itemListView.setCellFactory(itemListView -> new ItemListCell());

        } catch (Exception e) {
            e.printStackTrace();
        }

        itemListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Handle the selection change
                Item selected = (Item) newValue;
//                writer.println("updates");
//                writer.flush();
//                boolean updated;
//                try {
//                    updated = (boolean) in.readObject();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                } catch (ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//                if (updated) {
//                    List<Item> items = null;
//                    try {
//                        items = (List<Item>) in.readObject();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    } catch (ClassNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
//                    ObservableList<Item> observableList = FXCollections.observableArrayList(items);
//                    itemListView.setItems(observableList);
//                    itemListView.setCellFactory(itemListView -> new ItemListCell());
//                }
                if (member.hasItem(selected)) {
                    checkout.setText("Return Item");
                }
                else if (selected.isAvailable()) {
                    checkout.setText("Check Out Item");
                }
                else {
                    checkout.setText("Put Item on Hold");
                }

            }
        });

    }

    public void resetPassword() throws IOException, ClassNotFoundException {
        String newpw = enternewpassword.getText();
        writer.println("newpassword");
        writer.println(username);
        writer.println(newpw);
        writer.flush();
        String response = (String) in.readObject();
        if (response.equals("success")) {
            showAlert("Password changed!");
        }
        else {
            showAlert("Password change failed.");
        }

    }

    public void resetPfp() throws IOException, ClassNotFoundException {
        String newpfp = enterimageaddress.getText();
        Image image = new Image(newpfp);
        if (image == null) {
            showAlert("Profile picture change failed. Choose another image.");
        }
        else {
            writer.println("newpfp");
            writer.println(username);
            writer.println(newpfp);
            writer.flush();
            String response = (String) in.readObject();
            if (response.equals("success")) {
                showAlert("Profile picture changed!");
                pfp.setImage(image);
            }
            else {
                showAlert("Profile picture change failed.");
            }
        }
    }

    public void logoutClicked() throws IOException {
        socket.close();
        Parent root = FXMLLoader.load(getClass().getResource("FrontEnd.fxml"));
        Stage stage = (Stage) logout.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void myAccountClicked() throws IOException, ClassNotFoundException {
        if (myaccount.getText().equals("My Account")) {
            account.setDisable(false);
            account.setVisible(true);
            myaccount.setText("← Go back");
            librarycatalog.setText("My Borrowed Items");
            checkout.setVisible(false);
            checkout.setDisable(true);
            accounttxt.setVisible(true);
            resetpassword.setVisible(true);
            changepfp.setVisible(true);
            enternewpassword.setVisible(true);
            enternewpassword.setDisable(false);
            enterimageaddress.setVisible(true);
            enterimageaddress.setDisable(false);
            submitnewpassword.setVisible(true);
            submitnewpassword.setDisable(false);
            submitpfp.setVisible(true);
            submitpfp.setDisable(false);

            writer.println("getItems");
            writer.flush();
            List<Item> items = (List<Item>) in.readObject();
            ObservableList<Item> catalogItems = FXCollections.observableArrayList(items);
            ObservableList<Item> filteredItems = FXCollections.observableArrayList();

            for (Item item : catalogItems) {
                if (member.hasItem(item)) {
                    filteredItems.add(item);
                }
            }
            itemListView.setItems(filteredItems);
            itemListView.setCellFactory(itemListView -> new ItemListCell());
        }
        else {
            account.setDisable(true);
            account.setVisible(false);
            myaccount.setText("My Account");
            librarycatalog.setText("Library Catalog");
            checkout.setVisible(true);
            checkout.setDisable(false);
            accounttxt.setVisible(false);
            resetpassword.setVisible(false);
            changepfp.setVisible(false);
            enternewpassword.setVisible(false);
            enternewpassword.setDisable(true);
            enterimageaddress.setVisible(false);
            enterimageaddress.setDisable(true);
            submitnewpassword.setVisible(false);
            submitnewpassword.setDisable(true);
            submitpfp.setVisible(false);
            submitpfp.setDisable(true);

            writer.println("getItems");
            writer.flush();
            List<Item> items = (List<Item>) in.readObject();
            ObservableList<Item> catalogItems = FXCollections.observableArrayList(items);
            itemListView.setItems(catalogItems);
            itemListView.setCellFactory(itemListView -> new ItemListCell());
        }

    }

    public void filterItems() throws IOException, ClassNotFoundException {
        writer.println("getItems");
        writer.flush();
        List<Item> items = (List<Item>) in.readObject();
        ObservableList<Item> catalogItems = FXCollections.observableArrayList(items);
        ObservableList<Item> filteredItems = FXCollections.observableArrayList();


        for (Item item : catalogItems) {
            if ((bookchk.isSelected() && item instanceof Book) ||
                    (abookchk.isSelected() && item instanceof Audiobook) ||
                    (gamechk.isSelected() && item instanceof Game) ||
                    (moviechk.isSelected() && item instanceof Movie)) {
                if ((availablechk.isSelected() && !item.isAvailable()) ||
                        (hasitemchk.isSelected() && !member.hasItem(item))) // ADD HOLD BOX CHECK HERE
                    {
                        if ((availablechk.isSelected() && !item.isAvailable()) &&
                                (hasitemchk.isSelected() && member.hasItem(item))) {
                            filteredItems.add(item);
                        }
                        else if ((availablechk.isSelected() && item.isAvailable()) &&
                                (hasitemchk.isSelected() && !member.hasItem(item))) {
                            filteredItems.add(item);
                        }
                }
                else {
                    filteredItems.add(item);
                }

            }
            else if (!abookchk.isSelected() && !bookchk.isSelected()
                    && !gamechk.isSelected() && !moviechk.isSelected() &&
                    ((availablechk.isSelected() && item.isAvailable()) ||
                    (hasitemchk.isSelected() && member.hasItem(item)))) {
                filteredItems.add(item);
            }
        }

        itemListView.setItems(filteredItems);
        itemListView.setCellFactory(itemListView -> new ItemListCell());
    }

    public void search() throws IOException, ClassNotFoundException {
        String criteria = (String) search.getValue();
        String val = searchbar.getText();
        writer.println("getItems");
        writer.flush();
        List<Item> items = (List<Item>) in.readObject();
        ObservableList<Item> catalogItems = FXCollections.observableArrayList(items);
        ObservableList<Item> filteredItems = FXCollections.observableArrayList();
        if (criteria.equals("Title")) {
            for (Item item : catalogItems) {
                if (item.getTitle().contains(val)) {
                    filteredItems.add(item);
                }
            }
        }
        else if (criteria.equals("Author")) {
            for (Item item : catalogItems) {
                if (item instanceof Book) {
                    if (((Book) item).getAuthor().contains(val)) {
                        filteredItems.add(item);
                    }
                }
                else if (item instanceof Audiobook) {
                    if (((Audiobook) item).getAuthor().contains(val)) {
                        filteredItems.add(item);
                    }
                }
            }
        }
        itemListView.setItems(filteredItems);
        itemListView.setCellFactory(itemListView -> new ItemListCell());
    }

    public void resetFilters() throws IOException, ClassNotFoundException {
        writer.println("getItems");
        writer.flush();
        List<Item> items = (List<Item>) in.readObject();
        ObservableList<Item> catalogItems = FXCollections.observableArrayList(items);
        itemListView.setItems(catalogItems);
        itemListView.setCellFactory(itemListView -> new ItemListCell());

    }

    public void checkOut() throws IOException, ClassNotFoundException {
        initializeSocket();
        if (checkout.getText().equals("Check Out Item")) {
            Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
            writer.println("checkout");
            writer.println(selectedItem.getTitle());
            writer.println(username);
            writer.flush();

            String response = (String) in.readObject();
            if (response.equals("success")) {
                showAlert("Checkout successful!");
                member.borrowItem(selectedItem);
                checkout.setText("Return Item");
                List<Item> items = (List<Item>) in.readObject();
                ObservableList<Item> observableList = FXCollections.observableArrayList(items);
                itemListView.setItems(observableList);
                itemListView.setCellFactory(itemListView -> new ItemListCell());

                // Update UI or perform other actions
            } else {
                showAlert("Checkout failed. Item may be unavailable or already checked out.");
            }
        }
        else if (checkout.getText().equals("Return Item")) {
            Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
            writer.println("return");
            writer.println(selectedItem.getTitle());
            writer.println(username);
            writer.flush();
            String response = (String) in.readObject();
            if (response.equals("success")) {
                showAlert("Return successful!");
                member.returnItem(selectedItem);
                checkout.setText("Check Out Item");
                List<Item> items = (List<Item>) in.readObject();
                ObservableList<Item> observableList = FXCollections.observableArrayList(items);
                itemListView.setItems(observableList);
                itemListView.setCellFactory(itemListView -> new ItemListCell());

                // Update UI or perform other actions
            } else {
                showAlert("Return failed.");
            }

        }
        else if (checkout.getText().equals("Put Item on Hold")) {

        }


    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

}