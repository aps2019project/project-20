//Vital Note:
// For example when we add a card from shop to an account's collection via buy method, I don't know if the reference to
//the card in the singleton designed shop and in account's collection and in its hand and in its mainDeck is the same
//or not. And also I don't know if they are the same and some changes are made to the card, for example in a battle,
//do the changes affect the card in the shop and other places or not.
package Model;

import Datas.DeckDatas;
import Exceptions.RepeatedUserNameException;
import Exceptions.UserNotFoundException;
import Exceptions.WrongPasswordException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javax.security.auth.login.AccountNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.lang.*;
import java.util.ArrayList;

public class Account implements Comparable<Account> {
    final static int PRIMARY_BUDGET = 15000;
    private String Name;
    private String password;
    private Collection collection = new Collection();
    private ArrayList<Deck> decks = new ArrayList<>();
    private int budget = PRIMARY_BUDGET;
    private int numberOfWins = 0;
    private int numberOfLoses = 0;
    private Deck mainDeck;

    public void setName(String name) {
        this.Name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private ArrayList<MatchHistory> matchHistories = new ArrayList<>();

    public ArrayList<MatchHistory> getMatchHistories() {
        return matchHistories;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Account() {

    }

    public Account(String userName, String password) {
        this.Name = userName;
        this.password = password;
        this.setMainDeck(new Deck(this, "defaultDeck"));
        this.collection.setAssetsOfCollectionFromADeck(mainDeck);
        decks.add(mainDeck);
    }

    public String getName() {
        return Name;
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

    public int getNumberOfLoses() {
        return numberOfLoses;
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
            if(this.numberOfLoses - account.numberOfLoses!=0) {
            return -(this.numberOfLoses - account.numberOfLoses);
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
        ArrayList<Account> currentAccounts = new Gson().fromJson(reader, new TypeToken<java.util.Collection<Account>>() {
        }.getType());
        reader.close();
        return currentAccounts;
    }

    public static Account searchAccountInFile(String userName, String password, String filePath) throws IOException {
        Gson gson = new Gson();
        Reader reader = new FileReader(filePath);
        Account account;
        try {
            Account[] currentAccounts = gson.fromJson(reader, Account[].class);
            account = searchAccount(new ArrayList<>(Arrays.asList(currentAccounts)), userName, password);
        } catch (UserNotFoundException e) {
            throw e;
        } finally {
            reader.close();
        }
        return account;
    }

    public static Account searchAccountInFile(String userName, String filePath) throws IOException {
        Gson gson = new Gson();
        Reader reader = new FileReader(filePath);
        Account account;
        try {
            Account[] currentAccounts = gson.fromJson(reader, Account[].class);
            account = searchAccount(new ArrayList<>(Arrays.asList(currentAccounts)), userName);
        } catch (UserNotFoundException e) {
            throw e;
        } finally {
            reader.close();
        }
        return account;
    }

    public static void writeAccountArrayInFile(ArrayList<Account> accounts, String filePath) throws IOException {
        JsonWriter jsonWriter = new JsonWriter(new FileWriter(filePath));
        new Gson().toJson(accounts, new TypeToken<java.util.Collection<Account>>(){}.getType(), jsonWriter);
        jsonWriter.flush();
        jsonWriter.close();
    }

    public void saveInToFile(String filePath) throws IOException {
        ArrayList<Account> accounts = getAccountsFromFile(filePath);
        accounts.remove(searchAccount(accounts,this.getName()));
        accounts.add(this);
        writeAccountArrayInFile(accounts,filePath);
    }

}