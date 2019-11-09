package application.controller;

import application.model.Roadrunner;
import application.view.GamePanel;
import application.model.Roadrunner;
//import application.utility.Resources.Resources;
//import duckhunt.utility.sound.Sound;
import java.awt.image.BufferedImage;

public class RoadrunnerController {

    private static final int DELAY = 250;
    private static RoadrunnerController dogControllerIstance = null;

    private DogIntroAnimation dogIntroAnimation;
    private DogAnimation dogAnimation;

    private Spritesheet spriteSheet;

    private BufferedImage currentImage;

//    private GamePanel panel;

    private Roadrunner roadrunner;

//    private Sound dogCall;
//    private Sound capturedDuck;
//    private Sound dogLaugh;

    private boolean isJumpFinihed;
    private boolean isAnimationFinished;

    private int x;
    private int y;

    private RoadrunnerController() {
        currentImage = Resources.getImage("/images/dogRight0.png");
        dogCall = Resources.getSound("/sounds/dog.wav");
        capturedDuck = Resources.getSound("/sounds/capturedDuck.wav");
        dogLaugh = Resources.getSound("/sounds/dogLaugh.wav");
        dogIntroAnimation = new DogIntroAnimation();
        dogAnimation = new DogAnimation();
        spriteSheet = new Spritesheet();
        isAnimationFinished = false;
        isJumpFinihed = false;
        x = 0;
        y = 0;
    }

    public static RoadrunnerController getIstance() {
        if (dogControllerIstance == null) {
            return dogControllerIstance = new DogController();
        }
        return dogControllerIstance;
    }

    public DogIntroAnimation getIntroAnimation() {
        return dogIntroAnimation;
    }

    public DogAnimation getAnimation() {
        return dogAnimation;
    }

    public void setRoadrunner(Roadrunner pRoadrunner) {
        this.roadrunner = pRoadrunner;
    }

    public Roadrunner getRoadrunner() {
        return roadrunner;
    }
//
//    private void moveRigth() {
//        spriteSheet.setFrames(3, "dogRight");
//        spriteSheet.setDelay(DELAY);
//        spriteSheet.update();
//        currentImage = spriteSheet.getCurrentFrame();
//        x = roadrunner.getX();
//        x += roadrunner.getSpeed();
//        roadrunner.setX(x);
//    }
//
//    private void sniff() {
//        spriteSheet.setFrames(2, "dogSniff");
//        spriteSheet.setDelay(DELAY);
//        spriteSheet.update();
//        currentImage = spriteSheet.getCurrentFrame();
//    }
//
//    private void moveUp() {
//        currentImage = Resources.getImage("/images/dogHappy.png");
//        y = roadrunner.getY();
//        y -= roadrunner.getSpeed();
//        roadrunner.setY(y);
//    }
//
//    private void laughAndGoUp() {
//        spriteSheet.setFrames(2, "dogLaugh");
//        spriteSheet.update();
//        currentImage = spriteSheet.getCurrentFrame();
//        y = roadrunner.getY();
//        y -= roadrunner.getSpeed();
//        roadrunner.setY(y);
//    }
//
//    private void laugh() {
//        spriteSheet.setFrames(2, "dogLaugh");
//        spriteSheet.update();
//        currentImage = spriteSheet.getCurrentFrame();
//    }
//
//    private void laughAndGoDown() {
//        spriteSheet.setFrames(2, "dogLaugh");
//        spriteSheet.update();
//        currentImage = spriteSheet.getCurrentFrame();
//        y = roadrunner.getY();
//        y += roadrunner.getSpeed();
//        roadrunner.setY(y);
//    }
//
//    private void moveDown() {
//        currentImage = resources.getImage("/images/dogHappy.png");
//        y = roadrunner.getY();
//        y += roadrunner.getSpeed();
//        roadrunner.setY(y);
//    }
//
//    private void dogInAlert() {
//        currentImage = Resources.getImage("/images/dogAttention.png");
//    }
//
//    private void jump() {
//        currentImage = Resources.getImage("/images/dogJump.png");
//        x = roadrunner.getX();
//        y = roadrunner.getY();
//        x += roadrunner.getSpeed();
//        y -= roadrunner.getSpeed();
//        roadrunner.setX(x);
//        roadrunner.setY(y);
//    }
//
//    private void land() {
//        currentImage = Resources.getImage("/images/dogLanding.png");
//        x = roadrunner.getX();
//        y = roadrunner.getY();
//        x += roadrunner.getSpeed();
//        y += roadrunner.getSpeed();
//        roadrunner.setX(x);
//        roadrunner.setY(y);
//    }

    public BufferedImage getCurrentImage() {
        return currentImage;
    }

    public boolean isIntroAnimationFinished() {
        return isAnimationFinished;
    }

    public class DogIntroAnimation implements Runnable {

        private Thread thread;
        private int i;

        public void start() throws InterruptedException {
            reset();
            thread = new Thread(this);
            thread.start();
            thread.join();
        }

        public void stop() {
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
        }

        private void reset() {
            isAnimationFinished = false;
            i = 0;
            roadrunner.setX(0);
            roadrunner.setY(450);
        }

        @Override
        public void run() {
            while (!isAnimationFinished) {
                while (roadrunner.getX() < 250) {
                    moveRigth();
                    panel.setDogCurrentImage(currentImage);
                    panel.repaint();
                }
                if (roadrunner.getX() == 250) {
                    while (i < 180) {
                        sniff();
                        panel.setDogCurrentImage(currentImage);
                        panel.repaint();
                        i++;
                    }
                    dogInAlert();
                    panel.setDogCurrentImage(currentImage);
                    panel.repaint();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        System.out.println("an error occured during dog intro animation thread");
                        stop();
                    }
                    dogCall.play();
                    while (roadrunner.getY() > 320) {
                        jump();
                        panel.setDogCurrentImage(currentImage);
                        panel.repaint();
                    }
                    while (roadrunner.getY() < 390) {
                        land();
                        panel.setDogCurrentImage(currentImage);
                        panel.repaint();
                    }
                }
                isAnimationFinished = true;
                stop();
            }
        }
    }

    public class DogAnimation implements Runnable {

        private Thread thread;
        private boolean theDuckIsDead;
        private int i;

        public void start(boolean pValue) throws InterruptedException {
            reset();
            theDuckIsDead = pValue;
            thread = new Thread(this);
            thread.start();
            thread.join();
        }

        public void stop() {
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
        }

        private void reset() {
            roadrunner.setX(350);
            roadrunner.setY(380);
            i = 0;
            isAnimationFinished = false;
        }

        @Override
        public void run() {
            while (!isAnimationFinished) {
                if (theDuckIsDead) {
                    capturedDuck.play();
                    while (roadrunner.getY() > 350) {
                        moveUp();
                        panel.setDogCurrentImage(currentImage);
                        panel.repaint();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        System.out.println("an error occured during dog animation thread");
                        stop();
                    }
                    while (roadrunner.getY() < 360) {
                        moveDown();
                        panel.setDogCurrentImage(currentImage);
                        panel.repaint();
                    }
                } else {
                    dogLaugh.play();
                    while (roadrunner.getY() > 370) {
                        laughAndGoUp();
                        panel.setDogCurrentImage(currentImage);
                        panel.repaint();
                    }
                    while (i < 200) {
                        laugh();
                        panel.setDogCurrentImage(currentImage);
                        panel.repaint();
                        i++;
                    }
                    while (roadrunner.getY() < 390) {
                        laughAndGoDown();
                        panel.setDogCurrentImage(currentImage);
                        panel.repaint();
                    }
                }
                isAnimationFinished = true;
                stop();
            }
        }
    }
}