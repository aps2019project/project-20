package Model;

import Exceptions.TargetSelectedException_Spell;

import java.util.ArrayList;
import java.util.Random;

import static Model.BufferOfSpells.*;
import static Model.BufferOfSpells.Type.*;

public class Buffer {

    //AtFirst each Asset needs one function which needs his necessary parameters

    //spells
    public void totalDisarmAction(Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == opponent && battleGround.getGround().get(x).get(y) instanceof Card) {
            ((Card) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(0,  BufferOfSpells.Type.DISARM_BUFF));
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void areaDispelAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        for (int i = 0; i < 2 && i + x < BattleGround.getRows(); i++) {
            for (int j = 0; j < 2 && j + y < BattleGround.getCols(); j++) {
                if (battleGround.getGround().get(x + j).get(y + i) != null && battleGround.getGround().get(x + j).get(y + i) instanceof Card) {
                    for (int k = 0; k < ((Card) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().size(); k++) {
                        if (battleGround.getGround().get(x + j).get(y + i).getOwner() == player) {
                            if (((Card) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == BufferOfSpells.Type.DISARM_BUFF ||
                                    ((Card) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == POISON_BUFF ||
                                    ((Card) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == BufferOfSpells.Type.STUN_BUFF ||
                                    ((Card) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == BufferOfSpells.Type.WEAKNESS_BUFF_HEALTH ||
                                    ((Card) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == BufferOfSpells.Type.WEAKNESS_BUFF_POWER) {
                                ((Card) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().remove(k);
                                k--;
                            }
                        } else if (battleGround.getGround().get(x + j).get(y + i).getOwner() == opponent) {
                            if (((Card) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == BufferOfSpells.Type.HOLY_BUFF ||
                                    ((Card) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().get(k).getType() == POWER_BUFF_HEALTH) {
                                ((Card) battleGround.getGround().get(x + j).get(y + i)).getBufferEffected().remove(k);
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
        if (battleGround.getGround().get(x).get(y).getOwner() == player && battleGround.getGround().get(x).get(y) instanceof Card) {
            if (battleGround.getGround().get(x).get(y) instanceof Hero) {
                ((Hero) battleGround.getGround().get(x).get(y)).setAP(((Hero) battleGround.getGround().get(x).get(y)).getAP() + 2);
                return;
            }
            if (battleGround.getGround().get(x).get(y) instanceof Minion) {
                ((Minion) battleGround.getGround().get(x).get(y)).setAP(((Minion) battleGround.getGround().get(x).get(y)).getAP() + 2);
                return;
            }
            throw new TargetSelectedException_Spell();
        }
        throw new TargetSelectedException_Spell();
    }

    public void fireballAction(Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == opponent && battleGround.getGround().get(x).get(y) instanceof Card) {
            if (battleGround.getGround().get(x).get(y) instanceof Hero) {
                ((Hero) battleGround.getGround().get(x).get(y)).setHP(((Hero) battleGround.getGround().get(x).get(y)).getHP() - 4);
                return;
            }
            if (battleGround.getGround().get(x).get(y) instanceof Minion) {
                ((Minion) battleGround.getGround().get(x).get(y)).setHP(((Minion) battleGround.getGround().get(x).get(y)).getHP() - 4);
                return;
            }
            throw new TargetSelectedException_Spell();
        }
        throw new TargetSelectedException_Spell();
    }

    public void godStrengthAction(Account player, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == player && battleGround.getGround().get(x).get(y) instanceof Hero) {
            ((Hero) battleGround.getGround().get(x).get(y)).setAP(((Hero) battleGround.getGround().get(x).get(y)).getAP() + 4);
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void hellFireAction(BattleGround battleGround, int x, int y) {
        x--;
        y--;
        for (int i = 0; i < 2 && i + x < BattleGround.getRows(); i++) {
            for (int j = 0; j < 2 && j + y < BattleGround.getCols(); j++) {
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
            ((Hero) battleGround.getGround().get(x).get(y)).setHP(((Hero) battleGround.getGround().get(x).get(y)).getHP() - 8);
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void poisonLakeAction(BattleGround battleGround, int x, int y) {
        x--;
        y--;
        for (int i = 0; i < 3 && i + x < BattleGround.getRows(); i++) {
            for (int j = 0; j < 3 && j + y < BattleGround.getCols(); j++) {
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
        if (battleGround.getGround().get(x).get(y).getOwner() == player && battleGround.getGround().get(x).get(y) instanceof Card) {
            if (battleGround.getGround().get(x).get(y) instanceof Hero) {
                ((Hero) battleGround.getGround().get(x).get(y)).setAP(((Hero) battleGround.getGround().get(x).get(y)).getAP() + 4);
                ((Hero) battleGround.getGround().get(x).get(y)).setLifeTimeChangedAP(3);
                ((Hero) battleGround.getGround().get(x).get(y)).setAmountOfChangedAP(4);
                ((Hero) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(3,  BufferOfSpells.Type.DISARM_BUFF));
                return;
            }
            if (battleGround.getGround().get(x).get(y) instanceof Minion) {
                ((Minion) battleGround.getGround().get(x).get(y)).setAP(((Minion) battleGround.getGround().get(x).get(y)).getAP() + 4);
                ((Minion) battleGround.getGround().get(x).get(y)).setLifeTimeChangedAP(3);
                ((Minion) battleGround.getGround().get(x).get(y)).setAmountOfChangedAP(4);
                ((Minion) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(3,  BufferOfSpells.Type.DISARM_BUFF));
                return;
            }
            throw new TargetSelectedException_Spell();
        }
        throw new TargetSelectedException_Spell();
    }

    public void allDisarmAction(Account opponent, BattleGround battleGround) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getCols(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Card && battleGround.getGround().get(i).get(j).getOwner() == opponent) {
                    ((Card) battleGround.getGround().get(i).get(j)).getBufferEffected().add(new BufferOfSpells(1,  BufferOfSpells.Type.DISARM_BUFF));
                }
            }
        }
    }

    public void allPoisonAction(Account opponent, BattleGround battleGround) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getCols(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Card && battleGround.getGround().get(i).get(j).getOwner() == opponent) {
                    ((Hero) battleGround.getGround().get(i).get(j)).getBufferEffected().add(new BufferOfSpells(4, POISON_BUFF));
                }
            }
        }
    }

    public void dispelAction(Account player, Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y) != null && battleGround.getGround().get(x).get(y) instanceof Card) {
            for (int k = 0; k < ((Card) battleGround.getGround().get(x).get(y)).getBufferEffected().size(); k++) {
                if (battleGround.getGround().get(x).get(y).getOwner() == player) {
                    if (((Card) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == BufferOfSpells.Type.DISARM_BUFF ||
                            ((Card) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == POISON_BUFF ||
                            ((Card) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == BufferOfSpells.Type.STUN_BUFF ||
                            ((Card) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == BufferOfSpells.Type.WEAKNESS_BUFF_HEALTH ||
                            ((Card) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == BufferOfSpells.Type.WEAKNESS_BUFF_POWER) {
                        ((Card) battleGround.getGround().get(x).get(y)).getBufferEffected().remove(k);
                        k--;
                    }
                } else if (battleGround.getGround().get(x).get(y).getOwner() == opponent) {
                    if (((Card) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == BufferOfSpells.Type.HOLY_BUFF ||
                            ((Card) battleGround.getGround().get(x).get(y)).getBufferEffected().get(k).getType() == POWER_BUFF_HEALTH) {
                        ((Card) battleGround.getGround().get(x).get(y)).getBufferEffected().remove(k);
                        k--;
                    }
                }
            }
        }

    }

    public void healthWithProfitAction(Account player, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == player && battleGround.getGround().get(x).get(y) instanceof Card) {
            if (battleGround.getGround().get(x).get(y) instanceof Hero) {
                ((Hero) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(0, BufferOfSpells.Type.WEAKNESS_BUFF_HEALTH, 6));
                ((Hero) battleGround.getGround().get(x).get(y)).setHP(((Hero) battleGround.getGround().get(x).get(y)).getHP() - 6);
                ((Hero) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(3,  BufferOfSpells.Type.HOLY_BUFF));
                ((Hero) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(3,  BufferOfSpells.Type.HOLY_BUFF));
                return;
            }
            if (battleGround.getGround().get(x).get(y) instanceof Minion) {
                ((Minion) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(0,  BufferOfSpells.Type.WEAKNESS_BUFF_HEALTH));
                ((Minion) battleGround.getGround().get(x).get(y)).setHP(((Hero) battleGround.getGround().get(x).get(y)).getHP() - 6);
                ((Minion) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(3,  BufferOfSpells.Type.HOLY_BUFF));
                ((Minion) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(3,  BufferOfSpells.Type.HOLY_BUFF));
                return;
            }
            throw new TargetSelectedException_Spell();
        }
        throw new TargetSelectedException_Spell();

    }

    public void ghazaBokhorJoonBegiriAction(Account player, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == player && battleGround.getGround().get(x).get(y) instanceof Card) {
            if (battleGround.getGround().get(x).get(y) instanceof Hero) {
                ((Hero) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(0,  POWER_BUFF_HEALTH, ((Hero) battleGround.getGround().get(x).get(y)).getHP()));
                return;
            }
            if (battleGround.getGround().get(x).get(y) instanceof Minion) {
                ((Minion) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(0, POWER_BUFF_HEALTH, ((Minion) battleGround.getGround().get(x).get(y)).getHP()));
                return;
            }
            throw new TargetSelectedException_Spell();
        }
        throw new TargetSelectedException_Spell();

    }

    public void allPowerAction(Account player, BattleGround battleGround) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getCols(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Card && battleGround.getGround().get(i).get(j).getOwner() == player) {
                    ((Card) battleGround.getGround().get(i).get(j)).getBufferEffected().add(new BufferOfSpells(0,  POWER_BUFF_HEALTH, 2));
                }
            }
        }
    }

    public void allAttackAction(Account opponent, BattleGround battleGround, int col) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            if (battleGround.getGround().get(i).get(col) instanceof Card && battleGround.getGround().get(i).get(col).getOwner() == opponent) {
                if (battleGround.getGround().get(i).get(col) instanceof Hero) {
                    ((Hero) battleGround.getGround().get(i).get(col)).setHP(((Hero) battleGround.getGround().get(i).get(col)).getHP() - 6);

                } else if (battleGround.getGround().get(i).get(col) instanceof Minion) {
                    ((Minion) battleGround.getGround().get(i).get(col)).setHP(((Minion) battleGround.getGround().get(i).get(col)).getHP() - 6);

                }
            }
        }
    }

    public void weakeningAction(Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == opponent && battleGround.getGround().get(x).get(y) instanceof Minion) {
            ((Minion) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(0,  BufferOfSpells.Type.WEAKNESS_BUFF_POWER, 4));
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void sacrificeAction(Account player, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == player && battleGround.getGround().get(x).get(y) instanceof Minion) {
            player.getMainDeck().getHero().setHP(player.getMainDeck().getHero().getHP() + ((Minion) battleGround.getGround().get(x).get(y)).getHP());
            ((Minion) battleGround.getGround().get(x).get(y)).setHP(0);
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
            ((Minion) battleGround.getGround().get(x).get(y)).setHP(0);
            return;
        }
        throw new TargetSelectedException_Spell();
    }

    public void shockAction(Account opponent, BattleGround battleGround, int x, int y) {
        x--;
        y--;
        if (battleGround.getGround().get(x).get(y).getOwner() == opponent && battleGround.getGround().get(x).get(y) instanceof Card) {
            if (battleGround.getGround().get(x).get(y) instanceof Hero) {
                ((Hero) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(2,  BufferOfSpells.Type.STUN_BUFF));
                return;
            }
            if (battleGround.getGround().get(x).get(y) instanceof Minion) {
                ((Minion) battleGround.getGround().get(x).get(y)).getBufferEffected().add(new BufferOfSpells(2,  BufferOfSpells.Type.STUN_BUFF));
                return;
            }
            throw new TargetSelectedException_Spell();
        }
        throw new TargetSelectedException_Spell();
    }


    //minions
    public void noAction() {
    }

    public void farsSwordsmanAction(Card enemyCard) {
        enemyCard.getBufferEffected().add(new BufferOfSpells(1,  BufferOfSpells.Type.STUN_BUFF));
    }

    public void farsChampionAction(Minion playerMinion, Card enemyCard) {
        for (int i = 0; i < playerMinion.getAttackedCards().size(); i++) {
            if (enemyCard == playerMinion.getAttackedCards().get(i)) {
                if (enemyCard instanceof Hero) {
                    ((Hero) enemyCard).setHP(((Hero) enemyCard).getHP() - 5 * playerMinion.getMultiplicityOfEachAttackedCard().get(i));
                } else if (enemyCard instanceof Minion) {
                    ((Minion) enemyCard).setHP(((Minion) enemyCard).getHP() - 5 * playerMinion.getMultiplicityOfEachAttackedCard().get(i));
                }
                playerMinion.getMultiplicityOfEachAttackedCard().set(i, playerMinion.getMultiplicityOfEachAttackedCard().get(i) + 1);
                return;
            }
        }
    }

    public void farsChiefAction(Minion playerMinion, ArrayList<Card> ComboCards, Card enemyCard) {
        for (Card comboCard : ComboCards) {
            if (comboCard.getName().substring(0, 3).equals("fars")) {
                if (enemyCard instanceof Hero) {
                    ((Hero) enemyCard).setHP(((Hero) enemyCard).getHP() - 4);
                } else if (enemyCard instanceof Minion) {
                    ((Minion) enemyCard).setHP(((Minion) enemyCard).getHP() - 4);
                }
            }
        }
    }

    public void TooraniSpyAction(Minion playerMinion, Card enemyCard) {
        enemyCard.getBufferEffected().add(new BufferOfSpells(1,  BufferOfSpells.Type.DISARM_BUFF));
        enemyCard.getBufferEffected().add(new BufferOfSpells(4,  POISON_BUFF));
    }

    public void TooraniPrinceAction(Minion playerMinion, ArrayList<Card> ComboCards, Card enemyCard) {
        for (Card comboCard : ComboCards) {
            if (comboCard.getID() == 3010 || comboCard.getID() == 3009 || comboCard.getID() == 3008 || comboCard.getID() == 3007 || comboCard.getID() == 3006) {
                if (enemyCard instanceof Hero) {
                    ((Hero) enemyCard).setHP(((Hero) enemyCard).getHP() - 4);
                } else if (enemyCard instanceof Minion) {
                    ((Minion) enemyCard).setHP(((Minion) enemyCard).getHP() - 4);
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
                if (xCell < BattleGround.getRows() && yCell < BattleGround.getCols() && battleGround.getGround().get(xCell).get(yCell) instanceof Minion)
                    ((Minion) battleGround.getGround().get(xCell).get(yCell)).setHP(((Minion) battleGround.getGround().get(xCell).get(yCell)).getHP() - 2);
            }
        }

    }

    public void PoisonSnakeAction(Minion playerMinion, Minion enemyMinion) {
        BufferOfSpells bufferOfSpells = new BufferOfSpells(3, POISON_BUFF);
    }

    public void DrainLionAction(Minion playerMinion, Minion enemyMinion) {
        BufferOfSpells bufferOfSpells = new BufferOfSpells(3,  BufferOfSpells.Type.HOLY_BUFF, playerMinion.getAP() - 1);
    }

    public void giantSnakeAction(Minion playerMinion, Minion enemyMinion, BattleGround battleGround) {
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (Math.abs(i + j) <= 2) {
                    int xCell = playerMinion.getXInGround() + i;
                    int yCell = playerMinion.getYInGround() + j;
                    if (xCell < BattleGround.getRows() && yCell < BattleGround.getCols() && battleGround.getGround().get(xCell).get(yCell) instanceof Minion) {
                        ((Minion) battleGround.getGround().get(xCell).get(yCell)).setHP(((Minion) battleGround.getGround().get(xCell).get(yCell)).getHP() - 1);
                    }
                }
            }
        }

    }

    public void whiteWolfAction(Minion minion, Card enemyCard) {
        if (enemyCard instanceof Minion) {
            BufferOfSpells bufferOfSpells = new BufferOfSpells(1,  POISON_BUFF, 6, 1);
            BufferOfSpells bufferOfSpells1 = new BufferOfSpells(1,  POISON_BUFF, 4, 2);
        }
    }

    public void tigerAction(Minion playerMinion, Card enemyCard) {
        if (enemyCard instanceof Minion) {
            BufferOfSpells bufferOfSpells = new BufferOfSpells(1, POWER_BUFF_HEALTH, 8, 1);
        }
    }

    public void WolfAction(Minion playerMinion, Card enemyCard) {
        if (enemyCard instanceof Minion) {
            BufferOfSpells bufferOfSpells = new BufferOfSpells(1,  POISON_BUFF, 6, 1);
        }

    }

    public void wizardAction(Account player, Minion playerMinion, BattleGround battleGround) {

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xCell = playerMinion.getXInGround() + i;
                int yCell = playerMinion.getYInGround() + j;
                if (xCell < BattleGround.getRows() && yCell < BattleGround.getCols() && battleGround.getGround().get(xCell).get(yCell) instanceof Minion && battleGround.getGround().get(xCell).get(yCell).getOwner() == player) {
                    BufferOfSpells bufferOfSpells = new BufferOfSpells(1, POWER_BUFF_ATTACK, 2, 1);
                    BufferOfSpells bufferOfSpells1 = new BufferOfSpells(1, WEAKNESS_BUFF_HEALTH, 1, 1);
                }
            }
        }
    }

    public void greatWizard(Account player, Minion playerMinion, BattleGround battleGround) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xCell = playerMinion.getXInGround() + i;
                int yCell = playerMinion.getYInGround() + j;
                if (xCell < BattleGround.getRows() && yCell < BattleGround.getCols() && battleGround.getGround().get(xCell).get(yCell) instanceof Minion && battleGround.getGround().get(xCell).get(yCell).getOwner() == player) {
                    BufferOfSpells bufferOfSpells = new BufferOfSpells(1,  POWER_BUFF_ATTACK, 2);
                    BufferOfSpells bufferOfSpells1 = new BufferOfSpells(1,  HOLY_BUFF);
                }
            }
        }
    }

    public void elfAction(Account player, Minion playerMinion, BattleGround battleGround) {
        for (int i = 0; i <= BattleGround.getCols(); i++) {
            for (int j = 0; j <= BattleGround.getRows(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Minion && battleGround.getGround().get(i).get(j).getOwner() == player) {
                    BufferOfSpells bufferOfSpells = new BufferOfSpells(1,  POWER_BUFF_ATTACK, 1);
                }
            }
        }
    }

    public void wildBoarAction(Minion playerMinion) {
        playerMinion.setDisarm(false);
    }

    public void piranAction(Minion playerMinion) {
        playerMinion.setPoision(false);
    }

    public void givAction(Minion playerMinion) {
        for (int i = 0; i < playerMinion.getBufferEffected().size(); i++) {
            if (playerMinion.getBufferEffected().get(i).equals(DISARM_BUFF) ||
                playerMinion.getBufferEffected().get(i).equals(STUN_BUFF) ||
                playerMinion.getBufferEffected().get(i).equals(WEAKNESS_BUFF_HEALTH) ||
                playerMinion.getBufferEffected().get(i).equals(WEAKNESS_BUFF_POWER) ||
                playerMinion.getBufferEffected().get(i).equals(POISON_BUFF)) {
                playerMinion.getBufferEffected().remove(i);
                i--;
            }
        }
    }

    public void bahmanAction(Account player, Account enemy, BattleGround battleGround) {
        int numberOfEnemyMinions = 0;
        ArrayList<Minion> enemyMinion = new ArrayList<>();
        for (int i = 0; i <= BattleGround.getCols(); i++) {
            for (int j = 0; j <= BattleGround.getRows(); j++) {
                if (battleGround.getGround().get(i).get(j) instanceof Minion && battleGround.getGround().get(i).get(j).getOwner() == enemy) {
                    numberOfEnemyMinions++;
                    enemyMinion.add((Minion) battleGround.getGround().get(i).get(j));
                }
            }
            Random rand = new Random();
            int randomNumber = rand.nextInt(numberOfEnemyMinions) + 1;
            enemyMinion.get(randomNumber).setHP(enemyMinion.get(randomNumber).getHP() - 16);


        }
    }

    public void ashkbosAction(Minion playerMinion, Minion enemyMinion) {
        if (enemyMinion.getAP() < playerMinion.getAP()) {
            playerMinion.setHP(playerMinion.getHP() + enemyMinion.getAP());
        }
    }

    public void twoHeadGiantAction() {
    }

    public void coldMotherAction(Minion playerMinion, Minion enemyMinion, BattleGround battleGround, Account enemy) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xCell = playerMinion.getXInGround() + i;
                int yCell = playerMinion.getYInGround() + j;
                if ((xCell == 0 && yCell == 00) && xCell < BattleGround.getRows() && yCell < BattleGround.getCols() && battleGround.getGround().get(xCell).get(yCell) instanceof Minion && battleGround.getGround().get(xCell).get(yCell).getOwner() == enemy) {
                    BufferOfSpells bufferOfSpells = new BufferOfSpells(1,  STUN_BUFF);
            }
            }
        }
    }

    public void steelArmorAction(Minion playerMinion) {
        BufferOfSpells bufferOfSpells = new BufferOfSpells(1, HOLY_BUFF, 12);
    }

    public void siavashAction(Hero enemyhero) {
        enemyhero.setHP(enemyhero.getHP() - 6);
    }
}
