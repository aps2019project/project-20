package Client;

import Controller.OnlinePlayersTableController;
import Controller.ShopController;
import Model.*;
import Presenter.ScreenManager;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Application implements ScreenManager {
    private static StackPane stackPane = new StackPane();
    private final static int SERVER_PORT = 9000;
    private final static String PROXY = "localhost";
    private static Socket client;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static Account currentAccount;
    private static Scene currentScene;
    private static ClientListener messageListener;
    private static Object rLock = new Object();

    private static ShopController clientShopController;
    private static OnlinePlayersTableController onlinePlayersTableController;


    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadPageInNewStage(null, "../View/FXML/FirstPage.fxml", false);
    }

    public static void connectToServer() throws IOException {
        client = new Socket(Client.getPROXY(), Client.getServerPort());
        reader = new BufferedReader(new InputStreamReader(Client.getClient().getInputStream()));
        writer = new PrintWriter(Client.getClient().getOutputStream(), true);
        messageListener = new ClientListener();
        messageListener.start();
    }

    public static void closeConnection() {
        try {
            reader.close();
            writer.close();
            client.close();
            messageListener.kill();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static StackPane getStackPane() {
        return stackPane;
    }

    public static void setStackPane(StackPane stackPane) {
        Client.stackPane = stackPane;
    }

    public static ImageView getStackPaneBackGroundImage() {
        for (Node stackPaneChild : stackPane.getChildren()) {
            if (stackPaneChild instanceof ImageView) {
                return (ImageView) stackPaneChild;
            }
        }
        return null;
    }

    public static void waitForListener(){
        synchronized (Client.getrLock()) {
            try {
                Client.getrLock().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Pane getPaneOfMainStackPane() {
        for (Node child : stackPane.getChildren()) {
            if (child instanceof Pane) {
                return (Pane) child;
            }
        }
        return null;
    }

    public static int getServerPort() {
        return SERVER_PORT;
    }

    public static String getPROXY() {
        return PROXY;
    }

    public static Socket getClient() {
        return client;
    }

    public static void setClient(Socket client) {
        Client.client = client;
    }

    public static BufferedReader getReader() {
        return reader;
    }

    public static void setReader(BufferedReader reader) {
        Client.reader = reader;
    }

    public static PrintWriter getWriter() {
        return writer;
    }

    public static void setWriter(PrintWriter writer) {
        Client.writer = writer;
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Account currentAccount) {
        Client.currentAccount = currentAccount;
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void setCurrentScene(Scene currentScene) {
        Client.currentScene = currentScene;
    }

    public static ClientListener getMessageListener() {
        return messageListener;
    }

    public static void setMessageListener(ClientListener messageListener) {
        Client.messageListener = messageListener;
    }

    public static Object getrLock() {
        return rLock;
    }

    public static void setrLock(Object rLock) {
        Client.rLock = rLock;
    }

    public static ShopController getClientShopController() {
        return clientShopController;
    }

    public static void setClientShopController(ShopController clientShopController) {
        Client.clientShopController = clientShopController;
    }

    public static OnlinePlayersTableController getOnlinePlayersTableController() {
        return onlinePlayersTableController;
    }

    public static void setOnlinePlayersTableController(OnlinePlayersTableController onlinePlayersTableController) {
        Client.onlinePlayersTableController = onlinePlayersTableController;
    }
}
