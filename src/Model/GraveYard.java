package Model;

import Exceptions.*;

import java.util.ArrayList;

public class GraveYard {
    private ArrayList<Card> deadCard = new ArrayList<>();
    private Account owner;

    private int searchInGraveYard(String cardName) {
        for (Card card : deadCard) {
            if (card.getName().equals(cardName)) {
                return card.getID();
            }
        }
        throw new CardNotFoundInGraveYardException();
    }


    public ArrayList<Card> getDeadCard() {
        return deadCard;
    }

    public void setDeadCard(ArrayList<Card> deadCard) {
        this.deadCard = deadCard;
    }
}
