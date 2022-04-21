/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships;

import java.util.Observable;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author 17091837
 */

public class BSView extends Application implements Observer {

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;

    private BSModel model;
    private BSController controller;

    private final Label triesLabel = new Label();
    private final Label shipDownLabel = new Label();

    @Override
    public void start(Stage primaryStage) {

        model = new BSModel();
        controller = new BSController(model);
        LoadWindow loadBox = new LoadWindow();

        primaryStage.setTitle("Battleships");

        PaneOrganizer orginizer = new PaneOrganizer(controller, model);
        Scene scene = new Scene(orginizer.getRoot());

        primaryStage.setScene(scene);

        loadBox.display(controller);

        primaryStage.show();

        model.addObserver(this);
        controller.initialize();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
        shipDownLabel.setText("");
        triesLabel.setText("Tries: " + model.getTries().toString());

        if (model.getMessage() != null) {
            triesLabel.setText(model.getMessage());
            model.clearMessage();
        }
        if (model.getDownedShip() != null) {
            shipDownLabel.setText("Ship " + model.getDownedShip().getLength() + " is down!");
            model.clearDownedShip();
        }
        if (model.hasWon()) {
            triesLabel.setText("You Win! " + "Tries: " + model.getTries().toString());
        }
    }

    private class PaneOrganizer {

        private final BSModel model;
        private final BSController controller;
        private final BorderPane main;

        public PaneOrganizer(BSController controller, BSModel model) {
            this.controller = controller;
            this.model = model;

            main = new BorderPane();
            main.setStyle("-fx-background-color: grey;");
            main.setPrefHeight(WINDOW_WIDTH);
            main.setPrefWidth(WINDOW_HEIGHT);

            HBox box = new HBox(5.0);
            box.getChildren().addAll(triesLabel, shipDownLabel);

            main.setBottom(box);

            createGrid();
            
            assert (model.invariant() == true) : "game invariant must be maintained";
        }

        /**
         * Creates a 10x10 of interactive buttons for the GUI 
         * https://www.youtube.com/watch?v=EuXlTc2nlOY
         */
        private void createGrid() {
            Image imgH = new Image("other/h.png");
            Image imgM = new Image("other/m.png");

            GridPane playerGrid = new GridPane();

            for (int i = 0; i < 10; i++) {
                playerGrid.getRowConstraints().add(new RowConstraints(50));
                playerGrid.getColumnConstraints().add(new ColumnConstraints(50));
            }
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    Button guessButton = new Button();

                    guessButton.setPrefHeight(50);
                    guessButton.setPrefWidth(50);

                    GridPane.setConstraints(guessButton, y, x);
                    playerGrid.getChildren().add(guessButton);

                    ImageView viewH = new ImageView(imgH);
                    viewH.setFitHeight(35);
                    viewH.setFitWidth(30);

                    ImageView viewM = new ImageView(imgM);
                    viewM.setFitHeight(27);
                    viewM.setFitWidth(27);

                    final int xPos = x;
                    final int yPos = y;

                    guessButton.setOnAction((ActionEvent args) -> {
                        guessShot(guessButton, xPos, yPos, viewH, viewM);
                    });
                }
            }

            main.setCenter(playerGrid);
        }

        private void guessShot(Button guessButton, int x, int y, ImageView viewH, ImageView viewM) {

            controller.guessShot(x, y);

            if (model.hasHit(x, y) == true) {
                guessButton.setStyle("-fx-background-color: red;");
                guessButton.setGraphic(viewH);

            } else if (model.hasHit(x, y) == false) {
                {
                    guessButton.setGraphic(viewM);
                }
            }
            guessButton.setDisable(true);
        }

        private Pane getRoot() {
            return main;
        }

    }

}
