package battleships;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author 17091837
 */

public class LoadWindow {

    /**
     * Displays load window at game start 
     * https://www.youtube.com/watch?v=SpL3EToqaXA
     * @param controller used to send messages to model
     */
    public void display(BSController controller) {

        Stage window = new Stage();

        // Show until closed
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Load Game from Text File?");
        window.setWidth(400);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            try {
                controller.loadBoardFromText();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LoadWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            window.close();
        });

        noButton.setOnAction(e -> {
            controller.autoLoad();
            window.close();
        }
        );

        VBox layout = new VBox(10);
        layout.getChildren().addAll(yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);

        // Show until closed
        window.showAndWait();
    }

}
