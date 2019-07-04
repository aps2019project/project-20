package Controller;

import Datas.SoundDatas;
import Presenter.Animationable;
import View.Main;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Random;

public class SlideShowThread extends Thread implements Animationable {
    private ImageView imageView;
    private MediaPlayer mediaPlayer;

    public SlideShowThread(ImageView imageView) {
        this.imageView = imageView;
        mediaPlayer = new MediaPlayer(new Media(new File(SoundDatas.MENU_MUSIC_1).toURI().toString()));
        mediaPlayer.setVolume(0.08);
        this.setDaemon(true);
    }

    @Override
    public void finalize() {
        try {
            mediaPlayer.stop();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void run() {
        FadeTransition fadeInTransition = nodeFadeAnimation(Main.getStackPaneBackGroundImage(), 1000, 1, 0);
        FadeTransition fadeOutTransition = nodeFadeAnimation(Main.getStackPaneBackGroundImage(), 1000, 0, 1);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer = SoundDatas.getRandomSound();
            mediaPlayer.play();
        });

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


