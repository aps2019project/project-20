package View;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import Chat.Chat;

public class ChatView extends BaseView {

    private AnchorPane anchorPane = null;
    private TextArea conversationText;
    private TextArea inputArea;
    private Chat mainApp = BaseView.getMainApp();

    public AnchorPane getView() {
        if (anchorPane == null)
            this.createView();
        return anchorPane;
    }

    private EventHandler<KeyEvent> onKeyEvent = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {

                String sendingUser = mainApp.getUserCfg().getUsername() + ":";
                String message = inputArea.getText() + "\n";

                switch (message) {
                    case "emoji1":
                        //😃
                        mainApp.getChatClient().sendMessage(sendingUser + " " + "\uD83D\uDE03");
                        conversationText.appendText("You :" + "\uD83D\uDE03");
                        break;
                    case "emoji2":
                        //😁
                        mainApp.getChatClient().sendMessage(sendingUser + " " + "\uD83D\uDE01");
                        conversationText.appendText("You :" + "\uD83D\uDE01");
                        break;
                    case "1.jpg":
                        mainApp.getChatClient().sendImage("1.jpg");
                        break;
                    case "2.jpg":
                        mainApp.getChatClient().sendImage("2.jpg");

                        break;
                    default:
                        mainApp.getChatClient().sendMessage(sendingUser + " " + message);
                        conversationText.appendText("You :" + message);
                        break;
                }
                inputArea.clear();
                event.consume();
            }
        }
    };

    public void appendTextToConversation(String message) {
        conversationText.appendText(message);
    }

    private void createView() {
        anchorPane = new AnchorPane();

        conversationText = new TextArea();
        conversationText.setFocusTraversable(false);
        conversationText.setEditable(false);
        conversationText.setMinHeight(230);
        conversationText.setWrapText(true);

        AnchorPane.setTopAnchor(conversationText, 10.0);
        AnchorPane.setRightAnchor(conversationText, 10.0);
        AnchorPane.setLeftAnchor(conversationText, 10.0);

        anchorPane.getChildren().add(conversationText);

        inputArea = new TextArea();
        inputArea.setMaxHeight(40);
        inputArea.setWrapText(true);

        AnchorPane.setBottomAnchor(inputArea, 10.0);
        AnchorPane.setLeftAnchor(inputArea, 10.0);
        AnchorPane.setRightAnchor(inputArea, 10.0);

        inputArea.addEventHandler(KeyEvent.KEY_PRESSED, onKeyEvent);
        anchorPane.getChildren().add(inputArea);
    }

}
