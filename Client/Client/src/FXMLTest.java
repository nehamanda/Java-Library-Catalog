import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
public class FXMLTest extends ApplicationTest{

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("Resources/FrontEnd.fxml"));
        Scene scene = new Scene(fxml.load());
        ClientListener listener = fxml.getController();
        listener.initializeSocket();
        stage.setScene(scene);
        stage.setTitle("Library Client");
        stage.show();

    }

    @Test
    public void loginFiltersSearch() {
        // Example TestFX interaction and assertion
        clickOn("#usernameField").write("neha");
        clickOn("#passwordField").write("0818");
        clickOn("#loginButton");
        try {
            TimeUnit.SECONDS.sleep(5); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }

        // Verify alert appears
        verifyThat("Login successful!", isVisible());
        closeCurrentWindow();
        try {
            TimeUnit.SECONDS.sleep(1); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        clickOn("#search").clickOn("Title");
        clickOn("#searchbar").write("Wonder");
        clickOn("#searchgo");
        try {
            TimeUnit.SECONDS.sleep(1); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        ListView<Item> listView = lookup("#itemListView").query();
        List<Item> items = listView.getItems();

        // Verify that the ListView has only one item
        Assertions.assertEquals(1, listView.getItems().size());
        clickOn("#search").clickOn("Author");
        clickOn("#searchbar").eraseText(6).write("Palacio");
        clickOn("#searchgo");

        try {
            TimeUnit.SECONDS.sleep(1); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        ListView<Item> listView2 = lookup("#itemListView").query();
        Assertions.assertEquals(items, listView2.getItems());

        Assertions.assertEquals(1, listView.getItems().size());
        clickOn("#search").clickOn("Author");
        clickOn("#searchbar").eraseText(7).write("Kuang");
        clickOn("#searchgo");

        try {
            TimeUnit.SECONDS.sleep(1); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        ListView<Item> listViewNext = lookup("#itemListView").query();
        Assertions.assertNotEquals(items, listViewNext.getItems());
        Assertions.assertEquals(1, listViewNext.getItems().size());



        clickOn("#bookchk");
        clickOn("#filter");
        try {
            TimeUnit.SECONDS.sleep(1); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        ListView<Item> listView3 = lookup("#itemListView").query();
        Assertions.assertEquals(2, listView3.getItems().size());

        clickOn("#resetFilters");
        try {
            TimeUnit.SECONDS.sleep(2); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        ListView<Item> listView4 = lookup("#itemListView").query();
        Assertions.assertEquals(5, listView4.getItems().size());
    }

    @Test
    public void createAccountFail() {
        clickOn("#usernameField").write("neha");
        clickOn("#passwordField").write("0818");
        clickOn("#createacct");
        try {
            TimeUnit.SECONDS.sleep(2); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        // Verify alert appears
        verifyThat("Username taken. Please try again.", isVisible());
    }


    public void createAccountSuccess() {
        clickOn("#usernameField").write("newuser");
        clickOn("#passwordField").write("0818");
        clickOn("#createacct");
        try {
            TimeUnit.SECONDS.sleep(2); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        // Verify alert appears
        verifyThat("Account created successfully!", isVisible());
    }

    @Test
    public void checkout() {
        clickOn("#usernameField").write("neha");
        clickOn("#passwordField").write("0818");
        clickOn("#loginButton");
        try {
            TimeUnit.SECONDS.sleep(4); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }

        // Verify alert appears
        verifyThat("Login successful!", isVisible());
        closeCurrentWindow();
        clickOn("#itemListView");
        verifyThat("Check Out Item", isVisible());
        clickOn("#checkout");
        try {
            TimeUnit.SECONDS.sleep(1); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        closeCurrentWindow();
        try {
            TimeUnit.SECONDS.sleep(1); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }

        clickOn("My Account");
        try {
            TimeUnit.SECONDS.sleep(2); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        ListView<Item> listView = lookup("#itemListView").query();
        List<Item> items = listView.getItems();
        Assertions.assertNotEquals(0, items.size());
        clickOn("#myaccount");

        try {
            TimeUnit.SECONDS.sleep(2); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }


        clickOn("#itemListView");
        verifyThat("Return Item", isVisible());
        clickOn("#checkout");
        try {
            TimeUnit.SECONDS.sleep(1); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        closeCurrentWindow();
        try {
            TimeUnit.SECONDS.sleep(2); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        verifyThat("Check Out Item", isVisible());
        clickOn("Log out");
        try {
            TimeUnit.SECONDS.sleep(1); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        verifyThat("Manda Public Library", isVisible());

    }

}
