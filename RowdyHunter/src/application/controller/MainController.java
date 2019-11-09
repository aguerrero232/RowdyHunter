package application.controller;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ImageView maintitleimage1, maintitleimage2, alienimagemain,movingufoimage, playbuttonimage, mainbackgroundimage;

    @FXML
    private Pane mainpane;

    private Circle cir;
    @FXML
    private StackPane stackpane1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            stackpane1 = new StackPane(new ImageView(new Image(new FileInputStream("RowdyHunter/resources/images/ufo-1.gif"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        cir = new Circle();
//        cir.setFill();
        cir.setRadius(50);
        cir.setLayoutX(0);
        cir.setLayoutY(0);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(8));
        transition.setToX(500);
        transition.setToY(500);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setNode(stackpane1);
        transition.play();

//        ScaleTransition sctransition = new ScaleTransition(Duration.seconds(3),cir);
//        sctransition.setCycleCount(Animation.INDEFINITE);
//        sctransition.setAutoReverse(true);
//        sctransition.setToX(500);
//        sctransition.setToY(500);
//        sctransition.play();

        mainpane.getChildren().add(stackpane1);




        try {
            alienimagemain.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/alien.gif")));
//            movingufoimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/ufo-1.gif")));
            maintitleimage1.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/titlescreentitle2.png")));
            maintitleimage2.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/titlescreentitle2-1.png")));
            playbuttonimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/homescreenplay.gif")));
            mainbackgroundimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/mainbackground.gif")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
