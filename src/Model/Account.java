//Vital Note:
// For example when we add a card from shop to an account's collection via buy method, I don't know if the reference to
//the card in the singleton designed shop and in account's collection and in its hand and in its mainDeck is the same
//or not. And also I don't know if they are the same and some changes are made to the card, for example in a battle,
//do the changes affect the card in the shop and other places or not.
package Model;
import Datas.AccountDatas;
import Datas.DeckDatas;
import Exceptions.RepeatedUserNameException;
import Exceptions.UserNotFoundException;
import Exceptions.WrongPasswordException;

import java.util.*;
import java.lang.*;
import java.util.ArrayList;
public class Account implements Comparable<Account>{
    final static int PRIMARY_BUDGET = 15000;
    final static int numberOfCardsInHand = 5;
    private String userName;
    private String password;
    private Collection collection = new Collection();
    private ArrayList<Deck> decks = new ArrayList<>();
    private int budget = PRIMARY_BUDGET;
    private int numberOfWins = 0;
    private int numberOfLoses = 0;
    private Deck mainDeck;

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        AccountDatas.getAccounts().add(this);
        this.setMainDeck( new Deck("defaultDeck", DeckDatas.getEnemyDeckInStoryGameLevel1().getHero(),DeckDatas.getEnemyDeckInStoryGameLevel1().getItems(),DeckDatas.getEnemyDeckInStoryGameLevel1().getCards()));
        this.collection.setAssetsOfCollectionFromADeck(mainDeck);
        decks.add(mainDeck);
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

    public static Account createAccount(String userName, String password) {
        try {
            searchAccount(AccountDatas.getAccounts(),userName);
        } catch (UserNotFoundException e) {
            return new Account(userName, password);
        }
        throw new RepeatedUserNameException("The username is already taken.");
    }

    public static void deleteAccount(Account account) {
        AccountDatas.getAccounts().remove(account);
    }

    public static Account login(String userName, String password) {
        try {
            searchAccount(AccountDatas.getAccounts(),userName, password);
        } catch (UserNotFoundException | WrongPasswordException e) {
            throw e;
        }
        return searchAccount(AccountDatas.getAccounts(),userName, password);
    }

    @Override
    public int compareTo(Account account) {
        if (this.numberOfWins > account.numberOfWins)
            return 1;
        else if (this.numberOfWins < account.numberOfWins)
            return -1;
        return 0;
    }

    public static void sortAccounts(ArrayList<Account> accounts) {
        Collections.sort(accounts);
    }

    public static Account searchAccount(ArrayList<Account> accounts ,String userName, String password) {
            for (Account account : accounts) {
                if (userName.equals(account.getUserName())) {
                    if (password.equals(account.getPassword()))
                        return account;
                    else
                        throw new WrongPasswordException("The password is incorrect.");
                }
            }
        throw new UserNotFoundException("User not found.");
    }

    public static Account searchAccount(ArrayList<Account> accounts ,String userName) {
            for (Account account : accounts) {
                if (userName.equals(account.getUserName())) {
                    return account;
                }
            }
        throw new UserNotFoundException("User not found.");
    }

}