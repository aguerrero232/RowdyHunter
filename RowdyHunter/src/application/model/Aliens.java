package application.model;

import javafx.scene.shape.Rectangle;
import java.util.Random;

public class Aliens{

        private static final int START_Y_COORDINATE = 391;
        private final Rectangle rectangle;
        private final int speed;

        public Aliens() {
            rectangle = new Rectangle(randomX(),START_Y_COORDINATE,80, 80);
            speed = randomSpeed();
        }

        public int getSpeed() {
            return speed;
        }

        public void setX(int pX) {
            this.rectangle.setX(pX);
        }

        public int getX() {
            return (int) rectangle.getX();
        }

        public void setY(int pY) {
            this.rectangle.setY(pY);
        }

        public int getY() {
            return (int) rectangle.getY();
        }

        private int randomX() {
            Random random = new Random();
            int randomValue = random.nextInt(798) + 1;
            return randomValue;
        }

        private int randomSpeed() {
            Random random = new Random();
            int randomSpeed = random.nextInt(2) + 2;
            return randomSpeed;
        }

}
