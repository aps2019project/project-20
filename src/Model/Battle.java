package Model;

import Datas.AssetDatas;
import Exceptions.*;

import java.awt.event.WindowAdapter;
import java.util.ArrayList;
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
    public final static int STORY_REWARD_L1 = 500;
    public final static int STORY_REWARD_L2 = 1000;
    public final static int STORY_REWARD_L3 = 1500;
    public final static int CUSTOM_REWARD = 1000;
    public final static int UNFINISHED_GAME = -1;
    public final static int DRAW = 0;
    public final static int FIRST_PLAYER_WIN = 1;
    public final static int SECOND_PLAYER_WIN = 2;
    public int endGameStatus = UNFINISHED_GAME;
    public static final int FIRST_LATE_TURN = 15;
    public static final int MAX_MANA_IN_LATE_TURNS = 9;
    public int eachPlayerManaAtFirstOfTurn = 2;
    public static  final int NUMBER_OF_CARDS_IN_HAND = 5;
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
    private ArrayList<Integer> itemsCoordinates;
    protected int battleID;
    protected int reward;
    private ArrayList<Card> inGroundCards = new ArrayList<>();
    private ArrayList<Integer> IDsOfOnAttackItems = new ArrayList<>();
    private ArrayList<Integer> IDsOfEndTurnItems = new ArrayList<>();
    private ArrayList<Integer> IDsOfOnceCalledItems = new ArrayList<>();
    private ArrayList<Integer> IDsOfOnSpawnItems = new ArrayList<>();
    private ArrayList<Integer> IDsOfOnDeathItems = new ArrayList<>();

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
        this.inGroundCards.add(playersDeck[0].getHero());
        this.inGroundCards.add(playersDeck[1].getHero());
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

        //Adjusting other items' IDs
        IDsOfEndTurnItems.add(AssetDatas.getKingWisdom().getID());

        IDsOfOnAttackItems.add(AssetDatas.getDamoolArch().getID());
        IDsOfOnAttackItems.add(AssetDatas.getTerrorHood().getID());
        IDsOfOnAttackItems.add(AssetDatas.getPoisonousDagger().getID());
        IDsOfOnAttackItems.add(AssetDatas.getShockHammer().getID());

        IDsOfOnceCalledItems.add(AssetDatas.getKnowledgeCrown().getID());
        IDsOfOnceCalledItems.add(AssetDatas.getNamoos_e_separ().getID());
        IDsOfOnceCalledItems.add(AssetDatas.getSimorghWing().getID());

        IDsOfOnDeathItems.add(AssetDatas.getDeathCurse().getID());
        IDsOfOnDeathItems.add(AssetDatas.getSoulEater().getID());

        IDsOfOnSpawnItems.add(AssetDatas.getBaptism().getID());
        IDsOfOnSpawnItems.add(AssetDatas.getAssassinationDagger().getID());
        //Applying once-action items at the first of battle
        for (int i = 0; i < 2; i++) {
            if (playersDeck[i].getItems().size() > 0 && playersDeck[i].getItems().get(0).getPrice() != 0) {
                Item usableItem = playersDeck[i].getItems().get(0);
                if (IDsOfOnceCalledItems.contains(usableItem.getID()))
                    useItem(players[i], null, null, playersDeck[i].getItems().get(0));
            }
        }
    }

    public void selectWarrior(Account player, int cardID) {
        int playerIndex = getPlayerIndex(player);
        Warrior warrior;
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
        int playerIndex = getPlayerIndex(player);
        int pathLength = Math.abs(x - playersSelectedCard[playerIndex].getXInGround())
                + Math.abs(y - playersSelectedCard[playerIndex].getYInGround());
        if (playersSelectedCard[playerIndex] instanceof Warrior && ((Warrior) playersSelectedCard[playerIndex]).isMovedThisTurn())
            throw new WarriorSecondMoveInTurnException();
        if (pathLength > 2)
            throw new InvalidTargetException("Invalid target");
        else if (pathLength == 2) {
            try {
                isValidCoordinates(x, y, warrior, playerIndex);
            } catch (InvalidTargetException e) {
                throw e;
            }
        }
        if (battleGround.getGround().get(y).get(x) instanceof Flag)
            collectFlag(warrior, x, y);
        else if (battleGround.getGround().get(y).get(x) instanceof Item)
            collectItem((Item) battleGround.getGround().get(y).get(x), warrior, playersDeck[playerIndex], y, x);
        else if (battleGround.getGround().get(y).get(x) instanceof Card)
            throw new ThisCellFilledException();
        battleGround.getGround().get(playersSelectedCard[playerIndex].getYInGround()).set(playersSelectedCard[playerIndex].getXInGround(), null);
        playersSelectedCard[playerIndex].setXInGround(x);
        playersSelectedCard[playerIndex].setYInGround(y);
        if (playersSelectedCard[playerIndex] instanceof Warrior)
            ((Warrior) playersSelectedCard[playerIndex]).setMovedThisTurn(true);
        battleGround.getGround().get(y).set(x, playersSelectedCard[playerIndex]);
    }

    private void collectItem(Item collected, Warrior collector, Deck deck, int y, int x) {
        deck.getItems().add((Item) battleGround.getGround().get(y).get(x));
        collected.setCollector(collector);
        battleGround.getGround().get(y).set(x, null);
    }

    private void collectFlag(Warrior warrior, int flagX, int flagY) {
        warrior.setCollectedFlag(((Flag) battleGround.getGround().get(flagY).get(flagX)));
        battleGround.getGround().get(flagY).set(flagX, null);
    }

    public void attack(Account player, Warrior attacker, Warrior opponentWarrior) throws RuntimeException {
        if (attacker.isStun() || attacker.isAttackedThisTurn())
            throw new InvalidAttackException("The card can't attack twice!");
        int distance, playerIndex = getPlayerIndex(player);
        // AshkbosAction
        if (opponentWarrior.getName().equals("ashkbos") && attacker.getAP() < opponentWarrior.getAP())
            return;
        distance = Math.abs(opponentWarrior.getXInGround() - attacker.getXInGround())
                + Math.abs(opponentWarrior.getYInGround() - attacker.getYInGround());
        switch (attacker.getAttackType()) {
            case MELEE:
                meleeAttackOrCounterAttack(attacker, opponentWarrior, distance, ATTACK);
                attacker.setAttackedThisTurn(true);
                counterAttack(opponentWarrior, attacker);
                break;
            case RANGED:
                rangedAttackOrCounterAttack(attacker, opponentWarrior, distance, ATTACK);
                attacker.setAttackedThisTurn(true);
                counterAttack(opponentWarrior, attacker);
                break;
            case HYBRID:
                hybridAttackOrCounterAttack(attacker, opponentWarrior, distance, ATTACK);
                attacker.setAttackedThisTurn(true);
                counterAttack(opponentWarrior, attacker);
        }
        determineDeadWarriors(attacker, playerIndex);
        determineDeadWarriors(opponentWarrior, 1 - playerIndex);
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
            if (warrior instanceof Minion && ((Minion) warrior).getActivateTimeOfSpecialPower() == ON_DEATH)
                applyMinionSpecialPower((Minion) warrior, null, ON_DEATH);
            if (playersDeck[playerIndex].getItems().size() > 0 && playersDeck[playerIndex].getItems().get(0).getPrice() != 0) {
                Item usableItem = playersDeck[playerIndex].getItems().get(0);
                if (IDsOfOnDeathItems.contains(usableItem.getID()))
                    useItem(players[playerIndex],warrior, null, playersDeck[playerIndex].getItems().get(0));
            }
        }
    }

    public void meleeAttackOrCounterAttack(Warrior attacker, Warrior opponentWarrior, int distance, Status status) {
        if (distance == 1 || (distance == 2 && Math.abs(attacker.getXInGround() - opponentWarrior.getXInGround()) == 1)) {
            strikeEnemy(attacker, opponentWarrior, status);
            //zahhak special power
            if (status == ATTACK && attacker instanceof Hero && attacker.getName().equals("zahhak"))
                new Buffer(this).zahhakAction(opponentWarrior);
        } else if (status == ATTACK)
            throw new InvalidAttackException("Opponent warrior is unavailable for attack");
    }

    public void rangedAttackOrCounterAttack(Warrior attacker, Warrior opponentWarrior, int distance, Status status) {
        if (distance <= attacker.getRange() && !(distance == 1 || (distance == 2 && Math.abs(attacker.getXInGround() - opponentWarrior.getXInGround()) == 1)))
            strikeEnemy(attacker, opponentWarrior, status);
        else if (status == ATTACK)
            throw new InvalidAttackException("Opponent warrior is unavailable for attack");
    }

    public void hybridAttackOrCounterAttack(Warrior attacker, Warrior opponentWarrior, int distance, Status status) {
        try {
            meleeAttackOrCounterAttack(attacker, opponentWarrior, distance, status);
        } catch (InvalidAttackException e) {
            rangedAttackOrCounterAttack(attacker, opponentWarrior, distance, status);
        }
    }

    private void strikeEnemy(Warrior attacker, Warrior opponentWarrior, Status status) {
        if (status == ATTACK && attacker instanceof Minion && ((Minion) attacker).getActivateTimeOfSpecialPower() == ON_ATTACK)
            applyMinionSpecialPower((Minion) attacker, opponentWarrior, ON_ATTACK);
        else if (status == COUNTER_ATTACK && attacker instanceof Minion && ((Minion) attacker).getActivateTimeOfSpecialPower() == ON_DEFEND)
            applyMinionSpecialPower((Minion) attacker, opponentWarrior, ON_DEFEND);
        int attackerOwnerIndex = getPlayerIndex(attacker.getOwner());
        if (playersDeck[attackerOwnerIndex].getItems().size() > 0 && playersDeck[attackerOwnerIndex].getItems().get(0).getPrice() != 0) {
            Item usableItem = playersDeck[attackerOwnerIndex].getItems().get(0);
            if (IDsOfOnAttackItems.contains(usableItem.getID()))
                useItem(attacker.getOwner(), attacker, opponentWarrior, playersDeck[attackerOwnerIndex].getItems().get(0));
        }
        opponentWarrior.changeHP(-1 * attacker.getAP());
    }

    public void applyHeroSpecialPower(Account player, Asset targetAsset, int x, int y) {
        int playerIndex = getPlayerIndex(player);
        Account opponent = getOpponent(player);
        Buffer buffer = new Buffer(this);
        Hero playerHero = playersDeck[playerIndex].getHero();
        Warrior targetWarrior = null;
        if (targetAsset instanceof Warrior)
            targetWarrior = (Warrior) targetAsset;
        // For custom card
        if (playerHero.getBuff() != null) {
            if (targetWarrior == null)
                throw new InvalidTargetException("Please select a correct target.");
            buffer.customWarriorAction(playerHero, targetWarrior, playerHero.getBuff(), playerHero.isTargetFriend(), null);
        }

        if (playerHero.getAction().equals("NoAction"))
            throw new NoAvailableBufferForCardException("Your hero doesn't have special power.");
        if (x != -1 && y != -1 && playerHero.getID() != 2005 && (targetWarrior == null || targetWarrior.getOwner() != opponent))
            throw new InvalidTargetException("Please select an enemy warrior.");
        if (playerHero.getSpecialPowerCountdown() > 0)
            throw new InvalidCooldown("Cooldown of your special power hasn't reached.");
        if (playersMana[playerIndex] < playerHero.getMP())
            throw new InsufficientManaException();

        switch (playerHero.getID()) {
            case 2000:
                buffer.whiteDamnAction(player);
                break;
            case 2001:
                buffer.simorghAction(opponent, battleGround);
                break;
            case 2002:
                buffer.sevenHeadDragonAction(targetWarrior);
                break;
            case 2003:
                buffer.rakhshAction(targetWarrior);
                break;
            case 2005:
                buffer.kavehAction(battleGround, x, y);
                break;
            case 2006:
                buffer.arashAction(player, opponent, battleGround);
                break;
            case 2007:
                buffer.legendAction(targetWarrior);
                break;
            case 2008:
                buffer.esfandiarAction(player);
        }
        playerHero.changeSpecialPowerCountdown(playerHero.getCooldown());
        playersMana[playerIndex] -= playerHero.getMP();
    }

    public void applyMinionSpecialPower(Minion attacker, Warrior opponentWarrior, ActivateTimeOfSpecialPower activateTimeOfSpecialPower) {
        if (attacker.getAction().equals("NoAction"))
            return;
        Buffer buffer = new Buffer(this);
        Class bufferClass = buffer.getClass();
        Class[] methodArgs;
        int playerIndex = getPlayerIndex(attacker.getOwner());
        if (attacker.getID() >= 3040 && attacker.getID() < 4000) {
            buffer.customWarriorAction(attacker, opponentWarrior, attacker.getBuff(), attacker.isTargetFriend(), attacker.getActivateTimeOfSpecialPower());
            return;
        }
        switch (activateTimeOfSpecialPower) {
            case PASSIVE:
                methodArgs = new Class[]{Account.class, Minion.class, BattleGround.class};
                try {
                    bufferClass.getMethod(attacker.getAction(), methodArgs).invoke(buffer, attacker.getOwner(), attacker, battleGround);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ON_DEATH:
                switch (attacker.getName()) {
                    case "siavash":
                        buffer.siavashAction(playersDeck[playerIndex].getHero());
                        break;
                    case "oneEyeGiant":
                        buffer.oneEyeGiantAction(attacker, battleGround);
                }
                break;
            case ON_SPAWN:
                methodArgs = new Class[]{Account.class, Minion.class, BattleGround.class};
                try {
                    bufferClass.getMethod(attacker.getAction(), methodArgs).invoke(buffer, getOpponent(attacker.getOwner()), attacker, battleGround);
                } catch (Exception e) {
                    throw new NoAvailableBufferForCardException();
                }
                break;
            case ON_ATTACK:
                methodArgs = new Class[]{Minion.class, Warrior.class};
                try {
                    bufferClass.getMethod(attacker.getAction(), methodArgs).invoke(buffer, attacker, opponentWarrior);
                } catch (Exception e) {
                    throw new NoAvailableBufferForCardException();
                }
        }
    }

    public void  attackCombo(Account player, ArrayList<Minion> playerMinions, Warrior opponentWarrior) {
        for (int i = 0; i < playerMinions.size(); i++) {
            if (playerMinions.get(i).getOwner() == player) {
                if ((playerMinions.get(i)).getActivateTimeOfSpecialPower() == COMBO)
                    attack(player, playerMinions.get(i), opponentWarrior);
                else {
                    throw new NotComboException();
                }
            }
        }
    }

    public void insertCard(Account player, String cardName, int x, int y) {
        x--;
        y--;
        int playerIndex = getPlayerIndex(player);
        if (!isThereAnyAdjacentOwnWarrior(player, x, y, battleGround))
            throw new InvalidInsertInBattleGroundException("Invalid target");
        for (int i = 0; i < NUMBER_OF_CARDS_IN_HAND; i++) {
            Card card = playersHand[playerIndex][i];
            if (card != null && card.getName().equals(cardName)) {
                if (playersMana[playerIndex] >= card.getMP()) {
                    playersMana[playerIndex] -= card.getMP();
                    playersHand[playerIndex][i] = null;
                    if (card instanceof Spell) {
                        if (card.getBuff() != null)
                            new Buffer(this).customSpellAction((Spell) card, card.getName(), card.getBuff(), x, y);
                        else
                            applySpellBuffers(player, players[1 - playerIndex], card, x, y);
                        playersGraveYard[playerIndex].getDeadCards().add(card);
                    }
                    else {
                        if (battleGround.getGround().get(y).get(x) != null)
                            throw new ThisCellFilledException();
                        battleGround.getGround().get(y).set(x, card);
                        card.setXInGround(x);
                        card.setYInGround(y);
                        ((Warrior) card).setMovedThisTurn(true);
                        playersSelectedCard[playerIndex] = card;
                        inGroundCards.add(card);
                        if (card instanceof Minion && ((Minion) card).getActivateTimeOfSpecialPower() == ON_SPAWN)
                            applyMinionSpecialPower((Minion) card, null, ON_SPAWN);
                        if (playersDeck[playerIndex].getItems().size() > 0 && playersDeck[playerIndex].getItems().get(0).getPrice() != 0) {
                            Item usableItem = playersDeck[playerIndex].getItems().get(0);
                            if (IDsOfOnSpawnItems.contains(usableItem.getID()))
                                useItem(players[playerIndex], (Warrior) card,null, playersDeck[playerIndex].getItems().get(0));
                        }
                    }
                    return;
                }
                else
                    throw new InsufficientManaException();
            }
        }
        throw new AssetNotFoundException("Invalid card name");
    }

    private boolean isThereAnyAdjacentOwnWarrior(Account player, int x, int y, BattleGround battleGround) {
        boolean isThereAnyAdjacentOwnWarrior = false;
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
        return isThereAnyAdjacentOwnWarrior;
    }

    public void endTurn(Account player) {
        Account opponent = getOpponent(player);
        applyAndHandleManaBuffers(opponent);
        applyEffectedBuffersOfWarriors(opponent);
        applyPassiveMinionSpecialPowers(player);
        if (playersDeck[getPlayerIndex(player)].getItems().size() > 0 && playersDeck[getPlayerIndex(player)].getItems().get(0).getPrice() != 0) {
            Item usableItem = playersDeck[getPlayerIndex(player)].getItems().get(0);
            if (IDsOfEndTurnItems.contains(usableItem.getID()))
                useItem(player, null, null, usableItem);
        }
        fillEmptyPlacesOfHandFromDeck(player);
        battleGround.applyCellEffects(this, opponent);
        if (playersDeck[getPlayerIndex(opponent)].getHero().getSpecialPowerCountdown() > 0)
            playersDeck[getPlayerIndex(opponent)].getHero().changeSpecialPowerCountdown(-1);
        endGame();
        turn++;
        resetIsAttackedThisTurn();
        resetIsMovedThisTurn(player);
        setPlayersManaByDefault();
    }

    private void applyPassiveMinionSpecialPowers(Account player) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Asset asset = battleGround.getGround().get(i).get(j);
                if (asset instanceof  Minion && ((Minion) asset).getActivateTimeOfSpecialPower() == PASSIVE && asset.getOwner() == player)
                    applyMinionSpecialPower((Minion) asset, null, PASSIVE);
            }
        }
    }

    public Account getOpponent(Account player) {
        Account opponent;
        if (player == players[0])
            opponent = players[1];
        else
            opponent = players[0];
        return opponent;
    }

    public void applyAndHandleManaBuffers(Account opponent) {
//        for (int opponentIndex = 0; opponentIndex <= 1; opponentIndex++) {
        int opponentIndex = getPlayerIndex(opponent);
        Iterator<BufferOfSpells> iterator = playersManaBuffEffected[opponentIndex].iterator();
        while (iterator.hasNext()) {
            BufferOfSpells bufferOfSpells = iterator.next();
            if (bufferOfSpells.getTurnCountdownUntilActivation() > 0)
                bufferOfSpells.changeTurnCountdownUntilActivation(-1);
            else if (bufferOfSpells.getTurnCountdownUntilActivation() == 0) {
                changePlayerMana(opponentIndex, bufferOfSpells.getValue());
                if (!bufferOfSpells.isLifeEndless())
                    bufferOfSpells.changeTurnCountdownUntilActivation(-1);
            } else {
                bufferOfSpells.changeLifeTime(-1);
                if (bufferOfSpells.getLifeTime() == 0) {
                    changePlayerMana(opponentIndex, -1 * bufferOfSpells.getValue());
                    iterator.remove();
                }
            }
        }
//        }
    }

    public void applyEffectedBuffersOfWarriors(Account opponent) {
        Account player = getOpponent(opponent);
        Warrior warrior;
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battleGround.getGround().get(i).get(j) == null || battleGround.getGround().get(i).get(j) instanceof Item
                        || battleGround.getGround().get(i).get(j) instanceof Flag || battleGround.getGround().get(i).get(j).getOwner() == player)
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
        int playerIndex = getPlayerIndex(player);
        for (int j = 0; j < NUMBER_OF_CARDS_IN_HAND; j++) {
            int nextCardFromDeckIndex = playersDeck[playerIndex].getNextCardFromDeckIndex();
            if (playersHand[playerIndex][j] == null && nextCardFromDeckIndex < Deck.STANDARD_NUMBER_OF_MINIONS_AND_SPELLS) {
                playersHand[playerIndex][j] = playersDeck[playerIndex].getCards().get(nextCardFromDeckIndex);
                playersDeck[playerIndex].setNextCardFromDeckIndex(nextCardFromDeckIndex + 1);
                playersNextCardFromDeck[playerIndex] = playersDeck[playerIndex].getCards().get(nextCardFromDeckIndex + 1);
            }
        }
    }

    public int getPlayerIndex(Account player) {
        int playerIndex = 0;
        if (player == players[1])
            playerIndex = 1;
        return playerIndex;
    }

    private void resetIsAttackedThisTurn() {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Asset asset = battleGround.getGround().get(i).get(j);
                if (asset instanceof Warrior)
                    ((Warrior) asset).setAttackedThisTurn(false);
            }
        }
    }

    private void resetIsMovedThisTurn(Account player) {
        for (ArrayList<Asset> assets : battleGround.getGround()) {
            for (Asset asset : assets) {
                if (asset instanceof Warrior && asset.getOwner() == player)
                    ((Warrior) asset).setMovedThisTurn(false);
            }
        }
    }

    public void setPlayersManaByDefault() {
        if (turn < FIRST_LATE_TURN)
            playersMana[1 - turn % 2]++;
        else
            playersMana[1 - turn % 2] = MAX_MANA_IN_LATE_TURNS;
    }

    public void selectItem(Account player, int collectibleItemID) {
        int playerIndex = 0;
        Item candidateItem;
        if (player == players[1])
            playerIndex = 1;
        try {
            candidateItem = searchItemInPlayersDeck(playerIndex, collectibleItemID);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        playersSelectedItem[playerIndex] = candidateItem;
    }

    public void applySpellBuffers(Account player, Account enemy, Card playerSelectedSpell, int x, int y) {
        Buffer buffer = new Buffer(this);
        Class bufferClass = buffer.getClass();
        Class[] methodArgs = new Class[]{Account.class, Account.class, BattleGround.class, Integer.class, Integer.class};
        try {
            bufferClass.getMethod(playerSelectedSpell.getAction(), methodArgs).invoke(buffer, player, enemy, battleGround, x, y);
        } catch (Exception e) {
            throw new NoAvailableBufferForCardException();
        }
    }

    public void useItem(Account player, Warrior playerWarrior, Warrior enemyWarrior, Item selectedItem) {
        if (selectedItem == null)
            return;
        int playerIndex = getPlayerIndex(player);
        Warrior collector = selectedItem.getCollector();
        Account enemy = getOpponent(player);
        Buffer buffer = new Buffer(this);
        switch (selectedItem.getID()) {
            case 1000:
                buffer.knowledgeCrownAction(player);
                break;
            case 1001:
                buffer.namoos_e_separAction(playersDeck[playerIndex].getHero());
                break;
             case 1002:
                buffer.damoolArchAction(playersDeck[playerIndex].getHero(), enemyWarrior);
                break;
            case 1003:
                buffer.nooshdarooAction(player);
                break;
            case 1004:
                buffer.twoHornArrowAction(player);
                break;
            case 1005:
                buffer.simorghWingAction(enemy);
                break;
            case 1006:
                buffer.elixirAction(collector);
                break;
            case 1007:
                buffer.manaMixtureAction(collector);
                break;
            case 1008:
                buffer.invulnerableMixtureAction(player);
                break;
//TODO hard to implement           case 1009:
//                buffer.deathCurseAction(playerWarrior);
//                break;
            case 1010:
                buffer.randomDamageAction(player);
                break;
            case 1011:
                buffer.terrorHoodAction(enemy);
                break;
            case 1012:
                buffer.bladesOfAgilityAction(player);
                break;
            case 1013:
                buffer.kingWisdomAction(player);
                break;
            case 1014:
                buffer.assassinationDaggerAction(enemy);
                break;
            case 1015:
                buffer.poisonousDaggerAction(enemy);
                break;
            case 1016:
                buffer.shockHammerAction(playerWarrior, enemyWarrior);
                break;
            case 1017:
                buffer.soulEaterAction(player);
                break;
            case 1018:
                buffer.baptismAction((Minion) playerWarrior);
                break;
            case 1019:
                buffer.chineseSwordAction(collector);
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
        int playerIndex = getPlayerIndex(player);
        for (Card card : playersHand[playerIndex])
            if (cardID == card.getID())
                return card;
        throw new AssetNotFoundException("Card not found in the handAndNextCardGrid");
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

    public Item searchItemInPlayersDeck(int playerIndex, int collectibleItemID) {
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
                AIDeck = new Deck(ai, "enemyDeckInStoryGameLevel1");
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
                AIDeck = new Deck(ai, "enemyDeckInStoryGameLevel2");
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
                AIDeck = new Deck(ai, "enemyDeckInStoryGameLevel3");
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
        Deck AIDeck = new Deck(ai, "defaultDeck");
        AIDeck.setHero(customHero);
        customHero.setOwner(ai);
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
        Deck AIDeck = new Deck(ai, "defaultDeck");
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

    public ArrayList<Card> getInGroundCards() {
        return inGroundCards;
    }

    public void setInGroundCards(ArrayList<Card> inGroundCards) {
        this.inGroundCards = inGroundCards;
    }
}