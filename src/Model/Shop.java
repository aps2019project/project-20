package Model;

import java.util.ArrayList;
import java.util.InputMismatchException;

import static Model.CardAndItem.searchCardAndItem;

public class Shop {
    private static ArrayList<CardAndItem> cardAndItems = new ArrayList<>();

    public static void sell(Account account, int itemIDOrCardID) {
        CardAndItem card = searchCardAndItem(itemIDOrCardID, cardAndItems);
        if(card==null){
            throw new NullPointerException();
        }
        else{
            account.setBudget(account.getBudget() + card.getPrice());
            account.getCardAndItemCollection().getCardAndItems().remove(card);

        }


    }

    public static void buy(Account account, String name) {
        CardAndItem newCard = searchCardAndItem(name, cardAndItems);
        if(newCard==null){
            throw new NullPointerException();
        }else if(account.getBudget() - newCard.getPrice()<0){
            throw new IndexOutOfBoundsException();
        }
        else if(newCard instanceof Item) {
            if (!CardAndItem.checkNumberOfItems(cardAndItems)) {
                throw new InputMismatchException();
            }
        }
         else {
            account.setBudget(account.getBudget() - newCard.getPrice());
            account.getCardAndItemCollection().getCardAndItems().add(newCard);
        }

}


    public static int  searchInMyCollection(Account account,String cardAndItemName) {
        if(searchCardAndItem(cardAndItemName,account.getCardAndItemCollection().getCardAndItems())==null)
            throw new IndexOutOfBoundsException();
        else
            return searchCardAndItem(cardAndItemName,account.getCardAndItemCollection().getCardAndItems()).getID();
    }

    public static int searchInShop(String itemAndCardName) {
        if(searchCardAndItem(itemAndCardName,cardAndItems)==null)
            throw new IndexOutOfBoundsException();
        else
            return searchCardAndItem(itemAndCardName,cardAndItems).getID();

    }

}
