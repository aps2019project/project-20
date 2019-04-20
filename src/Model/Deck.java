package Model;

import java.util.ArrayList;

public class Deck {
    private String name;
    private ArrayList<CardAndItem>cardAndItems;

    public ArrayList<CardAndItem> getCardAndItems() {
        return cardAndItems;
    }

    public void setCardAndItems(ArrayList<CardAndItem> cardAndItems) {
        this.cardAndItems = cardAndItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Deck searchDeckInDecks(ArrayList<Deck>decks, String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().compareTo(deckName) == 0) {
                return deck;
            }

        }
        return null;
    }

    //برای اینکه اررور نده نوشتم
    public static Deck findDeck(ArrayList<Deck> decks){
        return Deck.findDeck(decks);
    }
    public static void addToDeck(String cardName , int cardID ){}
    //public static void addToDeck(String itemName ,int itemID ){}
    public static void deleteFromDeck(String cardName,int cardID ){}
    //public static void deleteFromDeck(String itemName ,int itemID ){}
}
