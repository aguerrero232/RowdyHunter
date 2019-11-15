package application.controller;

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
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class InstructionsController implements Initializable {
    @FXML
    private AnchorPane instructionspane;

    @FXML
    private ImageView arrowLIV, arrowRIV, instructionIV;//,instructionIV2;

    @FXML
    private Label instructionsLabel, pageNumberLabel;

    private int page = 0, pages = 2;
    private String[] pageText = {"Shoot the Ufo and aim for the Highest score!", "Click the left mouse button to shoot!", "Click the right mouse button to reload!"};
    private Bounds boundsInSceneLIV;// = arrowLIV.localToScene(arrowLIV.getBoundsInLocal());
    private Bounds boundsInSceneRIV;// = arrowRIV.localToScene(arrowRIV.getBoundsInLocal());


    public String getUrlArrow(String LorR) {
        return "RowdyHunter/resources/images/arrow" + LorR + ".png";
    }

    public String getUrlClick() {
        return "RowdyHunter/resources/images/click-" + page + ".gif";
    }

    /**
     * Name: changeScene()
     * Description: changes the scene from instructions to main
     *
     * @throws IOException
     */
    private void changeScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
        Main.tmpstage.setScene(new Scene(root, 900, 600));
        Main.tmpstage.show();
        Main.tmpstage.setResizable(false);
    }


    @FXML
    public void handleMouseClicked(MouseEvent mouseEvent) throws IOException {
        int x = (int) mouseEvent.getSceneX(), y = (int) mouseEvent.getSceneY();
        boundsInSceneLIV = arrowLIV.localToScene(arrowLIV.getBoundsInLocal());
        boundsInSceneRIV = arrowRIV.localToScene(arrowRIV.getBoundsInLocal());

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

        if (page == 2) {
            changeScene();
        }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pageNumberLabel.setText("" + (page + 1));
        instructionsLabel.setText(pageText[page]);
        try {
            instructionIV.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/ufoInSight.gif")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {
            arrowRIV.setImage(new Image(new FileInputStream("RowdyHunter/resources/images/arrowR.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
