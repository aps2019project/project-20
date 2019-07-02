package Model;

import Exceptions.AssetNotFoundException;
import Exceptions.DeckNotFoundException;
import Exceptions.InvalidSelectMainDeckException;
import Exceptions.RepeatedDeckException;
import Presenter.CurrentAccount;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class Collection {
    private final static int MAX_NUMBER_OF_CARDS_IN_DECK = 20;
    private ArrayList<Asset> assets = new ArrayList<>();

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public int searchAssetInMyCollection(String assetName) {
        Asset asset;
        try {
            asset = Asset.searchAsset(this.getAssets(), assetName);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        return asset.getID();
    }

    public void setAssets(ArrayList<Asset> assets) {
        this.assets = assets;
    }

    public void writeCollectionOnJsonFileAppended(FileWriter fileWriter) {
        try {
            fileWriter.write("{\"assets\":[");
            Gson gson = new Gson();
            for (int i = 0; i < this.getAssets().size(); i++) {
                if (this.getAssets().get(i) instanceof Hero) {
                    fileWriter.write(gson.toJson(this.getAssets().get(i), Hero.class));
                }
                if (this.getAssets().get(i) instanceof Minion) {
                    fileWriter.write(gson.toJson(this.getAssets().get(i), Minion.class));
                }
                if (this.getAssets().get(i) instanceof Spell) {
                    fileWriter.write(gson.toJson(this.getAssets().get(i), Spell.class));
                }
                if (this.getAssets().get(i) instanceof Item) {
                    fileWriter.write(gson.toJson(this.getAssets().get(i), Item.class));
                }
                if (i != assets.size() - 1) {
                    fileWriter.write(",");
                }
            }
            fileWriter.write("]}");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAssetsOfCollectionFromADeck(Deck deck) {
        assets.add(deck.getHero());
        assets.addAll(deck.getCards());
        assets.addAll(deck.getItems());
    }

}