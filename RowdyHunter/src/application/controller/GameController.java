package application.controller;

import application.Main;
import application.model.HighScores;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @TODO
 * Create user interface to view score, username, amount of current bullets, and number of magazines left
 * implement model
 * create high score data base
 * create 3rd scene for displaying results
 */

public class GameController implements Initializable {
    @FXML
    private AnchorPane gamepane;

    @FXML
    private ImageView gamebackground, bushesimage, round1, round2, round3;

    @FXML
    private StackPane gamestackpane1;
    @FXML
    private Label scoreLabel, usernameLabel, magzLabel;


    private ArrayList<ImageView> bullets = new ArrayList<ImageView>();
    private Image bullet;
    private double scoremultiplier = 0.0;
    private int bulletCount = 3, reloads = 0, rawscore = 0, totalscore = 0, reloadLimit = 10;
    private HashMap<Integer, ArrayList<Integer>> spawnLocations = new HashMap<Integer, ArrayList<Integer>>();
    private Duration tmpDuration = Duration.millis(900);

    private void addBulletsToList() {
        bullets.add(round1);
        bullets.add(round2);
        bullets.add(round3);
    }

    private int calcScore(int score, double multiplier) {
        return (int) (score * (1 + multiplier));
    }

    private void shot() {
        bullets.get(bulletCount - 1).setImage(null);
    }

    private void setBulletImage() throws FileNotFoundException {
        bullet = new Image(new FileInputStream("RowdyHunter/resources/images/bullet.png"));
    }

    private void setDifficulty(int score) {
        // -------- gradually increasing difficulty here by speeding up the image while also adding a score multiplier
        switch (score) {
            case 3:
                scoremultiplier = .4;
                tmpDuration = Duration.millis(600);
                break;
            case 8:
                scoremultiplier = .5;
                tmpDuration = Duration.millis(300);
                break;
            case 13:
                scoremultiplier = .9;
                tmpDuration = Duration.millis(150);
                break;
            case 15:
                scoremultiplier = 1.5;
                tmpDuration = Duration.millis(100);
                break;
            case 18:
                scoremultiplier = 3.5;
                tmpDuration = Duration.millis(75);
                break;
            default:
        }
    }

    public void handleMouse(MouseEvent mouseEvent) throws IOException {
        // reload (rmb)
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            if (bulletCount == 0) {
                for (int i = 0; i < bullets.size(); i++) {
                    bullets.get(i).setImage(bullet);
                }
                bulletCount = 3;
                magzLabel.setText("" + (reloadLimit - reloads));
            }
        }
        // gun was shot (lmb)
        else {
            if (bulletCount == 0) {
                return;
            }
            shot();
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
                gamepane.getChildren().remove(gamestackpane1);
                ufoExplosion(locations.get(0) - 150, locations.get(1) - 150);
                rawscore += 1;
                scoreLabel.setText("" + rawscore);
                setDifficulty(rawscore);
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
                if (reloads > reloadLimit) {
                    Random random = new Random();
                    int gameOverMusic = random.nextInt(3) + 1;
                    Media media;
                    totalscore = calcScore(rawscore, scoremultiplier); // use this value to display final score
                    HighScores tmpScore = new HighScores(totalscore, usernameLabel.getText());
                    tmpScore.writeScoreToFile(totalscore);
                    // 12 is considered gud in this game (*-*)-b
                    if (rawscore > 12) {
                        switch (gameOverMusic - 1) {
                            case 1:
                                media = new Media(new File("RowdyHunter/resources/sounds/winner.wav").toURI().toString());
                                break;
                            default:
                                media = new Media(new File("RowdyHunter/resources/sounds/winner2.wav").toURI().toString());
                    }
                    } else { // did bad (*_*)-p
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
                    changeScene();
                }
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
        Parent root = FXMLLoader.load(getClass().getResource("../view/EndScreen.fxml"));
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
        Random rand = new Random();
        spawnLocations.put(0, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));
        spawnLocations.put(1, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));
        spawnLocations.put(2, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));
        spawnLocations.put(3, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));
        spawnLocations.put(4, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));
        spawnLocations.put(5, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));
        spawnLocations.put(6, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));
        spawnLocations.put(7, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));
        spawnLocations.put(8, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));
        spawnLocations.put(9, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));
        spawnLocations.put(10, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));
    }

    public void startUFO(Duration d) {
        Random rand = new Random();
        int setToXLocation = rand.nextInt(200) + 301, setToYLocation = rand.nextInt(100) + 76;

        try {
            gamestackpane1 = new StackPane(new ImageView(new Image(new FileInputStream("RowdyHunter/resources/images/ufo-1.gif"))));
            gamestackpane1.setLayoutX(50);
            gamestackpane1.setLayoutY(50);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),
                new EventHandler<ActionEvent>() {

                    double dx = 4; //Step on x or velocity
                    double dy = 3; //Step on y

                    @Override
                    public void handle(ActionEvent t) {
                        //move the ball
                        gamestackpane1.setLayoutX(gamestackpane1.getLayoutX() + dx);
                        gamestackpane1.setLayoutY(gamestackpane1.getLayoutY() + dy);

                        Bounds bounds = gamestackpane1.getBoundsInLocal();
                        //If the ball reaches the left or right border make the step negative
                        if (gamestackpane1.getLayoutX() <= (bounds.getMinX() - 100) ||
                                gamestackpane1.getLayoutX() >= (bounds.getMaxX() + 100)) {
                            dx = -dx;
                        }

                        //If the ball reaches the bottom or top border make the step negative
                        if ((gamestackpane1.getLayoutY() >= (bounds.getMaxY() + 100)) ||
                                (gamestackpane1.getLayoutY() <= (bounds.getMinY() - 100))) {
                            dy = -dy;
                        }
                    }
                }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usernameLabel.setText(MainController.getUsername());
        scoreLabel.setText("0");
        magzLabel.setText("" + (reloadLimit - reloads));

        try {
            setBulletImage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        addBulletsToList();

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).setImage(bullet);
        }

        setSpawnLocations();
        startUFO(Duration.millis(500));

        // ------------------------------- GAME SCREEN IMAGES BEING SET -----------------------------------------------
        try {
            gamebackground.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/nightback.png")));
            bushesimage.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/meadow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // ------------------------------- GAME SCREEN IMAGES BEING SET -----------------------------------------------
        gamepane.setCursor(Cursor.CROSSHAIR);
    }
}
