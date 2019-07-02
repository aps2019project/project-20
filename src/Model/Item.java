package Model;

public class Item extends Asset {
    private String actionbarImageAddress;
    private String activeImageAddress;

    //price=0 ----> item is collectable
    public Item(String name, String desc, int price, int ID) {
        super(name, desc, price, ID, true);
        setImageAddresses(name);
    }

    public Item(String name, String desc, int ID) {
        super(name, desc, 0, ID, true);
        setImageAddresses(name);
    }

    public String getActionbarImageAddress() {
        return actionbarImageAddress;
    }

    public String getActiveImageAddress() {
        return activeImageAddress;
    }

    @Override
    protected void setImageAddresses(String name) {
        actionbarImageAddress = "file:images/cards/item/" + name + "/" + name + "_actionbar.gif";
        activeImageAddress = "file:images/cards/item/" + name + "/" + name + "_active.gif";
        assetImageAddress = "file:images/cards/item/"+name+"/"+name+".png";
    }

}
