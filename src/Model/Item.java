package Model;

public class Item extends Asset {
    //price=0 ----> item is collectable

    public Item(String name, String desc, int price, int ID) {
        super(name, desc, price, ID, true);
    }

    public Item(String name, String desc, int ID) {
        super(name, desc, 0, ID, true);
    }

}
