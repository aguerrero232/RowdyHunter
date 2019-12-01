package application;
/**
 * Main.java
 * 
 * Main class to launch application
 */
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    /**
	 * Stage object to hold stage reference
	 */
    public static Stage tmpstage;
    /**
     * Root to hold fxml reference
     */
    Parent root;
    /**
     * Load initial fxml file and present it
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            tmpstage = primaryStage;
            root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
            tmpstage.setTitle("ROWDY HUNTER");
            tmpstage.setScene(new Scene(root, 900, 600));
            tmpstage.show();
            tmpstage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        // i did all dis so music worx
        // by josh
        //relative path may not work on all IDE bc netbeans trash
//        Media media = new Media(new File("RowdyHunter/resources/sounds/gamemusic.mp3").toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//        mediaPlayer.setAutoPlay(true);
        launch(args);
    }
}
