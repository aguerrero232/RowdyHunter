package application.controller;
/**
 * InstructionsController.java
 * 
 */
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class InstructionsController implements Initializable {
    /**
	 * ImageViews to contain arrow graphics
	 */
    @FXML
    private ImageView arrowLIV, arrowRIV, instructionIV, cornerdisp;
    /**
     * Labels to display instruction information
     */
    @FXML
    private Label instructionsLabel, pageNumberLabel;
    /**
     * To track page count
     */
    private int page = 0, pages = 2;
    /**
     * instruction strings in array
     */
    private String[] pageText = {"Shoot the Ufo and aim for the Highest score!", "Click the left mouse button to shoot!", "Click the right mouse button to reload!"};
    /**
     * To hold x,y coordinates of left arrow object
     */
    private Bounds boundsInSceneLIV;
    /**
     * To hold x,y coordinates of right arrow object
     */
    private Bounds boundsInSceneRIV;
    /**
     * Gets the file path to the left or right arrow image
     * @param LorR
     * 				Passed in string. Expected value: "L" or "R"
     * @return
     */
    public String getUrlArrow(String LorR) {
        return "RowdyHunter/resources/images/arrow" + LorR + ".png";
    }

    /**
     * Name: changeScene()
     * Description: changes the scene from instructions to main
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
     * Handles navigation of instructions via mouse clicks
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    public void handleMouseClicked(MouseEvent mouseEvent) throws IOException {
        int x = (int) mouseEvent.getSceneX(), y = (int) mouseEvent.getSceneY();
        boundsInSceneLIV = arrowLIV.localToScene(arrowLIV.getBoundsInLocal());
        boundsInSceneRIV = arrowRIV.localToScene(arrowRIV.getBoundsInLocal());

        // if click on left arrow
        if (boundsInSceneLIV.contains(x, y)) {

            if (page > 0) {

                page -= 1;
                pageNumberLabel.setText("" + (page + 1));
                instructionsLabel.setText(pageText[page]);

                if (page == 0) {
                    arrowLIV.setImage(null);
                    instructionIV.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/ufoInSight.gif")));
                }

                if (page == 1) {
                    arrowRIV.setImage(new Image(new FileInputStream(getUrlArrow("R"))));
                    instructionIV.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/click-1.gif")));
                }
            }
        }

        // if on last page and click anywhere but the left arrow
        if (page == 2)
            changeScene();

        // if click on right arrow
        if (boundsInSceneRIV.contains(x, y)) {

            if (page < pages) {

                page += 1;
                pageNumberLabel.setText("" + (page + 1));
                instructionsLabel.setText(pageText[page]);

                if (page == 1) {
                    arrowLIV.setImage(new Image(new FileInputStream(getUrlArrow("L"))));
                    instructionIV.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/click-1.gif")));
                }

                if (page == 2) {
                    arrowRIV.setImage(null);
                    instructionIV.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/click-2.gif")));
                }
            }
        }

    }
    /**
     * Set up page on first load
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pageNumberLabel.setText("" + (page + 1));
        instructionsLabel.setText(pageText[page]);

        try {
            instructionIV.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/ufoInSight.gif")));
            arrowRIV.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/arrowR.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
