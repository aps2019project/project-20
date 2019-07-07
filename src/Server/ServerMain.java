package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {
    private static final int SERVER_PORT = 9000;
    private static ArrayList<ServerThread> myThreads = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("client connected to server");
            socket.setKeepAlive(true);
            ServerThread clientThread = new ServerThread(socket);
            myThreads.add(clientThread);
            clientThread.start();
        }
    }

    public static ArrayList<ServerThread> getMyThreads() {
        return myThreads;
    }
}
