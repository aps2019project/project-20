package View;

import Exceptions.*;
import Model.*;
import Presenter.CollectionMenuPresenter;
import Presenter.CurrentAccount;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CollectionMenu {
    private final static int BACK = 12;
    private CollectionMenuPresenter collectionMenuPresenter = new CollectionMenuPresenter();

    public int handleEvents(Scanner scanner) {
        while (true) {
            showCollectionMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                showCollection();
            } else if (option.compareTo("2") == 0) {
                searchCardOrItem(scanner);
            } else if (option.compareTo("3") == 0) {
                handleDeckEvents(scanner);
            } else if (option.compareTo("4") == 0) {
                showHelp();
            } else if (option.compareTo("5") == 0) {
                return BACK;
            } else {
                showMessage(0);
            }
        }
    }

    public void handleDeckEvents(Scanner scanner) {

        while (true) {
            showDeckMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                showAllDecks();
            } else if (option.compareTo("2") == 0) {
                chooseDeck(scanner);
            } else if (option.compareTo("3") == 0) {
                createDeck(scanner);
            } else if (option.compareTo("4") == 0) {
                chooseMainDeck(scanner);
            } else if (option.compareTo("5") == 0) {
                save();
            } else if (option.compareTo("6") == 0) {
                return;
            } else {
                showMessage(0);
            }
        }
    }

    public void handleSpecifiedDeckEvents(Scanner scanner, Deck deck) {

        while (true) {
            showSpecifiedDeckMenu(deck.getName());
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                showDeckDetails(deck);
            } else if (option.compareTo("2") == 0) {
                addToDeck(scanner, deck);
            } else if (option.compareTo("3") == 0) {
                removeFromDeck(scanner, deck);
            } else if (option.compareTo("4") == 0) {
                isValidDeck(deck);
            } else if (option.compareTo("5") == 0) {
                deleteDeck(deck);
                return;
            } else if (option.compareTo("6") == 0) {
                save();
            } else if (option.compareTo("7") == 0) {
                return;
            } else {
                showMessage(0);
            }
        }
    }

    public static void showCollectionInCollectionMenu(ArrayList<Item> items, ArrayList<Hero> heroes, ArrayList<Spell> spells, ArrayList<Minion> minions) {
        System.out.println("    Heroes :");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.printf("         %d :", i + 1);
            printHeroFormat(heroes.get(i), 1);
        }
        System.out.println("    Items :");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("         %d :", i + 1);
            printItemFormat(items.get(i), 1);
        }
        System.out.println("    Cards :");
        for (int i = 0; i < spells.size(); i++) {
            System.out.printf("         %d :", i + 1);
            printSpellFormat(spells.get(i), 1);
        }
        for (int i = 0; i < minions.size(); i++) {
            System.out.printf("         %d :", i + 1);
            printMinionFormat(minions.get(i), 1);
        }

    }

    public static void showCollectionInAllDecks(ArrayList<Deck> decks) {
        for (int i = 0; i < decks.size(); i++) {
            System.out.printf("    %d %s\n", i + 1, decks.get(i).getName());
            showCollectionInDeck(decks.get(i));
        }

    }

    public static void showCollectionInDeck(Deck deck) {
        System.out.println("          Hero :");
        if (deck.getHero() != null) {
            printHeroFormat(deck.getHero(), 2);
        }
        System.out.println("          Items :");
        for (int i = 0; i < deck.getItems().size(); i++) {
            System.out.printf("               %d :", i + 1);
            printItemFormat(deck.getItems().get(i), 2);
        }
        System.out.println("          Cards :");
        for (int i = 0; i < deck.getCards().size(); i++) {
            System.out.printf("               %d :", i + 1);
            if (deck.getCards().get(i) instanceof Spell) {
                printSpellFormat((Spell) deck.getCards().get(i), 2);
            }
            if (deck.getCards().get(i) instanceof Minion) {
                printMinionFormat((Minion) deck.getCards().get(i), 2);
            }
        }

    }

    public static void showCollectionInBuyMenu(ArrayList<Item> items, ArrayList<Hero> heroes, ArrayList<Spell> spells, ArrayList<Minion> minions) {
        System.out.println("    Heroes :");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.printf("         %d :", i + 1);
            printHeroFormat(heroes.get(i), 3);
        }
        System.out.println("    Items :");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("         %d :", i + 1);
            printItemFormat(items.get(i), 3);
        }
        System.out.println("    Cards :");
        for (int i = 0; i < spells.size(); i++) {
            System.out.printf("         %d :", i + 1);
            printSpellFormat(spells.get(i), 3);
        }
        for (int i = 0; i < minions.size(); i++) {
            System.out.printf("         %d :", i + 1);
            printMinionFormat(minions.get(i), 3);
        }

    }

    public static void printHeroFormat(Hero hero, int format) {
        switch (format) {
            case 1:
                System.out.printf(" : Name : %s - AP : %d – HP : %d – Class : %s – Special power: %s - Sell Cost : %d\n", hero.getName(), hero.getAP(), hero.getHP(), hero.getAttackType(), hero.getPrice());
                break;
            case 2:
                System.out.printf(" : Name : %s - AP : %d – HP : %d – Class : %s – Special power: %s\n", hero.getName(), hero.getAP(), hero.getHP(), hero.getAttackType());
                break;
            case 3:
                System.out.printf(" : Name : %s - AP : %d – HP : %d – Class : %s – Special power: %s - Buy Cost : %d\n", hero.getName(), hero.getAP(), hero.getHP(), hero.getAttackType(), hero.getPrice());
                break;

        }
    }

    public static void printItemFormat(Item item, int format) {
        switch (format) {
            case 1:
                System.out.printf(" : Name : %s – Desc : %s – Special power: %s - Sell Cost : %d\n", item.getName(), item.getDesc(), item.getPrice());
                break;
            case 2:
                System.out.printf(" : Name : %s – Desc : %s – Special power: %s\n", item.getName(), item.getDesc());
                break;
            case 3:
                System.out.printf(" : Name : %s – Desc : %s – Special power: %s - Buy Cost : %d\n", item.getName(), item.getDesc(), item.getPrice());
                break;

        }
    }

    public static void printSpellFormat(Spell spell, int format) {
        switch (format) {
            case 1:
                System.out.printf(" Type : Spell : Name : %s – MP : %d - Desc : %s – Special power: %s - Sell Cost : %d\n", spell.getName(), spell.getMP(), spell.getDesc(), spell.getPrice());
                break;
            case 2:
                System.out.printf(" Type : Spell : Name : %s – MP : %d -  Desc : %s – Special power: %s\n", spell.getName(), spell.getMP(), spell.getDesc());
                break;
            case 3:
                System.out.printf(" Type : Spell : Name : %s – MP : %d -  Desc : %s – Special power: %s - Buy Cost : %d\n", spell.getName(), spell.getMP(), spell.getDesc(), spell.getPrice());
                break;

        }
    }

    public static void printMinionFormat(Minion minion, int format) {
        switch (format) {
            case 1:
                System.out.printf(" Type : Minion : Name : %s - Class : %s - AP : %d – HP : %d – MP : %d – Special power: %s - Sell Cost : %d\n", minion.getName(), minion.getAttackType(), minion.getAP(), minion.getHP(), minion.getMP(), minion.getPrice());
                break;
            case 2:
                System.out.printf(" Type : Minion : Name : %s - Class : %s - AP : %d – HP : %d – MP : %d – Special power: %s\n", minion.getName(), minion.getAttackType(), minion.getAP(), minion.getHP(), minion.getMP());
                break;
            case 3:
                System.out.printf(" Type : Minion : Name : %s - Class : %s - AP : %d – HP : %d – MP : %d – Special power: %s - Buy Cost : %d\n", minion.getName(), minion.getAttackType(), minion.getAP(), minion.getHP(), minion.getMP(), minion.getPrice());
                break;

        }
    }

    public void chooseDeck(Scanner scanner) {
        System.out.println("        Deck Name : ");
        String name = scanner.next();
        Deck deck;
        try {
            deck = collectionMenuPresenter.chooseDeck(name);
        } catch (DeckNotFoundException e) {
            showMessage(2);
            return;
        }
        handleSpecifiedDeckEvents(scanner, deck);

    }

    public void chooseMainDeck(Scanner scanner) {
        System.out.println("        Deck Name : ");
        String name = scanner.next();
        try {
            collectionMenuPresenter.chooseMainDeck(name);
        } catch (DeckNotFoundException e) {
            showMessage(2);
            return;
        } catch (RepeatedDeckException i) {
            showMessage(5);
            return;
        } catch (InvalidSelectMainDeckException c) {
            showMessage(6);
            return;
        }
        showMessage(7);
    }

    public void isValidDeck(Deck deck) {
        try {
            collectionMenuPresenter.isValidDeck(deck);
        } catch (InvalidSelectMainDeckException e) {
            showMessage(13);
            return;
        }
        showMessage(14);
    }

    public void removeFromDeck(Scanner scanner, Deck deck) {
        System.out.println("        Input Card/Item Id : ");
        int ID;
        try {
            ID = scanner.nextInt();
            collectionMenuPresenter.deleteFromDeck(ID, deck);
        } catch (InputMismatchException i) {
            showMessage(16);
            return;
        } catch (AssetNotFoundException e) {
            showMessage(1);
            return;
        }
        showMessage(12);
    }

    public void addToDeck(Scanner scanner, Deck deck) {
        System.out.println("        Input Card/Item Id : ");
        int ID;
        try {
            ID = scanner.nextInt();
            collectionMenuPresenter.addToDeck(ID, deck);
        } catch (InputMismatchException p) {
            showMessage(16);
            return;
        } catch (AssetNotFoundException e) {
            showMessage(1);
            return;
        } catch (IllegalHeroAddToDeckException i) {
            showMessage(9);
            return;
        } catch (IllegalCardAddToDeckException c) {
            showMessage(10);
            return;
        }
        showMessage(11);
    }

    public void deleteDeck(Deck deck) {
        collectionMenuPresenter.deleteDeck(deck);
        showMessage(15);
    }

    public void showDeckDetails(Deck deck) {
        CollectionMenu.showCollectionInDeck(deck);
    }

    public void createDeck(Scanner scanner) {
        System.out.println("New Deck Name : ");
        String name = scanner.next();
        try {
            collectionMenuPresenter.createDeck(name);
        } catch (RepeatedDeckException e) {
            showMessage(3);
            return;
        }
        showMessage(4);

    }

    public void save() {
    }

    public void searchCardOrItem(Scanner scanner) {
        System.out.println("    Card/Item Name:");
        int ID = 0;
        try {
            ID = collectionMenuPresenter.searchAsset(scanner.next());
        } catch (AssetNotFoundException e) {
            showMessage(1);
            return;
        }
        System.out.println("    ID : " + ID);
    }

    public void showCollection() {
        collectionMenuPresenter.showCollection();
    }

    public void showAllDecks() {
        collectionMenuPresenter.showAllDecks();
    }

    public void showCollectionMenu() {
        System.out.println("\n-->> Collection :");
        System.out.println("    1.Show Collection");
        System.out.println("    2.Search Card Or Item");
        System.out.println("    3.My Decks");
        System.out.println("    4.Help");
        System.out.println("    5.Back");
    }

    public void showDeckMenu() {
        System.out.println("\n-->> Collection -->> My Decks:");
        System.out.println("        1.Show All Decks");
        System.out.println("        2.Choose A Deck");
        System.out.println("        3.Create A Deck");
        System.out.println("        4.Select Main Deck");
        System.out.println("        5.Save");
        System.out.println("        6.Back");
    }

    public void showSpecifiedDeckMenu(String deckName) {
        System.out.println("\n-->> Collection -->> My Decks -->> " + deckName + " :");
        System.out.println("            1.Show Deck Details");
        System.out.println("            2.Add Something To Deck");
        System.out.println("            3.Remove Something From Deck");
        System.out.println("            4.Is valid Deck");
        System.out.println("            5.Delete Deck");
        System.out.println("            6.Save");
        System.out.println("            7.Back");
    }

    public void showMessage(int number) {
        switch (number) {
            case 1:
                System.out.println("        There Is No Card/Item With This Name!!!");
                break;
            case 2:
                System.out.println("        There Is No Deck With This Name!!!");
                break;
            case 3:
                System.out.println("        You Had Made This Deck Before!!!");
                break;
            case 4:
                System.out.println("        Your Deck Created!!!");
                break;
            case 5:
                System.out.println("        Your Have Already Chosen This Deck For Main Deck Before!!!");
                break;
            case 6:
                System.out.println("        Your Deck Is InValid!!!");
                break;
            case 7:
                System.out.println("        Your Deck Is Chosen Successfully!!!");
                break;
            case 8:
                System.out.println("        This Item/Card Has Already Been In Deck Before!!!");
                break;
            case 9:
                System.out.println("        Your Deck Is Full , You Can't Add Anything!!!");
                break;
            case 10:
                System.out.println("        You Can't Add More Than One Hero To Deck!!!");
                break;
            case 11:
                System.out.println("        Card/Item Added To Deck Successfully!!!");
                break;
            case 12:
                System.out.println("        Card/Item Removed From Deck Successfully!!!");
                break;
            case 13:
                System.out.println("        Your Deck Is Not Valid!!!");
                break;
            case 14:
                System.out.println("        Your Deck Is Valid!!!");
                break;
            case 15:
                System.out.println("        Your Deck Is Removed Successfully!!!");
                break;
            case 16:
                System.out.println("        Please Enter Integer ID!!!");
                break;
            case 0:
                System.out.println("    InValid Command");
                break;
        }
    }

    public void showHelp() {
        new Help(){
            @Override
            public void show(){
                System.out.println("\nIn Collection Menu:");
                System.out.println("1 ----> You Can All Of Your Decks With Card And Items");
                System.out.println("2 ----> You Can Choose A Specified Deck To Manage It");
                System.out.println("3 ----> You Can Manage You Deck Here");
                System.out.println("5 ----> Back To Main Menu");

                System.out.println("\nIn My Decks Menu:");
                System.out.println("1 --------> You Can See All Of Your Cards Or Items");
                System.out.println("2 --------> You Search for Specified Card Or Item Here");
                System.out.println("3 --------> You Can Build A Deck Here");
                System.out.println("4 --------> You Can Select Your Main Deck Which Use In Battle");
                System.out.println("5 --------> Save Changes Here");


                System.out.println("\nIn Specified Deck Menu:");
                System.out.println("1 ------------> You Can See Details Of Your Specified Deck");
                System.out.println("2 ------------> You Can Add Card Or Item To Specified Deck");
                System.out.println("3 ------------> You Can Remove Card Or Item From Specified Deck");
                System.out.println("4 ------------> You Can Check If Your Specified Deck Is Valid Or Not");
                System.out.println("5 ------------> You Can Delete Your Specified Deck");
                System.out.println("6 ------------> Save Changes Here");
            }
        }.show();
    }
}
