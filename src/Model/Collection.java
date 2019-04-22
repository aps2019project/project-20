package Model;

import Exceptions.AssetNotFoundException;
import Exceptions.DeckNotFoundException;
import Exceptions.InvalidSelectMainDeckException;
import Exceptions.RepeatedDeckException;
import Presenter.CurrentAccount;

import java.util.ArrayList;

//import static Model.Asset.searchCardAndItem;
//import static Model.Deck.searchDeckInDecks;

public class Collection {
    private final static int MAX_NUMBER_OF_CARDS_IN_DECK = 20;
    private ArrayList<Asset> assets = new ArrayList<>();

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public int searchAssetInMyCollection (String assetName) {
        Asset asset ;
        try {
           asset = Asset.searchAsset(this.getAssets(), assetName);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        return asset.getID();
    }

    public static void save() {
    }

}