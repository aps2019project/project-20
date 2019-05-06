package Model;

import Exceptions.*;

import java.util.ArrayList;

public class GraveYard {
    private ArrayList<Card> deadCard = new ArrayList<>();
    private Account owner;

    public Card searchInGraveYard(String cardName) {
        for (Card card : deadCard) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        throw new CardNotFoundInGraveYardException();
    }


    public ArrayList<Card> getDeadCards() {
        return deadCard;
    }

    public void setDeadCard(ArrayList<Card> deadCard) {
        this.deadCard = deadCard;
    }
}
