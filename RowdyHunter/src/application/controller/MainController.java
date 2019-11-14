package application.controller;

import application.Main;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static String username;
    @FXML
    private TextField usernameTF;

    @FXML
    private ImageView maintitleimage1, maintitleimage2, alienimagemain, playbuttonimage, mainbackgroundimage;

    @FXML
    private Pane mainpane;

    @FXML
    private StackPane stackpane1;

    @FXML
    private Button playbutton;

    public static String getUsername() {
        return username;
    }

    @FXML
    private void handle(ActionEvent event) throws IOException {
        // play button is pushed
        username = usernameTF.getText();
        if (event.getSource() == playbutton && !username.isEmpty()) {
            Parent root = FXMLLoader.load(getClass().getResource("../view/GameScreen.fxml"));
            Main.tmpstage.setScene(new Scene(root, 900, 600));
            Main.tmpstage.show();
            Main.tmpstage.setResizable(false);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // ------------------------------- moving ufo stuff -----------------------------------------------
        try {
            stackpane1 = new StackPane(new ImageView(new Image(new FileInputStream("RowdyHunter/resources/images/ufo-1.gif"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        stackpane1.setLayoutX(-100);
        stackpane1.setLayoutY(80);
        // DIFFERENT TYPES OF TRANSITIONS
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(2));
        transition.setToX(850);
        transition.setToY(85);
        transition.setToZ(70);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setNode(stackpane1);   // <--- SETTING THE OBJECT TO TRANSITION
        transition.play();
        ScaleTransition sctransition = new ScaleTransition(Duration.seconds(5), stackpane1);
        sctransition.setToX(.4);
        sctransition.setToY(.4);
        sctransition.setToZ(.1);
        sctransition.setAutoReverse(true);
        sctransition.setCycleCount(Animation.INDEFINITE);
        sctransition.play();
        // ADDING THE STACK PANE WITH THE IMAGE VIEW TO THE THE MAIN PANE
        mainpane.getChildren().add(stackpane1);
        // ------------------------------- moving ufo stuff -----------------------------------------------

        // ------------------------------- MAIN SCREEN IMAGES BEING SET -----------------------------------------------
        try {
            alienimagemain.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/alien.gif")));
            maintitleimage1.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/titlescreentitle2.png")));
            maintitleimage2.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/titlescreentitle2-1.png")));
            playbuttonimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/homescreenplay.gif")));
            mainbackgroundimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/mainbackground.gif")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // ------------------------------- MAIN SCREEN IMAGES BEING SET -----------------------------------------------
    }
}
