/**
 * @Author Ariel Guerrero
 */

package application.controller;

import application.Main;
import application.model.Bullet;
import application.model.HighScores;
import application.model.Magazine;
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
    /**
	 * Main pane to hold view content
	 */
    @FXML
    private AnchorPane gamepane;
    /**
	 * Imageviews to hold visual game assets
	 */
    @FXML
    private ImageView gamebackground, bushesimage, round1, round2, round3;
    /**
     * Labels to display information tom user
     */
    @FXML
    private Label scoreLabel, usernameLabel, magzLabel, loadlabel;

    /**
     * Spaceship object to handle spaceship functionality
     */
    private SpaceShip tmpShip;
    /**
     * Arraylist of bullet objects(images)
     */
    private ArrayList<ImageView> bullets = new ArrayList<ImageView>();
    /**
     * bullet object (holds image of bullet)
     */
    private Bullet bullet = new Bullet();
    /**
     * The multiplication amount of score
     */
    private double scoremultiplier = 0.0;
    /**
     * Magazine object(holds bullet count, reloads)
     */
    private Magazine mag = new Magazine(3, 0);
    //private int bulletCount = 3, rawscore = 0, reloads = 0, totalscore = 0, reloadLimit = 10;
    /**
     * values for raw score amount and totalscore amount
     */
    private int rawscore = 0, totalscore = 0;
    /**
     * HashMap to hold possible spawn location for spaceship
     */
    private HashMap<Integer, ArrayList<Integer>> spawnLocations = new HashMap<Integer, ArrayList<Integer>>();
    /**
     * Holds delay time for movement of spaceship
     */
    private Duration tmpDuration = Duration.millis(900);
    
    /**
     * Constructor
     * @throws FileNotFoundException
     */
    public GameController() throws FileNotFoundException {
    }
    /**
     * Handles mouse click functionality
     * left click: fire shot
     * right click: reload
     * @param mouseEvent
     * @throws IOException
     */
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
    /**
     * Reload magazine
     * @throws IOException
     */
    private void reload() throws IOException {
        if (mag.getBulletCount() == 0) {
            if (mag.reloadsExceeded())
                endGame();

            for (int i = 0; i < bullets.size(); i++)
                bullets.get(i).setImage(bullet);

            mag.addReload();//reloads++;
            mag.reload();//bulletCount = 3;
            magzLabel.setText("" + (mag.getReloadLimit() - mag.getReloads()));//(reloadLimit - reloads));
            loadlabel.setText("");
        }
    }
    /**
     * handles gunshot and determines if spaceship is hit
     * @param x
     * @param y
     * @throws FileNotFoundException
     */
    private void gunshot(int x, int y) throws FileNotFoundException {
        if (mag.getBulletCount() == 0){ // no bullets in the gun nothing happened
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
    /**
     * Depletes one shot and changes bullet image
     */
    private void shot() {
        mag.fire();
        bullets.get(mag.getBulletCount()).setImage(null);
    }
    /**
     * Randomly select from a choice of sounds for gunshot sound
     */
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
    /**
     * Randomly move ship to a new location on screen
     */
    private void teleportShip() {
        Random ran = new Random();
        tmpShip.setLayoutX(ran.nextInt(425) + 1);
        tmpShip.setLayoutY(ran.nextInt(225) + 1);
    }
    /**
     * Populate bullets array
     */
    private void addBulletsToList() {
        bullets.add(round1);
        bullets.add(round2);
        bullets.add(round3);
    }
    /**
     * Calculate player score based on score amount and mulitplier
     * @param score
     * @param multiplier
     * @return
     */
    private int calcScore(int score, double multiplier) {
        return (int) (score * (1 + multiplier));
    }

    /**
     * Adjust difficulty as the player performs better
     * @param score
     */
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
    /**
     * Collects final score data and selects end music based on player performance
     * @throws IOException
     */
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
    /**
     * Adds passed parameter values to an arraylist and returns it
     * @param a
     * @param b
     * @return
     */
    private ArrayList<Integer> getAList(int a, int b) {
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        tmp.add(a);
        tmp.add(b);
        return tmp;
    }
    /**
     * Selects from a choice of sounds randomly for the explosion
     */
    private void playexplosionSound(){
        Random ran = new Random();
        int explosionOption = ran.nextInt(4) + 1;

        if(explosionOption == 2 || explosionOption == 3){
            explosionOption = 1;
        }
        else{
            explosionOption = ran.nextInt(4) + 1;
        }

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
    /**
     * Takes in the position of the spaceship and displays an explosion image for a limited time in its place and
     * plays explosion sound effect
     * @param x
     * @param y
     * @throws FileNotFoundException
     */
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

    /**
     * Randomly selects a new image to represent the spaceship on screen
     * @return
     * 			new spaceship object
     * @throws FileNotFoundException
     */
    public SpaceShip getRandomShip() throws FileNotFoundException {
        Random rand = new Random();
        String url = "RowdyHunter/resources/images/ufo-", extension = ".gif";
        return new SpaceShip(url + (rand.nextInt(3) + 1) + extension);
    }
    /**
     * Randomly sets a number spawn coordinates for spaceships based on passed amount
     * @param amountofSpawnLocations
     */
    private void setSpawnLocations(int amountofSpawnLocations) { // ufo
        Random rand = new Random();
        for (int i = 0; i < amountofSpawnLocations; i++)
            spawnLocations.put(i, getAList(rand.nextInt(200) + 1, rand.nextInt(100) + 50));

    }

    /**
     * Initiates and handles movement of spaceship
     * @param d
     */
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

   
    /**
     * Set up game for first pass
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
   
        //------------------    filling map of different spawn locations    ----------------------------------------
        setSpawnLocations(11);
        //--------------------------    entering starting data into the UI -----------------------------------------
        usernameLabel.setText(MainController.getUsername());
        scoreLabel.setText("0");
        magzLabel.setText("" + (mag.getReloadLimit() - mag.getReloads()));
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
