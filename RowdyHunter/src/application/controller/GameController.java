package application.controller;

import application.Main;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {


    @FXML
    private AnchorPane gamepane;

    @FXML
    private ImageView gamebackground, bushesimage, groundimage, treeimage, ufoimage;

    @FXML
    private StackPane gamestackpane1;

    private int bulletCount = 3, hitcount = 0, reloads = 0, score = 0;


    public void gunshot(MouseEvent mouseEvent) throws IOException {

        // -----------------------------  getting the location of the current press of the mouse! ----------------
        ArrayList<Double> locations = new ArrayList<Double>();
        locations.add(mouseEvent.getSceneX());
        locations.add(mouseEvent.getSceneY());
        // -----------------------------  And im checking if the pane is clicked on -------------------------------------------------
        Bounds boundsInScene = gamestackpane1.localToScene(gamestackpane1.getBoundsInLocal());
        if (boundsInScene.contains(locations.get(0), locations.get(1))) {
            System.out.println("HIT AT :" + locations.get(0) + ", " + locations.get(1));
            score += 1;
            System.out.println("CURRENT SCORE:" + score);
        }
        // -----------------------------  And im printing out the location (x,y) if true just so you can see the logic -------------------------------------------------


        // ------------------------------------- logic conditions -------------------------------------------------


        // -------------------- keeping track of the bullets and the number of reloads ----------------------------
        bulletCount--;
        System.out.println("BULLETS:" + bulletCount);
        // update bullets display like in project 4
        if (bulletCount == 0) {

            reloads += 1;
            // update bullets on display
            System.out.println("Reload #:" + reloads);
            System.out.println("Reloading....");

            if (reloads >= 5) {
                System.out.println("Game over condition.... whatever you want it to be .... i chose after 5 reloads its game over");
                changeScene();
            } else {
                bulletCount = 3;
            }


        }

    }

    /**
     * Name: changeScene()
     * Description: changes the scene from zone to main
     *
     * @throws IOException
     */
    private void changeScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
        Main.tmpstage.setScene(new Scene(root, 900, 600));
        Main.tmpstage.show();
        Main.tmpstage.setResizable(false);
    }


    private void startUFO() {

        // ------------------------------- moving ufo stuff -----------------------------------------------
        try {
            gamestackpane1 = new StackPane(new ImageView(new Image(new FileInputStream("RowdyHunter/resources/images/ufo-1.gif"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // DIFFERENT TYPES OF TRANSITIONS
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(2));
        transition.setToX(500);
        transition.setToY(500);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setNode(gamestackpane1);   // <--- SETTING THE OBJECT TO TRANSITION
        transition.play();
        ScaleTransition sctransition = new ScaleTransition(Duration.seconds(5), gamestackpane1);
        sctransition.setToX(.3);
        sctransition.setToY(.4);
        sctransition.setCycleCount(Animation.INDEFINITE);
        sctransition.play();
        // ADDING THE STACK PANE WITH THE IMAGE VIEW TO THE THE MAIN PANE
        gamepane.getChildren().add(gamestackpane1);
        // ------------------------------- moving ufo stuff -----------------------------------------------

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // ------------------------------- GAME SCREEN IMAGES BEING SET -----------------------------------------------
        try {
            gamebackground.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/nightback.png")));
            bushesimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/meadow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // ------------------------------- GAME SCREEN IMAGES BEING SET -----------------------------------------------

        startUFO();

    }
}
