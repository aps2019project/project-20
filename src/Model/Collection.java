package Model;

import java.util.ArrayList;

//import static Model.Asset.searchCardAndItem;
//import static Model.Deck.searchDeckInDecks;

public class Collection {
    final static int MAX_NUMBER_OF_CARDS_IN_DECK = 20;
    private ArrayList<Deck> decks = new ArrayList<Deck>();
    private ArrayList<Asset> assets;

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    //    public void setAssets(ArrayList<Asset> cardAndItems) {
//        this.cardAndItems = cardAndItems;
//    }
    public static void selectMainDeck(Account account,String deckName){
        try {
            searchDeck(account, deckName);
        } catch(DeckNotFoundException e) {
            throw e;
        }
        Deck mainDeck = searchDeck(account, deckName);
        if (isValidDeck(account, mainDeck.getName()))
            account.setMainDeck(mainDeck);
        else
            throw new InvalidSelectMainDeckException("The selected deck is invalid to be the main deck.");
    }

    public static boolean isValidDeck(Account account, String deckName){
        try {
            searchDeck(account, deckName);
        } catch (DeckNotFoundException e) {
            throw e;
        }
        Deck deck = searchDeck(account, deckName);
        int numberOfHeroes = 0;
        int numberOfOtherCards = 0;
        ArrayList<Asset> assets = account.getCollection().getAssets();
        for (Asset asset: assets) {
            if (asset instanceof Hero)
                numberOfHeroes++;
            else if (asset instanceof Card)
                numberOfOtherCards++;
        }
        if (numberOfHeroes == 1 && numberOfOtherCards == MAX_NUMBER_OF_CARDS_IN_DECK)
            return true;
        return false;
    }

    public static void removeFromDeck(Account account, String deckName, int assetID){
        try {
            searchDeck(account, deckName);
        } catch (DeckNotFoundException e) {
            throw e;
        }
        Deck deck = searchDeck(account, deckName);
        try {
            deck.removeFromDeck(assetID);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        deck.removeFromDeck(assetID);
    }

    public static void addToDeck(Account account, String deckName, int assetID) {
        try {
            searchDeck(account, deckName);
            searchAsset(account, assetID);
        } catch (DeckNotFoundException | AssetNotFoundException e) {
            throw e;
        }
        Deck deck = searchDeck(account, deckName);
        Asset asset = searchAsset(account, assetID);
        deck.addToDeck(asset);
    }

    public static void deleteDeck(Account account, String deckName) {
        try {
            searchDeck(account, deckName);
        } catch (DeckNotFoundException e) {
            throw e;
        }
        Deck deck = searchDeck(account, deckName);
        account.getCollection().getDecks().remove(deck);
    }

    public static void createDeck(Account account, String deckName) {
        try {
            searchDeck(account, deckName);
        } catch (DeckNotFoundException e) {
            ArrayList<Deck> decks = account.getCollection().getDecks();
            decks.add(new Deck(deckName));
        }
        throw new RepeatedDeckException("The deck name is already taken.");
    }

//    public static int searchDeck(Deck deck,String cardAndItemName){
//        for (Asset cardAndItem : deck.getCardAndItems()) {
//            if(cardAndItem.getName().compareTo(cardAndItemName)== 0)
//                return cardAndItem.getID();
//        }
//        throw new NullPointerException();
//    }

    public static Deck searchDeck(Account account, String deckName) {
        ArrayList<Deck> decks = account.getCollection().getDecks();
        for (Deck deck: decks)
            if (deckName.equals(deck.getName())){
                return deck;
            }
        throw new DeckNotFoundException("Deck not found in the collection.");
    }

    public static Asset searchAsset(Account account, int assetID) {
        ArrayList<Asset> assets = account.getCollection().getAssets();
        for (Asset asset: assets)
            if (assetID == asset.getID())
                return asset;
        throw new AssetNotFoundException("Asset not found in the collection.");
    }

    public static ArrayList<Asset> searchAsset(Account account, String assetName) {
        ArrayList<Asset> demandedAssets = new ArrayList<Asset>();
        ArrayList<Asset> allAssets = account.getCollection().getAssets();
        for (Asset asset: allAssets)
            if (assetName.equals(asset.getName()))
                demandedAssets.add(asset);
        if (demandedAssets.size() > 0)
            return demandedAssets;
        throw new AssetNotFoundException("No asset was found with this name.");
    }

    //public static void save(){}
}