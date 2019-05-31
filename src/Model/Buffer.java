package Model;

import Exceptions.*;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static Model.AttackType.*;
import static Model.BattleGround.CellsEffect.HOLY;
import static Model.BufferOfSpells.Type.*;

public class Buffer {
    public static int randomNumberGenerator(int supremeValueOfRange) {
        Random rand = new Random();
        return rand.nextInt(supremeValueOfRange);
    }

    public static ArrayList<Warrior> getWarriorsOfBattleGround(@NotNull BattleGround battleGround) {
        ArrayList<ArrayList<Asset>> ground = battleGround.getGround();
        ArrayList<Warrior> groundWarriors = null;
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (ground.get(i).get(j) instanceof Card)
                    groundWarriors.add((Warrior) ground.get(i).get(j));
            }
        }
        return groundWarriors;
    }

    //spells
    public void totalDisarmAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(y).get(x).getOwner() == opponent && battleGround.getGround().get(y).get(x) instanceof Warrior) {
            ((Warrior) battleGround.getGround().get(y).get(x)).getBufferEffected().add(new BufferOfSpells(DISARM_BUFF, 1, false));
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void areaDispelAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        for (int i = 0; i < 2 && i + x < BattleGround.getColumns(); i++) {
            for (int j = 0; j < 2 && j + y < BattleGround.getRows(); j++) {
                if (battleGround.getGround().get(y + j).get(x + i) != null && battleGround.getGround().get(y + j).get(x + i) instanceof Warrior) {
                    Warrior warrior = (Warrior) battleGround.getGround().get(y + j).get(x + i);
                    Iterator<BufferOfSpells> iterator = warrior.getBufferEffected().iterator();
                    while (iterator.hasNext()) {
                        BufferOfSpells bufferOfSpells = iterator.next();
                        if (battleGround.getGround().get(y + j).get(x + i).getOwner() == player) {
                            if (bufferOfSpells.getType() == DISARM_BUFF || bufferOfSpells.getType() == POISON_BUFF ||
                                    bufferOfSpells.getType() == STUN_BUFF || bufferOfSpells.getType() == WEAKNESS_BUFF_HEALTH ||
                                    bufferOfSpells.getType() == WEAKNESS_BUFF_ATTACK) {
                                if (bufferOfSpells.isContinuous())
                                    bufferOfSpells.changeTurnCountdownUntilActivation(1);
                                else {
                                    bufferOfSpells.deactivate(warrior);
                                    iterator.remove();
                                }
                            }
                        } else if (battleGround.getGround().get(y + j).get(x + i).getOwner() == opponent) {
                            if (bufferOfSpells.getType() == HOLY_BUFF || bufferOfSpells.getType() == POWER_BUFF_HEALTH
                                    || bufferOfSpells.getType() == POWER_BUFF_ATTACK) {
                                if (bufferOfSpells.isContinuous())
                                    bufferOfSpells.changeTurnCountdownUntilActivation(1);
                                else {
                                    bufferOfSpells.deactivate(warrior);
                                    iterator.remove();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void empowerAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(y).get(x).getOwner() == player && battleGround.getGround().get(y).get(x) instanceof Warrior) {
            ((Warrior) battleGround.getGround().get(y).get(x)).changeAP(2);
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void fireballAction(Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(y).get(x).getOwner() == opponent && battleGround.getGround().get(y).get(x) instanceof Warrior) {
            ((Warrior) battleGround.getGround().get(y).get(x)).changeHP(-4);
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void godStrengthAction(Account player, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(y).get(x).getOwner() == player && battleGround.getGround().get(y).get(x) instanceof Hero) {
            ((Hero) battleGround.getGround().get(y).get(x)).changeAP(4);
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void hellFireAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        for (int i = 0; i < 2 && i + x < BattleGround.getColumns(); i++) {
            for (int j = 0; j < 2 && j + y < BattleGround.getRows(); j++) {
                if (battleGround.getEffectsPosition().get(y + j).get(x + i) != BattleGround.CellsEffect.FIRE) {
                    battleGround.getEffectsPosition().get(y + j).set(x + i, BattleGround.CellsEffect.FIRE);
                    battleGround.getEffectsLifeTimePosition().get(y + j).set(x + i, 2);
                }
            }
        }
    }

    public void lightingBoltAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(y).get(x).getOwner() == opponent && battleGround.getGround().get(y).get(x) instanceof Hero) {
            ((Hero) battleGround.getGround().get(y).get(x)).changeHP(-8);
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void poisonLakeAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        for (int i = 0; i < 3 && i + x < BattleGround.getRows(); i++) {
            for (int j = 0; j < 3 && j + y < BattleGround.getColumns(); j++) {
                if (battleGround.getEffectsPosition().get(y + j).get(x + i) != BattleGround.CellsEffect.POISON) {
                    battleGround.getEffectsPosition().get(y + j).set(x + i, BattleGround.CellsEffect.POISON);
                    battleGround.getEffectsLifeTimePosition().get(y + j).set(x + i, 1);
                }
            }
        }
    }

    public void madnessAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(y).get(x).getOwner() == player && battleGround.getGround().get(y).get(x) instanceof Warrior) {
            Warrior warrior = (Warrior) battleGround.getGround().get(y).get(x);
            warrior.getBufferEffected().add(new BufferOfSpells(3, POWER_BUFF_ATTACK, 4));
            warrior.getBufferEffected().add(new BufferOfSpells(3, DISARM_BUFF));
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void allDisarmAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Warrior && battleGround.getGround().get(i).get(j).getOwner() == opponent)
                    ((Warrior) battleGround.getGround().get(i).get(j)).getBufferEffected().add(new BufferOfSpells(1, DISARM_BUFF));
            }
        }
    }

    public void allPoisonAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Warrior && battleGround.getGround().get(i).get(j).getOwner() == opponent)
                    ((Hero) battleGround.getGround().get(i).get(j)).getBufferEffected().add(new BufferOfSpells(4, POISON_BUFF));
            }
        }
    }

    public void dispelAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        Warrior targetWarrior;
        if (battleGround.getGround().get(y).get(x) != null && battleGround.getGround().get(y).get(x) instanceof Warrior) {
            targetWarrior = (Warrior) battleGround.getGround().get(y).get(x);
            Iterator<BufferOfSpells> iterator = targetWarrior.getBufferEffected().iterator();
            while (iterator.hasNext()) {
                BufferOfSpells bufferOfSpells = iterator.next();
                if (targetWarrior.getOwner() == player)
                    if (bufferOfSpells.getType() == DISARM_BUFF || bufferOfSpells.getType() == POISON_BUFF ||
                            bufferOfSpells.getType() == STUN_BUFF || bufferOfSpells.getType() == WEAKNESS_BUFF_HEALTH ||
                            bufferOfSpells.getType() == WEAKNESS_BUFF_ATTACK) {
                        if (bufferOfSpells.isContinuous())
                            bufferOfSpells.changeTurnCountdownUntilActivation(1);
                        else {
                            bufferOfSpells.deactivate(targetWarrior);
                            iterator.remove();
                        }
                    } else if (targetWarrior.getOwner() == opponent) {
                        if (bufferOfSpells.getType() == HOLY_BUFF || bufferOfSpells.getType() == POWER_BUFF_HEALTH) {
                            if (bufferOfSpells.isContinuous())
                                bufferOfSpells.changeTurnCountdownUntilActivation(1);
                            else {
                                bufferOfSpells.deactivate(targetWarrior);
                                iterator.remove();
                            }
                        }
                    }
            }
        }

    }

    public void healthWithProfitAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        Warrior targetWarrior;
        if (battleGround.getGround().get(y).get(x).getOwner() == player && battleGround.getGround().get(y).get(x) instanceof Warrior) {
            targetWarrior = (Warrior) battleGround.getGround().get(y).get(x);
            targetWarrior.getBufferEffected().add(new BufferOfSpells(WEAKNESS_BUFF_HEALTH, 6, false));
            targetWarrior.getBufferEffected().add(new BufferOfSpells(3, HOLY_BUFF, 2));
            return;
        }
        throw new TargetSelectedException_Spell();

    }

    public void powerUpAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(y).get(x).getOwner() == player && battleGround.getGround().get(y).get(x) instanceof Warrior) {
            ((Warrior) battleGround.getGround().get(y).get(x)).getBufferEffected().add(new BufferOfSpells(POWER_BUFF_ATTACK, 6, false));
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void allPowerAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Warrior && battleGround.getGround().get(i).get(j).getOwner() == player)
                    ((Warrior) battleGround.getGround().get(i).get(j)).getBufferEffected().add(new BufferOfSpells(POWER_BUFF_ATTACK, 2, false));
            }
        }
    }

    public void allAttackAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            if (battleGround.getGround().get(i).get(x) instanceof Warrior && battleGround.getGround().get(i).get(x).getOwner() == opponent) {
                ((Warrior) battleGround.getGround().get(i).get(x)).changeHP(-6);
            }
        }
    }

    public void weakeningAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(y).get(x).getOwner() == opponent && battleGround.getGround().get(y).get(x) instanceof Minion) {
            ((Minion) battleGround.getGround().get(y).get(x)).getBufferEffected().add(new BufferOfSpells(WEAKNESS_BUFF_ATTACK, 4, false));
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void sacrificeAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(y).get(x).getOwner() == player && battleGround.getGround().get(y).get(x) instanceof Minion) {
            Minion minion = (Minion) battleGround.getGround().get(y).get(x);
            minion.getBufferEffected().add(new BufferOfSpells(WEAKNESS_BUFF_HEALTH, 6, false));
            minion.getBufferEffected().add(new BufferOfSpells(POWER_BUFF_ATTACK, 8, false));
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void kingsGuardAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        int X = player.getMainDeck().getHero().getXInGround();
        int Y = player.getMainDeck().getHero().getYInGround();
        if (battleGround.getGround().get(y).get(x).getOwner() == opponent && battleGround.getGround().get(y).get(x) instanceof Minion && (x - X <= 1 && x - X >= -1 && y - Y <= 1 && y - Y >= -1)) {
            ((Minion) battleGround.getGround().get(y).get(x)).changeHP(-((Minion) battleGround.getGround().get(y).get(x)).getHP());
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void shockAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(y).get(x).getOwner() == opponent && battleGround.getGround().get(y).get(x) instanceof Warrior) {
            ((Warrior) battleGround.getGround().get(y).get(x)).getBufferEffected().add(new BufferOfSpells(2, STUN_BUFF));
            return;
        }
        throw new TargetSelectedException_Spell();
    }


    //minions
    public void noAction() {
    }

    public void farsSwordsmanAction(Minion playerMinion, Warrior enemyWarrior) {
        enemyWarrior.getBufferEffected().add(new BufferOfSpells(1, STUN_BUFF));
    }

    public void farsChampionAction(Minion playerMinion, Warrior enemyWarrior) {
        for (int i = 0; i < playerMinion.getAttackedCards().size(); i++) {
            if (enemyWarrior == playerMinion.getAttackedCards().get(i)) {
                enemyWarrior.changeHP(-5 * playerMinion.getMultiplicityOfEachAttackedCard().get(i));
                playerMinion.getMultiplicityOfEachAttackedCard().set(i, playerMinion.getMultiplicityOfEachAttackedCard().get(i) + 1);
                return;
            }
        }
    }

    public void farsChiefAction(Minion playerMinion, ArrayList<Card> ComboCards, Card enemyCard) {
        for (Card comboCard : ComboCards) {
            if (comboCard.getName().length() > 4 && comboCard.getName().substring(0, 3).equals("fars")) {
                if (enemyCard instanceof Warrior) {
                    ((Warrior) enemyCard).changeHP(-4);
                }
            }
        }
    }

    public void TooraniSpyAction(Minion playerMinion, Warrior enemyWarrior) {
        enemyWarrior.getBufferEffected().add(new BufferOfSpells(1, DISARM_BUFF));
        enemyWarrior.getBufferEffected().add(new BufferOfSpells(4, POISON_BUFF));
    }

    public void TooraniPrinceAction(Minion playerMinion, ArrayList<Card> ComboCards, Card enemyCard) {
        for (Card comboCard : ComboCards) {
            if (comboCard.getName().length() > 7 && comboCard.getName().substring(0, 6).equals("Toorani")) {
                if (enemyCard instanceof Warrior) {
                    ((Warrior) enemyCard).changeHP(-4);
                }
            }
        }
    }

    public void EagleAction(Account player, Minion playerMinion, BattleGround battleGround) {
        playerMinion.getBufferEffected().add(new BufferOfSpells(POWER_BUFF_HEALTH, 10, false));
    }

    public void oneEyeGiantAction(Warrior playerWarrior, BattleGround battleGround) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xCell = playerWarrior.getXInGround() + i;
                int yCell = playerWarrior.getYInGround() + j;
                if (xCell < BattleGround.getColumns() && yCell < BattleGround.getRows() && battleGround.getGround().get(yCell).get(xCell) instanceof Minion)
                    ((Minion) battleGround.getGround().get(yCell).get(xCell)).changeHP(-2);
            }
        }
    }

    public void PoisonSnakeAction(Minion playerMinion, Warrior enemyWarrior) {
        enemyWarrior.getBufferEffected().add(new BufferOfSpells(3, POISON_BUFF));
    }

    public void drainLionAction(Minion playerMinion, Warrior enemyWarrior) {
        BufferOfSpells bufferOfSpells = new BufferOfSpells(3, HOLY_BUFF, playerMinion.getAP() - 1);
        playerMinion.getBufferEffected().add(bufferOfSpells);
    }

    public void giantSnakeAction(Account enemy, Minion playerMinion, BattleGround battleGround) {
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (Math.abs(i + j) <= 2) {
                    int xCell = playerMinion.getXInGround() + i;
                    int yCell = playerMinion.getYInGround() + j;
                    if (xCell < BattleGround.getRows() && yCell < BattleGround.getColumns() && battleGround.getGround().get(yCell).get(xCell) instanceof Minion) {
                        ((Minion) battleGround.getGround().get(yCell).get(xCell)).changeHP(-1);
                    }
                }
            }
        }

    }

    public void whiteWolfAction(Minion playerMinion, Warrior enemyWarrior) {
        if (enemyWarrior instanceof Minion) {
            playerMinion.getBufferEffected().add(new BufferOfSpells(1, POISON_BUFF, 6, 1));
            playerMinion.getBufferEffected().add(new BufferOfSpells(1, POISON_BUFF, 4, 2));
        }
    }

    public void tigerAction(Minion playerMinion, Warrior enemyWarrior) {
        if (enemyWarrior instanceof Minion)
            playerMinion.getBufferEffected().add(new BufferOfSpells(1, WEAKNESS_BUFF_HEALTH, 8, 1));
    }

    public void wolfAction(Minion playerMinion, Warrior enemyWarrior) {
        if (enemyWarrior instanceof Minion) {
            playerMinion.getBufferEffected().add(new BufferOfSpells(1, POISON_BUFF, 6, 1));
        }
    }

    public void wizardAction(Account player, Minion playerMinion, BattleGround battleGround) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xCell = playerMinion.getXInGround() + i;
                int yCell = playerMinion.getYInGround() + j;
                if (xCell < BattleGround.getRows() && yCell < BattleGround.getColumns() && battleGround.getGround().get(yCell).get(xCell) instanceof Minion && battleGround.getGround().get(yCell).get(xCell).getOwner() == player) {
                    playerMinion.getBufferEffected().add(new BufferOfSpells(1, POWER_BUFF_ATTACK, 2));
                    playerMinion.getBufferEffected().add(new BufferOfSpells(1, WEAKNESS_BUFF_HEALTH, 1));
                }
            }
        }
    }

    public void greatWizard(Account player, Minion playerMinion, BattleGround battleGround) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xCell = playerMinion.getXInGround() + i;
                int yCell = playerMinion.getYInGround() + j;
                if (xCell < BattleGround.getRows() && yCell < BattleGround.getColumns() && battleGround.getGround().get(yCell).get(xCell) instanceof Minion && battleGround.getGround().get(yCell).get(xCell).getOwner() == player) {
                    playerMinion.getBufferEffected().add(new BufferOfSpells(POWER_BUFF_ATTACK, 2, true));
                    playerMinion.getBufferEffected().add(new BufferOfSpells(HOLY_BUFF, true));
                }
            }
        }
    }

    public void elfAction(Account player, Minion playerMinion, BattleGround battleGround) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Minion && battleGround.getGround().get(i).get(j).getOwner() == player)
                    playerMinion.getBufferEffected().add(new BufferOfSpells(POWER_BUFF_ATTACK, 1, true));
            }
        }
    }

    public void bahmanAction(Account enemy, Minion minion, BattleGround battleGround) {
        int numberOfEnemyMinions = 0;
        ArrayList<Minion> enemyMinion = new ArrayList<>();
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Minion && battleGround.getGround().get(i).get(j).getOwner() == enemy) {
                    numberOfEnemyMinions++;
                    enemyMinion.add((Minion) battleGround.getGround().get(i).get(j));
                }
            }
            int randomNumber = randomNumberGenerator(numberOfEnemyMinions) + 1;
            enemyMinion.get(randomNumber).changeHP(-16);
        }
    }

    public void twoHeadGiantAction(Minion playerMinion, Warrior enemyWarrior) {
        Iterator<BufferOfSpells> iterator = enemyWarrior.getBufferEffected().iterator();
        while (iterator.hasNext()) {
            BufferOfSpells bufferOfSpells = iterator.next();
            if (bufferOfSpells.getType() == POWER_BUFF_HEALTH || bufferOfSpells.getType() == POWER_BUFF_ATTACK || bufferOfSpells.getType() == HOLY_BUFF) {
                bufferOfSpells.deactivate(enemyWarrior);
                iterator.remove();
            }
        }
    }

    public void coldMotherAction(Account enemy, Minion playerMinion, BattleGround battleGround) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xCell = playerMinion.getXInGround() + i;
                int yCell = playerMinion.getYInGround() + j;
                if ((xCell == 0 && yCell == 0) && xCell < BattleGround.getRows() && yCell < BattleGround.getColumns() && battleGround.getGround().get(yCell).get(xCell) instanceof Minion && battleGround.getGround().get(yCell).get(xCell).getOwner() == enemy) {
                    playerMinion.getBufferEffected().add(new BufferOfSpells(1, STUN_BUFF));
                }
            }
        }
    }

    public void steelArmorAction(Account player, Minion playerMinion, BattleGround battleGround) {
        playerMinion.getBufferEffected().add(new BufferOfSpells(HOLY_BUFF, 12, true));
    }

    public void siavashAction(Warrior enemyhero) {
        enemyhero.changeHP(-6);
    }

    //Heroes
    public void whiteGoblinAction(Account player) {
        Hero theOwnHero = player.getMainDeck().getHero();
        theOwnHero.getBufferEffected().add(new BufferOfSpells(POWER_BUFF_ATTACK, 4, false));
    }

    public void simorghAction(Account enemy, BattleGround battleGround) {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        for (Warrior warrior : groundWarriors) {
            if (warrior.getOwner() == enemy) {
                warrior.getBufferEffected().add(new BufferOfSpells(1, STUN_BUFF, 1));
            }
        }
    }

    public void sevenHeadDragonAction(Card enemyWarrior) {
        enemyWarrior.getBufferEffected().add(new BufferOfSpells(DISARM_BUFF, 1, false));
    }

    public void rakhshAction(Card enemyWarrior) {
        enemyWarrior.getBufferEffected().add(new BufferOfSpells(1, STUN_BUFF, 1));
    }

    public void zahhakAction(Card attackedEnemyWarrior) {
        attackedEnemyWarrior.getBufferEffected().add(new BufferOfSpells(3, POISON_BUFF));
    }

    public void kavehAction(BattleGround battleGround, int x, int y) {
        battleGround.getEffectsPosition().get(y).set(x, HOLY);
        battleGround.getEffectsLifeTimePosition().get(y).set(x, 3);
    }

    public void arashAction(Account player, BattleGround battleGround) {
        Hero theOwnHero = player.getMainDeck().getHero();
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        for (Warrior warrior : groundWarriors) {
            if (warrior.getYInGround() == theOwnHero.getYInGround())
                warrior.changeHP(-4);
        }
    }

    public void legendAction(Warrior enemyWarrior) {
        ArrayList<BufferOfSpells> enemyWarriorBuffers = enemyWarrior.getBufferEffected();
        Iterator<BufferOfSpells> iterator = enemyWarriorBuffers.iterator();
        while (iterator.hasNext()) {
            BufferOfSpells bufferOfSpells = iterator.next();
            if (bufferOfSpells.getType() == POWER_BUFF_ATTACK || bufferOfSpells.getType() == POWER_BUFF_HEALTH || bufferOfSpells.getType() == HOLY_BUFF) {
                if (bufferOfSpells.isContinuous())
                    bufferOfSpells.changeTurnCountdownUntilActivation(1);
                else {
                    bufferOfSpells.deactivate(enemyWarrior);
                    iterator.remove();
                }
            }
        }
    }

    public void esfandiarAction(Hero theOwnHero) {
        theOwnHero.getBufferEffected().add(new BufferOfSpells(HOLY_BUFF, 3, true));
    }

    //Items
    //TODO: Items' methods call must be edited.
    Battle battle;
    BattleGround battleGround;
    public void knowledgeCrownAction(Account player) {
        int playerIndex = 0;
        if (player == battle.getPlayers()[1])
            playerIndex = 1;
        battle.getPlayersManaBuffEffected()[playerIndex].add(new BufferOfSpells(3, MANA_BUFF, 1));
    }

    public void namoos_e_separAction(Hero theOwnHero) {
        theOwnHero.getBufferEffected().add(new BufferOfSpells(HOLY_BUFF, 12, false));
    }

    public void damoolArchAction(Hero theOwnHero, Card enemyWarrior) {
        if (theOwnHero.getAttackType() == RANGED || theOwnHero.getAttackType() == HYBRID) {
            enemyWarrior.getBufferEffected().add(new BufferOfSpells(1, DISARM_BUFF, 1));
        }
    }

    public void nooshdaroo() {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        if (groundWarriors.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(groundWarriors.size());
            groundWarriors.get(randomWarriorIndex).changeHP(6);
        }
    }

    public void twoHornArrowAction() {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        ArrayList<Warrior> rangedOrHybrids = null;
        for (Warrior warrior : groundWarriors) {
            if (warrior.getAttackType() == RANGED || warrior.getAttackType() == HYBRID)
                rangedOrHybrids.add(warrior);
        }
        if (rangedOrHybrids.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(rangedOrHybrids.size());
            rangedOrHybrids.get(randomWarriorIndex).changeAP(2);
        }
    }

    public void simorghWingAction(Account enemy) {
        Hero enemyHero = enemy.getMainDeck().getHero();
        if (enemyHero.getAttackType() == RANGED || enemyHero.getAttackType() == HYBRID)
            enemyHero.changeAP(-2);
    }

    public void elixirAction( Warrior collector) {
        collector.changeHP(3);

        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        ArrayList<Minion> minions = null;
        for (Warrior warrior : groundWarriors) {
            if (warrior instanceof Minion)
                minions.add((Minion) warrior);
        }
        if (minions.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(minions.size());
            minions.get(randomWarriorIndex).getBufferEffected().add(new BufferOfSpells(POWER_BUFF_ATTACK, 3, false));
        }
    }

    public void manaMixtureAction( Warrior collector) {
        int playerIndex = 0;
        if (collector.getOwner() == battle.getPlayers()[1])
            playerIndex = 1;
        battle.getPlayersManaBuffEffected()[playerIndex].add(new BufferOfSpells(1, MANA_BUFF, 3, 1));
    }

    public void invulnerableMixtureAction() {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        if (groundWarriors.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(groundWarriors.size());
            for (int i = 1; i <= 10; i++)
                groundWarriors.get(randomWarriorIndex).getBufferEffected().add(new BufferOfSpells(2, HOLY_BUFF));
        }
    }

    public void deathCurseAction() {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        ArrayList<Minion> minions = null;
        for (Warrior warrior : groundWarriors) {
            if (warrior instanceof Minion)
                minions.add((Minion) warrior);
        }
        if (minions.size() > 0) {
            int randomMinionIndex = randomNumberGenerator(minions.size());

        }
    }

    public void randomDamageAction() {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        if (groundWarriors.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(groundWarriors.size());
            groundWarriors.get(randomWarriorIndex).changeAP(2);
        }
    }

    public void terrorHoodAction(Account enemy) {
        // This function must be called when one of the own Warriors strike.
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        ArrayList<Warrior> enemyWarriors = null;
        for (Warrior warrior : groundWarriors) {
            if (warrior.getOwner() == enemy)
                enemyWarriors.add(warrior);
        }
        if (enemyWarriors.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(groundWarriors.size());
            enemyWarriors.get(randomWarriorIndex).getBufferEffected().add(new BufferOfSpells(1, WEAKNESS_BUFF_ATTACK, 2));
        }
    }

    public void bladesOfAgilityAction() {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        if (groundWarriors.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(groundWarriors.size());
            groundWarriors.get(randomWarriorIndex).changeAP(6);
        }
    }

    public void kingWisdomAction(Account player) {
        int playerIndex = 0;
        if (player == battle.getPlayers()[1])
            playerIndex = 1;
        battle.getPlayersManaBuffEffected()[playerIndex].add(new BufferOfSpells(MANA_BUFF, 1, false));
    }

    public void assassinationDaggerAction(Account enemy) {
        enemy.getMainDeck().getHero().changeHP(-1);
    }

    public void poisonousDaggerAction(Account enemy) {
        ArrayList<Warrior> groundWarriors = getWarriorsOfBattleGround(battleGround);
        ArrayList<Warrior> enemyWarriors = null;
        for (Warrior warrior : groundWarriors) {
            if (warrior.getOwner() == enemy)
                enemyWarriors.add(warrior);
        }
        if (enemyWarriors.size() > 0) {
            int randomWarriorIndex = randomNumberGenerator(groundWarriors.size());
            enemyWarriors.get(randomWarriorIndex).getBufferEffected().add(new BufferOfSpells(1, POISON_BUFF));
        }
    }

    public void shockHammerAction(Warrior attackedEnemyWarrior) {
        attackedEnemyWarrior.getBufferEffected().add(new BufferOfSpells(1, DISARM_BUFF));
    }

    public void soulEaterAction(Account enemy, Warrior targetOwnWarrior) {
        if (targetOwnWarrior.getOwner() == enemy) {
            targetOwnWarrior.getBufferEffected().add(new BufferOfSpells(POWER_BUFF_ATTACK, 1, false));
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void baptismAction(Minion spawnedMinion) {
        spawnedMinion.getBufferEffected().add(new BufferOfSpells(2, HOLY_BUFF));
    }

    public void chineseSwordAction(Warrior collector) {
        if (collector.getAttackType() == MELEE)
            collector.changeAP(5);
    }
}