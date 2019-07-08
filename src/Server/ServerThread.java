package Server;

import Exceptions.RepeatedUserNameException;
import Exceptions.UserNotFoundException;
import Exceptions.WrongPasswordException;
import Model.*;
import com.gilecode.yagson.YaGson;
import com.jniwrapper.Int;

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

                    //Battle Listeners
                    if (data.matches("applyHeroSpecialPower;.+")) {
                        Account player = new YaGson().fromJson(data.split(";")[1], Account.class);
                        Asset targetAsset = new YaGson().fromJson(data.split(";")[2], Asset.class);
                        int x = Integer.valueOf(data.split(";")[3]);
                        int y = Integer.valueOf(data.split(";")[4]);
                        battle.applyHeroSpecialPower(player, targetAsset, x, y);
                        sendInstructionToPlayers("applyHeroSpecialPower;");
                        String updatedBattle = new YaGson().toJson(battle, Battle.class);
                        connectedThread.sendMessageToClient("opponentAction;applyHeroSpecialPower;" + updatedBattle);
                    }
                    if (data.matches("useItem;.+")) {
                        Account player = new YaGson().fromJson(data.split(";")[1], Account.class);
                        Item item = new YaGson().fromJson(data.split(";")[2], Item.class);
                        battle.useItem(player, null, null, item);
                        sendInstructionToPlayers("useItem;");
                    }
                    if (data.matches("insertCard;.+")) {
                        Account player = new YaGson().fromJson(data.split(";")[1], Account.class);
                        Card card = new YaGson().fromJson(data.split(";")[2], Card.class);
                        int x = Integer.valueOf(data.split(";")[3]);
                        int y = Integer.valueOf(data.split(";")[4]);
                        battle.insertCard(player, card.getName(), x, y);
                        sendInstructionToPlayers("insertCard;");
                        String updatedBattle = new YaGson().toJson(battle, Battle.class);
                        connectedThread.sendMessageToClient("opponentAction;insertCard;" + (y - 1) + ";" + (x - 1) + ";" + data.split(";")[2] + ";" + updatedBattle);
                    }
                    if (data.matches("cardMoveTo;.+")) {
                        Account player = new YaGson().fromJson(data.split(";")[1], Account.class);
                        Card warrior = new YaGson().fromJson(data.split(";")[2], Card.class);
                        int x = Integer.valueOf(data.split(";")[3]);
                        int y = Integer.valueOf(data.split(";")[4]);
                        battle.cardMoveTo(player, (Warrior) warrior, x, y);
                        sendInstructionToPlayers("cardMoveTo");
                        String updatedBattle = new YaGson().toJson(battle, Battle.class);
                        connectedThread.sendMessageToClient("opponentAction;cardMoveTo;" + (y - 1) + ";" + (x - 1) + ";" + updatedBattle);
                    }
                    if (data.matches("attack;.+")) {
                        Account player = new YaGson().fromJson(data.split(";")[1], Account.class);
                        Card attacker = new YaGson().fromJson(data.split(";")[2], Card.class);
                        Warrior attackedWarrior = new YaGson().fromJson(data.split(";")[3], Warrior.class);
                        int i = Integer.valueOf(data.split(";")[4]);
                        int j = Integer.valueOf(data.split(";")[5]);
                        battle.attack(player, (Warrior) attacker, attackedWarrior);
                        sendInstructionToPlayers("attack;");
                        String updatedBattle = new YaGson().toJson(battle, Battle.class);
                        connectedThread.sendMessageToClient("opponentAction;attack;" + data.split(";")[3] + ";" + i + ";" + j + ";" + updatedBattle);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendInstructionToPlayers(String action) throws IOException {
        String updatedBattle = new YaGson().toJson(battle, Battle.class);
        sendMessageToClient(action + updatedBattle);
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
