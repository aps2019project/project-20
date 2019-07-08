package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.SocketException;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import View.MainPageView;

public class ClientThread extends Thread {

    private ChatClient chatClient;
    private BufferedWriter outStream;
    private BufferedReader inStream;

    public ClientThread(ChatClient chatClient, BufferedWriter outStream,
                        BufferedReader inStream) {
        this.chatClient = chatClient;
        this.outStream = outStream;
        this.inStream = inStream;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = inStream.readLine();
                chatClient.getMainApp().appendToChat(message + "\n");
            }
        } catch (SocketException e) {

            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Connection Lost");
                alert.setHeaderText("Lost the connection to the server.");
                alert.setContentText("The connection to the server was lost.");
                alert.showAndWait();

                chatClient.getMainApp().setView(new MainPageView());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedWriter getWriter() {
        return this.outStream;
    }

    public ChatClient getChatClient() {
        return chatClient;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public BufferedWriter getOutStream() {
        return outStream;
    }

    public void setOutStream(BufferedWriter outStream) {
        this.outStream = outStream;
    }

    public BufferedReader getInStream() {
        return inStream;
    }

    public void setInStream(BufferedReader inStream) {
        this.inStream = inStream;
    }
}
