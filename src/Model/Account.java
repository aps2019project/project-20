package Model;

import java.util.ArrayList;

public class Account {
    private String userName;
    private String password;
    private Collection cardAndItemCollection;
    private int budget;
    private Deck mainDeck;
    private ArrayList<Deck> decks;
    private ArrayList<Card> hand;
    private Shop shop;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection getCardAndItemCollection() {
        return cardAndItemCollection;
    }

    public void setCardAndItemCollection(Collection cardAndItemCollection) {
        this.cardAndItemCollection = cardAndItemCollection;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
    //private View view;

    public static void selectUser(String userName) {
    }

    public static void createAccount(String userName) {
    }

    public static void login(String userName) {
    }

    public static void save() {
    }

    public static void logout() {
    }

    public static void help() {
    }

    public static void sortAccounts(ArrayList<Account> accounts) {
    }
}
