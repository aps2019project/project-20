package Controller;

import Presenter.Animationable;
import View.Main;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

public class SlideShowThread extends Thread implements Animationable {
    private ImageView imageView = Main.getStackPaneBackGroundImage();


    @Override
    public void finalize() {
        try {
            super.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.setDaemon(true);
        FadeTransition fadeInTransition = nodeFadeAnimation(Main.getStackPaneBackGroundImage(),1000,1,0);
        FadeTransition fadeOutTransition = nodeFadeAnimation(Main.getStackPaneBackGroundImage(),1000,0,1);

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

