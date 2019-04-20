package Model;
import java.util.*;
import java.lang.*;

public class Account implements Comparable<Account>{
    final int primaryBudget = 15000;
//    final int numberOfCardsInHand = 5;

    public static ArrayList<Account> accounts = new ArrayList<Account>();
    private String userName;
    private String password;
    private Collection collection = new Collection();
    private int budget = primaryBudget;
    private int numberOfWins = 0;
    private int numberOfLoses = 0;
    private Deck mainDeck;
    private ArrayList<Deck> decks = new ArrayList<Deck>();
    private ArrayList<Card> hand = new ArrayList<Card>();
    //private Shop shop = new Shop();
    //private View view;

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        accounts.add(this);
    }

    public String getUserName() {
        return userName;
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

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

//    public Shop getShop() {
//        return shop;
//    }

    public static void createAccount(String userName, String password) {
        try {
            findAccount(userName, password);
        } catch (UserNotFoundException e) {
            new Account(userName, password);
        }
        throw new RepeatedUserNameException("The username already exists.");
    }

    public static Account login(String userName, String password) {
        try {
            findAccount(userName, password);
        } catch (UserNotFoundException | WrongPasswordException e) {
            throw e;
        }
        Account loginnedAccount = findAccount(userName, password);
        return loginnedAccount;
    }

    @Override
    public int compareTo(Account account) {
        if (this.numberOfWins > account.numberOfWins)
            return 1;
        else if (this.numberOfWins < account.numberOfWins)
            return -1;
        return 0;
    }

    public static void sortAccounts() {
        Collections.sort(accounts);
    }

    public static Account findAccount(String userName, String password) {
        for (Account account: accounts){
            if (userName.equals(account.getUserName())){
                if (password.equals(account.getPassword()))
                    return account;
                else
                    throw new WrongPasswordException("The password is incorrect.");
            }
        }
        throw new WrongPasswordException("User not found.");
    }
}