package Model;

public class Item extends Asset {
    private String desc;
    private String name;
    private int price;
    private Account owner;
    private int ID;
    private int xInGround;
    private int yInGround;


    public String getDesc() {
        return desc;
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

    public int getxInGround() {
        return xInGround;
    }

    public void setxInGround(int xInGround) {
        this.xInGround = xInGround;
    }

    public int getyInGround() {
        return yInGround;
    }

    public void setyInGround(int yInGround) {
        this.yInGround = yInGround;
    }
}
