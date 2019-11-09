package application.controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

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


    public void gunshot(MouseEvent mouseEvent) {
        // -----------------------------  getting the location of the current press of the mouse! ----------------
        ArrayList<Double> locations = new ArrayList<Double>();
        locations.add(mouseEvent.getSceneX());
        locations.add(mouseEvent.getSceneY());
        System.out.println(locations.get(0) + ", " + locations.get(1));
        // -----------------------------  And im printing it out -------------------------------------------------
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


    }
}
