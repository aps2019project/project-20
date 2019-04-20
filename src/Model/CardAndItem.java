package Model;

import java.util.ArrayList;

public class CardAndItem {
    private String name;
    private int price;
    private Account owner;
    private int ID;

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

    public static CardAndItem searchCardAndItem(String name, ArrayList<CardAndItem> cardAndItems) {
        for (CardAndItem cardAndItem : cardAndItems) {
            if (cardAndItem.getName().compareTo(name) == 0) {
                return cardAndItem;
            }

        }
        return null;
    }

    public static CardAndItem searchCardAndItem(int itemIDOrCardID, ArrayList<CardAndItem> cardAndItems) {
        for (CardAndItem cardAndItem : cardAndItems) {
            if (cardAndItem.getID()== itemIDOrCardID) {
                return cardAndItem;
            }

        }
        return null;
    }

    public static CardAndItem searchCardAndItem(int itemIDOrCardID, Deck decks) {
        for (CardAndItem cardAndItem : decks.getCardAndItems()) {
            if (cardAndItem.getID()== itemIDOrCardID) {
                return cardAndItem;
            }
        }
        return null;
    }

    public static boolean checkNumberOfItems(ArrayList<CardAndItem> cardAndItems) {
        int itemNum = 0;
        for (CardAndItem cardAndItem : cardAndItems) {
            if (cardAndItem instanceof Item) {
                itemNum++;
            }
        }
        if (itemNum >= 3)
            return false;
        return true;
    }

}
