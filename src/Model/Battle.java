package Model;

import Datas.AssetDatas;
import Datas.DeckDatas;
import Exceptions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import Model.Minion.*;
import Presenter.CurrentAccount;

import static Model.Minion.ActivateTimeOfSpecialPower.*;
import static Model.Status.*;
import static Model.Buffer.randomNumberGenerator;

public abstract class Battle {
    public enum Mode {
        NORMAL, FLAG_KEEPING, FLAG_COLLECTING;
    }

    protected Mode mode;
    protected final static int STORY_REWARD_L1 = 500;
    protected final static int STORY_REWARD_L2 = 1000;
    protected final static int STORY_REWARD_L3 = 1500;
    protected final static int CUSTOM_REWARD = 1000;
    protected final int UNFINISHED_GAME = -1;
    protected final int DRAW = 0;
    protected final int FIRST_PLAYER_WIN = 1;
    protected final int SECOND_PLAYER_WIN = 2;
    protected int endGameStatus = UNFINISHED_GAME;
    protected final int FIRST_LATE_TURN = 15;
    protected final int MAX_MANA_IN_LATE_TURNS = 9;
    protected int eachPlayerManaAtFirstOfTurn = 2;
    protected final int NUMBER_OF_CARDS_IN_HAND = 5;
    protected int turn;
    protected transient Account[] players = new Account[2];
    protected Deck[] playersDeck = new Deck[2];
    protected BattleGround battleGround = new BattleGround();
    protected int[] playersMana = {eachPlayerManaAtFirstOfTurn, eachPlayerManaAtFirstOfTurn};
    protected ArrayList<BufferOfSpells>[] playersManaBuffEffected = new ArrayList[2];
    protected Card[] playersSelectedCard = new Card[2];
    protected Item[] playersSelectedItem = new Item[2];
    protected Card[][] playersHand = new Card[2][NUMBER_OF_CARDS_IN_HAND];
    protected Card[] playersNextCardFromDeck = new Card[2];
    protected ArrayList<Item> collectibleItems = new ArrayList<>();
    protected GraveYard[] playersGraveYard = new GraveYard[2];
    private ArrayList<Integer> itemsCoordinates = new ArrayList<>();
    protected int battleID;
    protected int reward;


    public Deck[] getPlayersDeck() {
        return playersDeck;
    }

    public Battle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstPlayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward) {
        this.mode = mode;
        this.turn = 0;
        this.players[0] = firstPlayer;
        this.players[1] = secondPlayer;
        this.playersDeck[0] = firstPlayerDeck;
        this.playersDeck[1] = secondPlayerDeck;
        this.battleGround = battleGround;
        this.playersSelectedCard[0] = null;
        this.playersSelectedCard[1] = null;
        this.playersSelectedItem[0] = null;
        this.playersSelectedItem[1] = null;
        this.playersNextCardFromDeck[0] = null;
        this.playersNextCardFromDeck[1] = null;
        this.playersManaBuffEffected[0] = new ArrayList<BufferOfSpells>();
        this.playersManaBuffEffected[1] = new ArrayList<BufferOfSpells>();
        this.playersGraveYard[0] = new GraveYard();
        this.playersGraveYard[1] = new GraveYard();
        this.reward = reward;
        //Filling Players' hands
        for (int i = 0; i <= 1; i++) {
            int nextCardFromDeckIndex = 0;
            for (int j = 0; j < NUMBER_OF_CARDS_IN_HAND; j++) {
                nextCardFromDeckIndex = playersDeck[i].getNextCardFromDeckIndex();
                playersHand[i][j] = playersDeck[i].getCards().get(nextCardFromDeckIndex++);
                playersDeck[i].setNextCardFromDeckIndex(nextCardFromDeckIndex);
            }
            playersNextCardFromDeck[i] = playersDeck[i].getCards().get(nextCardFromDeckIndex);
        }
        //Locating heroes
        battleGround.getGround().get(BattleGround.getRows() / 2).set(0, playersDeck[0].getHero());
        playersDeck[0].getHero().setYInGround(BattleGround.getRows() / 2);
        playersDeck[0].getHero().setXInGround(0);
        battleGround.getGround().get(BattleGround.getRows() / 2).set(BattleGround.getColumns() - 1, playersDeck[1].getHero());
        playersDeck[1].getHero().setYInGround(BattleGround.getRows() / 2);
        playersDeck[1].getHero().setXInGround(BattleGround.getColumns() - 1);

        //Locating collectible Items
        itemsCoordinates = differentRandomNumbersGenerator(9, BattleGround.getColumns() * BattleGround.getRows(), 18, 26);
        collectibleItems.add(AssetDatas.getNooshdaroo());
        collectibleItems.add(AssetDatas.getTwoHornArrow());
        collectibleItems.add(AssetDatas.getElixir());
        collectibleItems.add(AssetDatas.getManaMixture());
        collectibleItems.add(AssetDatas.getInvulnerableMixture());
        collectibleItems.add(AssetDatas.getDeathCurse());
        collectibleItems.add(AssetDatas.getRandomDamage());
        collectibleItems.add(AssetDatas.getBladesOfAgility());
        collectibleItems.add(AssetDatas.getChineseSword());
        for (int i = 0; i < collectibleItems.size(); i++)
            battleGround.getGround().get(itemsCoordinates.get(i) / BattleGround.getColumns()).set(itemsCoordinates.get(i) % BattleGround.getColumns(), collectibleItems.get(i));
//        battleGround.getGround().get(itemsCoordinates.get(0) / BattleGround.getColumns()).set(itemsCoordinates.get(0) % BattleGround.getColumns(), );
//        battleGround.getGround().get(itemsCoordinates.get(1) / BattleGround.getColumns()).set(itemsCoordinates.get(1) % BattleGround.getColumns(), );
//        battleGround.getGround().get(itemsCoordinates.get(2) / BattleGround.getColumns()).set(itemsCoordinates.get(2) % BattleGround.getColumns(), );
//        battleGround.getGround().get(itemsCoordinates.get(3) / BattleGround.getColumns()).set(itemsCoordinates.get(3) % BattleGround.getColumns(), );
//        battleGround.getGround().get(itemsCoordinates.get(4) / BattleGround.getColumns()).set(itemsCoordinates.get(4) % BattleGround.getColumns(), );
//        battleGround.getGround().get(itemsCoordinates.get(5) / BattleGround.getColumns()).set(itemsCoordinates.get(5) % BattleGround.getColumns(), );
//        battleGround.getGround().get(itemsCoordinates.get(6) / BattleGround.getColumns()).set(itemsCoordinates.get(6) % BattleGround.getColumns(), );
//        battleGround.getGround().get(itemsCoordinates.get(7) / BattleGround.getColumns()).set(itemsCoordinates.get(7) % BattleGround.getColumns(), );
//        battleGround.getGround().get(itemsCoordinates.get(8) / BattleGround.getColumns()).set(itemsCoordinates.get(8) % BattleGround.getColumns(), );
    }


    public void selectWarrior(Account player, int cardID) {
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
        x--;
        y--;
        int playerIndex = 0;
        if (player == players[1])
            playerIndex = 1;
        int pathLength = Math.abs(x - playersSelectedCard[playerIndex].getXInGround())
                + Math.abs(y - playersSelectedCard[playerIndex].getYInGround());
//        if(playersSelectedCard[playerIndex] instanceof Warrior && ((Warrior)playersSelectedCard[playerIndex]).isHasMoved()){
//            throw new WarriorSecondMoveInTurnException();
//        }
        if (pathLength > 2)
            throw new InvalidTargetException("Invalid target");
        else if (pathLength == 2) {
            try {
                isValidCoordinates(x, y, warrior, playerIndex);
            } catch (InvalidTargetException e) {
                throw e;
            }
        } else if (battleGround.getGround().get(y).get(x) instanceof Flag) {
            warrior.setCollectedFlag(((Flag) battleGround.getGround().get(y).get(x)));
            battleGround.getGround().get(y).remove(x);
        } else if (battleGround.getGround().get(y).get(x) instanceof Item) {
            playersDeck[playerIndex].getItems().add((Item) battleGround.getGround().get(y).get(x));
            battleGround.getGround().get(y).remove(x);
        } else if (battleGround.getGround().get(y).get(x) instanceof Card)
            throw new ThisCellFilledException();
        ////////
        battleGround.getGround().get(playersSelectedCard[playerIndex].getYInGround()).set(playersSelectedCard[playerIndex].getXInGround(), null);
        playersSelectedCard[playerIndex].setXInGround(x);
        playersSelectedCard[playerIndex].setYInGround(y);
        if (playersSelectedCard[playerIndex] instanceof Warrior) {
            ((Warrior) playersSelectedCard[playerIndex]).setHasMoved(true);
        }
        battleGround.getGround().get(y).set(x, playersSelectedCard[playerIndex]);
    }


    public void attack(Account player, Warrior attacker, int opponentWarriorID) throws RuntimeException {
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
        // AshkbosAction
        if (opponentWarrior.getName().equals("ashkbos") && attacker.getAP() < opponentWarrior.getAP())
            return;
        distance = Math.abs(opponentWarrior.getXInGround() - attacker.getXInGround())
                + Math.abs(opponentWarrior.getYInGround() - attacker.getYInGround());
        switch (attacker.getAttackType()) {
            case MELEE:
                meleeAttackOrCounterAttack(attacker, opponentWarrior, distance, ATTACK);
                counterAttack(opponentWarrior, attacker);
                break;
            case RANGED:
                rangedAttackOrCounterAttack(attacker, opponentWarrior, distance, ATTACK);
                counterAttack(opponentWarrior, attacker);
                break;
            case HYBRID:
                hybridAttackOrCounterAttack(attacker, opponentWarrior, distance, ATTACK);
                counterAttack(opponentWarrior, attacker);
        }
        determineDeadWarriors(attacker, playerIndex);
        determineDeadWarriors(opponentWarrior, playerIndex);
    }


    public void counterAttack(Warrior counterAttacker, Warrior opponentWarrior) {
        applyEffectedBuffersOfWarrior(counterAttacker, COUNTER_ATTACK);
        if (counterAttacker.isDisarm())
            return;
        int distance;
        distance = Math.abs(opponentWarrior.getXInGround() - counterAttacker.getXInGround())
                + Math.abs(opponentWarrior.getYInGround() - counterAttacker.getYInGround());
        switch (counterAttacker.getAttackType()) {
            case MELEE:
                meleeAttackOrCounterAttack(counterAttacker, opponentWarrior, distance, COUNTER_ATTACK);
                break;
            case RANGED:
                rangedAttackOrCounterAttack(counterAttacker, opponentWarrior, distance, COUNTER_ATTACK);
                break;
            case HYBRID:
                hybridAttackOrCounterAttack(counterAttacker, opponentWarrior, distance, COUNTER_ATTACK);
        }
    }

    public void determineDeadWarriors(Warrior warrior, int playerIndex) {
        if (warrior.getHP() <= 0) {
            battleGround.getGround().get(warrior.getYInGround()).set(warrior.getXInGround(), warrior.getCollectedFlag());
            if (warrior.getCollectedFlag() != null) {
                warrior.getCollectedFlag().setKeptDuration(0);
                warrior.getCollectedFlag().setOwner(null);
            }
            playersGraveYard[playerIndex].getDeadCards().add(warrior);
            System.out.println(playersGraveYard[playerIndex].getDeadCards().get(0));
        }
    }

    public void meleeAttackOrCounterAttack(Warrior attacker, Warrior opponentWarrior, int distance, Status status) {
        if (distance == 1 || (distance == 2 && Math.abs(attacker.getXInGround() - opponentWarrior.getXInGround()) == 1)) {
            if (!attacker.getAction().equals("NoAction"))
                applySpecialPower(attacker, opponentWarrior, ON_ATTACK);
            opponentWarrior.changeHP(-1 * attacker.getAP());
        } else if (status == ATTACK)
            throw new InvalidAttackException("Opponent warrior is unavailable for attack");
    }

    public void rangedAttackOrCounterAttack(Warrior attacker, Warrior opponentWarrior, int distance, Status status) {
        if (distance <= attacker.getRange() && !(distance == 1 || (distance == 2 && Math.abs(attacker.getXInGround() - opponentWarrior.getXInGround()) == 1))) {
            if (!attacker.getAction().equals("NoAction"))
                applySpecialPower(attacker, opponentWarrior, ON_ATTACK);
            opponentWarrior.changeHP(-1 * attacker.getAP());
        } else if (status == ATTACK)
            throw new InvalidAttackException("Opponent warrior is unavailable for attack");
    }

    public void hybridAttackOrCounterAttack(Warrior attacker, Warrior opponentWarrior, int distance, Status status) {
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

    public void applySpecialPower(Warrior attacker, Warrior opponentWarrior, ActivateTimeOfSpecialPower activateTimeOfSpecialPower) {
        Buffer buffer = new Buffer();
        Class bufferClass = buffer.getClass();
        Class[] methodArgs;
        int playerIndex = 0;
        if (attacker.getOwner() == players[1])
            playerIndex = 1;
        switch (activateTimeOfSpecialPower) {
            case PASSIVE:
                methodArgs = new Class[]{Account.class, Minion.class, BattleGround.class};
                try {
                    bufferClass.getMethod(attacker.getAction(), methodArgs).invoke(buffer, attacker.getOwner(), attacker, battleGround);
                } catch (Exception e) {
                    throw new NoAvailableBufferForCardException();
                }
                break;
            case ON_DEATH:
                switch (attacker.getName()) {
                    case "siavash":
                        buffer.siavashAction(playersDeck[playerIndex].getHero());
                    case "oneEyeGiant":
                        buffer.oneEyeGiantAction(attacker, battleGround);
                }
                break;
            case ON_SPAWN:
                methodArgs = new Class[]{Account.class, Minion.class, BattleGround.class};
                try {
                    bufferClass.getMethod(attacker.getAction(), methodArgs).invoke(buffer, opponentWarrior.getOwner(), attacker, battleGround);
                } catch (Exception e) {
                    throw new NoAvailableBufferForCardException();
                }
                break;
            case ON_ATTACK:
                methodArgs = new Class[]{Warrior.class, Warrior.class};
                try {
                    bufferClass.getMethod(attacker.getAction(), methodArgs).invoke(buffer, attacker, opponentWarrior);
                } catch (Exception e) {
                    throw new NoAvailableBufferForCardException();
                }
                break;
        }
    }

    public void attackCombo(Account player, ArrayList<Minion> playerMinions, int opponentCardId) {
        for (int i = 0; i < playerMinions.size(); i++) {
            if (playerMinions.get(i).getOwner() == player) {
                if ((playerMinions.get(i)).getActivateTimeOfSpecialPower() == COMBO)
                    attack(player, playerMinions.get(i), opponentCardId);
                else {
                    throw new NotComboException();
                }
            }
        }
    }

    public void insertIn(Account player, String cardName, int x, int y, BattleGround battleGround) {
        x--;
        y--;
        int playerIndex = 0;
        boolean isThereAnyAdjacentOwnWarrior = false;
        if (player == players[1])
            playerIndex = 1;
        outer:
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                if (y + j >= 0 && x + i >= 0 && y + j < BattleGround.getRows() && x + i < BattleGround.getColumns() && battleGround.getGround().get(y + j).get(x + i) != null && battleGround.getGround().get(y + j).get(x + i).getOwner() == player) {
                    isThereAnyAdjacentOwnWarrior = true;
                    break outer;
                }
            }
        }
        if (isThereAnyAdjacentOwnWarrior)
            throw new InvalidInsertInBattleGroundException("Invalid target");
        for (int i = 0; i < NUMBER_OF_CARDS_IN_HAND; i++) {
            Card card = playersHand[playerIndex][i];
            if (card != null && card.getName().equals(cardName)) {
                if (playersMana[playerIndex] >= card.getMP()) {
                    playersMana[playerIndex] -= card.getMP();
                    playersHand[playerIndex][i] = null;
                    if (card instanceof Spell)
                        applySpellBuffers(player, players[1 - playerIndex], (Spell) card, x, y);
                    else {
                        if (battleGround.getGround().get(y).get(x) != null)
                            throw new ThisCellFilledException();
                        battleGround.getGround().get(y).set(x, card);
                        card.setXInGround(x);
                        card.setYInGround(y);
                    }
                    return;
                } else
                    throw new DontHaveEnoughManaException();
            }
        }
        throw new AssetNotFoundException("Invalid card name");
    }

    public void endTurn(Account player) {
        applyAndHandleManaBuffers();
        applyEffectedBuffersOfAllWarriorsAtEndOfTurn();
        fillEmptyPlacesOfHandFromDeck(player);
        endGame();
        turn++;
        resetIsAttackedThisTurn();
        setPlayersManaByDefault();
        for (ArrayList<Asset> assets : battleGround.getGround()) {
            for (Asset asset : assets) {
                if (asset instanceof Warrior && asset.getOwner() == player) {
                    ((Warrior) asset).setHasMoved(false);
                }
            }
        }
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
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battleGround.getGround().get(i).get(j) == null || battleGround.getGround().get(i).get(j) instanceof Item
                        || battleGround.getGround().get(i).get(j) instanceof Flag)
                    continue;
                warrior = (Warrior) battleGround.getGround().get(i).get(j);
                applyEffectedBuffersOfWarrior(warrior, END_TURN);
                handleBufferOfSpellsAtEndOfTurn(warrior);
            }
        }
    }

    public void applyEffectedBuffersOfWarrior(Warrior warrior, Status status) {
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
        int playerIndex = 0;
        if (player == players[1])
            playerIndex = 1;
        for (int j = 0; j < NUMBER_OF_CARDS_IN_HAND; j++) {
            int nextCardFromDeckIndex = playersDeck[playerIndex].getNextCardFromDeckIndex();
            if (playersHand[playerIndex][j] == null && nextCardFromDeckIndex < Deck.STANDARD_NUMBER_OF_MINIONS_AND_SPELLS) {
                playersHand[playerIndex][j] = playersDeck[playerIndex].getCards().get(nextCardFromDeckIndex);
                playersDeck[playerIndex].setNextCardFromDeckIndex(nextCardFromDeckIndex + 1);
                playersNextCardFromDeck[playerIndex] = playersDeck[playerIndex].getCards().get(nextCardFromDeckIndex + 1);
            }
        }
    }

    private void resetIsAttackedThisTurn() {
        for (int i = 0; i < BattleGround.getRows(); i++)
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Asset asset = battleGround.getGround().get(i).get(j);
                if (asset != null && asset instanceof Warrior)
                    ((Warrior) asset).setAttackedThisTurn(false);
            }
    }

    public void setPlayersManaByDefault() {
        eachPlayerManaAtFirstOfTurn++;
        if (turn < FIRST_LATE_TURN)
            playersMana[2 - (turn % 2)] = eachPlayerManaAtFirstOfTurn;
        else
            playersMana[2 - (turn % 2)] = MAX_MANA_IN_LATE_TURNS;
    }

    public void selectItem(Account player, int collectibleItemID) {
        int playerIndex = 0;
        Item candidateItem;
        if (player == players[1])
            playerIndex = 1;
        try {
            candidateItem = searcjItemInPlayersDeck(playerIndex, collectibleItemID);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        playersSelectedItem[playerIndex] = candidateItem;
    }

    public void applySpellBuffers(Account player, Account enemy, Card playerSelectedSpell, int x, int y) {
        int playerIndex = 0;
        if (player == players[1])
            playerIndex = 1;
        Buffer buffer = new Buffer();
        Class bufferClass = buffer.getClass();
        Class[] methodArgs;

        methodArgs = new Class[]{Account.class, Account.class, BattleGround.class, Integer.class, Integer.class};
        try {
            bufferClass.getMethod(playerSelectedSpell.getAction(), methodArgs).invoke(buffer, player, enemy, battleGround, x, y);
        } catch (Exception e) {
            throw new NoAvailableBufferForCardException();
        }
    }

    public void useItem(Account player, Account enemy, Card enemyWarrior, Card myCard, Item playerItemSelected) {
        int playerIndex = 0;
        if (player == players[1])
            playerIndex = 1;
        switch (playerItemSelected.getID()) {
            case 1000:
                playerItemSelected.getBuffer().knowledgeCrownAction(player);
                break;
            case 1001:
                playerItemSelected.getBuffer().namoos_e_separAction(playersDeck[playerIndex].getHero());
                break;
            case 1002:
                playerItemSelected.getBuffer().damoolArchAction(playersDeck[playerIndex].getHero(), enemyWarrior);
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

    public abstract int endGame();

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

    public Mode getMode() {
        return mode;
    }

    public int getReward() {
        return reward;
    }

    public ArrayList<Item> getCollectibleItems() {
        return collectibleItems;
    }

    public void setCollectibleItems(ArrayList<Item> collectibleItems) {
        this.collectibleItems = collectibleItems;
    }

    public ArrayList<Integer> getItemsCoordinates() {
        return itemsCoordinates;
    }

    public void setItemsCoordinates(ArrayList<Integer> itemsCoordinates) {
        this.itemsCoordinates = itemsCoordinates;
    }

    public Warrior searchWarriorInBattleGround(int warriorID) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Asset asset = battleGround.getGround().get(i).get(j);
                if (asset instanceof Warrior && warriorID == asset.getID())
                    return (Warrior) asset;
            }
        }
        throw new AssetNotFoundException("Asset not found in the battleground.");
    }

    public static ArrayList<Integer> differentRandomNumbersGenerator(int numberOfRandomNumbers, int supremeValueOfRange, int... invalidCells) {
        ArrayList<Integer> answer = new ArrayList<>();
        boolean isValidRandomNumber;
        for (int i = 0; i < numberOfRandomNumbers; i++) {
            while (true) {
                isValidRandomNumber = true;
                int temp = randomNumberGenerator(supremeValueOfRange);
                for (int j = 0; j < invalidCells.length; j++) {
                    if (temp == invalidCells[j])
                        isValidRandomNumber = false;
                }
                for (Integer integer : answer) {
                    if (integer == temp)
                        isValidRandomNumber = false;
                }
                if (isValidRandomNumber) {
                    answer.add(temp);
                    break;
                }
            }
        }
        return answer;
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

    public boolean isValidCoordinates(int x, int y, Warrior warrior, int playerIndex) {
        if (x > BattleGround.getColumns() || x < 0 || y > BattleGround.getRows() || y < 0)
            return false;
        if ((battleGround.getGround().get(y).get((warrior.getXInGround() + x) / 2) != null &&
                Math.abs(warrior.getXInGround() - x) == 2 && battleGround.getGround().get(y).get((warrior.getXInGround() + x) / 2).getOwner() == players[1 - playerIndex])
                || (battleGround.getGround().get((warrior.getYInGround() + y) / 2).get(x) != null &&
                Math.abs(warrior.getYInGround() - y) == 2 && battleGround.getGround().get((warrior.getXInGround() + y) / 2).get(x).getOwner() == players[1 - playerIndex]))
            throw new InvalidTargetException("Invalid target");
        if (Math.abs(x - warrior.getXInGround()) == 1) {
            if (battleGround.getGround().get(warrior.getYInGround()).get(x) != null && battleGround.getGround().get(warrior.getYInGround()).get(x).getOwner() == players[1 - playerIndex]
                    && battleGround.getGround().get(y).get(warrior.getXInGround()).getOwner() == players[1 - playerIndex])
                throw new InvalidTargetException("Invalid target");
        }
        return true;
    }

    public Item searcjItemInPlayersDeck(int playerIndex, int collectibleItemID) {
        for (Item item : playersDeck[playerIndex].getItems())
            if (collectibleItemID == item.getID())
                return item;
        throw new AssetNotFoundException();
    }

    public Card searchCardWithInGameCardID(ArrayList<Asset> cards, String cardId) {
        String cardName = cardId.split("_")[1];
        return (Card) Asset.searchAsset(cards, cardName);
    }

    public static Battle soloStoryModeConstructor(int levelNumber) {
        Deck AIDeck;
        Battle.Mode battleMode;
        AI ai = new AI("AI", "1234");
        int reward;
        switch (levelNumber) {
            case 1:
                AIDeck = new Deck(ai, DeckDatas.getEnemyDeckInStoryGameLevel1().getName(), DeckDatas.getEnemyDeckInStoryGameLevel1().getHero(), DeckDatas.getEnemyDeckInStoryGameLevel1().getItems(), DeckDatas.getEnemyDeckInStoryGameLevel1().getCards());
                battleMode = Battle.Mode.NORMAL;
                reward = STORY_REWARD_L1;
                return new KillHeroBattle
                        (battleMode,
                                CurrentAccount.getCurrentAccount(),
                                ai,
                                new Deck(CurrentAccount.getCurrentAccount(), CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                                AIDeck,
                                new BattleGround(), reward);
            case 2:
                AIDeck = new Deck(ai, DeckDatas.getEnemyDeckInStoryGameLevel2().getName(), DeckDatas.getEnemyDeckInStoryGameLevel2().getHero(), DeckDatas.getEnemyDeckInStoryGameLevel2().getItems(), DeckDatas.getEnemyDeckInStoryGameLevel2().getCards());
                battleMode = Battle.Mode.FLAG_KEEPING;
                reward = STORY_REWARD_L2;
                return new KeepFlagBattle
                        (battleMode,
                                CurrentAccount.getCurrentAccount(),
                                ai,
                                new Deck(CurrentAccount.getCurrentAccount(), CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                                AIDeck,
                                new BattleGround(), reward);
            case 3:
                AIDeck = new Deck(ai, DeckDatas.getEnemyDeckInStoryGameLevel3().getName(), DeckDatas.getEnemyDeckInStoryGameLevel3().getHero(), DeckDatas.getEnemyDeckInStoryGameLevel3().getItems(), DeckDatas.getEnemyDeckInStoryGameLevel3().getCards());
                battleMode = Battle.Mode.FLAG_COLLECTING;
                reward = STORY_REWARD_L3;
                return new CollectFlagBattle
                        (battleMode,
                                CurrentAccount.getCurrentAccount(),
                                ai,
                                new Deck(CurrentAccount.getCurrentAccount(), CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                                AIDeck,
                                new BattleGround(), reward);
        }
        return null;
    }

    public static Battle soloCustomKillHeroModeConstructor(String heroName) {
        Hero customHero = Hero.searchHeroForCustomGame(heroName);
        AI ai = new AI("AI", "1234");
        Deck AIDeck = new Deck(ai, DeckDatas.getDefaultDeck().getName(), customHero, DeckDatas.getDefaultDeck().getItems(), DeckDatas.getDefaultDeck().getCards());
        Battle.Mode battleMode = Battle.Mode.NORMAL;
        int reward = CUSTOM_REWARD;
        return new KillHeroBattle
                (battleMode,
                        CurrentAccount.getCurrentAccount(),
                        ai,
                        new Deck(CurrentAccount.getCurrentAccount(), CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                        AIDeck,
                        new BattleGround(), reward);
    }

    public static Battle soloCustomFlagModeConstructor(int numberOfFlags) {
        AI ai = new AI("AI", "1234");
        Deck AIDeck = new Deck(ai, DeckDatas.getDefaultDeck().getName(), DeckDatas.getDefaultDeck().getHero(), DeckDatas.getDefaultDeck().getItems(), DeckDatas.getDefaultDeck().getCards());
        Battle.Mode battleMode;
        int reward = CUSTOM_REWARD;
        if (numberOfFlags == 0) {
            battleMode = Battle.Mode.FLAG_KEEPING;
            return new KeepFlagBattle
                    (battleMode,
                            CurrentAccount.getCurrentAccount(),
                            ai,
                            new Deck(CurrentAccount.getCurrentAccount(), CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                            AIDeck,
                            new BattleGround(), reward);
        } else {
            battleMode = Battle.Mode.FLAG_COLLECTING;
            return new CollectFlagBattle
                    (battleMode,
                            CurrentAccount.getCurrentAccount(),
                            ai,
                            new Deck(CurrentAccount.getCurrentAccount(), CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                            AIDeck,
                            new BattleGround(),
                            reward,
                            numberOfFlags);
        }
    }


}