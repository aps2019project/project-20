package Client;

import java.io.*;
import java.net.Socket;

import Chat.Chat;


public class ChatClient {

    private Chat mainApp;
    private Socket clientSocket;
    private ClientThread clientThread;

    public ChatClient(Chat mainApp) {
        this.mainApp = mainApp;
    }

    public void start() throws IOException {
        String host = mainApp.getUserCfg().getHost();

        int port = Integer.valueOf(mainApp.getUserCfg().getPort());

        clientSocket = new Socket(host, port);

        BufferedWriter outStream = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
        outStream.flush();
        BufferedReader inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));

        clientThread = new ClientThread(this, outStream, inStream);

        Thread thread = new Thread(clientThread);
        thread.start();
    }

    public void sendMessage(String message) {
        try {
            clientThread.getWriter().write(message);
            clientThread.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void sendImage(String address) {
        //BufferedWriter outStream = clientThread.getOutStream();
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(address+"copy.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(address);

            int data ;
            while ((data=fileInputStream.read()) !=-1){
                outStream.write(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Chat getMainApp() {
        return mainApp;
    }

    public void setMainApp(Chat mainApp) {
        this.mainApp = mainApp;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ClientThread getClientThread() {
        return clientThread;
    }

    public void setClientThread(ClientThread clientThread) {
        this.clientThread = clientThread;
    }

}
