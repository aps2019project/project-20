package Controller;

import View.Main;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

public class SlideShowController extends Thread {
    private ImageView imageView = Main.getStackPaneBackGroundImage();

    @Override
    public void run() {
        FadeTransition fadeInTransition = new FadeTransition();
        fadeInTransition.setDuration(new Duration(1000));
        FadeTransition fadeOutTransition = new FadeTransition();
        fadeOutTransition.setDuration(new Duration(1000));
        fadeInTransition.setNode(Main.getStackPaneBackGroundImage());
        fadeOutTransition.setNode(Main.getStackPaneBackGroundImage());
        fadeInTransition.setFromValue(1);
        fadeInTransition.setToValue(0);
        fadeOutTransition.setFromValue(0);
        fadeOutTransition.setToValue(1);


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        while (true) {
            fadeInTransition.setOnFinished(event -> {
                imageView.setImage(new Image("file:images/codex/chapter" + (new Random().nextInt(24) + 1) + "_background@2x.jpg"));
                fadeOutTransition.play();
            });
            fadeInTransition.play();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

