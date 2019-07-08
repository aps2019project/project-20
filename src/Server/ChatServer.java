package Server;

import Server.ClientThread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    final private int port = 9000;
    final private List<ClientThread> clientList = new ArrayList<>();

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                BufferedWriter outStream = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));

                outStream.flush();

                BufferedReader inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));


                ClientThread clientThread = new ClientThread(this, outStream, inStream);
                clientList.add(clientThread);

                Thread thread = new Thread(clientThread);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeClient(ClientThread client) {
        clientList.remove(clientList.indexOf(client));
    }

    public void sendToClients(String message, ClientThread sender) {


        for (ClientThread clientThread : clientList) {

            if (clientThread == sender)
                continue;

            try {
                clientThread.getWriter().write(message + "\n");
                clientThread.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
