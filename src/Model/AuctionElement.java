package Model;

import Exceptions.AssetNotFoundException;
import Exceptions.AuctionElementNotFoundException;
import Exceptions.AuctionNotSuccessfulException;
import java.util.ArrayList;

public class AuctionElement {
    private static final Integer DEFAULT_TIME_WAITING = 25000;
    private Asset auctionAsset;
    private Account customer;
    private Integer recommendedPrice;

    public Account[] auctionResult() {
        if (getCustomer() == null) {
            throw new AuctionNotSuccessfulException();
        }
        Account[] accounts = new Account[2];
        getCustomer().setBudget(getCustomer().getBudget() - getRecommendedPrice());
        getAuctionAsset().getOwner().setBudget(getAuctionAsset().getOwner().getBudget() + getRecommendedPrice());
        getAuctionAsset().getOwner().getCollection().getAssets().remove(getAuctionAsset());
        for (Deck deck : getAuctionAsset().getOwner().getDecks()) {
            try {
                deck.searchAssetInDeck(getAuctionAsset().getID());
            } catch (AssetNotFoundException e) {
                continue;
            }
            deck.removeFromDeck(getAuctionAsset().getID());
            if (getAuctionAsset().getOwner().getMainDeck()!=null && getAuctionAsset().getOwner().getMainDeck().getName().equals(deck.getName())) {
                getAuctionAsset().getOwner().setMainDeck(null);
            }
        }
        accounts[0] = getAuctionAsset().getOwner();
        getCustomer().getCollection().getAssets().add(getAuctionAsset());
        getAuctionAsset().setOwner(getCustomer());
        accounts[1] = getAuctionAsset().getOwner();
        return accounts;
    }

    public AuctionElement findInCollection(ArrayList<AuctionElement> auctionElements) {
        for (AuctionElement auctionElement : auctionElements) {
            if (auctionElement.getAuctionAsset().getOwner().getName().equals(this.getAuctionAsset().getOwner().getName()) && auctionElement.getAuctionAsset().getName().equals(this.getAuctionAsset().getName())) {
                return auctionElement;
            }
        }
        throw new AuctionElementNotFoundException();
    }

    public AuctionElement(Asset auctionAsset) {
        this.auctionAsset = auctionAsset;
    }

    public AuctionElement(Asset auctionAsset, Account customer, Integer recommendedPrice) {
        this.auctionAsset = auctionAsset;
        this.customer = customer;
        this.recommendedPrice = recommendedPrice;
    }

    public Asset getAuctionAsset() {
        return auctionAsset;
    }

    public void setAuctionAsset(Asset auctionAsset) {
        this.auctionAsset = auctionAsset;
    }

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }

    public Integer getRecommendedPrice() {
        return recommendedPrice;
    }

    public void setRecommendedPrice(Integer recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
    }

    public static Integer getDefaultTimeWaiting() {
        return DEFAULT_TIME_WAITING;
    }
}
