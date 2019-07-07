package Chat;

import java.io.IOException;

import Datas.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import Client.ChatClient;
import View.ChatView;
import View.MainPageView;
import View.BaseView;

public class Chat extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private User userCfg;
    private ChatClient chatClient;
    private MainPageView mainPageView;
    private ChatView chatView;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Chat.Chat Client");
        this.primaryStage.setResizable(false);
        this.primaryStage.setOnCloseRequest(e -> System.exit(0));

        chatClient = new ChatClient(this);

        BaseView.setMainApp(this);

        mainPageView = new MainPageView();
        chatView = new ChatView();

        this.makeRoot();
    }

    private void makeRoot() {
        rootLayout = new BorderPane();
        Scene scene = new Scene(rootLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        this.setView(getMainPageView());
    }

    public void appendToChat(String message) {
        chatView.appendTextToConversation(message);
    }


    public void startChatClient() throws IOException {
        getChatClient().start();
    }

    public void setView(BaseView newView) {
        rootLayout.setCenter(newView.getView());
    }

    public User getUserCfg() {
        return userCfg;
    }

    public void setUserCfg(User userCfg) {
        this.userCfg = userCfg;
    }

    public ChatClient getChatClient() {
        return chatClient;
    }

    public ChatView getChatView() {
        return chatView;
    }

    public MainPageView getMainPageView() {
        return mainPageView;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public void setMainPageView(MainPageView mainPageView) {
        this.mainPageView = mainPageView;
    }

    public void setChatView(ChatView chatView) {
        this.chatView = chatView;
    }

}