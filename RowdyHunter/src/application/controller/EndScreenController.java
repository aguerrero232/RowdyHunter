package application.controller;

import application.Main;
import application.model.HighScores;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class EndScreenController implements Initializable {

    ArrayList<String> lvdata = new ArrayList<String>();
    @FXML
    private ListView highscoresListView;

    private void populateListView(ArrayList<HighScores> list) {

        for (int i = 0; i < list.size(); i++)
            lvdata.add("USER: " + list.get(i).getUsername() + " - SCORE: " + list.get(i).getScore());

        highscoresListView.setItems(FXCollections.observableList(lvdata));
    }

    /**
     * Name: changeScene()
     * Description: changes the scene from zone to main
     *
     * @throws IOException
     */
    private void changeScene() throws IOException {
//        long mTime = System.currentTimeMillis();
//        long end = mTime + 5000; // 5 seconds
//
//        while (mTime < end)
//        {
//            mTime = System.currentTimeMillis();
//        }

        Parent root = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
        Main.tmpstage.setScene(new Scene(root, 900, 600));
        Main.tmpstage.show();
        Main.tmpstage.setResizable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HighScores sc = null;

        try {
            sc = new HighScores(0, "none");
            sc.loadScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // need this to load in the highest scores to populate the list view with their data
        populateListView(sc.getScores());

        // changes the scene back to main after 3 sec
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(3000),
                ae -> {
                    try {
                        changeScene();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
        timeline.play();
    }
}
