package Controller;

import Client.Client;
import Datas.SoundDatas;
import Presenter.CurrentAccount;
import Presenter.ScreenManager;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements ScreenManager, Initializable {


    public ImageView back;
    public AnchorPane anchorPane;
    public VBox conversationText;
    public TextArea inputArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputArea.addEventHandler(KeyEvent.KEY_PRESSED,onKeyEvent);
        Client.setChatController(this);
    }

    private EventHandler<KeyEvent> onKeyEvent = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                String message = inputArea.getText() + "\n";
                Client.getWriter().println("Chat;" + CurrentAccount.getCurrentAccount().getName() + " : " + message);
                inputArea.clear();
                event.consume();
            }
        }

    };

    public void appendTextToConversation(String message) {
        conversationText.getChildren().add(new TextArea(message));
    }


    public void setBackButtonOnMouseEntered() {
        back.setImage(new Image("file:images/hover_back_button_corner.png"));
    }

    public void setBackButtonOnMousePressed() {
        SoundDatas.playSFX(SoundDatas.PAGE_CHANGING);
        back.setImage(new Image("file:images/pressed_back_button_corner.png"));
    }

    public void setBackButtonOnMouseExited() {
        back.setImage(new Image("file:images/button_back_corner.png"));
    }

    public void setBackButtonOnMouseReleased() throws IOException {
        Client.getWriter().println("exitFromPage");
        loadPageOnStackPane(back.getParent(), "../View/FXML/MainMenu.fxml", "ltr");
    }


}
