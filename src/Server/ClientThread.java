package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Logger;

public class ClientThread extends Thread {

    private BufferedWriter outStream;
    private BufferedReader inStream;
    private ChatServer chatServer;


    public ClientThread(ChatServer chatServer, BufferedWriter outStream,
                        BufferedReader inStream) {
        this.chatServer = chatServer;
        this.outStream = outStream;
        this.inStream = inStream;
    }

    @Override
    public void run() {
        try {

            while (true)
                chatServer.sendToClients(inStream.readLine(), this);

        } catch (SocketException e) {

            chatServer.removeClient(this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedWriter getWriter() {
        return this.outStream;
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

    public ChatServer getChatServer() {
        return chatServer;
    }

    public void setChatServer(ChatServer chatServer) {
        this.chatServer = chatServer;
    }


}
