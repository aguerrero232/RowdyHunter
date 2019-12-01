/**
 * Bullet.java
 * 
 * @Author Ariel Guerrero
 */

package application.model;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bullet extends Image {
    /**
	 * Constructor
	 * Uses super call to store image path from file
	 * @throws FileNotFoundException
	 */
    public Bullet() throws FileNotFoundException {
        super(new FileInputStream("RowdyHunter/resources/images/bullet.png"));
    }
}
