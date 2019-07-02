package Model;

import Datas.DeckDatas;
import Exceptions.*;
import Presenter.CurrentAccount;
import Presenter.JsonDeserializerWithInheritance;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.*;
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
        this.hero = (Hero) hero.clone();
        for (Asset card : cards) {
            if (card instanceof Item) {
                items.add(((Item) card.clone()));
            } else {
                this.cards.add((Card) card.clone());
            }
        }
    }

    //for clone the deck
    public Deck(Account account, String name, Hero hero, ArrayList<Item> items, ArrayList<Card> cards) {
        this(name);
        this.hero = (Hero) hero.clone();
        this.hero.setOwner(account);
        int i = 0;
        for (Item item : items) {
            this.items.add((Item) item.clone());
            this.getItems().get(i).setOwner(account);
            i++;

        }
        i = 0;
        for (Card card : cards) {
            this.cards.add((Card) card.clone());
            this.getCards().get(i).setOwner(account);
            i++;
        }
    }

    public Deck(Account account, String deckNameInFile) {
        Deck deck = null;
        try {
            deck = getDefaultDecksFromFile(deckNameInFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        deck.setOwner(account);

        this.setName(deck.getName());
        this.setHero(deck.getHero());
        this.setCards(deck.getCards());
        this.setItems(deck.getItems());
        this.setNextCardFromDeckIndex(deck.getNextCardFromDeckIndex());
    }

    public Deck(Account account, String deckNameInFile, Hero customHero) {
        this(account, deckNameInFile);
        this.setHero(customHero);
    }

    public ArrayList<Asset> getAllAssets() {
        ArrayList<Asset> collection = new ArrayList<>();
        if (this.getHero() != null) {
            collection.add(this.getHero());
        }
        if (this.getCards() != null) {
            collection.addAll(this.getCards());
        }
        if (this.getItems() != null) {
            collection.addAll(this.getItems());
        }
        return collection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getStandardNumberOfHeroes() {
        return STANDARD_NUMBER_OF_HEROES;
    }

    public static int getStandardNumberOfMinionsAndSpells() {
        return STANDARD_NUMBER_OF_MINIONS_AND_SPELLS;
    }

    public void setNextCardFromDeckIndex(int nextCardFromDeckIndex) {
        this.nextCardFromDeckIndex = nextCardFromDeckIndex;
    }

    public void addToDeck(Asset asset) {
        if (asset instanceof Hero) {
            if (hero == null)
                hero = (Hero) asset;
            else
                throw new IllegalHeroAddToDeckException("The deck's hero is already selected.");
        } else if (asset instanceof Card) {
            if (cards.size() < 20)
                cards.add((Card) asset);
            else
                throw new IllegalCardAddToDeckException();
        } else {
            if (items.size() == 0) {
                items.add((Item) asset);
            }
        }
    }

    public void removeFromDeck(int assetID) {
        if (hero != null && assetID == hero.getID())
            hero = null;
        else {
            for (Asset item : items)
                if (assetID == item.getID()) {
                    items.remove(item);
                    return;
                }
            for (Asset card : cards)
                if (assetID == card.getID()) {
                    cards.remove(card);
                    return;
                }
        }
    }

    public Asset searchAssetInDeck(int assetID) {
        if (assetID == hero.getID())
            return hero;
        else {
            for (Asset item : items) {
                if (assetID == item.getID())
                    return item;
            }
            for (Asset card : cards) {
                if (assetID == card.getID())
                    return card;
            }
        }
        throw new AssetNotFoundException("Asset not found in the deck");
    }

    public static void importDeck(Account owner, String filePath) {
        Deck deck = getDeckFromFile(filePath);
        try {
            Deck.findDeck(owner.getDecks(), deck.getName());
        } catch (DeckNotFoundException e) {
            deck.setOwner(owner);
            owner.getDecks().add(deck);
            return;
        }
        throw new RepeatedDeckException("");
    }

    public static void exportDeck(Deck deck, String filePath) {
        Deck copyDeck = new Deck(null, deck.getName(), deck.getHero(), deck.getItems(), deck.getCards());
        writeDeckToJsonFile(copyDeck, filePath);
    }

    public void setOwner(Account account) {
        if (this.getCards() != null) {
            for (Asset card : this.getCards()) {
                card.setOwner(account);
            }
        }
        if (this.getItems() != null) {
            for (Asset item : this.getItems()) {
                item.setOwner(account);
            }
        }
        if (this.getHero() != null) {
            this.getHero().setOwner(account);
        }
    }

    public static Deck findDeck(ArrayList<Deck> decks, String deckName) {
        for (Deck deck : decks)
            if (deckName.equals(deck.getName()))
                return deck;
        throw new DeckNotFoundException("The deck not found.");
    }

    public static void selectMainDeck(Account account, Deck deck) {
        if (deck.isValidOfMainDeck())
            account.setMainDeck(deck);
        else
            throw new InvalidSelectMainDeckException("The selected deck is invalid to be the main deck.");
    }

    public static void unSelectMainDeck(Account account) {
        account.setMainDeck(null);
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

    public void renameDeck(String deckName) {
        try {
            Deck.findDeck(CurrentAccount.getCurrentAccount().getDecks(), deckName);
        } catch (DeckNotFoundException e) {
            this.setName(deckName);
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
        if (deck.isThisMainDeck(account)) {
            account.setMainDeck(null);
        }
        account.getDecks().remove(deck);
    }

    public boolean isThisMainDeck(Account owner) {
        if (owner.getMainDeck() == null) {
            return false;
        }
        return owner.getMainDeck().getName().equals(this.getName());
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
        FileWriter fileWriter = new FileWriter("Data/DefaultDecksData.json");
        writeDeckArrayToJsonFileAppended(decks,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public static void writeDeckToJsonFile(Deck deck, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            deck.writeDeckToJsonFileAppended(fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDeckArrayToJsonFileAppended(ArrayList<Deck> decks,FileWriter fileWriter) {
        try {
            fileWriter.write('[');
            for (int i = 0; i < decks.size(); i++) {
                decks.get(i).writeDeckToJsonFileAppended(fileWriter);
                if (i != decks.size() - 1) {
                    fileWriter.write(",");
                }
            }
            fileWriter.write(']');
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDeckToJsonFileAppended(FileWriter fileWriter) {
        try {
            Gson gson = new Gson();
            fileWriter.write("{\"name\":\"" + this.getName() + "\",");
            fileWriter.write("\"cards\":[");
            for (int i = 0; i < this.getCards().size(); i++) {
                if (this.getCards().get(i) instanceof Hero) {
                    fileWriter.write(gson.toJson(this.getCards().get(i), Hero.class));
                }
                if (this.getCards().get(i) instanceof Minion) {
                    fileWriter.write(gson.toJson(this.getCards().get(i), Minion.class));
                }
                if (this.getCards().get(i) instanceof Spell) {
                    fileWriter.write(gson.toJson(this.getCards().get(i), Spell.class));
                }
                if (i != this.getCards().size() - 1) {
                    fileWriter.write(",");
                }
            }
            fileWriter.write("],\"hero\":" + gson.toJson(this.getHero(), Hero.class) + ",");
            fileWriter.write("\"items\":" + gson.toJson(this.getItems(), new TypeToken<java.util.Collection<Item>>(){}.getType()) + ",");
            fileWriter.write("\"nextCardFromDeckIndex\":" + this.getNextCardFromDeckIndex() + "}");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Deck> searchAndGetDecksFromCollection(ArrayList<Deck> decks, String deckName) {
        if (deckName.equals("") || decks == null) {
            return decks;
        }
        ArrayList<Deck> result = new ArrayList<>();
        for (Deck deck : decks) {
            if (deck != null && deck.getName().matches(".*" + deckName + ".*")) {
                result.add(deck);
            }
        }
        return result;
    }

    public static Deck getDefaultDecksFromFile(String deckName) throws IOException {
        Reader reader = new FileReader("Data/DefaultDecksData.json");
        ArrayList<Deck> decks = new GsonBuilder().registerTypeAdapter(Card.class, new JsonDeserializerWithInheritance<Card>()).create().fromJson(reader, new TypeToken<java.util.Collection<Deck>>() {}.getType());
        try {
            return findDeck(decks, deckName);
        } catch (DeckNotFoundException e) {
            throw e;
        } finally {
            reader.close();
        }
    }

    public static Deck getDeckFromFile(String path) {
        Reader reader = null;
        try {
            reader = new FileReader(path);
            return new GsonBuilder().registerTypeAdapter(Card.class, new JsonDeserializerWithInheritance<Card>()).create().fromJson(reader, Deck.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
