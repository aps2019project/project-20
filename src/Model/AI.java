package Model;

import java.util.Random;

public class AI extends Account {
    public int makeRandomNumber(int value) {
        Random rand = new Random();
        return rand.nextInt(value) + 1;
    }

    public void handleAIEvent(Account player, Battle battle,BattleGround battleGround) {
        int event = makeRandomNumber(3);
        switch (event) {
            case 1:
                //insert card
                //battle.insertIn(this,this.getMainDeck().getCards().get(makeRandomNumber(this.getMainDeck().getCards().size())).getName(),makeRandomNumber())
            case 2:
                //move card
            case 3:
                //attack
        }
    }
}
