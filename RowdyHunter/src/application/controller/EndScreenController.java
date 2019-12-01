/**
 * EndScreenController.java
 * 
 * @Author Ariel Guerrero
 */

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

public class EndScreenController implements Initializable {
    /**
	 * ArrayList of strings to hold scores
	 */
    ArrayList<String> lvdata = new ArrayList<String>();
    /**
     * ListView to display scores
     */
    @FXML
    private ListView highscoresListView;
    /**
     * Set score arraylist values to ListView
     * @param list
     */
    private void populateListView(ArrayList<HighScores> list) {
        for (int i = 0; i < list.size(); i++)
            lvdata.add("USER: " + list.get(i).getUsername() + " - SCORE: " + list.get(i).getScore());

        highscoresListView.setItems(FXCollections.observableList(lvdata));
    }

    /**
     * Name: changeScene()
     * Description: changes the scene from ENDSCREEN to main
     *
     * @throws IOException
     */
    private void changeScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/application/view/Main.fxml"));
        Main.tmpstage.setScene(new Scene(root, 900, 600));
        Main.tmpstage.show();
        Main.tmpstage.setResizable(false);
    }
    /**
     * Set up page on first load
     */
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
