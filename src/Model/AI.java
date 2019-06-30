
package Model;

import java.util.*;

import static Model.Minion.ActivateTimeOfSpecialPower.*;

public class AI extends Account {

    public AI(String userName, String password) {
        super(userName, password);
    }

    public void handleAIEvent(Account player, Battle battle) {
        try {
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
                    battle.endTurn(this);

                    event1 = makeRandomNumber(3);
                    switch (event1) {
                        case 1:
                            //attack

                            AIAttackPlayerHero(player, battle);
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
                    battle.endTurn(this);
                    break;

                case 2:

                    int event2 = makeRandomNumber(3);

                    switch (event2) {
                        case 1:
                            //attack

                            AIAttackWarriors(player, battle);
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
                    battle.endTurn(this);
                    event2 = makeRandomNumber(3);
                    switch (event2) {
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
                    battle.endTurn(this);
                    break;

                case 3:
                    int event3 = makeRandomNumber(3);

                    switch (event3) {
                        case 1:
                            //insert card

                            insertAICard(battle);
                            break;
                        case 2:
                            //attack

                            AIAttackPlayerHero(player, battle);
                            break;
                        case 3:
                            //use special power

                            AIUseSpecialPower(battle);
                            break;
                    }

                    event3 = makeRandomNumber(3);
                    switch (event3) {
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
                    battle.endTurn(this);
                    break;

            }
        } catch (Exception e) {
            battle.endTurn(this);
        }
    }

    public int makeRandomNumber(int value) {
        Random rand = new Random();
        return rand.nextInt(value) + 1;
    }

    public void AIUseSpecialPower(Battle battle) {
        // battle.applySpecialPower(this, makeRandomNumber(Controller.BattleGroundController.getRows()), makeRandomNumber(Controller.BattleGroundController.getColumns()));
    }

    public void AIComboAttack(Account player, Battle battle) {
        Asset playerAsset;
        ArrayList<Minion> minions = new ArrayList<>();
        while (true) {
            playerAsset = battle.getBattleGround().getGround().get(BattleGround.getColumns()).get(BattleGround.getRows());
            if (playerAsset.getOwner() == player)
                break;
        }
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Minion minion = (Minion) battle.getBattleGround().getGround().get(i).get(j);
                if (minion.getOwner() == this && minion.getActivateTimeOfSpecialPower() == COMBO) {
                    minions.add(minion);
                }
            }
        }
        battle.attackCombo(this, minions, (Warrior) playerAsset);
    }

    public void AIAttackWarriors(Account player, Battle battle) {
        Asset attacker;
        attacker = findAttacker(battle);
        Asset playerCard = findPlayerMinion(player, battle);
        battle.attack(this, (Warrior) attacker, (Warrior) playerCard);
    }

    public void AIAttackWeakWarriors(Account player, Battle battle) {
        Asset attacker;
        attacker = findAttacker(battle);
        Asset playerCard = findPlayerWeakAsset(player, battle);
        battle.attack(this, (Warrior) attacker, (Warrior) playerCard);
    }

    public Asset findPlayerMinion(Account player, Battle battle) {
        Asset playerCard;
        while (true) {
            playerCard = battle.getBattleGround().getGround().get(BattleGround.getColumns()).get(BattleGround.getRows());
            if (playerCard instanceof Warrior && playerCard.getOwner() == player)
                break;
        }
        return playerCard;
    }

    public void AIAttackPlayerHero(Account player, Battle battle) {
        Asset attacker = findAttacker(battle);
        battle.attack(this, (Warrior) attacker, (Warrior) findPlayerHero(player, battle));
    }

    public void AIAttackWithStrongestAsset(Account player, Battle battle) {
        Asset attacker = findAIStrongAsset(battle);
        battle.attack(this, (Warrior) attacker, (Warrior) findPlayerMinion(player, battle));
    }

    public void moveAICard(Battle battle) {

        Asset moveWarrior = findPlayerMinion(this, battle);
        battle.cardMoveTo(this, (Warrior) moveWarrior, BattleGround.getColumns(), BattleGround.getRows());

    }

    public void selectAICard(Battle battle) {

        Asset selectedCard;
        while (true) {
            selectedCard = battle.getBattleGround().getGround().get(makeRandomNumber(BattleGround.getColumns())).get(BattleGround.getRows());
            if (selectedCard.getOwner() == this)
                break;
        }
        battle.selectWarrior(this, selectedCard.getID());

    }

    public void insertAICard(Battle battle) {

        Card[] AIHand = battle.getPlayersHand()[1];

        Card insertedCard = AIHand[makeRandomNumber(battle.getNUMBER_OF_CARDS_IN_HAND()) - 1];
        battle.insertCard(this, insertedCard.getName(), makeRandomNumber(BattleGround.getColumns())
                , makeRandomNumber(BattleGround.getRows()));
        battle.selectWarrior(this, insertedCard.getID());

    }

    public Asset findPlayerHero(Account player, Battle battle) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battle.getBattleGround().getGround().get(i).get(j).getOwner() == player
                        && battle.getBattleGround().getGround().get(i).get(j) instanceof Hero)
                    return battle.getBattleGround().getGround().get(i).get(j);
            }
        }
        return null;
    }

    public Asset findAttacker(Battle battle) {
        Asset attacker;
        while (true) {
            attacker = battle.getBattleGround().getGround().get(BattleGround.getColumns()).get(BattleGround.getRows());
            if (attacker instanceof Warrior && attacker.getOwner() == this)
                break;
        }
        return attacker;
    }

    public Asset findAIStrongAsset(Battle battle) {
        ArrayList<Asset> assets = new ArrayList<>();
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battle.getBattleGround().getGround().get(i).get(j).getOwner() == this)
                    assets.add(battle.getBattleGround().getGround().get(i).get(j));
            }
        }
        if (assets.size() == 0)
            return null;
        else {
            Asset strongAsset = assets.get(0);
            for (Asset asset : assets) {
                if (((Warrior) asset).getAP() >= ((Warrior) strongAsset).getAP())
                    strongAsset = asset;
            }
            return strongAsset;
        }
    }

    public Asset findPlayerWeakAsset(Account player, Battle battle) {
        ArrayList<Asset> playerAssets = new ArrayList<>();
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battle.getBattleGround().getGround().get(i).get(j).getOwner() == player)
                    playerAssets.add(battle.getBattleGround().getGround().get(i).get(j));
            }
        }
        if (playerAssets.size() == 0)
            return null;
        else {
            Asset weakAsset = playerAssets.get(0);
            for (Asset asset : playerAssets) {
                if (((Warrior) asset).getHP() <= ((Warrior) weakAsset).getHP())
                    weakAsset = asset;
            }
            return weakAsset;
        }
    }
}