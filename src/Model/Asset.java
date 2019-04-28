package Model;

import Exceptions.AssetNotFoundException;

import java.util.ArrayList;

public class Asset {
    private String name;
    private String desc;
    private int price;
    private int ID;
    private int xInGround;
    private int yInGround;
    private Account owner;
    private String action;
    private Buffer buffer;

    public Asset() {

    }
    public Asset(String name, String desc, int price, int ID, boolean doesHaveAction) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.ID = ID;
        this.xInGround = 0;
        this.yInGround = 0;
        this.owner = null;
        if (doesHaveAction)
            this.action = name + "Action";
        else
            this.action = "NoAction";
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public static ArrayList<Hero> getHeroesOfAssetCollection(ArrayList<Asset> cardAndItems){
        ArrayList<Hero> heroes = new ArrayList<>();
        for (Asset asset : cardAndItems) {
            if (asset instanceof Hero) {
                heroes.add((Hero) asset);
            }
        }
        return heroes;
    }

    public static ArrayList<Item> getItemsOfAssetCollection(ArrayList<Asset> cardAndItems){
        ArrayList<Item> items = new ArrayList<>();
        for (Asset asset : cardAndItems) {
            if (asset instanceof Item) {
                items.add((Item) asset);
            }
        }
        return items;
    }

    public static ArrayList<Spell> getSpellsOfAssetCollection(ArrayList<Asset> cardAndItems){
        ArrayList<Spell> spells = new ArrayList<>();
        for (Asset asset : cardAndItems) {
            if (asset instanceof Spell) {
                spells.add((Spell) asset);
            }
        }
        return spells;
    }

    public static ArrayList<Minion> getMinionsOfAssetCollection(ArrayList<Asset> cardAndItems){
        ArrayList<Minion> minions = new ArrayList<>();
        for (Asset asset : cardAndItems) {
            if (asset instanceof Minion) {
                minions.add((Minion) asset);
            }
        }
        return minions;
    }

    public static Asset searchAsset(ArrayList<Asset> cardAndItems, String name){
        for (Asset asset : cardAndItems) {
            if(asset.getName().compareTo(name)==0){
                return asset;
            }
        }
        throw new AssetNotFoundException("");
    }

    public static Asset searchAsset(ArrayList<Asset> assets, int ID){
        for (Asset asset : assets) {
            if(asset.getID() == ID){
                return asset;
            }
        }
        throw new AssetNotFoundException("");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getXInGround() {
        return xInGround;
    }

    public void setxInGround(int xInGround) {
        this.xInGround = xInGround;
    }

    public int getYInGround() {
        return yInGround;
    }

    public void setyInGround(int yInGround) {
        this.yInGround = yInGround;
    }
}
