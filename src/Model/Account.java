//Vital Note:
// For example when we add a card from shop to an account's collection via buy method, I don't know if the reference to
//the card in the singleton designed shop and in account's collection and in its hand and in its mainDeck is the same
//or not. And also I don't know if they are the same and some changes are made to the card, for example in a battle,
//do the changes affect the card in the shop and other places or not.
package Model;
import java.util.*;
import java.lang.*;

public class Account implements Comparable<Account>{
    final static int PRIMARY_BUDGET = 15000;
//    final int numberOfCardsInHand = 5;

    private static ArrayList<Account> accounts = new ArrayList<Account>();
    private String userName;
    private String password;
    private Collection collection = new Collection();
    private int budget = PRIMARY_BUDGET;
    private int numberOfWins = 0;
    private int numberOfLoses = 0;
    private Deck mainDeck;
//    private ArrayList<Card> hand = new ArrayList<Card>();
//    private Shop shop = new Shop();
//    private View view;

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        accounts.add(this);
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
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

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
    //    public ArrayList<Card> getHand() {
//        return hand;
//    }

//    public Shop getShop() {
//        return shop;
//    }

    public static void createAccount(String userName, String password) {
        try {
            searchAccount(userName, password);
        } catch (UserNotFoundException e) {
            new Account(userName, password);
        }
        throw new RepeatedUserNameException("The username is already taken.");
    }

    public static Account login(String userName, String password) {
        try {
            searchAccount(userName, password);
        } catch (UserNotFoundException | WrongPasswordException e) {
            throw e;
        }
        Account loginnedAccount = searchAccount(userName, password);
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

    public static Account searchAccount(String userName, String password) {
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