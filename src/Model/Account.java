//Vital Note:
// For example when we add a card from shop to an account's collection via buy method, I don't know if the reference to
//the card in the singleton designed shop and in account's collection and in its handAndNextCardGrid and in its mainDeck is the same
//or not. And also I don't know if they are the same and some changes are made to the card, for example in a battle,
//do the changes affect the card in the shop and other places or not.
package Model;

import Datas.AssetDatas;
import Exceptions.RepeatedUserNameException;
import Exceptions.UserNotFoundException;
import Exceptions.WrongPasswordException;
import com.gilecode.yagson.YaGson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.lang.*;
import java.util.ArrayList;

public class Account implements Comparable<Account> {
    final static int PRIMARY_BUDGET = 15000;
    private String name;
    private String password;
    private Collection collection = new Collection();
    private ArrayList<Deck> decks = new ArrayList<>();
    private int budget = PRIMARY_BUDGET;
    private int numberOfWins = 0;
    private int numberOfLooses = 0;
    private int rank;
    private Deck mainDeck;
    private ArrayList<MatchHistory> matchHistories = new ArrayList<>();
    private ArrayList<SavedBattle> savedBattles = new ArrayList<>();


    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<MatchHistory> getMatchHistories() {
        return matchHistories;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Account() {

    }

    public Account(String userName, String password) {
        this.name = userName;
        this.password = password;
        this.setMainDeck(new Deck(this, "defaultDeck"));
        this.collection.setAssetsOfCollectionFromADeck(mainDeck);
        decks.add(mainDeck);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Collection getCollection() {
        return collection;
    }

    public int getBudget() {
        return budget;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public int getNumberOfLooses() {
        return numberOfLooses;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    @Override
    public int compareTo(Account account) {
        if(this.numberOfWins - account.numberOfWins!=0) {
            return this.numberOfWins - account.numberOfWins;
        }else
            if(this.numberOfLooses - account.numberOfLooses !=0) {
            return -(this.numberOfLooses - account.numberOfLooses);
        }else {
            return this.getName().compareTo(account.getName());
        }
    }

    public static Account createAccount(String userName, String password) throws IOException {
        try {
            searchAccountInFile(userName, "Data/AccountsData.json");
        } catch (UserNotFoundException e) {
            ArrayList<Account> accounts = getAccountsFromFile("Data/AccountsData.json");
            Account account = new Account(userName, password);
            accounts.add(account);
            writeAccountArrayInFile(accounts,"Data/AccountsData.json");
            return account;
        }
        throw new RepeatedUserNameException("The username is already taken.");
    }

    public static void deleteAccount(Account account) {
        //AccountDatas.getAccounts().remove(account);
    }

    public static Account login(String userName, String password) {
        Account account = new Account();
        try {
            account = searchAccountInFile(userName, password, "Data/AccountsData.json");
        } catch (UserNotFoundException | WrongPasswordException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }

    public static void sortAccounts(ArrayList<Account> accounts) {
        Collections.sort(accounts);
        int i=1;
        for (Account account : accounts) {
            account.setRank(i);
            i++;
        }
    }

    public static Account searchAccount(ArrayList<Account> accounts, String userName, String password) {
        for (Account account : accounts) {
            if (userName.equals(account.getName())) {
                if (password.equals(account.getPassword()))
                    return account;
                else
                    throw new WrongPasswordException("The password is incorrect.");
            }
        }
        throw new UserNotFoundException("User not found.");
    }

    public static Account searchAccount(ArrayList<Account> accounts, String userName) {
        for (Account account : accounts) {
            if (userName.equals(account.getName())) {
                return account;
            }
        }
        throw new UserNotFoundException("User not found.");
    }

    public static ArrayList<Account> getAccountsFromFile(String filePath) throws IOException {
        Reader reader = new FileReader(filePath);
        ArrayList<Account> currentAccounts = new YaGson().fromJson(reader,new TypeToken<java.util.Collection<Account>>(){}.getType());
        reader.close();
        return currentAccounts;
    }

    public static Account searchAccountInFile(String userName, String password, String filePath) throws IOException {
        Reader reader = new FileReader(filePath);
        Account account;
        try {
            ArrayList<Account> accounts = getAccountsFromFile(filePath);
            Account[] currentAccounts = new Account[accounts.size()];
            accounts.toArray(currentAccounts);
            account = searchAccount(new ArrayList<>(Arrays.asList(currentAccounts)), userName, password);
        } catch (UserNotFoundException e) {
            throw e;
        } finally {
            reader.close();
        }
        return account;
    }

    public static Account searchAccountInFile(String userName, String filePath) throws IOException {
        Reader reader = new FileReader(filePath);
        Account account;
        try {
            ArrayList<Account> accounts = getAccountsFromFile(filePath);
            Account[] currentAccounts = new Account[accounts.size()];
            accounts.toArray(currentAccounts);
            account = searchAccount(new ArrayList<>(Arrays.asList(currentAccounts)), userName);
        } catch (UserNotFoundException e) {
            throw e;
        } finally {
            reader.close();
        }
        return account;
    }

    public static void writeAccountArrayInFile(ArrayList<Account> accounts, String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(new YaGson().toJson(accounts,new TypeToken<java.util.Collection<Account>>(){}.getType()));
        fileWriter.flush();
        fileWriter.close();
    }

    public void saveInToFile(String filePath) throws IOException {
        ArrayList<Account> accounts = getAccountsFromFile(filePath);
        accounts.remove(searchAccount(accounts,this.getName()));
        accounts.add(this);
        writeAccountArrayInFile(accounts,filePath);
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void updateNumberOfWins() {
        this.numberOfWins++;
    }

    public void updateNumberOfLooses() {
        this.numberOfLooses++;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }

    public void setMatchHistories(ArrayList<MatchHistory> matchHistories) {
        this.matchHistories = matchHistories;
    }

    public ArrayList<SavedBattle> getSavedBattles() {
        return savedBattles;
    }

    public void setSavedBattles(ArrayList<SavedBattle> savedBattles) {
        this.savedBattles = savedBattles;
    }
}