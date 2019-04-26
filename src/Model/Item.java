package Model;

public class Item extends Asset {
    //price=0 ----> item is collectable
    public Item(String name,String desc, int price, int ID,String action) {
        this.setName(name);
        this.setDesc(desc);
        this.setPrice(price);
        this.setID(ID);
        this.setXInGround(0);
        this.setYInGround(0);
        this.setOwner(null);
        this.setAction(action);
    }
}
