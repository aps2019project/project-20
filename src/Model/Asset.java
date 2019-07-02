package Model;

import Datas.AssetDatas;
import Exceptions.AssetNotFoundException;
import Exceptions.RepeatedAddingAssetToDatabaseException;
import Presenter.ImageComparable;
import Presenter.JsonDeserializerWithInheritance;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class Asset implements ImageComparable {
    @SerializedName("type")
    private String typeName = getClass().getName();
    private String name;
    private String desc;
    private int price;
    private int ID;
    protected int xInGround;
    protected int yInGround;
    protected transient Account owner;
    private String action;
    private Buffer buffer;
    protected String assetImageAddress;

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

    public static ArrayList<Hero> getHeroesOfAssetCollection(ArrayList<Asset> cardAndItems) {
        ArrayList<Hero> heroes = new ArrayList<>();
        for (Asset asset : cardAndItems) {
            if (asset instanceof Hero) {
                heroes.add((Hero) asset);
            }
        }
        return heroes;
    }

    public static ArrayList<Item> getItemsOfAssetCollection(ArrayList<Asset> cardAndItems) {
        ArrayList<Item> items = new ArrayList<>();
        for (Asset asset : cardAndItems) {
            if (asset instanceof Item) {
                items.add((Item) asset);
            }
        }
        return items;
    }

    public static ArrayList<Spell> getSpellsOfAssetCollection(ArrayList<Asset> cardAndItems) {
        ArrayList<Spell> spells = new ArrayList<>();
        for (Asset asset : cardAndItems) {
            if (asset instanceof Spell) {
                spells.add((Spell) asset);
            }
        }
        return spells;
    }

    public static ArrayList<Minion> getMinionsOfAssetCollection(ArrayList<Asset> cardAndItems) {
        ArrayList<Minion> minions = new ArrayList<>();
        for (Asset asset : cardAndItems) {
            if (asset instanceof Minion) {
                minions.add((Minion) asset);
            }
        }
        return minions;
    }

    public static Asset searchAsset(ArrayList<Asset> cardAndItems, String name) {
        for (Asset asset : cardAndItems) {
            if (asset.getName().compareTo(name) == 0) {
                return asset;
            }
        }
        throw new AssetNotFoundException("");
    }

    public static ArrayList<Asset> searchAndGetAssetCollectionFromCollection(ArrayList<Asset> assets ,String assetName){
        if(assetName.equals("") || assets == null){
            return assets;
        }
        ArrayList<Asset> result = new ArrayList<>();
        for (Asset asset : assets) {
            if(asset != null && asset.getName().matches(".*"+assetName+".*")){
                result.add(asset);
            }
        }
        return result;
    }

    public Asset searchAssetFromCardImage(ArrayList<Asset> assets, Image assetCardImage){
        for (Asset asset : assets) {
            if(assetCardImage.impl_getUrl().split("/")[assetCardImage.impl_getUrl().split("/").length-1].equals(asset.getAssetImageAddress().split("/")[asset.getAssetImageAddress().split("/").length-1])){
                return asset;
            }
        }
        throw new AssetNotFoundException();
    }

    //todo :: fix bug
    public Asset clone() {
        try {
            if (this instanceof Hero) {
                if (this.getAction().equals("NoAction")) {
                    return new Hero(this.getName(), this.getPrice(), this.getID(), ((Hero) this).getRange(), ((Hero) this).getAP(), ((Hero) this).getHP(), false, ((Hero) this).getAttackType());
                }
                return new Hero(this.getName(), this.getPrice(), this.getID(), ((Hero) this).getRange(), ((Hero) this).getAP(), ((Hero) this).getHP(), ((Hero) this).getMP(), ((Hero) this).getCoolDown(), ((Hero) this).getAttackType());
            }
            if (this instanceof Minion) {
                if (this.getAction().equals("NoAction")) {
                    return new Minion(this.getName(), this.getDesc(), this.getPrice(), this.getID(), ((Minion) this).getRange(), ((Minion) this).getAP(), ((Minion) this).getHP(), ((Minion) this).getMP(), ((Minion) this).getAttackType());
                }
                return new Minion(this.getName(), this.getDesc(), this.getPrice(), this.getID(), ((Minion) this).getRange(), ((Minion) this).getAP(), ((Minion) this).getHP(), ((Minion) this).getMP(), ((Minion) this).getAttackType(), ((Minion) this).getActivateTimeOfSpecialPower());
            }
            if (this instanceof Spell) {
                return new Spell(this.getName(), this.getDesc(), this.getPrice(), this.getID(), ((Spell) this).getMP(), ((Spell) this).getTargetType(), ((Spell) this).getSquareSideLength());
            }
            if (this instanceof Item) {
                return new Item(this.getName(), this.getDesc(), this.getPrice(), this.getID());
            }
        }catch (ClassCastException e){
            e.printStackTrace();
        }
       return null;
    }

    public static Card searchCard(ArrayList<Card> cards, String name) {
        for (Card card : cards) {
            if (card.getName().compareTo(name) == 0) {
                return card;
            }
        }
        throw new AssetNotFoundException("");
    }

    public static void saveDefaultCardsToJsonDatabase() throws IOException {
        ArrayList<Asset> assets = new ArrayList<>();
        assets.add(AssetDatas.getTotalDisarm());
        assets.add(AssetDatas.getAreaDispel());
        assets.add(AssetDatas.getEmpower());
        assets.add(AssetDatas.getFireball());
        assets.add(AssetDatas.getGodStrength());
        assets.add(AssetDatas.getHellFire());
        assets.add(AssetDatas.getLightingBolt());
        assets.add(AssetDatas.getPoisonLake());
        assets.add(AssetDatas.getMadness());
        assets.add(AssetDatas.getAllDisarm());
        assets.add(AssetDatas.getAllPoison());
        assets.add(AssetDatas.getDispel());
        assets.add(AssetDatas.getHealthWithProfit());
        assets.add(AssetDatas.getPowerUp());
        assets.add(AssetDatas.getAllPower());
        assets.add(AssetDatas.getAllAttack());
        assets.add(AssetDatas.getWeakening());
        assets.add(AssetDatas.getSacrifice());
        assets.add(AssetDatas.getKingsGuard());
        assets.add(AssetDatas.getShock());
        assets.add(AssetDatas.getFarsArcher());
        assets.add(AssetDatas.getFarsSwordsman());
        assets.add(AssetDatas.getFarsSpear());
        assets.add(AssetDatas.getFarsHorseman());
        assets.add(AssetDatas.getFarsChampion());
        assets.add(AssetDatas.getFarsChief());
        assets.add(AssetDatas.getTooraniArcher());
        assets.add(AssetDatas.getTooraniCatapult());
        assets.add(AssetDatas.getTooraniSpear());
        assets.add(AssetDatas.getTooraniSpy());
        assets.add(AssetDatas.getTooraniSwampy());
        assets.add(AssetDatas.getTooraniPrince());
        assets.add(AssetDatas.getBlackGoblin());
        assets.add(AssetDatas.getThrowStoneGiant());
        assets.add(AssetDatas.getHogRiderGoblin());
        assets.add(AssetDatas.getEagle());
        assets.add(AssetDatas.getOneEyeGiant());
        assets.add(AssetDatas.getPoisonSnake());
        assets.add(AssetDatas.getThrowFireDragon());
        assets.add(AssetDatas.getDrainLion());
        assets.add(AssetDatas.getGiantSnake());
        assets.add(AssetDatas.getWhiteWolf());
        assets.add(AssetDatas.getTiger());
        assets.add(AssetDatas.getWolf());
        assets.add(AssetDatas.getWizard());
        assets.add(AssetDatas.getGreatWizard());
        assets.add(AssetDatas.getElf());
        assets.add(AssetDatas.getWildBoar());
        assets.add(AssetDatas.getPiran());
        assets.add(AssetDatas.getGiv());
        assets.add(AssetDatas.getBahman());
        assets.add(AssetDatas.getAshkbos());
        assets.add(AssetDatas.getIraj());
        assets.add(AssetDatas.getBigGiant());
        assets.add(AssetDatas.getTwoHeadGiant());
        assets.add(AssetDatas.getColdMother());
        assets.add(AssetDatas.getSteelArmor());
        assets.add(AssetDatas.getSiavash());
        assets.add(AssetDatas.getKingGiant());
        assets.add(AssetDatas.getArjangGoblin());
        assets.add(AssetDatas.getWhiteDamn());
        assets.add(AssetDatas.getSimorgh());
        assets.add(AssetDatas.getSevenHeadDragon());
        assets.add(AssetDatas.getRakhsh());
        assets.add(AssetDatas.getZahhak());
        assets.add(AssetDatas.getKaveh());
        assets.add(AssetDatas.getArash());
        assets.add(AssetDatas.getLegend());
        assets.add(AssetDatas.getEsfandiar());
        assets.add(AssetDatas.getRostam());
        assets.add(AssetDatas.getKnowledgeCrown());
        assets.add(AssetDatas.getNamoos_e_separ());
        assets.add(AssetDatas.getDamoolArch());
        assets.add(AssetDatas.getNooshdaroo());
        assets.add(AssetDatas.getTwoHornArrow());
        assets.add(AssetDatas.getSimorghWing());
        assets.add(AssetDatas.getElixir());
        assets.add(AssetDatas.getManaMixture());
        assets.add(AssetDatas.getInvulnerableMixture());
        assets.add(AssetDatas.getDeathCurse());
        assets.add(AssetDatas.getRandomDamage());
        assets.add(AssetDatas.getTerrorHood());
        assets.add(AssetDatas.getBladesOfAgility());
        assets.add(AssetDatas.getKingWisdom());
        assets.add(AssetDatas.getAssassinationDagger());
        assets.add(AssetDatas.getPoisonousDagger());
        assets.add(AssetDatas.getShockHammer());
        assets.add(AssetDatas.getSoulEater());
        assets.add(AssetDatas.getBaptism());
        assets.add(AssetDatas.getChineseSword());

        writeAssetArrayInFile(assets,"Data/CardsData.json");

    }

    public static void writeAssetArrayInFile(ArrayList<Asset> assets,String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write('[');
        Gson gson = new Gson();
        for (int i = 0; i < assets.size(); i++) {
            if(assets.get(i) instanceof Hero){
                fileWriter.write(gson.toJson(assets.get(i),Hero.class));
            }
            if(assets.get(i) instanceof Minion){
                fileWriter.write(gson.toJson(assets.get(i),Minion.class));
            }
            if(assets.get(i) instanceof Spell){
                fileWriter.write(gson.toJson(assets.get(i),Spell.class));
            }
            if(assets.get(i) instanceof Item){
                fileWriter.write(gson.toJson(assets.get(i),Item.class));
            }
            if (i!=assets.size()-1){
                fileWriter.write(",");
            }
        }
        fileWriter.write(']');
        fileWriter.flush();
        fileWriter.close();
    }

    public static void addNewAssetToDataBase(Asset asset){
        ArrayList<Asset> assets = new ArrayList<>();
        try {
            assets=getAssetsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Asset.searchAsset(assets, asset.getName());
        }catch (AssetNotFoundException e){
          assets.add(asset);
            try {
                writeAssetArrayInFile(assets,"Data/CardsData.json");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        throw new RepeatedAddingAssetToDatabaseException();
    }

    public static ArrayList<Asset> getAssetsFromFile() throws IOException {
        Reader reader = new FileReader("Data/CardsData.json");
        ArrayList<Asset> assets = new GsonBuilder().registerTypeAdapter(Asset.class, new JsonDeserializerWithInheritance<Asset>()).create().fromJson(reader, new TypeToken<java.util.Collection<Asset>>(){}.getType());
        reader.close();
        return assets;
    }

    public static Asset searchAsset(ArrayList<Asset> assets, int ID) {
        for (Asset asset : assets) {
            if (asset.getID() == ID) {
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

    public void setXInGround(int xInGround) {
        this.xInGround = xInGround;
    }

    public int getYInGround() {
        return yInGround;
    }

    public void setYInGround(int yInGround) {
        this.yInGround = yInGround;
    }

    public String getAssetImageAddress() {
        return assetImageAddress;
    }

    public void setAssetImageAddress(String assetImageAddress) {
        this.assetImageAddress = assetImageAddress;
    }

    protected void setImageAddresses(String name){}
}
