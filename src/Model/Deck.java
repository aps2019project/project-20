package Model;

import java.util.ArrayList;

public class Deck {
    private String name;
    //برای اینکه اررور نده نوشتم
    public static Deck findDeck(ArrayList<Deck> decks){
        return Deck.findDeck(decks);
    }
    public static void addToDeck(String cardName , int cardID ){}
    //public static void addToDeck(String itemName ,int itemID ){}
    public static void deleteFromDeck(String cardName,int cardID ){}
    //public static void deleteFromDeck(String itemName ,int itemID ){}
}
