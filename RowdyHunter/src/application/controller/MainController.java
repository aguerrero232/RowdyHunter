/**
 * MainController.java
 * 
 * @Author Ariel Guerrero
 */

package application.controller;

import application.Main;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    /**
	 * The player's name
	 */
    private static String username;
    /**
     * Text field for name entry
     */
    @FXML
    private TextField usernameTF;
    /**
     * ImageViews for holding title screen graphics
     */
    @FXML
    private ImageView maintitleimage1, maintitleimage2, alienimagemain, playbuttonimage, mainbackgroundimage, textBubbleIV;
    /**
     * Core pane for attaching assets
     */
    @FXML
    private Pane mainpane;
    /**
     * Secondary asset container
     */
    @FXML
    private StackPane stackpane1;
    /**
     * Button for user to begin game
     */
    @FXML
    private Button playbutton;
    /**
     * Allows for access of the player's user name
     * @return
     */
    public static String getUsername() {
        return username;
    }
    /**
     * Action handler for play button
     * @param event
     * @throws IOException
     */
    @FXML
    private void handle(ActionEvent event) throws IOException {
        // play button is pushed
        username = usernameTF.getText();
        if (event.getSource() == playbutton && !username.isEmpty()) {
            Parent root = FXMLLoader.load(getClass().getResource("/application/view/GameScreen.fxml"));
            Main.tmpstage.setScene(new Scene(root, 900, 600));
            Main.tmpstage.show();
            Main.tmpstage.setResizable(false);
        }

    }
    /**
     * Launch instructions screen when text bubble clicked
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    private void handleMouse(MouseEvent mouseEvent) throws IOException {
        int x = (int) mouseEvent.getSceneX(), y = (int) mouseEvent.getSceneY();
        Bounds boundsInScene = textBubbleIV.localToScene(textBubbleIV.getBoundsInLocal());
        if (boundsInScene.contains(x, y)) {
            Parent root = FXMLLoader.load(getClass().getResource("/application/view/Instructions.fxml"));
            Main.tmpstage.setScene(new Scene(root, 900, 600));
            Main.tmpstage.show();
            Main.tmpstage.setResizable(false);
        }
    }
    /**
     * Set menu screen with images and animations
     */
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
        transition.setNode(stackpane1);   // <--- SETTING THE NODE TO TRANSITION
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
            textBubbleIV.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/help-pixel-speech-bubble.gif")));
            alienimagemain.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/alien.gif")));
            maintitleimage1.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/titlescreentitle2.png")));
            maintitleimage2.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/titlescreentitle2-1.png")));
            playbuttonimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/homescreenplay.gif")));
            mainbackgroundimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/newmain.gif")));
            mainbackgroundimage.fitWidthProperty().bind(mainpane.widthProperty());
            mainbackgroundimage.fitHeightProperty().bind(mainpane.heightProperty());
        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // ------------------------------- MAIN SCREEN IMAGES BEING SET -----------------------------------------------


        sctransition = new ScaleTransition(Duration.seconds(2), textBubbleIV);
        sctransition.setAutoReverse(true);
        sctransition.setToX(1.2);
        sctransition.setToY(1.2);
        sctransition.setCycleCount(Animation.INDEFINITE);
        sctransition.play();
        // ADDING THE STACK PANE WITH THE IMAGE VIEW TO THE THE MAIN PANE

//        mainpane.getChildren().add(textBubbleIV);


    }
}
