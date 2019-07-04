package Controller;

import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SoundController implements Initializable {

    private MediaView mediaView;
    private MediaPlayer mediaPlayer ;
    private  final String MEDIA_URL ;

    public SoundController(String MEDIA_URL ,MediaView mediaView ) {
        this.MEDIA_URL = MEDIA_URL;
        this.mediaView = mediaView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


//        System.out.println(location.toString());
//        System.out.println(this.getClass().getResource(MEDIA_URL).toExternalForm());

        mediaPlayer = new MediaPlayer(new Media(this.getClass().getResource(MEDIA_URL).toExternalForm()));
        mediaPlayer.setAutoPlay(true);

        mediaView.setMediaPlayer(mediaPlayer);

    }

}
