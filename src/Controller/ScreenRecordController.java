package Controller;

import com.teamdev.jxcapture.*;

import java.awt.*;
import java.io.*;

public class ScreenRecordController extends Thread {
    //todo for end of game
    private final Object finish = new Object();

    private String path;


    public ScreenRecordController(String dateOfBattle) {
        this.path = "Data/"+ dateOfBattle+".wmv";
    }

    @Override
    public void run() {
        VideoCapture videoCapture = VideoCapture.create();
        videoCapture.setCaptureArea(new Rectangle(60, 60, 1800, 900));
        java.util.List<Codec> videoCodecs = videoCapture.getVideoCodecs();
        Codec videoCodec = videoCodecs.get(0);
        EncodingParameters encodingParameters = new EncodingParameters(new File(path));
        encodingParameters.setSize(new Dimension(1800, 900));
        encodingParameters.setBitrate(800000);
        encodingParameters.setFramerate(30);
        encodingParameters.setCodec(videoCodec);
        videoCapture.start(encodingParameters);
        synchronized (finish) {
            try {
                finish.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        videoCapture.stop();
    }

    public Object getFinish() {
        return finish;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
