package application.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SpaceShip extends StackPane {
    String imageUrl = "";
    ImageView ufoimage = null;

    public SpaceShip(String imageUrl) throws FileNotFoundException {
        this.imageUrl = imageUrl;
        setUfoimage(new ImageView(new Image(new FileInputStream(imageUrl))));
        getChildren().add(getUfoimage());
    }

    public ImageView getUfoimage() {
        return ufoimage;
    }

    public void setUfoimage(ImageView ufoimage) {
        this.ufoimage = ufoimage;
    }
}
