package Model;

import java.util.*;

public class AI extends Account {
    public void handleAIEvent(Account player, Battle battle) {

        int key = makeRandomNumber(3);
        switch (key) {
            case 1:
                int event1 = makeRandomNumber(3);

                switch (event1) {
                    case 1:  //insert card


                        insertAICard(battle);
                        break;
                    case 2:
                        //select card

                        selectAICard(battle);
                        break;
                    case 3:
                        //move card

                        moveAICard(battle);
                        break;
                }

                event1 = makeRandomNumber(3);
                switch (event1) {
                    case 1:
                        //attack

                        AIAttack(battle);
                        break;
                    case 2:
                        //combo attack

                        AIComboAttack(player, battle);
                        break;
                    case 3:
                        //use special power

                        AIUseSpecialPower(battle);
                        break;
                }
                break;
            case 2:
                int event3 = makeRandomNumber(3);

                switch (event3) {
                    case 1:
                        //attack

                        AIAttack(battle);
                        break;

                    case 2:
                        //insert card

                        insertAICard(battle);
                        break;
                    case 3:
                        //select card

                        selectAICard(battle);
                        break;
                }

                event3 = makeRandomNumber(3);
                switch (event3) {
                    case 1:
                        //use special power

                        AIUseSpecialPower(battle);
                        break;

                    case 2:
                        //move card

                        moveAICard(battle);
                        break;

                    case 3:
                        //combo attack

                        AIComboAttack(player, battle);
                        break;

                }
            default:
                int event2 = makeRandomNumber(3);

                switch (event2) {
                    case 1:
                        //insert card

                        insertAICard(battle);
                        break;
                    case 2:
                        //attack

                        AIAttack(battle);
                        break;
                    case 3:
                        //use special power

                        AIUseSpecialPower(battle);
                        break;
                }

                event2 = makeRandomNumber(3);
                switch (event2) {
                    case 1:
                        //select card

                        selectAICard(battle);
                        break;
                    case 2:
                        //combo attack

                        AIComboAttack(player, battle);
                        break;
                    case 3:
                        //move card

                        moveAICard(battle);
                        break;
                }
        }
    }

    public int makeRandomNumber(int value) {
        Random rand = new Random();
        return rand.nextInt(value) + 1;
    }

    public void AIUseSpecialPower(Battle battle) {
        battle.useSpecialPower(this, makeRandomNumber(BattleGround.getRows()), makeRandomNumber(BattleGround.getColumns()));
    }

    public void AIComboAttack(Account player, Battle battle) {
        Asset playerAsset;
//        Warrior[] warriors = new Warrior[50];
        ArrayList<Warrior> warriors = new ArrayList<>();
        int index = 0;
        while (true) {
            playerAsset = battle.getBattleGround().getGround().get(BattleGround.getColumns()).get(BattleGround.getRows());
            if (playerAsset.getOwner() == player)
                break;
        }
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Warrior warrior = (Warrior) battle.getBattleGround().getGround().get(i).get(j);
                if (warrior.getOwner() == this && warrior.getActivateTimeOfSpecialPower() == Warrior.ActivateTimeOfSpecialPower.COMBO) {
                    warriors.add(warrior);
                    index++;
                }
            }
        }
        battle.attackCombo(this, warriors, playerAsset.getID());
    }

    public void AIAttack(Battle battle) {
        Asset attacker;
        Asset playerCard;
        while (true) {
            attacker = battle.getBattleGround().getGround().get(BattleGround.getColumns()).get(BattleGround.getRows());
            if (attacker instanceof Warrior && attacker.getOwner() == this)
                break;
        }
        while (true) {
            playerCard = battle.getBattleGround().getGround().get(BattleGround.getColumns()).get(BattleGround.getRows());
            if (playerCard instanceof Warrior && playerCard.getOwner() == this)
                break;
        }
        battle.attack(this, (Warrior) attacker, playerCard.getID());
    }

    public void moveAICard(Battle battle) {
        Asset moveWarrior;
        while (true) {
            moveWarrior = battle.getBattleGround().getGround().get(BattleGround.getColumns()).get(BattleGround.getRows());
            if (moveWarrior instanceof Warrior && moveWarrior.getOwner() == this)
                break;
        }
        battle.cardMoveTo(this, (Warrior) moveWarrior, BattleGround.getColumns(), BattleGround.getRows());
    }

    public void selectAICard(Battle battle) {
        Asset selectedCard;
        while (true) {
            selectedCard = battle.getBattleGround().getGround().get(makeRandomNumber(BattleGround.getColumns())).get(BattleGround.getRows());
            if (selectedCard.getOwner() == this)
                break;
        }
        battle.selectCard(this, selectedCard.getID(), ((Warrior) selectedCard).getInGameID());
    }

    public void insertAICard(Battle battle) {
        Card[] AIHand = battle.getPlayersHand()[1];

        Card insertedCard = AIHand[makeRandomNumber(battle.getNUMBER_OF_CARDS_IN_HAND()) - 1];
        battle.insertIn(this, insertedCard.getName(), makeRandomNumber(BattleGround.getColumns())
                , makeRandomNumber(BattleGround.getRows()), battle.getBattleGround());
        battle.selectCard(this, insertedCard.getID(), insertedCard.getInGameID());
    }
}
