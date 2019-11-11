package application.controller;

import application.Main;
import javafx.animation.*;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private AnchorPane gamepane;

    @FXML
    private ImageView gamebackground, bushesimage, groundimage, treeimage, ufoimage;

    @FXML
    private StackPane gamestackpane1;

    private int bulletCount = 3, hitcount = 0, reloads = 0, score = 0;
    private HashMap<Integer, ArrayList<Integer>> spawnLocations = new HashMap<Integer, ArrayList<Integer>>();
    private Duration tmpDuration = Duration.millis(900);

    public void gunshot(MouseEvent mouseEvent) throws IOException {

        // ----------------------------- gunshot noise -------------------------------------------------
        Random ran = new Random();
        int gunNoiseOption = ran.nextInt(100) + 1;

        //        System.out.println(gunNoiseOption);
        if (gunNoiseOption % 2 == 0) {
            Media m = new Media(new File("RowdyHunter/resources/sounds/laser2.wav").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(m);
            mediaPlayer.setAutoPlay(true);
        } else {
            Media n = new Media(new File("RowdyHunter/resources/sounds/laser.wav").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(n);
            mediaPlayer.setAutoPlay(true);
        }


        // ----------------------------------------------------------------------------------------------------------------------------

        // -----------------------------  getting the location of the current press of the mouse! ----------------
        ArrayList<Double> locations = new ArrayList<Double>();
        locations.add(mouseEvent.getSceneX());
        locations.add(mouseEvent.getSceneY());

        // ----------------------------- checking if the pane (the UFO) is clicked on -------------------------------------------------
        // -----------------------------  And printing out the location (x,y) if true just so you can see the logic -------------------
        Bounds boundsInScene = gamestackpane1.localToScene(gamestackpane1.getBoundsInLocal());
        if (boundsInScene.contains(locations.get(0), locations.get(1))) {
            //            System.out.println("HIT AT :" + locations.get(0) + ", " + locations.get(1));
            // could add an alien animation here of one falling and fading away using a fade transition , scale transition and transition transition

            // last ufo blew up so place an explosion wherever you clicked the ufo ( x-150,y-150 because the image size when placing the explosion needs an offset)
            ufoExplosion(locations.get(0) - 150, locations.get(1) - 150);
            gamepane.getChildren().remove(gamestackpane1);

            score += 1;
            //            System.out.println("CURRENT SCORE:" + score);


            // -------- gradually increasing difficulty here bu speeding up the image
            if (score == 3) {
                tmpDuration = Duration.millis(300);
            }
            if (score == 5) {
                tmpDuration = Duration.millis(150);
            }
            if (score == 8) {
                tmpDuration = Duration.millis(100);
            }
            if (score == 13) {
                tmpDuration = Duration.millis(50);
            }
            // ---------- updating and placing the new image here ----------------------------------------
            startUFO(tmpDuration);
            // ------------------------------------------------------------------
        }

        // ------------------------------------- logic conditions -------------------------------------------------


        // -------------------- keeping track of the bullets and the number of reloads ----------------------------
        bulletCount--;
        //        System.out.println("BULLETS:" + bulletCount);
        // update bullets display like in project 4
        if (bulletCount == 0) {
            reloads += 1;
            // update bullets on display
            //            System.out.println("Reload #:" + reloads);
            //            System.out.println("Reloading....");
            if (reloads > 8) {
                Random random = new Random();
                int gameOverMusic = random.nextInt(3) + 1;
                Media media;

                if (score > 12) {
                    switch (gameOverMusic - 1) {
                        case 1:
                            media = new Media(new File("RowdyHunter/resources/sounds/winner.wav").toURI().toString());
                            break;
                        default:
                            media = new Media(new File("RowdyHunter/resources/sounds/winner2.wav").toURI().toString());
                    }
                } else {
                    switch (gameOverMusic) {
                        case 2:
                            media = new Media(new File("RowdyHunter/resources/sounds/gameover2.wav").toURI().toString());
                            break;
                        case 1:
                            media = new Media(new File("RowdyHunter/resources/sounds/gameover.wav").toURI().toString());
                            break;
                        default:
                            media = new Media(new File("RowdyHunter/resources/sounds/gameover3.wav").toURI().toString());
                    }
                }

                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);

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


    private void ufoExplosion(double x, double y) throws FileNotFoundException {
        // need an explosion sound here
        // and possibly if we can find it then an alien with a parachute
        Image image1 = new Image(new FileInputStream("RowdyHunter/resources/images/explosion.gif"));
        ImageView imageView = new ImageView();
        imageView.setX(x);
        imageView.setY(y);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(imageView.imageProperty(), image1)),
                new KeyFrame(Duration.millis(500), new KeyValue(imageView.imageProperty(), null))
        );
        timeline.play();
        gamepane.getChildren().add(imageView);
    }


    private ArrayList<Integer> getAList(int a, int b) {
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        tmp.add(a);
        tmp.add(b);
        return tmp;
    }

    private void setSpawnLocations() {
        spawnLocations.put(0, getAList(200, 150));
        spawnLocations.put(1, getAList(300, 20));
        spawnLocations.put(2, getAList(350, 140));
        spawnLocations.put(3, getAList(470, 100));
        spawnLocations.put(4, getAList(100, 130));
        spawnLocations.put(5, getAList(200, 170));
        spawnLocations.put(6, getAList(50, 120));
        spawnLocations.put(7, getAList(350, 220));
        spawnLocations.put(8, getAList(432, 133));
        spawnLocations.put(9, getAList(211, 150));
        spawnLocations.put(10, getAList(400, 200));

    }

    private void startUFO(Duration d) {
        Random rand = new Random();
        int paneX = rand.nextInt(400) + 1, paneY = rand.nextInt(300) + 1, spawnLocation = rand.nextInt(spawnLocations.size()), setToXLocation = rand.nextInt(300) + 201, setToYLocation = rand.nextInt(100) + 51;

        //                System.out.println("X: " + spawnLocations.get(spawnLocation).get(0) + ", Y: " + spawnLocations.get(spawnLocation).get(1));
        //                System.out.println("X: " + paneX + ", Y: " + paneY);

        // ------------------------------- moving ufo stuff -----------------------------------------------
        try {
            gamestackpane1 = new StackPane(new ImageView(new Image(new FileInputStream("RowdyHunter/resources/images/ufo-1.gif"))));
            gamestackpane1.setLayoutX(spawnLocations.get(spawnLocation).get(0));
            gamestackpane1.setLayoutY(spawnLocations.get(spawnLocation).get(1));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // DIFFERENT TYPES OF TRANSITIONS
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(d);
        transition.setToX(setToXLocation);
        transition.setToY(setToYLocation);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setNode(gamestackpane1);   // <--- SETTING THE OBJECT TO TRANSITION
        transition.play();
        ScaleTransition sctransition = new ScaleTransition(Duration.seconds(1), gamestackpane1);
        sctransition.setAutoReverse(true);
        sctransition.setToX(.2);
        sctransition.setToY(.3);
        sctransition.setCycleCount(Animation.INDEFINITE);
        sctransition.play();
        // ADDING THE STACK PANE WITH THE IMAGE VIEW TO THE THE MAIN PANE
        gamepane.getChildren().add(gamestackpane1);
        // ------------------------------- moving ufo stuff -----------------------------------------------
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // need to get this here to work .. its the games music
        //        Media media = new Media(new File("RowdyHunter/resources/sounds/gamemusic.mp3").toURI().toString());
        //        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //        mediaPlayer.setAutoPlay(true);
        //        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        setSpawnLocations();
        // ------------------------------- GAME SCREEN IMAGES BEING SET -----------------------------------------------
        try {
            gamebackground.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/nightback.png")));
            bushesimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/meadow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // ------------------------------- GAME SCREEN IMAGES BEING SET -----------------------------------------------
        startUFO(Duration.seconds(1));
    }
}
