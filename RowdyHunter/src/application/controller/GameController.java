package application.controller;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    private int bulletCount = 3, hitcount = 0;




    public void gunshot(MouseEvent mouseEvent) {
        // -----------------------------  getting the location of the current press of the mouse! ----------------
        ArrayList<Double> locations = new ArrayList<Double>();
        locations.add(mouseEvent.getSceneX());
        locations.add(mouseEvent.getSceneY());
        System.out.println(locations.get(0) + ", " + locations.get(1));
        // -----------------------------  And im printing it out -------------------------------------------------
        Bounds boundsInScene = gamestackpane1.localToScene(gamestackpane1.getBoundsInLocal());
        if (boundsInScene.contains(locations.get(0), locations.get(1))) {
            System.out.println("it works im cool");
        }




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // ------------------------------- GAME SCREEN IMAGES BEING SET -----------------------------------------------
        try {
            gamebackground.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/nightback.png")));
            bushesimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/meadow.png")));

            //            maintitleimage2.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/titlescreentitle2-1.png")));
            //            playbuttonimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/homescreenplay.gif")));
            //            mainbackgroundimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/mainbackground.gif")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // ------------------------------- GAME SCREEN IMAGES BEING SET -----------------------------------------------


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
        sctransition.setToX(2);
        sctransition.setToY(1);
        sctransition.setCycleCount(Animation.INDEFINITE);
        sctransition.play();
        // ADDING THE STACK PANE WITH THE IMAGE VIEW TO THE THE MAIN PANE
        gamepane.getChildren().add(gamestackpane1);
        // ------------------------------- moving ufo stuff -----------------------------------------------



    }
}
