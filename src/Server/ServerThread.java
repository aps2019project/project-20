package Server;

import Exceptions.*;
import Model.*;
import com.gilecode.yagson.YaGson;
import com.google.gson.reflect.TypeToken;
import com.jniwrapper.Int;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import static Model.Shop.shop;

public class ServerThread extends Thread {
    private boolean kill;

    private enum CurrentPageType {
        LEADER_BOARD, ONLINE_PLAYERS_BOARD, SHOP_COLLECTION_BOARD, NONE
    }

    private CurrentPageType pageType;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket client;
    private Account currentAccount;
    private Battle battle;
    private ServerThread connectedThread;
    private String SendingMessage;

    private Object lock = new Object();

    public ServerThread(Socket client) throws IOException {
        this.client = client;
        inputStream = client.getInputStream();
        outputStream = client.getOutputStream();
        kill = false;
        pageType = CurrentPageType.NONE;
    }

    @Override
    public void run() {
        Scanner clientScanner = new Scanner(inputStream);
        try {
            while (!kill) {
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
                    if ("logOut".equals(command)) {
                        kill();
                        return;
                    }
                    if ("getLeaderBoard".equals(command)) {
                        sendLeaderBoard(false);
                        continue;
                    }
                    if ("exitFromPage".equals(command)) {
                        pageType = CurrentPageType.NONE;
                        continue;
                    }
                    if ("getShopCollection".equals(command)) {
                        sendShopCollection(false);
                        continue;
                    }
                    if ("buy".equals(command)) {
                        buy(data);
                        continue;
                    }
                    if ("sell".equals(command)) {
                        sell(data);
                        continue;
                    }
                    if ("save".equals(command)) {
                        save(data);
                        continue;
                    }
                    if ("getOnlinePlayersTable".equals(command)) {
                        sendOnlinePlayersTable(false);
                        continue;
                    }
                    if ("battleQuest".equals(command)) {
                        sendBattleRequestMessage(data);
                        continue;
                    }
                    if ("rejectBattleMessage".equals(command)) {
                        sendBattleRejectMessage(data);
                        continue;
                    }
                    if ("acceptBattleMessage".equals(command)) {
                        sendBattleAcceptMessage(data);
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


    synchronized private void sendBattleRequestMessage(String data) throws IOException {
        ServerThread destination = ServerThread.searchServerThreadInServer(data.substring(12));
        synchronized (destination.getOutputStream()) {
            destination.sendMessageToClient("matchQuest " + currentAccount.getName());
        }
    }

    private void sendBattleRejectMessage(String data) throws IOException {
        ServerThread destination = ServerThread.searchServerThreadInServer(data.substring(20));
        synchronized (destination.getOutputStream()) {
            destination.sendMessageToClient("requestDeclined");
        }
    }

    private void sendBattleAcceptMessage(String data) throws IOException {
        ServerThread destination = ServerThread.searchServerThreadInServer(data.substring(20));
        //todo make battle and threads connection


        synchronized (destination.getOutputStream()) {
            destination.sendMessageToClient("startingBattle " + " ... ");
        }
        sendMessageToClient("startingBattle " + " ... ");
        autoUpdateOnlinePlayersTableForAllClients();
    }

    synchronized private void sendOnlinePlayersTable(boolean isUpdate) throws IOException {
        ArrayList<Account> accounts = Account.getAccountsFromFile("Data/AccountsData.json");
        ArrayList<PlayerViewer> playerViewers = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            if (!accounts.get(i).getName().equals(currentAccount.getName())) {
                ServerThread serverThreadFound;
                try {
                    serverThreadFound = searchServerThreadInServer(accounts.get(i).getName());
                } catch (ServerThreadNotFoundException e) {
                    playerViewers.add(0, new PlayerViewer(accounts.get(i).getName(), PlayerViewer.Status.OFFLINE));
                    continue;
                }
                if (serverThreadFound.getConnectedThread() != null) {
                    playerViewers.add(0, new PlayerViewer(accounts.get(i).getName(), "Playing With " + serverThreadFound.getConnectedThread().getCurrentAccount().getName()));
                } else {
                    playerViewers.add(0, new PlayerViewer(accounts.get(i).getName(), PlayerViewer.Status.FREE));
                }
            }
        }
        if (!isUpdate) {
            sendMessageToClient("onlinePlayersTable " + new YaGson().toJson(playerViewers, new TypeToken<Collection<PlayerViewer>>(){}.getType()));
            pageType = CurrentPageType.ONLINE_PLAYERS_BOARD;
        } else {
            sendMessageToClient("updateOnlinePlayersTable " + new YaGson().toJson(playerViewers, new TypeToken<Collection<PlayerViewer>>(){}.getType()));
        }
    }

    synchronized private void save(String data) throws IOException {
        Account newAccount = new YaGson().fromJson(data.substring(5), Account.class);
        newAccount.saveInToFile("Data/AccountsData.json");
        sendMessageToClient("saveSuccess");
    }

    synchronized private void buy(String data) throws IOException {
        String args = data.substring(4);
        AssetContainer buyItem = shop.searchInShop((new YaGson().fromJson(args, Asset.class)).getName());
        try {
            Shop.buy(currentAccount, buyItem);
        } catch (InsufficientMoneyInBuyFromShopException e) {
            sendMessageToClient("buyError InsufficientMoney");
            return;
        } catch (MaximumNumberOfItemsInBuyException e) {
            sendMessageToClient("buyError MaximumNumberOfItems");
            return;
        } catch (NotEnoughQuantityException e) {
            sendMessageToClient("buyError NotEnoughQuantity");
            return;
        }
        sendMessageToClient("buySuccess " + new YaGson().toJson(currentAccount, Account.class) + " || " + new YaGson().toJson(shop.getAssetContainers(), new TypeToken<Collection<AssetContainer>>() {
        }.getType()));
        autoUpdateShopViewForAllClients();
    }

    synchronized private void sell(String data) throws IOException {
        String args = data.substring(5);
        AssetContainer sellItem = shop.searchInShop((new YaGson().fromJson(args, Asset.class)).getName());
        Shop.sell(currentAccount, sellItem);
        sendMessageToClient("sellSuccess " + new YaGson().toJson(currentAccount, Account.class) + " || " + new YaGson().toJson(shop.getAssetContainers(), new TypeToken<Collection<AssetContainer>>() {
        }.getType()));
        autoUpdateShopViewForAllClients();
    }

    private void sendShopCollection(boolean isUpdate) throws IOException {
        if (isUpdate) {
            sendMessageToClient("updateShopCollection " + new YaGson().toJson(shop.getAssetContainers(), new TypeToken<Collection<AssetContainer>>() {
            }.getType()));
        } else {
            sendMessageToClient("shopCollection " + new YaGson().toJson(shop.getAssetContainers(), new TypeToken<Collection<AssetContainer>>() {
            }.getType()));
            pageType = CurrentPageType.SHOP_COLLECTION_BOARD;
        }
    }

    private void sendLeaderBoard(boolean isUpdate) throws IOException {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            accounts = Account.getAccountsFromFile("Data/AccountsData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Account.sortAccounts(accounts);
        if (isUpdate) {
            sendMessageToClient("updateLeaderTable " + new YaGson().toJson(accounts, new TypeToken<Collection<Account>>() {
            }.getType()));
        } else {
            sendMessageToClient("leaderTable " + new YaGson().toJson(accounts, new TypeToken<Collection<Account>>() {
            }.getType()));
            pageType = CurrentPageType.LEADER_BOARD;
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
        sendMessageToClient("loginSuccess " + new YaGson().toJson(currentAccount, Account.class));
        autoUpdateOnlinePlayersTableForAllClients();
    }

    synchronized private void createAccount(String data) throws IOException {
        String[] args = data.split("\\s");
        try {
            currentAccount = Account.createAccount(args[1], args[2]);
        } catch (RepeatedUserNameException e) {
            disconnectServerWithMessage("signUpError repeatedAccount");
            return;
        }
        sendMessageToClient("signUpSuccess " + new YaGson().toJson(currentAccount, Account.class));
        autoUpdateLeaderBoardViewForAllClients();
        autoUpdateOnlinePlayersTableForAllClients();
    }

    private void autoUpdateShopViewForAllClients() throws IOException {
        synchronized (Server.getThreads()) {
            for (int i = 0; i < Server.getThreads().size(); i++) {
                if (!Server.getThreads().get(i).getCurrentAccount().getName().equals(currentAccount.getName()) && Server.getThreads().get(i).getPageType() == CurrentPageType.SHOP_COLLECTION_BOARD) {
                    Server.getThreads().get(i).sendShopCollection(true);
                }
            }
        }
    }

    //todo at end of a battle call this method
    private void autoUpdateOnlinePlayersTableForAllClients() throws IOException {
        synchronized (Server.getThreads()) {
            for (int i = 0; i < Server.getThreads().size(); i++) {
                if (Server.getThreads().get(i).getPageType() == CurrentPageType.ONLINE_PLAYERS_BOARD) {
                    Server.getThreads().get(i).sendOnlinePlayersTable(true);
                }
            }
        }
    }

    private void autoUpdateLeaderBoardViewForAllClients() throws IOException {
        synchronized (Server.getThreads()) {
            for (int i = 0; i < Server.getThreads().size(); i++) {
                if (Server.getThreads().get(i).getPageType() == CurrentPageType.LEADER_BOARD) {
                    Server.getThreads().get(i).sendLeaderBoard(true);
                }
            }
        }
    }

    public void disconnectServerWithMessage(String sendingMessage) throws IOException {
        outputStream.write((sendingMessage + "\n").getBytes());
        outputStream.flush();
        kill();
    }

    public void sendMessageToClient(String sendingMessage) throws IOException {
        outputStream.write((sendingMessage + "\n").getBytes());
        outputStream.flush();
    }

    public static ServerThread searchServerThreadInServer(String accountName) {
        synchronized (Server.getThreads()) {
            for (int i = 0; i < Server.getThreads().size(); i++) {
                if (Server.getThreads().get(i).getCurrentAccount().getName().equals(accountName)) {
                    return Server.getThreads().get(i);
                }
            }
            throw new ServerThreadNotFoundException();
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void kill() throws IOException {
        autoUpdateOnlinePlayersTableForAllClients();
        this.kill = true;
        client.close();
        inputStream.close();
        outputStream.close();
        Server.getThreads().remove(this);
        System.out.println("Client Disconnected");
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

    public CurrentPageType getPageType() {
        return pageType;
    }

    public void setPageType(CurrentPageType pageType) {
        this.pageType = pageType;
    }
}
