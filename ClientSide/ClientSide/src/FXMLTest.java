import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.api.FxToolkit;

import java.util.concurrent.TimeUnit;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
public class FXMLTest extends ApplicationTest{

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("FrontEnd.fxml"));
        Scene scene = new Scene(fxml.load());
        ClientListener listener = fxml.getController();
        listener.initializeSocket();
        stage.setScene(scene);
        stage.setTitle("Library Client");
        stage.show();

    }

    @Test
    public void testButtonClick() {
        // Example TestFX interaction and assertion
        clickOn("#usernameField").write("neha");
        clickOn("#passwordField").write("0818");
        clickOn("#loginButton");
        try {
            TimeUnit.SECONDS.sleep(6); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }

        // Verify alert appears
        verifyThat("Login successful!", isVisible());
        clickOn("OK");
        try {
            TimeUnit.SECONDS.sleep(1); // Adjust delay time as needed
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
        //verifyThat("Login successful!", isNull());

    }
}
