/**
 * @Author Ariel Guerrero
 */

package application.model;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bullet extends Image {
    public Bullet() throws FileNotFoundException {
        super(new FileInputStream("RowdyHunter/resources/images/bullet.png"));
    }
}
