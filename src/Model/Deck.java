package Model;

import Datas.DeckDatas;
import Exceptions.*;
import Presenter.CurrentAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

public class Deck {
    public static final int STANDARD_NUMBER_OF_HEROES = 1;
    public static final int STANDARD_NUMBER_OF_MINIONS_AND_SPELLS = 20;
    private String name;
    private ArrayList<Card> cards = new ArrayList<>();
    private Hero hero;
    private ArrayList<Item> items = new ArrayList<>();
    private int nextCardFromDeckIndex = 0;

    public int getNextCardFromDeckIndex() {
        return nextCardFromDeckIndex;
    }

    public Deck(String name) {
        this.name = name;
    }

    public Deck(String name, Hero hero, Asset... cards) {
        this(name);
        this.hero = (Hero)hero.clone();
        for (Asset card : cards) {
            if (card instanceof Item) {
                items.add(((Item) card.clone()));
            } else {
                this.cards.add((Card)card.clone());
            }
        }
    }

    //for clone the deck
    public Deck(Account account, String name, Hero hero, ArrayList<Item> items, ArrayList<Card> cards) {
        this(name);
        this.hero = (Hero)hero.clone();
        this.hero.setOwner(account);
        int i = 0;
        for (Item item : items) {
            this.items.add((Item) item.clone());
            this.getItems().get(i).setOwner(account);
            i++;

        }
        i = 0;
        for (Card card : cards) {
            this.cards.add((Card)card.clone());
            this.getCards().get(i).setOwner(account);
            i++;
        }
    }

    public Deck(Account account, String deckNameInFile) {
        Deck deck = null;
        try {
            deck = getDefaultDeckFromFile(deckNameInFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Card card : deck.getCards()) {
            card.setOwner(account);
        }
        for (Item item : deck.getItems()) {
            item.setOwner(account);
        }
        deck.getHero().setOwner(account);

        this.setName(deck.getName());
        this.setHero(deck.getHero());
        this.setCards(deck.getCards());
        this.setItems(deck.getItems());
        this.setNextCardFromDeckIndex(deck.getNextCardFromDeckIndex());
    }

    public Deck(Account account, String deckNameInFile , Hero customHero) {
        this(account,deckNameInFile);
        this.setHero(customHero);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hero getHero() {
        return hero;
    }

    public static int getStandardNumberOfHeroes() {
        return STANDARD_NUMBER_OF_HEROES;
    }

    public static int getStandardNumberOfMinionsAndSpells() {
        return STANDARD_NUMBER_OF_MINIONS_AND_SPELLS;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setNextCardFromDeckIndex(int nextCardFromDeckIndex) {
        this.nextCardFromDeckIndex = nextCardFromDeckIndex;
    }

    public void addToDeck(Account account, int ID) {
        Asset asset;
        try {
            asset = Asset.searchAsset(account.getCollection().getAssets(), ID);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        if (asset instanceof Hero) {
            if (hero == null)
                hero = (Hero) asset;
            else
                throw new IllegalHeroAddToDeckException("The deck's hero is already selected.");
        } else if (!(asset instanceof Item)) {
            if (cards.size() < 20)
                cards.add((Card) asset);
            else
                throw new IllegalCardAddToDeckException();
        }else{
            if(items.size()==0) {
                items.add((Item) asset);
            }
        }
    }

    public void removeFromDeck(int assetID) {
        if (hero != null && assetID == hero.getID())
            hero = null;
        else {
            for (Item item : items)
                if (assetID == item.getID()) {
                    items.remove(item);
                    return;
                }
            for (Card card : cards)
                if (assetID == card.getID()) {
                    cards.remove(card);
                    return;
                }
            throw new AssetNotFoundException("Asset not found in the deck.");
        }
    }

    public Asset searchAssetInDeck(int assetID) {
        if (assetID == hero.getID())
            return hero;
        else {
            for (Item item : items) {
                if (assetID == item.getID())
                    return item;
            }
            for (Card card : cards) {
                if (assetID == card.getID())
                    return card;
            }
        }
        throw new AssetNotFoundException("Asset not found in the deck");
    }

    public static Deck findDeck(ArrayList<Deck> decks, String deckName) {
        for (Deck deck : decks)
            if (deckName.equals(deck.getName()))
                return deck;
        throw new DeckNotFoundException("The deck not found.");
    }

    public static void selectMainDeck(Account account, String deckName) {
        Deck deck;
        try {
            deck = Deck.findDeck(account.getDecks(), deckName);
        } catch (DeckNotFoundException e) {
            throw e;
        }
        if (account.getMainDeck() != null && account.getMainDeck().getName().equals(deckName)) {
            throw new RepeatedDeckException("");
        }
        if (deck.isValidOfMainDeck())
            account.setMainDeck(deck);
        else
            throw new InvalidSelectMainDeckException("The selected deck is invalid to be the main deck.");
    }

    public static void createDeck(String deckName) {
        try {
            Deck.findDeck(CurrentAccount.getCurrentAccount().getDecks(), deckName);
        } catch (DeckNotFoundException e) {
            CurrentAccount.getCurrentAccount().getDecks().add(new Deck(deckName));
            return;
        }
        throw new RepeatedDeckException("");
    }

    public static void deleteDeck(Account account, String deckName) {
        Deck deck;
        try {
            deck = Deck.findDeck(account.getDecks(), deckName);
        } catch (DeckNotFoundException e) {
            throw e;
        }
        account.getDecks().remove(deck);
    }

    public boolean isValidOfMainDeck() {
        return (this.getHero() != null && this.getCards().size() == STANDARD_NUMBER_OF_MINIONS_AND_SPELLS);
    }

    public static void saveDefaultDecksToJson() throws IOException {
        ArrayList<Deck> decks = new ArrayList<>();
        decks.add(DeckDatas.getEnemyDeckInStoryGameLevel1());
        decks.add(DeckDatas.getEnemyDeckInStoryGameLevel2());
        decks.add(DeckDatas.getEnemyDeckInStoryGameLevel3());
        decks.add(DeckDatas.getDefaultDeck());
        JsonWriter jsonWriter = new JsonWriter(new FileWriter("Data/DefaultDecksData.json"));
        new Gson().toJson(decks, new TypeToken<Collection<Deck>>(){}.getType(), jsonWriter);
        jsonWriter.flush();
        jsonWriter.close();
    }

    public static Deck getDefaultDeckFromFile(String deckName) throws IOException {
        Reader reader = new FileReader("Data/DefaultDecksData.json");
        ArrayList<Deck> decks = new Gson().fromJson(reader, new TypeToken<java.util.Collection<Deck>>(){}.getType());
        try{
            return findDeck(decks,deckName);
        }catch (DeckNotFoundException e){
            throw e;
        }
        finally{
            reader.close();
        }
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
