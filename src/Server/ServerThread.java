package Server;

import Exceptions.RepeatedUserNameException;
import Exceptions.UserNotFoundException;
import Exceptions.WrongPasswordException;
import Model.Account;
import Model.Battle;
import Presenter.AccountManageable;
import Presenter.CurrentAccount;
import com.gilecode.yagson.YaGson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket client;
    private Account currentAccount;
    private Battle battle;
    private ServerThread connectedThread;
    private String SendingMessage;
    private Object lock = new Object();

    @Override
    protected void finalize() {
        try {
            client.close();
            inputStream.close();
            outputStream.close();
            ServerMain.getMyThreads().remove(this);
            System.out.println("Client Disconnected");
        } catch (IOException e) {
            System.out.println("Could not close socket");
            System.exit(-1);
        }
    }

    public ServerThread(Socket client) throws IOException {
        this.client = client;
        inputStream = client.getInputStream();
        outputStream = client.getOutputStream();
    }

    @Override
    public void run() {
        Scanner clientScanner = new Scanner(inputStream);
        try {
            while (true) {
                if (!client.isClosed() && clientScanner.hasNext()) {
                    String data = clientScanner.nextLine();
                    System.out.println(data);
                    String command = data.split("\\s")[0];

                    //Add Listeners
                    if ("logIn".equals(command)) {
                        login(data);
                        continue;
                    }
                    if ("signUp".equals(command)) {
                        createAccount(data);
                        continue;
                    }
                    if ("logOut".equals(command)){
                        this.finalize();
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(String data) throws IOException {
        String[] args = data.split("\\s");
        try {
            currentAccount = Account.login(args[1], args[2]);
        } catch (UserNotFoundException e) {
            disconnectServerWithMessage("loginError userNotFound");
            return;
        } catch (WrongPasswordException e) {
            disconnectServerWithMessage("loginError wrongPassword");
            return;
        }
        sendMessageToClient("loginSuccess "+new YaGson().toJson(currentAccount,Account.class));
    }

    private void createAccount(String data) throws IOException {
        String[] args = data.split("\\s");
        try {
            currentAccount = Account.createAccount(args[1], args[2]);
        } catch (RepeatedUserNameException e){
            disconnectServerWithMessage("signUpError repeatedAccount");
            return;
        }
        sendMessageToClient("signUpSuccess "+new YaGson().toJson(currentAccount,Account.class));
    }

    public void disconnectServerWithMessage(String sendingMessage) throws IOException {
        outputStream.write((sendingMessage + "\n").getBytes());
        outputStream.flush();
        this.finalize();
    }

    public void sendMessageToClient(String sendingMessage) throws IOException {
        outputStream.write((sendingMessage + "\n").getBytes());
        outputStream.flush();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public Socket getClient() {
        return client;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public ServerThread getConnectedThread() {
        return connectedThread;
    }

    public void setConnectedThread(ServerThread connectedThread) {
        this.connectedThread = connectedThread;
    }

    public Object getLock() {
        return lock;
    }

    public void setLock(Object lock) {
        this.lock = lock;
    }

    public String getSendingMessage() {
        return SendingMessage;
    }

    public void setSendingMessage(String sendingMessage) {
        SendingMessage = sendingMessage;
    }

    public Battle getMatch() {
        return battle;
    }

    public void setMatch(Battle match) {
        this.battle = match;
    }
}
