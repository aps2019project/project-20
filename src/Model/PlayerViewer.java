package Model;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class PlayerViewer {
    public enum Status {
        PLAYING, FREE, OFFLINE;
    }

    private String name;
    private Status status;
    private String statusView;

    public PlayerViewer(String name, Status status) {
        this.name = name;
        this.status = status;
            switch (status) {
                case FREE:
                    statusView = "Online Free";
                    break;
                case OFFLINE:
                    statusView = "Offline";
                    break;
            }
    }

    public PlayerViewer(String name, String statusView) {
        this.name = name;
        this.status = Status.PLAYING;
        this.statusView = statusView;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public String getStatusView() {
        return statusView;
    }

    public void setStatusView(String statusView) {
        this.statusView = statusView;
    }

}
