/**
 * SpaceShip.java
 * 
 * @Author Ariel Guerrero
 */

package application.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SpaceShip extends StackPane {
    /**
	 * String to hold path to image location
	 */
    String imageUrl = "";
    /**
     * IamgeView object to hold image for displaying
     */
    ImageView ufoimage = null;
    /**
     * Constructor
     * Sets a ufo image to the IamgeView ufoimage and adds it to itself(a StackPane)
     * @param imageUrl
     * 				passed in image location string
     * @throws FileNotFoundException
     */
    public SpaceShip(String imageUrl) throws FileNotFoundException {
        this.imageUrl = imageUrl;
        setUfoimage(new ImageView(new Image(new FileInputStream(imageUrl))));
        getChildren().add(getUfoimage());
    }
    /**
     * Get the ImageView object for the class
     * @return
     */
    public ImageView getUfoimage() {
        return ufoimage;
    }
    /**
     * Set the ImageView object for the class
     * @param ufoimage
     */
    public void setUfoimage(ImageView ufoimage) {
        this.ufoimage = ufoimage;
    }
}
