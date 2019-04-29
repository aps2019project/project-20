package Model;

import Exceptions.TargetSelectedException_Spell;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.Random;

import static Model.AttackType.*;
import static Model.BufferOfSpells.Type.*;
import static Model.Buffer.*;

public class Buffer {

    public static int randomNumberGenerator(int supremeValueOfRange) {
        Random rand = new Random();
        return rand.nextInt(supremeValueOfRange);
    }

    public static ArrayList<Warrior> getWarriorsOfBattleGround(BattleGround battleGround){
        ArrayList<ArrayList<Asset>> ground = battleGround.getGround();
        ArrayList<Warrior> groundWarriors = null;
        for (int i = 0; i < BattleGround.getRows(); i++){
            for (int j = 0; j < BattleGround.getColumns(); j++){
                if (ground.get(i).get(j) instanceof Card)
                    groundWarriors.add((Warrior) ground.get(i).get(j));
            }
        }
        return groundWarriors;
    }

    //spells
    public void totalDisarmAction(Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == opponent && battleGround.getGround().get(x).get(y) instanceof Warrior) {
            ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(0,  DISARM_BUFF));
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void areaDispelAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        for (int i = 0; i < 2 && i + x < BattleGround.getRows(); i++) {
            for (int j = 0; j < 2 && j + y < BattleGround.getColumns(); j++) {
                if (battleGround.getGround().get(x + j).get(y + i) != null && battleGround.getGround().get(x + j).get(y + i) instanceof Warrior) {
                    for (int k = 0; k < ((Warrior) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().size(); k++) {
                        if (battleGround.getGround().get(x + j).get(y + i).getOwner() == player) {
                            if (((Warrior) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == DISARM_BUFF ||
                                    ((Warrior) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == POISON_BUFF ||
                                    ((Warrior) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == STUN_BUFF ||
                                    ((Warrior) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == WEAKNESS_BUFF_HEALTH ||
                                    ((Warrior) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == WEAKNESS_BUFF_POWER) {
                                ((Warrior) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().remove(k);
                                k--;
                            }
                        } else if (battleGround.getGround().get(x + j).get(y + i).getOwner() == opponent) {
                            if (((Warrior) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == HOLY_BUFF ||
                                    ((Warrior) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == POWER_BUFF_HEALTH) {
                                ((Warrior) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().remove(k);
                                k--;
                            }
                        }
                    }
                }
            }
        }
    }

    public void empowerAction(Account player, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == player && battleGround.getGround().get(x).get(y) instanceof Warrior) {
                ((Warrior) battleGround.getGround().get(x).get(y)).changeAP( 2);
                return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void fireballAction(Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == opponent && battleGround.getGround().get(x).get(y) instanceof Warrior) {
                ((Warrior) battleGround.getGround().get(x).get(y)).changeHP( - 4);
                return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void godStrengthAction(Account player, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == player && battleGround.getGround().get(x).get(y) instanceof Hero) {
            ((Hero) battleGround.getGround().get(x).get(y)).changeAP(4);
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void hellFireAction(BattleGround battleGround, int x, int y) {
        x--;
        y--;
        for (int i = 0; i < 2 && i + x < BattleGround.getRows(); i++) {
            for (int j = 0; j < 2 && j + y < BattleGround.getColumns(); j++) {
                if (battleGround.getEffectsPosition().get(x + j).get(y + i) != BattleGround.CellsEffect.FIRE) {
                    battleGround.getEffectsPosition().get(x + j).set(y + i, BattleGround.CellsEffect.FIRE);
                    battleGround.getEffectsLifeTimePosition().get(x + j).set(y + i, 2);
                }
            }
        }
    }

    public void lightingBoltAction(Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == opponent && battleGround.getGround().get(x).get(y) instanceof Hero) {
            ((Hero) battleGround.getGround().get(x).get(y)).changeHP( - 8);
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void poisonLakeAction(BattleGround battleGround, int x, int y) {
        x--;
        y--;
        for (int i = 0; i < 3 && i + x < BattleGround.getRows(); i++) {
            for (int j = 0; j < 3 && j + y < BattleGround.getColumns(); j++) {
                if (battleGround.getEffectsPosition().get(x + j).get(y + i) != BattleGround.CellsEffect.POISON) {
                    battleGround.getEffectsPosition().get(x + j).set(y + i, BattleGround.CellsEffect.POISON);
                    battleGround.getEffectsLifeTimePosition().get(x + j).set(y + i, 1);
                }
            }
        }
    }

    public void madnessAction(Account player, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == player && battleGround.getGround().get(x).get(y) instanceof Warrior) {
                ((Warrior) battleGround.getGround().get(x).get(y)).changeAP(4);
                ((Warrior) battleGround.getGround().get(x).get(y)).setLifeTimeChangedAP(3);
                ((Warrior) battleGround.getGround().get(x).get(y)).setAmountOfChangedAP(4);
                ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(3,  DISARM_BUFF));
                return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void allDisarmAction(Account opponent, BattleGround battleGround) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Warrior && battleGround.getGround().get(i).get(j).getOwner() == opponent) {
                    ((Warrior) battleGround.getGround().get(i).get(j)).getBufferEffected().add(new BufferOfSpells(1,  DISARM_BUFF));
                }
            }
        }
    }

    public void allPoisonAction(Account opponent, BattleGround battleGround) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Warrior && battleGround.getGround().get(i).get(j).getOwner() == opponent) {
                    ((Hero) battleGround.getGround().get(i).get(j)).getBufferEffected().add(new BufferOfSpells(4, POISON_BUFF));
                }
            }
        }
    }

    public void dispelAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y) != null && battleGround.getGround().get(x).get(y) instanceof Warrior) {
            for (int k = 0; k < ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().size(); k++) {
                if (battleGround.getGround().get(x).get(y).getOwner() == player) {
                    if (((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == DISARM_BUFF ||
                            ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == POISON_BUFF ||
                            ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == STUN_BUFF ||
                            ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == WEAKNESS_BUFF_HEALTH ||
                            ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == WEAKNESS_BUFF_POWER) {
                        ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().remove(k);
                        k--;
                    }
                } else if (battleGround.getGround().get(x).get(y).getOwner() == opponent) {
                    if (((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == HOLY_BUFF ||
                            ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == POWER_BUFF_HEALTH) {
                        ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().remove(k);
                        k--;
                    }
                }
            }
        }

    }

    public void healthWithProfitAction(Account player, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == player && battleGround.getGround().get(x).get(y) instanceof Warrior) {
                ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(0, WEAKNESS_BUFF_HEALTH, 6));
                ((Warrior) battleGround.getGround().get(x).get(y)).changeHP(((Hero) battleGround.getGround().get(x).get(y)).getHP() - 6);
                ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(3,  HOLY_BUFF));
                ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(3,  HOLY_BUFF));
                return;
        }
        throw new TargetSelectedException_Spell();

    }

    public void ghazaBokhorJoonBegiriAction(Account player, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == player && battleGround.getGround().get(x).get(y) instanceof Warrior) {
                ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(0,  POWER_BUFF_HEALTH, ((Warrior) battleGround.getGround().get(x).get(y)).getHP()));
                return;
        }
        throw new TargetSelectedException_Spell();

    }

    public void allPowerAction(Account player, BattleGround battleGround) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Warrior && battleGround.getGround().get(i).get(j).getOwner() == player) {
                    ((Warrior) battleGround.getGround().get(i).get(j)).getBufferEffected().add(new BufferOfSpells(0,  POWER_BUFF_HEALTH, 2));
                }
            }
        }
    }

    public void allAttackAction(Account opponent, BattleGround battleGround, int col) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            if (battleGround.getGround().get(i).get(col) instanceof Warrior && battleGround.getGround().get(i).get(col).getOwner() == opponent) {
                    ((Warrior) battleGround.getGround().get(i).get(col)).changeHP( - 6);
            }
        }
    }

    public void weakeningAction(Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == opponent && battleGround.getGround().get(x).get(y) instanceof Minion) {
            ((Minion) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(0,  WEAKNESS_BUFF_POWER, 4));
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void sacrificeAction(Account player, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == player && battleGround.getGround().get(x).get(y) instanceof Minion) {
            player.getMainDeck().getHero().changeHP( + ((Minion) battleGround.getGround().get(x).get(y)).getHP());
            ((Minion) battleGround.getGround().get(x).get(y)).changeHP(-((Minion) battleGround.getGround().get(x).get(y)).getHP());
//            player.getMainDeck().getCards().remove(((Minion) battleGround.getGround().get(x).get(y)).getHP());
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void kingsGuardAction(Account opponent, Account player, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        int X = player.getMainDeck().getHero().getXInGround();
        int Y = player.getMainDeck().getHero().getYInGround();
        if (battleGround.getGround().get(x).get(y).getOwner() == opponent && battleGround.getGround().get(x).get(y) instanceof Minion && (x - X <= 1 && x - X >= -1 && y - Y <= 1 && y - Y >= -1)) {
            ((Minion) battleGround.getGround().get(x).get(y)).changeHP(-((Minion) battleGround.getGround().get(x).get(y)).getHP());
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void shockAction(Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == opponent && battleGround.getGround().get(x).get(y) instanceof Warrior) {
                ((Warrior) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(2,  STUN_BUFF));
                return;
        }
        throw new TargetSelectedException_Spell();
    }


    //minions
    public void noAction() {
    }

    public void farsSwordsmanAction(Card enemyCard) {
        enemyCard.getBufferEffected().add(new BufferOfSpells(1,  STUN_BUFF));
    }

    public void farsChampionAction(Minion playerMinion, Card enemyCard) {
        for (int i = 0; i < playerMinion.getAttackedCards().size(); i++) {
            if (enemyCard == playerMinion.getAttackedCards().get(i)) {
                if (enemyCard instanceof Warrior) {
                    ((Warrior) enemyCard).changeHP( - 5 * playerMinion.getMultiplicityOfEachAttackedCard().get(i));
                }
                playerMinion.getMultiplicityOfEachAttackedCard().set(i, playerMinion.getMultiplicityOfEachAttackedCard().get(i) + 1);
                return;
            }
        }
    }

    public void farsChiefAction(Minion playerMinion, ArrayList<Card> ComboCards, Card enemyCard) {
        for (Card comboCard : ComboCards) {
            if (comboCard.getName().length() > 4 && comboCard.getName().substring(0, 3).equals("fars")) {
                if (enemyCard instanceof Warrior) {
                    ((Warrior) enemyCard).changeHP( - 4);
                }
            }
        }
    }

    public void TooraniSpyAction(Minion playerMinion, Card enemyCard) {
        enemyCard.getBufferEffected().add(new BufferOfSpells(1,  DISARM_BUFF));
        enemyCard.getBufferEffected().add(new BufferOfSpells(4,  POISON_BUFF));
    }

    public void TooraniPrinceAction(Minion playerMinion, ArrayList<Card> ComboCards, Card enemyCard) {
        for (Card comboCard : ComboCards) {
            if (comboCard.getName().length() > 7 && comboCard.getName().substring(0, 6).equals("Toorani")) {
                if (enemyCard instanceof Warrior) {
                    ((Warrior) enemyCard).changeHP( - 4);
                }
            }
        }
    }


    public void EagleAction(Minion playerMinion) {
        BufferOfSpells bufferOfSpells = new BufferOfSpells(0,  POWER_BUFF_HEALTH, 10);
    }

    public void OneEyeGiantAction(Minion playerMinion, BattleGround battleGround) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xCell = playerMinion.getXInGround() + i;
                int yCell = playerMinion.getYInGround() + j;
                if (xCell < BattleGround.getRows() && yCell < BattleGround.getColumns() && battleGround.getGround().get(xCell).get(yCell) instanceof Minion)
                    ((Minion) battleGround.getGround().get(xCell).get(yCell)).changeHP( - 2);
            }
        }

    }

    public void PoisonSnakeAction(Minion playerMinion, Minion enemyMinion) {
        BufferOfSpells bufferOfSpells = new BufferOfSpells(3, POISON_BUFF);
    }

    public void DrainLionAction(Minion playerMinion, Minion enemyMinion) {
        BufferOfSpells bufferOfSpells = new BufferOfSpells(3,  HOLY_BUFF, playerMinion.getAP() - 1);
    }

    public void giantSnakeAction(Minion playerMinion, Minion enemyMinion, BattleGround battleGround) {
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (Math.abs(i + j) <= 2) {
                    int xCell = playerMinion.getXInGround() + i;
                    int yCell = playerMinion.getYInGround() + j;
                    if (xCell < BattleGround.getRows() && yCell < BattleGround.getColumns() && battleGround.getGround().get(xCell).get(yCell) instanceof Minion) {
                        ((Minion) battleGround.getGround().get(xCell).get(yCell)).changeHP( - 1);
                    }
                }
            }
        }

    }

    public void whiteWolfAction(Minion playerMinion, Card enemyCard) {
        if (enemyCard instanceof Minion) {
            playerMinion.getBufferEffected().add( new BufferOfSpells(1,  POISON_BUFF, 6, 1));
            playerMinion.getBufferEffected().add( new BufferOfSpells(1,  POISON_BUFF, 4, 2));
        }
    }

    public void tigerAction(Minion playerMinion, Card enemyCard) {
        if (enemyCard instanceof Minion) {
            playerMinion.getBufferEffected().add( new BufferOfSpells(1, POWER_BUFF_HEALTH, 8, 1));
        }
    }

    public void WolfAction(Minion playerMinion, Card enemyCard) {
        if (enemyCard instanceof Minion) {
            playerMinion.getBufferEffected().add( new BufferOfSpells(1,  POISON_BUFF, 6, 1));
        }

    }

    public void wizardAction(Account player, Minion playerMinion, BattleGround battleGround) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xCell = playerMinion.getXInGround() + i;
                int yCell = playerMinion.getYInGround() + j;
                if (xCell < BattleGround.getRows() && yCell < BattleGround.getColumns() && battleGround.getGround().get(xCell).get(yCell) instanceof Minion && battleGround.getGround().get(xCell).get(yCell).getOwner() == player) {
                    playerMinion.getBufferEffected().add( new BufferOfSpells(1, POWER_BUFF_ATTACK, 2, 1));
                    playerMinion.getBufferEffected().add( new BufferOfSpells(1, WEAKNESS_BUFF_HEALTH, 1, 1));
                }
            }
        }
    }

    public void greatWizard(Account player, Minion playerMinion, BattleGround battleGround) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xCell = playerMinion.getXInGround() + i;
                int yCell = playerMinion.getYInGround() + j;
                if (xCell < BattleGround.getRows() && yCell < BattleGround.getColumns() && battleGround.getGround().get(xCell).get(yCell) instanceof Minion && battleGround.getGround().get(xCell).get(yCell).getOwner() == player) {
                    playerMinion.getBufferEffected().add( new BufferOfSpells(1,  POWER_BUFF_ATTACK, 2));
                    playerMinion.getBufferEffected().add( new BufferOfSpells(1,  HOLY_BUFF));
                }
            }
        }
    }

    public void elfAction(Account player, Minion playerMinion, BattleGround battleGround) {
        for (int i = 0; i <= BattleGround.getColumns(); i++) {
            for (int j = 0; j <= BattleGround.getRows(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Minion && battleGround.getGround().get(i).get(j).getOwner() == player) {
                    playerMinion.getBufferEffected().add( new BufferOfSpells(1,  POWER_BUFF_ATTACK, 1));
                }
            }
        }
    }

    public void wildBoarAction(Minion playerMinion) {
        for (int i = 0; i < playerMinion.getBufferEffected().size(); i++) {
            if (playerMinion.getBufferEffected().get(i).getType() == DISARM_BUFF ) {
                playerMinion.getBufferEffected().remove(i);
                i--;
            }
        }
    }

    public void piranAction(Minion playerMinion) {
        for (int i = 0; i < playerMinion.getBufferEffected().size(); i++) {
            if (playerMinion.getBufferEffected().get(i).getType() == POISON_BUFF) {
                playerMinion.getBufferEffected().remove(i);
                i--;
            }
        }
    }

    public void givAction(Minion playerMinion) {
        for (int i = 0; i < playerMinion.getBufferEffected().size(); i++) {
            if (playerMinion.getBufferEffected().get(i).getType() == DISARM_BUFF ||
                playerMinion.getBufferEffected().get(i).getType() == STUN_BUFF ||
                playerMinion.getBufferEffected().get(i).getType() == WEAKNESS_BUFF_HEALTH ||
                playerMinion.getBufferEffected().get(i).getType() == WEAKNESS_BUFF_POWER ||
                playerMinion.getBufferEffected().get(i).getType() == POISON_BUFF) {
                playerMinion.getBufferEffected().remove(i);
                i--;
            }
        }
    }

    public void bahmanAction(Account player, Account enemy, BattleGround battleGround) {
        int numberOfEnemyMinions = 0;
        ArrayList<Minion> enemyMinion = new ArrayList<>();
        for (int i = 0; i < BattleGround.getColumns(); i++) {
            for (int j = 0; j < BattleGround.getRows(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Minion && battleGround.getGround().get(i).get(j).getOwner() == enemy) {
                    numberOfEnemyMinions++;
                    enemyMinion.add((Minion) battleGround.getGround().get(i).get(j));
                }
            }
            int randomNumber = randomNumberGenerator(numberOfEnemyMinions) + 1;
            enemyMinion.get(randomNumber).changeHP(-16);
        }
    }

    public void ashkbosAction(Minion playerMinion, Minion enemyMinion) {
        if (enemyMinion.getAP() < playerMinion.getAP()) {
            playerMinion.changeHP(+ enemyMinion.getAP());
        }
    }

    public void twoHeadGiantAction(Minion enemyMinion) {

        for (int i = 0; i < enemyMinion.getBufferEffected().size(); i++) {
            if (enemyMinion.getBufferEffected().get(i).getType() == POWER_BUFF_ATTACK ||
                    enemyMinion.getBufferEffected().get(i).getType() == POWER_BUFF_HEALTH ||
                    enemyMinion.getBufferEffected().get(i).getType() == HOLY_BUFF ) {
                enemyMinion.getBufferEffected().remove(i);
                i--;
            }
        }
    }

    public void coldMotherAction(Minion playerMinion, Minion enemyMinion, BattleGround battleGround, Account enemy) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xCell = playerMinion.getXInGround() + i;
                int yCell = playerMinion.getYInGround() + j;
                if ((xCell == 0 && yCell == 0) && xCell < BattleGround.getRows() && yCell < BattleGround.getColumns() && battleGround.getGround().get(xCell).get(yCell) instanceof Minion && battleGround.getGround().get(xCell).get(yCell).getOwner() == enemy) {
                    playerMinion.getBufferEffected().add( new BufferOfSpells(1,  STUN_BUFF));
            }
            }
        }
    }

    public void steelArmorAction(Minion playerMinion) {
        playerMinion.getBufferEffected().add( new BufferOfSpells(1, HOLY_BUFF, 12));
    }

    public void siavashAction(Hero enemyhero) {
        enemyhero.changeHP(-6);
    }

    //Heroes
    public void whiteDamnAction(Hero theOwnHero) {
        BufferOfSpells buff = new BufferOfSpells(POWER_BUFF_ATTACK, 4);
        theOwnHero.getBufferEffected().add(buff);
    }

    public void simorghAction(Account enemy, BattleGround battleGround) {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        for (Warrior warrior: groundWarriors){
            if (warrior instanceof Warrior && warrior.getOwner() == enemy) {
                BufferOfSpells buff = new BufferOfSpells(1, STUN_BUFF, 1);
                warrior.getBufferEffected().add(buff);
            }
        }
    }

    public void sevenHeadDragonAction(Card enemyWarrior) {
        BufferOfSpells buff = new BufferOfSpells(DISARM_BUFF, 1);
        enemyWarrior.getBufferEffected().add(buff);
    }

    public void rakhshAction(Card enemyWarrior) {
        BufferOfSpells buff = new BufferOfSpells(1, STUN_BUFF, 1);
        enemyWarrior.getBufferEffected().add(buff);
    }

    public void zahhakAction(Card attackedEnemyWarrior) {
        BufferOfSpells buff = new BufferOfSpells(3, POISON_BUFF);
        attackedEnemyWarrior.getBufferEffected().add(buff);
    }

    public void kavehAction(BattleGround battleGround, int x, int y) {
        battleGround.getEffectsPosition().get(x).set(y,BattleGround.CellsEffect.HOLY);
        battleGround.getEffectsLifeTimePosition().get(x).set(y,3);
    }

    public void arashAction(Account player, BattleGround battleGround) {
        Hero theOwnHero = player.getMainDeck().getHero();
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        for (Warrior warrior: groundWarriors){
            if (warrior.getYInGround() == theOwnHero.getYInGround())
                warrior.changeHP(-4);
        }
    }

    public void legendAction(Card enemyWarrior) {
        ArrayList<BufferOfSpells> enemyWarriorBuffers = enemyWarrior.getBufferEffected();
        // TODO: The following for will throw ConcurrentModificationException and must be edited.
        for (BufferOfSpells buff: enemyWarriorBuffers){
            if (buff.getType() == POWER_BUFF_ATTACK || buff.getType() == POWER_BUFF_HEALTH || buff.getType() == HOLY_BUFF)
                enemyWarriorBuffers.remove(buff);
        }
    }

    public void esfandiarAction(Hero theOwnHero) {
        BufferOfSpells buff = new BufferOfSpells(HOLY_BUFF, 3);
        theOwnHero.getBufferEffected().add(buff);
    }

    //Items
    public void knowledgeCrownAction(Account player, int turn) {
        //TODO
    }

    public void namoos_e_separAction(Hero theOwnHero) {
        BufferOfSpells buff = new BufferOfSpells(HOLY_BUFF, 12);
        theOwnHero.getBufferEffected().add(buff);
    }

    public void damoolArchAction(Hero theOwnHero, Card enemyWarrior) {
        if (theOwnHero.getAttackType() == RANGED || theOwnHero.getAttackType() == HYBRID) {
            BufferOfSpells buff = new BufferOfSpells(1, DISARM_BUFF, 1);
            enemyWarrior.getBufferEffected().add(buff);
        }
    }

    public void nooshdaroo(BattleGround battleGround) {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        if (groundWarriors.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(groundWarriors.size());
            groundWarriors.get(randomWarriorIndex).changeHP(6);
        }
    }

    public void twoHornArrowAction(BattleGround battleGround) {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        ArrayList<Warrior> rangedOrHybrids = null;
        for (Warrior warrior: groundWarriors){
            if (warrior.getAttackType() == RANGED || warrior.getAttackType() == HYBRID)
                rangedOrHybrids.add(warrior);
        }
        if (rangedOrHybrids.size() > 0){
            int randomWarriorIndex = randomNumberGenerator(rangedOrHybrids.size());
            rangedOrHybrids.get(randomWarriorIndex).changeAP(2);
        }
    }

    public void simorghWingAction(Account enemy) {
        Hero enemyHero = enemy.getMainDeck().getHero();
        if (enemyHero.getAttackType() == RANGED || enemyHero.getAttackType() == HYBRID)
            enemyHero.changeAP(-2);
    }

    public void elixirAction(BattleGround battleGround, Warrior collector) {
        collector.changeHP(3);

        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        ArrayList<Minion> minions = null;
        for (Warrior warrior: groundWarriors){
            if (warrior instanceof Minion)
                minions.add((Minion) warrior);
        }
        if (minions.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(minions.size());
            BufferOfSpells buff = new BufferOfSpells(POWER_BUFF_ATTACK, 3);
            minions.get(randomWarriorIndex).getBufferEffected().add(buff);
        }
    }

    public void manaMixtureAction() {
        //TODO
    }

    public void invulnerableMixtureAction(BattleGround battleGround) {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        if (groundWarriors.size() > 0){
            int randomWarriorIndex = randomNumberGenerator(groundWarriors.size());
            for (int i = 1; i <= 10; i++) {
                BufferOfSpells buff = new BufferOfSpells(2, HOLY_BUFF);
                groundWarriors.get(randomWarriorIndex).getBufferEffected().add(buff);
            }
        }
    }

    public void deathCurseAction(BattleGround battleGround) {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        ArrayList<Minion> minions = null;
        for (Warrior warrior: groundWarriors){
            if (warrior instanceof Minion)
                minions.add((Minion) warrior);
        }
        if (minions.size() > 0) {
            int randomMinionIndex = randomNumberGenerator(minions.size());
            //TODO
        }
    }


    public void randomDamageAction(BattleGround battleGround) {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        if (groundWarriors.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(groundWarriors.size());
            groundWarriors.get(randomWarriorIndex).changeAP(2);
        }
    }

    public void terrorHoodAction(Account enemy, BattleGround battleGround) {
        // This function must be called when one of the own Warriors strike.
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        ArrayList<Warrior> enemyWarriors = null;
        for (Warrior warrior: groundWarriors){
            if (warrior.getOwner() == enemy)
                enemyWarriors.add(warrior);
        }
        if (enemyWarriors.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(groundWarriors.size());
            BufferOfSpells buff = new BufferOfSpells(1, WEAKNESS_BUFF_POWER, 2);
            enemyWarriors.get(randomWarriorIndex).getBufferEffected().add(buff);
        }
    }

    public void bladesOfAgilityAction(BattleGround battleGround) {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        if (groundWarriors.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(groundWarriors.size());
            groundWarriors.get(randomWarriorIndex).changeAP(6);
        }
    }

    public void kingWisdomAction(Account player) {
        //TODO
    }

    public void assassinationDaggerAction(Account enemy) {
        enemy.getMainDeck().getHero().changeHP(-1);
    }

    public void poisonousDaggerAction(Account enemy, BattleGround battleGround) {
        //TODO: Only when own warrior strikes enemy.
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        ArrayList<Warrior> enemyWarriors = null;
        for (Warrior warrior: groundWarriors){
            if (warrior.getOwner() == enemy)
                enemyWarriors.add(warrior);
        }
        if (enemyWarriors.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(groundWarriors.size());
            BufferOfSpells buff = new BufferOfSpells(1, POISON_BUFF);
            enemyWarriors.get(randomWarriorIndex).getBufferEffected().add(buff);
        }
    }

    public void shockHammerAction(Account player, Warrior attackedEnemyWarrior) {
        //TODO: Only when the own hero attacks enemy's warrior.
        BufferOfSpells buff = new BufferOfSpells(1, DISARM_BUFF);
        attackedEnemyWarrior.getBufferEffected().add(buff);
    }

    public void soulEaterAction(Account player, Warrior targetOwnWarrior) {
        //TODO: It must be called when an own warrior dies.
        BufferOfSpells buff = new BufferOfSpells(POWER_BUFF_ATTACK, 1);
        targetOwnWarrior.getBufferEffected().add(buff);
    }

    public void baptismAction(Account player) {
        //TODO: when an own minion is spawned, it receives HOLY_BUFF for 2 turns.
    }

    public void chineseSwordAction(Warrior collector) {
        if (collector.getAttackType() == MELEE)
            collector.changeAP(5);
    }
}