package Model;
import java.util.ArrayList;

public class Shop {
    final static int MAX_NUMBER_OF_ITEMS_IN_COLLECTION = 3;
    private static ArrayList<Asset> assets = new ArrayList<Asset>();

    public static void sell(Account account, int assetID) {
        try {
            Collection.searchAsset(account, assetID);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        Asset asset = Collection.searchAsset(account, assetID);
        account.getCollection().getAssets().remove(asset);
        account.setBudget(account.getBudget() + asset.getPrice());
    }

    public static void buy(Account account, String assetName) {
        try {
            searchAsset(assetName);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        Asset buyingAsset = searchAsset(assetName);

        int numberOfAccountItems = 0;
        for (Asset asset : account.getCollection().getAssets())
            if (asset instanceof Item)
                numberOfAccountItems++;

        if (buyingAsset.getPrice() > account.getBudget())
            throw new IllegalBuyAssetException("Your budget is insufficient to buy the asset.");
        else if (numberOfAccountItems == MAX_NUMBER_OF_ITEMS_IN_COLLECTION)
            throw new IllegalBuyAssetException("Maximum number of items in collection has reached.");
        account.getCollection().getAssets().add(buyingAsset);
        account.setBudget(account.getBudget() - buyingAsset.getPrice());
    }

    public static ArrayList<Asset> searchAssetInCollection(Account account, String assetName) {
        try {
            Collection.searchAsset(account, assetName);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        return Collection.searchAsset(account, assetName);
    }

    public static Asset searchAsset(String assetName) {
        for (Asset asset: assets)
            if (assetName.equals(asset.getName()))
                return asset;
        throw new AssetNotFoundException("Asset not found in the shop.");
    }
}
