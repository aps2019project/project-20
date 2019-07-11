package Model;

import Exceptions.*;
import com.gilecode.yagson.YaGson;

import java.io.*;
import java.util.ArrayList;

public class Shop {
    private final static int MAX_NUMBER_OF_ITEMS_IN_COLLECTION = 3;
    private ArrayList<AssetContainer> assetContainers = new ArrayList<>();
    public static Shop shop = new Shop();
    private ArrayList<AuctionElement> auctionElements = new ArrayList<>();

    private Shop() {
        try {
            assetContainers =  AssetContainer.getAssetContainersArrayFromFile("Data/ShopData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    synchronized public void saveToDataBase() throws IOException {
        FileWriter fileWriter = new FileWriter(new File("Data/ShopData.json"));
        fileWriter.write(new YaGson().toJson(this,Shop.class));
        fileWriter.flush();
        fileWriter.close();
    }

    public void addNewAssetToShop(AssetContainer assetContainer) {
        for (int i = 0; i < this.getAssetContainers().size(); i++) {
            if(this.getAssetContainers().get(i).getAsset().getName().equals(assetContainer.getAsset().getName())){
                this.getAssetContainers().get(i).setQuantity(this.getAssetContainers().get(i).getQuantity()+1);
                return;
            }
        }
        this.getAssetContainers().add(assetContainer);
    }


    public static void createDefaultShop() throws IOException {
        ArrayList<Asset> assets = new ArrayList<>();
        try {
            assets = Asset.getAssetsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<AssetContainer> assetContainers = new ArrayList<>();
        for (Asset asset : assets) {
            assetContainers.add(new AssetContainer(asset));
        }
            AssetContainer.writeAssetContainersArrayInFile(assetContainers,"Data/ShopData.json");
    }

    public static void sell(Account account, AssetContainer sellingAsset) {
        for (Deck deck : account.getDecks()) {
            try {
                deck.searchAssetInDeck(sellingAsset.getAsset().getID());
            }catch (AssetNotFoundException e){
                continue;
            }
            deck.removeFromDeck(sellingAsset.getAsset().getID());
            if(account.getMainDeck().getName().equals(deck.getName())){
                account.setMainDeck(null);
            }
        }
        sellingAsset.sell();
        Asset.removeAssetFromCollection(sellingAsset.getAsset().getName(),account.getCollection().getAssets());
        account.setBudget(account.getBudget() + sellingAsset.getAsset().getPrice());
        sellingAsset.getAsset().setOwner(null);
    }

    public static void buy(Account account, AssetContainer buyingAsset) {
        int numberOfAccountItems = 0;
        for (Asset asset : account.getCollection().getAssets())
            if (asset instanceof Item)
                numberOfAccountItems++;
        if (buyingAsset.getAsset().getPrice() > account.getBudget())
            throw new InsufficientMoneyInBuyFromShopException("Your budget is insufficient to buy the asset.");
        else if (numberOfAccountItems == MAX_NUMBER_OF_ITEMS_IN_COLLECTION)
            throw new MaximumNumberOfItemsInBuyException();
        try {
            buyingAsset.buy();
        }catch (NotEnoughQuantityException e){
            throw e;
        }
        Asset asset = buyingAsset.getAsset().clone();
        asset.setOwner(account);
        account.getCollection().getAssets().add(asset);
        account.setBudget(account.getBudget() - asset.getPrice());
    }

    public ArrayList<AssetContainer> getAssetContainers() {
        return assetContainers;
    }

    public AssetContainer searchInShop(String assetName){
        for (AssetContainer assetContainer : shop.getAssetContainers()) {
            if(assetContainer.getAsset().getName().equals(assetName)){
                return assetContainer;
            }
        }
        throw new AssetNotFoundException() ;
    }

    public ArrayList<AuctionElement> getAuctionElements() {
        return auctionElements;
    }

    public void setAuctionElements(ArrayList<AuctionElement> auctionElements) {
        this.auctionElements = auctionElements;
    }
}