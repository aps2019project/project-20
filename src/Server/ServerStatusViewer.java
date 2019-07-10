package Server;

import Model.Account;
import Model.AssetContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static Model.Shop.shop;
import static Server.Server.isAccountOnLine;

public class ServerStatusViewer extends Thread {
    //todo Graphical interface
    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        while (true) {
            String command = s.nextLine();
            if (command.equals("turn off")) {
                forceTurnOff();
                return ;
            }
            if (command.equals("show accounts")) {
                showAccounts();
            }
            if (command.equals("show shop")) {
                showCardsInShop();
            }
        }
    }

    private void forceTurnOff() {
        synchronized (Server.getThreads()){
            for (int i = 0; i < Server.getThreads().size(); i++) {
                synchronized (Server.getThreads().get(i).getOutputStream()){
                    try {
                        Server.getThreads().get(i).disconnectServerWithMessage("forceDisconnection");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Server.kill();
    }

    private void showCardsInShop(){
        synchronized (shop.getAssetContainers()) {
            for (int i = 0; i < shop.getAssetContainers().size(); i++) {
                System.out.println(shop.getAssetContainers().get(i).getAsset().getName() + "  " + shop.getAssetContainers().get(i).getQuantity());
            }
        }
    }

    private void showAccounts(){
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            accounts = Account.getAccountsFromFile("Data/AccountsData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("%s --> ", accounts.get(i).getName());
            if (isAccountOnLine(accounts.get(i).getName(), Server.getOnlineAccounts())) {
                System.out.printf("%s\n", "Online");
            } else {
                System.out.printf("%s\n", "Offline");
            }
        }
    }
}
