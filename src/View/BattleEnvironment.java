package View;


import Exceptions.*;
import Model.*;
import Presenter.BattleEnvironmentPresenter;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BattleEnvironment {
    private final static int GAME_ENDED = 321;
    private final static int BACK = 12;

    private BattleEnvironmentPresenter battleEnvironmentPresenter;

    public BattleEnvironment(BattleEnvironmentPresenter battleEnvironmentPresenter) {
        this.battleEnvironmentPresenter = battleEnvironmentPresenter;
    }

    public void handleMainBattleMenuEvents(Scanner scanner) {
        while (true) {

            System.out.println();
            for (ArrayList<Asset> rows : battleEnvironmentPresenter.getBattle().getBattleGround().getGround()) {
                for (Asset asset : rows) {
                    if (asset != null) {
                        if (asset instanceof Hero) {
                            if (asset.getOwner() == battleEnvironmentPresenter.getBattle().getPlayers()[0])
                                System.out.printf("H1");
                            else
                                System.out.printf("H2");

                        }
                        if (asset instanceof Minion) {
                            if (asset.getOwner() == battleEnvironmentPresenter.getBattle().getPlayers()[0])
                                System.out.printf("M1");
                            else
                                System.out.printf("M2");
                        }
                        if (asset instanceof Spell) {
                            if (asset.getOwner() == battleEnvironmentPresenter.getBattle().getPlayers()[0])
                                System.out.printf("S1");
                            else
                                System.out.printf("S2");
                        }
                        if (asset instanceof Flag) {
                            System.out.printf("F");
                        }
                        if (asset instanceof Item) {
                            System.out.printf("I");
                        }
                    } else {
                        System.out.printf("*");
                    }
                    System.out.printf(" ");
                }
                System.out.println();
            }

            showMainBattleMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                if (selectCard(scanner) == GAME_ENDED) {
                    return;
                }
            } else if (option.compareTo("2") == 0) {
                if (selectItem(scanner) == GAME_ENDED) {
                    return;
                }
            } else if (option.compareTo("3") == 0) {
                attackCombo(scanner);
            } else if (option.compareTo("4") == 0) {
                showHand();
            } else if (option.compareTo("5") == 0) {
                showNextCard();
            } else if (option.compareTo("6") == 0) {
                showMyMinions();
            } else if (option.compareTo("7") == 0) {
                showOpponentMinions();
            } else if (option.compareTo("8") == 0) {
                insertCardInBattleGround(scanner);
            } else if (option.compareTo("9") == 0) {
                showCollectableItems();
            } else if (option.compareTo("10") == 0) {
                showGameInfo();
            } else if (option.compareTo("11") == 0) {
                showInfoOfCard(scanner);
            } else if (option.compareTo("12") == 0) {
                enterGraveYard(scanner);
            } else if (option.compareTo("13") == 0) {
                endTurn();
            } else if (option.compareTo("14") == 0) {
                showhelp();
            } else if (option.compareTo("15") == 0) {
                return;
            } else {
                System.out.println("InValid Command");
            }
            if (battleEnvironmentPresenter.endGamePresenter() != -1) {
                return;
            }
        }
    }

    public int handleCardInGroundMenuEvents(Scanner scanner) {
        while (true) {
            showCardInGroundMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                cardMoveTo(scanner);
            } else if (option.compareTo("2") == 0) {
                attack(scanner);
            } else if (option.compareTo("3") == 0) {
                useSpecialPower(scanner);
            } else if (option.compareTo("4") == 0) {
                return BACK;
            } else {
                System.out.println("InValid Command");
            }
            if (battleEnvironmentPresenter.endGamePresenter() != -1) {
                return GAME_ENDED;
            }
        }
    }

    public int handleItemUseMenuEvents(Scanner scanner) {
        while (true) {
            showItemUseMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                showItemInfo();
            } else if (option.compareTo("2") == 0) {
                useItem(scanner);
            } else if (option.compareTo("3") == 0) {
                return BACK;
            } else {
                System.out.println("InValid Command");
            }
            if (battleEnvironmentPresenter.endGamePresenter() != -1) {
                return GAME_ENDED;
            }
        }
    }

    public void handleGraveYardMenuEvents(Scanner scanner) {
        while (true) {
            showGraveYardMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                showCardsInGraveYard();
            } else if (option.compareTo("2") == 0) {
                showCardInfoInGraveYard(scanner);
            } else if (option.compareTo("3") == 0) {
                return;
            } else {
                System.out.println("InValid Command");
            }
        }
    }

    public void showInfoOfCard(Scanner scanner) {
        System.out.println("Enter Card ID : ");
        String cardID = scanner.next();
        Card card;
        try {
            card = battleEnvironmentPresenter.showInfoOfCard(cardID);
        } catch (InvalidInGameAssetIDFormatException e) {
            showMessage(1);
            return;
        } catch (AssetNotFoundException e) {
            showMessage(2);
            return;
        }
        if (card instanceof Hero) {
            printInGameHeroFormat((Hero) card);
        }
        if (card instanceof Minion) {
            printInGameMinionFormat((Minion) card, 2);
        }
        if (card instanceof Spell) {
            printInGameSpellFormat((Spell) card);
        }
    }

    public void showGameInfo() {
        System.out.printf("Your Mana : %s - %s : Opponent Mana\n", battleEnvironmentPresenter.getBattle().getPlayersMana()[0], battleEnvironmentPresenter.getBattle().getPlayersMana()[1]);
        if (battleEnvironmentPresenter.getBattle().getMode() == Battle.Mode.NORMAL) {
            System.out.printf("Your Hero HP : %s - %s : Opponent Hero HP", battleEnvironmentPresenter.getBattle().getPlayersDeck()[0].getHero().getHP(), battleEnvironmentPresenter.getBattle().getPlayersDeck()[1].getHero().getHP());
        }
        if (battleEnvironmentPresenter.getBattle().getMode() == Battle.Mode.FLAG_KEEPING) {
            System.out.printf("Flag Position : (%d,%d)\n", ((KeepFlagBattle) battleEnvironmentPresenter.getBattle()).getSingleFlag().getXInGround() + 1, ((KeepFlagBattle) battleEnvironmentPresenter.getBattle()).getSingleFlag().getYInGround() + 1);
            if (((KeepFlagBattle) battleEnvironmentPresenter.getBattle()).getSingleFlag().getOwner() != null) {
                System.out.printf("Flag Owner : %s", ((KeepFlagBattle) battleEnvironmentPresenter.getBattle()).getSingleFlag().getOwner().getName());
            }
            System.out.println();
        }
        if (battleEnvironmentPresenter.getBattle().getMode() == Battle.Mode.FLAG_COLLECTING) {
            System.out.println(battleEnvironmentPresenter.getBattle().getPlayers()[0].getName() + " Flags :");
            for (Flag flag : ((CollectFlagBattle) battleEnvironmentPresenter.getBattle()).getFlags()) {
                if (flag.getOwner() != null && flag.getOwner() == battleEnvironmentPresenter.getBattle().getPlayers()[0]) {
                    System.out.println("     " + flag.getOwner().getName());
                }
            }
            System.out.println();
            System.out.println(battleEnvironmentPresenter.getBattle().getPlayers()[1].getName() + " Flags :");
            for (Flag flag : ((CollectFlagBattle) battleEnvironmentPresenter.getBattle()).getFlags()) {
                if (flag.getOwner() != null && flag.getOwner() == battleEnvironmentPresenter.getBattle().getPlayers()[1]) {
                    System.out.println("     " + flag.getOwner().getName());
                }
            }
            System.out.println();
        }
    }

    public int selectCard(Scanner scanner) {
        System.out.println("Enter In Game Card ID : ");
        String cardID = scanner.next();
        try {
            battleEnvironmentPresenter.selectCardPresenter(cardID);
        } catch (InvalidInGameAssetIDFormatException e) {
            showMessage(1);
            return 0;
        } catch (AssetNotFoundException e) {
            showMessage(2);
            return 0;
        }
        if (handleCardInGroundMenuEvents(scanner) == GAME_ENDED)
            return GAME_ENDED;
        return 0;
    }

    public void cardMoveTo(Scanner scanner) {
        int x, y;
        try {
            System.out.println("Enter X-destination : ");
            x = scanner.nextInt();
            System.out.println("Enter Y-destination : ");
            y = scanner.nextInt();
            battleEnvironmentPresenter.cardMoveToPresenter(x, y);
        } catch (InputMismatchException e) {
            showMessage(14);
            return;
        } catch (InvalidTargetException e) {
            showMessage(7);
            return;
        } catch (ThisCellFilledException e) {
            showMessage(15);
            return;
        } catch (WarriorSecondMoveInTurnException e) {
            showMessage(13);
            return;
        }
        System.out.printf("%s moved to %d %d", battleEnvironmentPresenter.getBattle().getPlayersSelectedCard()[0].getName(), x, y);
    }

    public void attack(Scanner scanner) {
        System.out.println("Enter Opponent Card ID : ");
        String OpponentID = scanner.next();
        try {
            battleEnvironmentPresenter.attackPresenter(OpponentID);
        } catch (AssetNotFoundException e) {
            showMessage(2);
            return;
        } catch (InvalidAttackException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void attackCombo(Scanner scanner) {
        System.out.println("Enter Opponent Card ID :");
        String Opponent = scanner.next();
        scanner.nextLine();
        System.out.println("Enter Your Card IDs :");
        String[] myCards = scanner.nextLine().split("\\s");
        try {
            battleEnvironmentPresenter.attackComboPresenter(Opponent, myCards);
        } catch (InvalidInGameAssetIDFormatException e) {
            showMessage(1);
            return;
        } catch (AssetNotFoundException e) {
            showMessage(2);
            return;
        } catch (NotComboException e) {
            showMessage(11);
            return;
        }
    }

    public void showCardsInGraveYard() {
        if (battleEnvironmentPresenter.getBattle().getPlayersGraveYard()[0] != null) {
            for (Card card : battleEnvironmentPresenter.getBattle().getPlayersGraveYard()[0].getDeadCards()) {
                if (card instanceof Minion) {
                    printInGameMinionFormat((Minion) card, 2);
                }
                if (card instanceof Spell) {
                    printInGameSpellFormat((Spell) card);
                }
            }
        }
    }

    public void useSpecialPower(Scanner scanner) {
        System.out.println("Enter X-destination : ");
        try {
            int x = scanner.nextInt();
            System.out.println("Enter Y-destination : ");
            int y = scanner.nextInt();
            battleEnvironmentPresenter.useSpecialPowerPresenter(x, y);
        } catch (InputMismatchException e) {
            showMessage(14);
            return;
        } catch (SpecialPowerMisMatchException | NoAvailableBufferForCardException e) {
            showMessage(8);
            return;
        }
    }

    public void insertCardInBattleGround(Scanner scanner) {
        System.out.println("Enter Card Name : ");
        String cardName = scanner.next();
        try {
            System.out.println("Enter X-destination : ");
            int x = scanner.nextInt();
            System.out.println("Enter Y-destination : ");
            int y = scanner.nextInt();
            battleEnvironmentPresenter.insertCardPresenter(cardName, x, y);
        } catch (InputMismatchException e) {
            showMessage(14);
            return;
        } catch (AssetNotFoundException e) {
            showMessage(2);
            return;
        } catch (InvalidInsertInBattleGroundException e) {
            showMessage(4);
            return;
        } catch (ThisCellFilledException e) {
            showMessage(5);
            return;
        } catch (DontHaveEnoughManaException e) {
            showMessage(9);
            return;
        }
        showMessage(6);
    }

    public void endTurn() {
        battleEnvironmentPresenter.endTurnPresenter();
    }

    public static void ShowEndGame(Account player1, Account player2, Battle battle, Battle.Mode mode, int status) {
        if (status == 0) {
            System.out.println("  NoOne Wins!!!");
        }
        if (status == 1) {
            System.out.println(player1.getName() + " Wins!!!");
        }
        if (status == 2) {
            System.out.println(player2.getName() + " Wins!!!");
        }
        if (mode == Battle.Mode.FLAG_COLLECTING) {
            System.out.println(player1.getName() + " : " + CollectFlagBattle.getNumberOfPlayerFlags(player1, ((CollectFlagBattle) battle).getFlags()) + "  -  " + CollectFlagBattle.getNumberOfPlayerFlags(player2, ((CollectFlagBattle) battle).getFlags()) + " : " + player1.getName());
        }
    }

    public int selectItem(Scanner scanner) {
        System.out.println("Enter Item ID: ");
        try {
            int itemID = scanner.nextInt();
            battleEnvironmentPresenter.selectItemPresenter(itemID);
        } catch (InputMismatchException e) {
            showMessage(12);
            return 0;
        } catch (AssetNotFoundException e) {
            showMessage(3);
            return 0;
        }
        if (handleItemUseMenuEvents(scanner) == GAME_ENDED) {
            return GAME_ENDED;
        }
        return 0;
    }

    public void enterGraveYard(Scanner scanner) {
        handleGraveYardMenuEvents(scanner);
    }

    public void useItem(Scanner scanner) {
        try {
            System.out.println("Enter X-destination : ");
            int x = scanner.nextInt();
            System.out.println("Enter Y-destination : ");
            int y = scanner.nextInt();
            battleEnvironmentPresenter.useItemPresenter(x, y);
        } catch (InputMismatchException e) {
            showMessage(14);
            return;
        } catch (InvalidTargetException e) {
            showMessage(7);
            return;
        }
    }

    public void showMyMinions() {
        showInMinionCollectionInBattleGround(battleEnvironmentPresenter.getBattle().getPlayers()[0]);
    }

    public void showInMinionCollectionInBattleGround(Account account) {
        for (ArrayList<Asset> rows : battleEnvironmentPresenter.getBattle().getBattleGround().getGround()) {
            for (Asset asset : rows) {
                if (asset instanceof Minion && asset.getOwner() == account) {
                    printInGameMinionFormat((Minion) asset, 1);
                }
            }
        }
    }

    public void showOpponentMinions() {
        showInMinionCollectionInBattleGround(battleEnvironmentPresenter.getBattle().getPlayers()[1]);
    }

    public void showCardInfoInGraveYard(Scanner scanner) {
        System.out.println("Enter Card ID : ");
        String cardID = scanner.next();
        Card card;
        try {
            card = battleEnvironmentPresenter.showCardInfoInGraveYardPresenter(cardID);
        } catch (InvalidInGameAssetIDFormatException e) {
            showMessage(1);
            return;
        } catch (CardNotFoundInGraveYardException | NullPointerException e) {
            showMessage(10);
            return;
        }
        if (card instanceof Minion) {
            printInGameMinionFormat((Minion) card, 2);
        }
        if (card instanceof Spell) {
            printInGameSpellFormat((Spell) card);
        }
    }

    public void showHand() {
        for (Card card : battleEnvironmentPresenter.getBattle().getPlayersHand()[0]) {
            if (card instanceof Minion) {
                printInGameMinionFormat((Minion) card, 2);
            }
            if (card instanceof Spell) {
                printInGameSpellFormat((Spell) card);
            }
        }
    }

    public void showNextCard() {
        if (battleEnvironmentPresenter.getBattle().getPlayersNextCardFromDeck()[0] instanceof Minion) {
            printInGameMinionFormat((Minion) battleEnvironmentPresenter.getBattle().getPlayersNextCardFromDeck()[0], 2);
        }
        if (battleEnvironmentPresenter.getBattle().getPlayersNextCardFromDeck()[0] instanceof Spell) {
            printInGameSpellFormat((Spell) battleEnvironmentPresenter.getBattle().getPlayersNextCardFromDeck()[0]);
        }
    }

    public void showItemInfo() {
        printInGameItemFormat(battleEnvironmentPresenter.getBattle().getPlayersSelectedItem()[0]);
    }

    public void showCollectableItems() {
        System.out.println("My Items : ");
        if (battleEnvironmentPresenter.getBattle().getPlayersDeck()[0].getItems() != null) {
            for (int i = 0; i < battleEnvironmentPresenter.getBattle().getPlayersDeck()[0].getItems().size(); i++) {
                printInGameItemFormat(battleEnvironmentPresenter.getBattle().getPlayersDeck()[0].getItems().get(i));
            }
        }
    }

    public static void printInGameMinionFormat(Minion minion, int number) {
        switch (number) {
            case 1:
                System.out.printf("%d : %s , health : %d , location : ( %d, %d ) ; power : %d\n", minion.getID(), minion.getName(), minion.getHP(), minion.getXInGround() + 1, minion.getYInGround() + 1, minion.getAP());
                break;
            case 2:
                System.out.printf("Minion:\n" +
                        "Name: %s\n" +
                        "HP: %d AP: %d MP: %d\n" +
                        "Range: %d\n" +
                        "Combo-ability: %s\n" +
                        "Cost: %d\n" +
                        "Desc: %s\n\n", minion.getName(), minion.getHP(), minion.getAP(), minion.getMP(), minion.getRange(), minion.getAttackType(), minion.getPrice(), minion.getDesc());
                break;
        }
    }

    public static void printInGameHeroFormat(Hero hero) {
        System.out.printf("Hero:\n" +
                "Name: %s\n" +
                "HP: %s\n" +
                "Cost: %d\n" +
                "Desc: %s\n\n", hero.getName(), hero.getHP(), hero.getPrice(), hero.getDesc());
    }

    public static void printInGameItemFormat(Item item) {
        if (item.getPrice() != 0) {
            System.out.printf(
                    "Name: %s\n" +
                            "Cost: %d\n" +
                            "Desc: %s\n\n", item.getName(), item.getPrice(), item.getDesc());
        } else {
            System.out.printf(
                    "Name: %s\n" +
                            "Type: Collectable\n" +
                            "Desc: %s\n\n", item.getName(), item.getDesc());
        }

    }

    public static void printInGameSpellFormat(Spell spell) {
        System.out.printf("Spell:\n" +
                "Name: %s\n" +
                "MP: %d\n" +
                "Cost: %d\n" +
                "Desc: %s\n\n", spell.getName(), spell.getMP(), spell.getPrice(), spell.getDesc());
    }

    public void showMainBattleMenu() {
        System.out.println("\n<<  YOUR TURN  >>");
        System.out.println("1.Select An Card In Ground");
        System.out.println("2.Select An Item From Your Items");
        System.out.println("3.Attack Combo");
        System.out.println("4.Show handAndNextCardGrid");
        System.out.println("5.Show Next Card");
        System.out.println("6.Show My Minions");
        System.out.println("7.Show Opponent Minions");
        System.out.println("8.Insert A Card From Your Hand");
        System.out.println("9.Show My Items");
        System.out.println("10.Show Game Info");
        System.out.println("11.Show Info Of Card");
        System.out.println("12.Enter Grave Yard");
        System.out.println("13.End Turn");
        System.out.println("14.Help");
        System.out.println("15.Exit From Battle");
    }

    public void showCardInGroundMenu() {
        System.out.println("\n--->> " + battleEnvironmentPresenter.getBattle().getPlayersSelectedCard()[0].getName() + " Selected :");
        System.out.println("    1.Move Your Card");
        System.out.println("    2.Attack");
        System.out.println("    3.Use Special Power");
        System.out.println("    4.Back");
    }

    public void showItemUseMenu() {
        System.out.println("\n--->> " + battleEnvironmentPresenter.getBattle().getPlayersSelectedItem()[0].getName() + " Selected :");
        System.out.println("    1.Show Item Info");
        System.out.println("    2.Use This Item");
        System.out.println("    3.Back");
    }

    public void showGraveYardMenu() {
        System.out.println("---> Grave Yard :");
        System.out.println("    1.Show Cards");
        System.out.println("    2.Show Info For A Card");
        System.out.println("    3.Back");
    }

    public void showMessage(int number) {
        switch (number) {
            case 1:
                System.out.println("Your Input ID Format Is Incorrect!!!\nthe Correct Form Is [Player name] _[card Name] _[Turn Use].");
                break;
            case 2:
                System.out.println("There is No Card With This ID In Battle Ground!!!");
                break;
            case 10:
                System.out.println("There is No Card With This ID In Grave Yard!!!");
                break;
            case 3:
                System.out.println("You Have No Item With This ID!!!");
                break;
            case 4:
                System.out.println("Your Destination Is Unavailable!!!");
                break;
            case 5:
                System.out.println("Your Destination Is filled!!!");
                break;
            case 6:
                System.out.println("Your Card Inserted.");
                break;
            case 7:
                System.out.println("Invalid Target!!!");
                break;
            case 8:
                System.out.println("This Card Can't Use Special Power Right Now!!!");
                break;
            case 9:
                System.out.println("You Don't Have Enough Mana!!!");
                break;
            case 11:
                System.out.println("Some Of Your Minions Can't Attack Combo!!!");
                break;
            case 12:
                System.out.println("Please Enter Integer ID!!!");
                break;
            case 13:
                System.out.println("You Can't Move This Card Twice!!!");
                break;
            case 14:
                System.out.println("Please Enter Integer Destination!!!");
                break;
            case 15:
                System.out.println("There Is SomeOne In Your Destination!!");
                break;
        }
    }

    public void showhelp() {
        System.out.println("Duelyst is played between two players on a 9x5 board. At the beginning of the game, the board is occupied by the players' Generals. The goal of a game is to reduce the health of the other player's general to 0.\n" +
                "\n" +
                "Each player starts with a deck of cards. Cards come in three varieties: minions; spells; and artifacts. Play takes place in turns. On their turn, a player may move their general and minions and attack adjacent enemies with their minions and general; summon new minions adjacent to their general, cast spells, and equip artifacts to their general. The player may also replace one of their cards. The player's actions are limited by the amount of mana they have and the number of cards they hold. There is also a time limit of 90 seconds per turn. The amount of mana available to the player increases every turn until it reaches its maximum. Players can also gain a one-time boost to their mana total by capturing one of the mana springs around the center of the board. Players draw an extra card from their deck at the end of their turn.\n" +
                "\n" +
                "These general rules are subject to exceptions. Many minions have abilities that alter the normal rules of gameplay. Spells and artifacts can also alter the game's basic rules. Common abilities are indicated by \"keywords\" on cards. For example, a minion with the ranged keyword can attack any enemy, not just those adjacent to it. More unusual abilities are described in text on their cards.\n" +
                "You start with a collection of basic cards. These cannot be crafted or disenchanted, but you can gain more basic cards for each faction (including the secondary general) by leveling that faction up to level 11 (see Faction Progression).\n" +
                "Other cards are gained from opening spirit orbs.\n" +
                "Spirit orbs contain cards. They can be bought with gold or diamonds (obtained with real-world money) and are also offered as rewards for in-game events such as Gauntlet and Boss Battles.\n" +
                "Each spirit orbs belongs to a set (such as the Core set or the Shim'zar set). It only contains cards from that set.\n" +
                "Most cards can be crafted with spirit or disenchanted to gain spirit.\n" +
                "Each card has a particular rarity level that affects how frequently it appears in orbs and how much spirit it is worth.\n" +
                "There are also \"prismatic\" versions of every card.\n" +
                "Prismatic cards cost more spirit to craft and yield more spirit when disenchanted.\n" +
                "Prismatic cards have no special gameplay effect, but have a shiny appearance.\n" +
                "Cards from the Rise of the Bloodbound and Ancient Bonds sets cannot be crafted or disenchanted.\n" +
                "A few cards -- the Seven Sisters -- can only be obtained by having a certain number of other cards in your collection.");
    }


}
