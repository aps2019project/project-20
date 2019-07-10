package Server;

import Model.Account;
import Model.AssetContainer;
import Model.Shop;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static Model.Shop.shop;

public class Server extends Application {
    private static final int SERVER_PORT = 9000;
    private final static ArrayList<ServerThread> threads = new ArrayList<>();
    private static ServerStatusViewer serverStatusViewer = new ServerStatusViewer();

    public Server() {
        serverStatusViewer.setDaemon(true);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("Server Started");
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        serverStatusViewer.start();
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("client connected to server");
            socket.setKeepAlive(true);
            ServerThread clientThread = new ServerThread(socket);
            threads.add(clientThread);
            clientThread.start();
        }
    }

    public static void main(String[] args) throws IOException {
//        save default data
//        Asset.saveDefaultCardsToJsonDatabase();
//        Deck.saveDefaultDecksToJson();
//        Shop.createDefaultShop();
        launch(args);
    }

    public static void kill(){
        try {
            AssetContainer.writeAssetContainersArrayInFile(shop.getAssetContainers(),"Data/ShopData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server turned off");
        System.exit(0);
    }

    public static ArrayList<ServerThread> getThreads() {
        return threads;
    }

    public static boolean isAccountOnLine(String accountName, ArrayList<Account> onlineAccounts) {
        for (Account onlineAccount : onlineAccounts) {
            if (accountName.equals(onlineAccount.getName())) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Account> getOnlineAccounts(){
        synchronized (threads){
            ArrayList<Account>accounts = new ArrayList<>();
            for (int i = 0; i < threads.size(); i++) {
                accounts.add(threads.get(i).getCurrentAccount());
            }
            return accounts;
        }
    }
}
