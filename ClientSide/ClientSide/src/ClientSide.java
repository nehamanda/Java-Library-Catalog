
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
    static Stage primaryStage;
    private static Scene scene1;
    private static Parent root2;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("FrontEnd.fxml"));
        Scene scene = new Scene(fxml.load());
        ClientListener listener = fxml.getController();
        listener.initializeSocket();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Library Client");

        //FXMLLoader loader2 = new FXMLLoader(getClass().getResource("LibraryCatalog.fxml"));
        //CatalogListener catalog = loader2.getController();
        //catalog.initializeSocket();
        //root2 = loader2.load();

        primaryStage.show();
        this.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }


}