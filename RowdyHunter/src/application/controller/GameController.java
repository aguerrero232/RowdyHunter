/**
 * @Author Ariel Guerrero
 */

package application.controller;

import application.Main;
import application.model.Bullet;
import application.model.HighScores;
import application.model.SpaceShip;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.*;

public class GameController implements Initializable {
    @FXML
    private AnchorPane gamepane;

    @FXML
    private ImageView gamebackground, bushesimage, round1, round2, round3;

    @FXML
    private Label scoreLabel, usernameLabel, magzLabel, loadlabel;

    private SpaceShip tmpShip;
    private ArrayList<ImageView> bullets = new ArrayList<ImageView>();
    private Bullet bullet = new Bullet();
    private double scoremultiplier = 0.0;
    private int bulletCount = 3, rawscore = 0, reloads = 0, totalscore = 0, reloadLimit = 10;
    private HashMap<Integer, ArrayList<Integer>> spawnLocations = new HashMap<Integer, ArrayList<Integer>>();
    private Duration tmpDuration = Duration.millis(900);

    public GameController() throws FileNotFoundException {
    }

    public void handleMouse(MouseEvent mouseEvent) throws IOException {
        // reload (rmb)
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            reload();
        }
        // gun was shot (lmb)
        else {
            gunshot((int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY());
        }
    }

    private void reload() throws IOException {
        if (bulletCount == 0) {
            if (reloads >= reloadLimit)
                endGame();

            for (int i = 0; i < bullets.size(); i++)
                bullets.get(i).setImage(bullet);

            reloads++;
            bulletCount = 3;
            magzLabel.setText("" + (reloadLimit - reloads));
            loadlabel.setText("");
        }
    }

    private void gunshot(int x, int y) throws FileNotFoundException {
        if (bulletCount == 0){ // no bullets in the gun nothing happened
            loadlabel.setText("RELOAD!!!");
            return;
        }

        // ----------------------------------------------------------------------------------------------------------------------------
        // ------------------------- update bullet count and display with shot(); -----------------------------------------------------
        shot();

        // ----------------------------------------------------------------------------------------------------------------------------
        // ----------------------------- gunshot noise -------------------------------------------------
        //                  --- decides which laser noise to make here ---
        playGunshotNoise();

        // ----------------------------------------------------------------------------------------------------------------------------
        // -----------------------------  getting the location of the current press of the mouse! ----------------
        ArrayList<Integer> locations = getAList(x, y);//new ArrayList<Double>();

        // ----------------------------- checking if the pane (the UFO) is clicked on -------------------------------------------------
        // -----------------------------  And printing out the location (x,y) if true just so you can see the logic -------------------
        Bounds boundsInScene = tmpShip.localToScene(tmpShip.getBoundsInLocal());
        if (boundsInScene.contains(locations.get(0), locations.get(1))) {
            // remove the current spaceship because it was hit and is going to blow up
            gamepane.getChildren().remove(tmpShip);
            // last ufo blew up so place an explosion wherever you clicked the ufo ( x-150,y-150 because the image size when placing the explosion needs an offset)
            ufoExplosion(locations.get(0) - 150, locations.get(1) - 150);
            rawscore += 1;
            scoreLabel.setText("" + rawscore);
            setDifficulty(rawscore);
            startUFO(tmpDuration);
        } else {
            // shot missed ship teleports
            teleportShip();
        }
    }

    private void shot() {
        bullets.get(--bulletCount).setImage(null);
    }

    private void playGunshotNoise() {
        Random ran = new Random();
        int gunNoiseOption = ran.nextInt(100) + 1;
        if (gunNoiseOption % 2 == 0) {
            Media m = new Media(new File("RowdyHunter/resources/sounds/laser2.wav").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(m);
            mediaPlayer.setAutoPlay(true);
        } else {
            Media n = new Media(new File("RowdyHunter/resources/sounds/laser.wav").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(n);
            mediaPlayer.setAutoPlay(true);
        }
    }

    private void teleportShip() {
        Random ran = new Random();
        tmpShip.setLayoutX(ran.nextInt(425) + 1);
        tmpShip.setLayoutY(ran.nextInt(225) + 1);
    }

    private void addBulletsToList() {
        bullets.add(round1);
        bullets.add(round2);
        bullets.add(round3);
    }

    private int calcScore(int score, double multiplier) {
        return (int) (score * (1 + multiplier));
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
                tmpDuration = Duration.millis(500);
                break;
            case 13:
                scoremultiplier = .9;
                tmpDuration = Duration.millis(400);
                break;
            case 15:
                scoremultiplier = 1.5;
                tmpDuration = Duration.millis(200);
                break;
            case 18:
                scoremultiplier = 3.5;
                tmpDuration = Duration.millis(130);
                break;
            default:
        }
    }

    private void endGame() throws IOException {
        //System.out.println("we're in the endgame now!");
        Random ran = new Random();
        int gameOverMusic = ran.nextInt(3) + 1;
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

    /**
     * Name: changeScene()
     * Description: changes the scene from gamrscreen to endscreen
     *
     * @throws IOException
     */
    private void changeScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/application/view/EndScreen.fxml"));
        Main.tmpstage.setScene(new Scene(root, 900, 600));
        Main.tmpstage.show();
        Main.tmpstage.setResizable(false);
    }

    private ArrayList<Integer> getAList(int a, int b) {
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        tmp.add(a);
        tmp.add(b);
        return tmp;
    }

    private void playexplosionSound(){
        Random ran = new Random();
        int explosionOption = ran.nextInt(4) + 1;
        Media media;
        switch (explosionOption){
            case 1:
                media = new Media(new File("RowdyHunter/resources/sounds/ex1.wav").toURI().toString());
                break;
            case 2:
                media = new Media(new File("RowdyHunter/resources/sounds/ex2.wav").toURI().toString());
                break;
            case 3:
                media = new Media(new File("RowdyHunter/resources/sounds/ex3.wav").toURI().toString());
                break;
            default:
                media = new Media(new File("RowdyHunter/resources/sounds/ex4.wav").toURI().toString());
        }
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }
    private void ufoExplosion(double x, double y) throws FileNotFoundException {
        // need an explosion sound here
        playexplosionSound();
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


    public SpaceShip getRandomShip() throws FileNotFoundException {
        Random rand = new Random();
        String url = "RowdyHunter/resources/images/ufo-", extension = ".gif";
        return new SpaceShip(url + (rand.nextInt(3) + 1) + extension);
    }

    private void setSpawnLocations(int amountofSpawnLocations) { // ufo
        Random rand = new Random();
        for (int i = 0; i < amountofSpawnLocations; i++)
            spawnLocations.put(i, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));

    }

    
    // example of try catch exceptions
    public void startUFO(Duration d) {
        Random rand = new Random();
        int setToXLocation = rand.nextInt(200) + 301, setToYLocation = rand.nextInt(100) + 76;
        try {
            tmpShip = getRandomShip();
            tmpShip.setLayoutX(50);
            tmpShip.setLayoutY(50);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(d);
        transition.setToX(setToXLocation);
        transition.setToY(setToYLocation);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setNode(tmpShip);   // <--- SETTING THE OBJECT TO TRANSITION
        transition.play();
        ScaleTransition sctransition = new ScaleTransition(Duration.seconds(1), tmpShip);
        sctransition.setAutoReverse(true);
        sctransition.setToX(.2);
        sctransition.setToY(.3);
        sctransition.setCycleCount(Animation.INDEFINITE);
        sctransition.play();
        // ADDING THE STACK PANE WITH THE IMAGE VIEW TO THE THE MAIN PANE

        gamepane.getChildren().add(tmpShip);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {

            double dx = 4; //Step on x or velocity
            double dy = 3; //Step on y

            @Override
            public void handle(ActionEvent t) {
                //move the ship
                tmpShip.setLayoutX(tmpShip.getLayoutX() + dx);
                tmpShip.setLayoutY(tmpShip.getLayoutY() + dy);

                Bounds bounds = tmpShip.getBoundsInLocal();
                //If the ship reaches the left or right border make the step negative
                if (tmpShip.getLayoutX() <= (bounds.getMinX() - 100) ||
                        tmpShip.getLayoutX() >= (bounds.getMaxX() + 100)) {
                    dx = -dx;
                }

                //If the ship reaches the bottom or top border make the step negative
                if ((tmpShip.getLayoutY() >= (bounds.getMaxY() + 100)) ||
                        (tmpShip.getLayoutY() <= (bounds.getMinY() - 100))) {
                    dy = -dy;
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

   
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
   
        //------------------    filling map of different spawn locations    ----------------------------------------
        setSpawnLocations(11);
        //--------------------------    entering starting data into the UI -----------------------------------------
        usernameLabel.setText(MainController.getUsername());
        scoreLabel.setText("0");
        magzLabel.setText("" + (reloadLimit - reloads));
        //----------------------------------------------------------------------------------------------------------

        //--------------------------------  Updating bullets images to start with 3   ------------------------------
        addBulletsToList();
        for (int i = 0; i < bullets.size(); i++)
            bullets.get(i).setImage(bullet);

        //--------------------  Starting up first ufo with beginning diff --------------------------------------------------------------------
        startUFO(tmpDuration);

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
