package application.model;

import javafx.scene.shape.Rectangle;

public class Roadrunner {

    private static final int START_X_COORDINATE = 0;
    private static final int START_Y_COORDINATE = 450;

    private Rectangle rectangle;
    private int speed;

    public Roadrunner() {
        rectangle = new Rectangle(START_X_COORDINATE,START_Y_COORDINATE,70, 50);
        speed = 1;
    }

    public void setSpeed(int pSpeed) {
        this.speed = pSpeed;
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

}