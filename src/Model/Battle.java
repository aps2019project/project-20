package Model;

import Exceptions.*;
import Model.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Battle {
    private final int FIRST_LATE_TURN = 15;
    private final int MAX_MANA_IN_LATE_TURNS = 9;
    private int eachPlayerManaAtFirstOfTurn = 2;
    private final int NUMBER_OF_CARDS_IN_HAND = 5;
    private final int EXIT = 1234;
    private final int CONTINUE = 12345;
    private final int PRIZE = 1000;
    private int turn;
    private Account[] players = new Account[2];
    private BattleGround battleGround;
    private int[] playersMana = {eachPlayerManaAtFirstOfTurn, eachPlayerManaAtFirstOfTurn};
    private ArrayList<BufferOfSpells>[] playersManaBuffEffected = new ArrayList[2];

    {
        playersManaBuffEffected[0] = new ArrayList<>();
        playersManaBuffEffected[1] = new ArrayList<>();
    }

    private Card[] playersSelectedCard = new Card[2];
    private Item[] playersSelectedItem = new Item[2];
    private Card[][] playersHand = new Card[2][NUMBER_OF_CARDS_IN_HAND];

    {
        for (int i = 0; i <= 1; i++)
            for (int j = 0; j < NUMBER_OF_CARDS_IN_HAND; j++) {
                int nextCardFromDeckIndex = players[i].getMainDeck().getNextCardFromDeckIndex();
                playersHand[i][j] = players[i].getMainDeck().getCards().get(nextCardFromDeckIndex);
                players[i].getMainDeck().setNextCardFromDeckIndex(nextCardFromDeckIndex + 1);
            }
    }

    private Card[] playersNextCardFromDeck = new Card[2];
    private GraveYard[] playersGraveYard = new GraveYard[2];
    private int battleID;

    public static void gameInfo() {
    }

    public void selectCard(Account player, int cardID) {
        int playerIndex = 0;
        Warrior warrior;
        if (player == players[1])
            playerIndex = 1;
        try {
            warrior = searchWarriorInBattleGround(cardID);
        } catch (AssetNotFoundException e) {
            throw new AssetNotFoundException("Invalid card id");
        }
        playersSelectedCard[playerIndex] = warrior;
    }

    public void cardMoveTo(Account player, Warrior warrior, int x, int y) {
        if (!isValidCoordinates(x, y))
            throw new InvalidTargetException("Invalid target");
        int playerIndex = 0;
        x--;
        y--;
        if (player == players[1])
            playerIndex = 1;
        int pathLength = Math.abs(x - playersSelectedCard[playerIndex].getXInGround()) + Math.abs(y - playersSelectedCard[playerIndex].getYInGround());
        if (pathLength > 2)
            throw new InvalidTargetException("Invalid target");
        else if (pathLength == 2) {
            if ((battleGround.getGround().get((warrior.getXInGround() + x) / 2).get(y) != null &&
                    Math.abs(warrior.getXInGround() - x) == 2 && battleGround.getGround().get((warrior.getXInGround() + x) / 2).get(y).getOwner() == players[1 - playerIndex])
                    || (battleGround.getGround().get(x).get((warrior.getYInGround() + y) / 2) != null &&
                    Math.abs(warrior.getYInGround() - y) == 2 && battleGround.getGround().get(x).get((warrior.getXInGround() + y) / 2).getOwner() == players[1 - playerIndex]))
                throw new InvalidTargetException("Invalid target");
            if (Math.abs(x - warrior.getXInGround()) == 1) {
                if (battleGround.getGround().get(x).get(warrior.getYInGround()).getOwner() == players[1 - playerIndex]
                        && battleGround.getGround().get(warrior.getXInGround()).get(y).getOwner() == players[1 - playerIndex])
                    throw new InvalidTargetException("Invalid target");
            }
        } else if (battleGround.getGround().get(x).get(y) instanceof Item)
            selectItem(player, battleGround.getGround().get(x).get(y).getID());
        else if (battleGround.getGround().get(x).get(y) instanceof Card)
            throw new InvalidTargetException("Invalid target");
        playersSelectedCard[playerIndex].setXInGround(x);
        playersSelectedCard[playerIndex].setYInGround(y);
    }

    public void attack(Account player, Warrior attacker, int opponentWarriorID) {
        applyEffectedBuffersOfWarrior(attacker, "attack");
        if (attacker.isStun() || attacker.isAttackedThisTurn())
            throw new InvalidAttackException("Card with" + attacker.getID() + "can't attack");
        int distance, playerIndex;
        if (player == players[0])
            playerIndex = 0;
        else
            playerIndex = 1;
        Warrior opponentWarrior;
        try {
            opponentWarrior = searchWarriorInBattleGround(opponentWarriorID);
        } catch (AssetNotFoundException e) {
            throw new AssetNotFoundException("Invalid card id");
        }
        distance = Math.abs(opponentWarrior.getXInGround() - attacker.getXInGround())
                + Math.abs(opponentWarrior.getYInGround() - attacker.getYInGround());
        switch (attacker.getAttackType()) {
            case MELEE:
                meleeAttackOrCounterAttack(attacker, opponentWarrior, distance, "attack");
                break;
            case RANGED:
                rangedAttackOrCounterAttack(attacker, opponentWarrior, distance, "attack");
                break;
            case HYBRID:
                hybridAttackOrCounterAttack(attacker, opponentWarrior, distance, "attack");
        }
        if (attacker.getHP() <= 0) {
            playersGraveYard[playerIndex].getDeadCards().add(attacker);
            battleGround.getGround().remove(attacker);
        }
        if (opponentWarrior.getHP() <= 0) {
            playersGraveYard[playerIndex].getDeadCards().add(opponentWarrior);
            battleGround.getGround().remove(opponentWarriorID);
        }
    }

    public void counterAttack(Warrior counterAttacker, Warrior opponentWarrior) {
        applyEffectedBuffersOfWarrior(counterAttacker, "counterAttack");
        if (counterAttacker.isDisarm())
            return;
        int distance;
        distance = Math.abs(opponentWarrior.getXInGround() - counterAttacker.getXInGround()) + Math.abs(opponentWarrior.getYInGround() - counterAttacker.getYInGround());
        switch (counterAttacker.getAttackType()) {
            case MELEE:
                meleeAttackOrCounterAttack(counterAttacker, opponentWarrior, distance, "counterAttack");
                break;
            case RANGED:
                rangedAttackOrCounterAttack(counterAttacker, opponentWarrior, distance, "counterAttack");
                break;
            case HYBRID:
                hybridAttackOrCounterAttack(counterAttacker, opponentWarrior, distance, "counterAttack");
        }
    }

    public void meleeAttackOrCounterAttack(Warrior attacker, Warrior opponentWarrior, int distance, String status) {
        if (distance == 1 || (distance == 2 && Math.abs(attacker.getXInGround() - opponentWarrior.getXInGround()) == 1)) {
            if (!attacker.getAction().equals("NoAction")) {
                //TODO: special power
                Buffer buffer = new Buffer();
                Class bufferClass = buffer.getClass();
                //bufferClass.getMethod(attacker.getAction(), attacker, opponentWarrior);
            }
            opponentWarrior.changeHP(-1 * attacker.getAP());
            if (status.equals("attack"))
                counterAttack(opponentWarrior, attacker);
        } else if (status.equals("attack"))
            throw new InvalidAttackException("Opponent warrior is unavailable for attack");
    }

    public void rangedAttackOrCounterAttack(Warrior attacker, Warrior opponentWarrior, int distance, String status) {
        if (distance <= attacker.getRange() && !(distance == 1 || (distance == 2 && Math.abs(attacker.getXInGround() - opponentWarrior.getXInGround()) == 1))) {
            //TODO: special power

            opponentWarrior.changeHP(-1 * attacker.getAP());
            if (status.equals("attack"))
                counterAttack(opponentWarrior, attacker);
        } else if (status.equals("attack"))
            throw new InvalidAttackException("Opponent warrior is unavailable for attack");
    }

    public void hybridAttackOrCounterAttack(Warrior attacker, Warrior opponentWarrior, int distance, String status) {
        //TODO: special power

        try {
            meleeAttackOrCounterAttack(attacker, opponentWarrior, distance, status);
        } catch (InvalidAttackException e) {
            try {
                rangedAttackOrCounterAttack(attacker, opponentWarrior, distance, status);
            } catch (InvalidAttackException e1) {
                throw e1;
            }
        }
    }

    public void attackCombo(Account player, Warrior[] playerWarrior, int opponentCardId) {

        for (int i = 0; i < playerWarrior.length; i++) {
            if (playerWarrior[i].getOwner() == player) {
                if ((playerWarrior[i]).getActivateTimeOfSpecialPower() == Warrior.ActivateTimeOfSpecialPower.COMBO)
                    attack(player, playerWarrior[i], opponentCardId);
                else {
                    throw new NotComboException();
                }
            }
        }
    }

    public  void useSpecialPower(Account player, int x, int y) {
    }

    public void insertIn(Account player, String cardName, int x, int y, BattleGround battleGround) {
        x--;
        y--;
        int playerIndex = 0;
        boolean isThereAnyAdjacentOwnWarrior = false;
        if (player == players[1])
            playerIndex = 1;
        if (battleGround.getGround().get(x).get(y) != null)
            throw new InvalidInsertInBattleGroundException("The selected cell is filled.");
        outer:
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                if (battleGround.getGround().get(x + i).get(y + j).getOwner() == player) {
                    isThereAnyAdjacentOwnWarrior = true;
                    break outer;
                }
            }
        }
        if (!isThereAnyAdjacentOwnWarrior)
            throw new InvalidInsertInBattleGroundException("Invalid target");
        for (int i = 0; i < NUMBER_OF_CARDS_IN_HAND; i++) {
            Card card = playersHand[playerIndex][i];
            if (card.getName().equals(cardName)) {
                if (playersMana[playerIndex] >= card.getMP()) {
                    battleGround.getGround().get(x).set(y, card);
                    playersMana[playerIndex] -= card.getMP();
                    playersHand[playerIndex][i] = null;
                    return;
                } else
                    throw new InvalidInsertInBattleGroundException("You don't have enough mana.");
            }
        }
        throw new InvalidInsertInBattleGroundException("Invalid card name");
    }

    public void endTurn(Account player) {
        applyAndHandleManaBuffers();
        applyEffectedBuffersOfAllWarriorsAtEndOfTurn();
        fillEmptyPlacesOfHandFromDeck(player);
        turn++;
        setPlayersManaByDefault();
    }

    public void applyAndHandleManaBuffers() {
        for (int playerIndex = 0; playerIndex <= 1; playerIndex++) {
            Iterator<BufferOfSpells> iterator = playersManaBuffEffected[playerIndex].iterator();
            while (iterator.hasNext()) {
                BufferOfSpells bufferOfSpells = iterator.next();
                if (bufferOfSpells.getTurnCountdownUntilActivation() > 0)
                    bufferOfSpells.changeTurnCountdownUntilActivation(-1);
                else if (bufferOfSpells.getTurnCountdownUntilActivation() == 0) {
                    changePlayerMana(playerIndex, bufferOfSpells.getValue());
                    if (!bufferOfSpells.isLifeEndless())
                        bufferOfSpells.changeTurnCountdownUntilActivation(-1);
                } else {
                    bufferOfSpells.changeLifeTime(-1);
                    if (bufferOfSpells.getLifeTime() == 0) {
                        changePlayerMana(playerIndex, -1 * bufferOfSpells.getValue());
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void applyEffectedBuffersOfAllWarriorsAtEndOfTurn() {
        Warrior warrior = null;
        for (int i = 0; i < BattleGround.getColumns(); i++) {
            for (int j = 0; j < BattleGround.getRows(); j++) {
                if (battleGround.getGround().get(i).get(j) == null || battleGround.getGround().get(i).get(j) instanceof Item)
                    continue;
                warrior = (Warrior) battleGround.getGround().get(i).get(j);
                applyEffectedBuffersOfWarrior(warrior, "endTurn");
                handleBufferOfSpellsAtEndOfTurn(warrior);
            }
        }
    }

    public void applyEffectedBuffersOfWarrior(Warrior warrior, String status) {
        for (BufferOfSpells bufferOfSpells : warrior.getBufferEffected())
            bufferOfSpells.applyBufferOfSpells(warrior, status);
    }

    public void handleBufferOfSpellsAtEndOfTurn(Warrior warrior) {
        Iterator<BufferOfSpells> iterator = warrior.getBufferEffected().iterator();
        while (iterator.hasNext()) {
            BufferOfSpells bufferOfSpells = iterator.next();
            if (bufferOfSpells.getTurnCountdownUntilActivation() > 0)
                bufferOfSpells.changeTurnCountdownUntilActivation(-1);
            else if (bufferOfSpells.getTurnCountdownUntilActivation() < 0 && !bufferOfSpells.isLifeEndless()) {
                bufferOfSpells.changeLifeTime(-1);
                if (bufferOfSpells.getLifeTime() == 0) {
                    bufferOfSpells.deactivate(warrior);
                    iterator.remove();
                }
            }
        }
    }

    public void fillEmptyPlacesOfHandFromDeck(Account player) {
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j < NUMBER_OF_CARDS_IN_HAND; j++) {
                int nextCardFromDeckIndex = player.getMainDeck().getNextCardFromDeckIndex();
                if (playersHand[i][j] == null && nextCardFromDeckIndex < Deck.STANDARD_NUMBER_OF_MINIONS_AND_SPELLS) {
                    playersHand[i][j] = player.getMainDeck().getCards().get(nextCardFromDeckIndex);
                    player.getMainDeck().setNextCardFromDeckIndex(nextCardFromDeckIndex + 1);
                }
            }
        }
    }

    public void selectItem(Account player, int collectableItemID) {
    }


    public static void useItem(Account player, Account enemy, Card enemyWarrior, Card myCard, Item playerItemSelected) {
        switch (playerItemSelected.getID()) {
            case 1000:
                playerItemSelected.getBuffer().knowledgeCrownAction(player);
                break;
            case 1001:
                playerItemSelected.getBuffer().namoos_e_separAction(player.getMainDeck().getHero());
                break;
            case 1002:
                playerItemSelected.getBuffer().damoolArchAction(player.getMainDeck().getHero(), enemyWarrior);
                break;
            case 1003:
                playerItemSelected.getBuffer().nooshdaroo();
                break;
            case 1004:
                playerItemSelected.getBuffer().twoHornArrowAction();
                break;
            case 1005:
                playerItemSelected.getBuffer().simorghWingAction(enemy);
                break;
            case 1006:
                playerItemSelected.getBuffer().elixirAction((Warrior) myCard);
                break;
            case 1007:
                playerItemSelected.getBuffer().manaMixtureAction((Warrior) myCard);
                break;
            case 1008:
                playerItemSelected.getBuffer().invulnerableMixtureAction();
                break;
            case 1009:
                playerItemSelected.getBuffer().deathCurseAction();
                break;
            case 1010:
                playerItemSelected.getBuffer().randomDamageAction();
                break;
            case 1011:
                playerItemSelected.getBuffer().terrorHoodAction(enemy);
                break;
            case 1012:
                playerItemSelected.getBuffer().bladesOfAgilityAction();
                break;
            case 1013:
                playerItemSelected.getBuffer().kingWisdomAction(player);
                break;
            case 1014:
                playerItemSelected.getBuffer().assassinationDaggerAction(enemy);
                break;
            case 1015:
                playerItemSelected.getBuffer().poisonousDaggerAction(enemy);
                break;
            case 1016:
                playerItemSelected.getBuffer().shockHammerAction((Warrior) enemyWarrior);
                break;
            case 1017:
                playerItemSelected.getBuffer().soulEaterAction(enemy, (Warrior) myCard);
                break;
            case 1018:
                playerItemSelected.getBuffer().baptismAction((Minion) myCard);
                break;
            case 1019:
                playerItemSelected.getBuffer().chineseSwordAction((Warrior) myCard);
                break;
        }

    }

    public void setPlayersMana(int[] playersMana) {
        this.playersMana = playersMana;
    }

    public Card[][] getPlayersHand() {
        return playersHand;
    }

    public void setPlayersHand(Card[][] playersHand) {
        this.playersHand = playersHand;
    }

    public void enterGraveYard(Account player, Card playerCard) {
        playersGraveYard[0].getDeadCards().add(playerCard);
    }

    public int endGame() {
        if (players[0].getMainDeck().getHero().getHP() <= 0) {
            players[1].setBudget(players[1].getBudget() + PRIZE);
            return EXIT;
        } else if (players[1].getMainDeck().getHero().getHP() <= 0) {
            players[0].setBudget(players[0].getBudget() + PRIZE);
            return EXIT;
        }
        return CONTINUE;
        //TODO: State of all the cards must be reset.
    }

    //Getters and Setters
    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Account[] getPlayers() {
        return players;
    }

    public void setPlayers(Account[] players) {
        this.players = players;
    }

    public BattleGround getBattleGround() {
        return battleGround;
    }

    public void setBattleGround(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public int[] getPlayersMana() {
        return playersMana;
    }

    public void setPlayersManaByDefault() {
        eachPlayerManaAtFirstOfTurn++;
        if (turn < FIRST_LATE_TURN)
            playersMana[2 - (turn % 2)] = eachPlayerManaAtFirstOfTurn;
        else
            playersMana[2 - (turn % 2)] = MAX_MANA_IN_LATE_TURNS;
    }

    public void changePlayerMana(int playerIndex, int value) {
        playersMana[playerIndex] += value;
    }

    public ArrayList<BufferOfSpells>[] getPlayersManaBuffEffected() {
        return playersManaBuffEffected;
    }

    public void setPlayersManaBuffEffected(ArrayList<BufferOfSpells>[] playersManaBuffEffected) {
        this.playersManaBuffEffected = playersManaBuffEffected;
    }

    public Card[] getPlayersSelectedCard() {
        return playersSelectedCard;
    }

    public void setPlayersSelectedCard(Card[] playersSelectedCard) {
        this.playersSelectedCard = playersSelectedCard;
    }

    public Item[] getPlayersSelectedItem() {
        return playersSelectedItem;
    }

    public void setPlayersSelectedItem(Item[] playersSelectedItem) {
        this.playersSelectedItem = playersSelectedItem;
    }

    public Card[] getPlayersNextCardFromDeck() {
        return playersNextCardFromDeck;
    }

    public void setPlayersNextCardFromDeck(Card[] playersNextCardFromDeck) {
        this.playersNextCardFromDeck = playersNextCardFromDeck;
    }

    public GraveYard[] getPlayersGraveYard() {
        return playersGraveYard;
    }

    public void setPlayersGraveYard(GraveYard[] playersGraveYard) {
        this.playersGraveYard = playersGraveYard;
    }

    public int getBattleID() {
        return battleID;
    }

    public void setBattleID(int battleID) {
        this.battleID = battleID;
    }

    public int getNUMBER_OF_CARDS_IN_HAND() {
        return NUMBER_OF_CARDS_IN_HAND;
    }

    public Warrior searchWarriorInBattleGround(int warriorID) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++)
                if (battleGround.getGround().get(i).get(j) instanceof Warrior && warriorID == battleGround.getGround().get(i).get(j).getID())
                    return (Warrior) battleGround.getGround().get(i).get(j);
        }
        throw new AssetNotFoundException("Asset not found in the battleground.");
    }

    public Card searchCardInHand(Account player, int cardID) {
        int playerIndex = 0;
        if (player == players[1])
            playerIndex = 1;
        for (Card card : playersHand[playerIndex])
            if (cardID == card.getID())
                return card;
        throw new AssetNotFoundException("Card not found in the hand");
    }

    public static boolean isValidCoordinates(int x, int y) {
        if (x > BattleGround.getColumns() || x < 1 || y > BattleGround.getRows() || y < 1)
            return false;
        return true;
    }
}