package View;


import Model.Battle;
import Presenter.BattleEnvironmentPresenter;

import java.util.Scanner;

public class BattleEnvironment {
    private final static int BACK = 12;
    private BattleEnvironmentPresenter battleEnvironmentPresenter;

    public BattleEnvironment(BattleEnvironmentPresenter battleEnvironmentPresenter) {
        this.battleEnvironmentPresenter = battleEnvironmentPresenter;
    }

    public void handleMainBattleMenuEvents(Scanner scanner) {
        while (true) {
            showMainBattleMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                selectCard(scanner);
            } else if (option.compareTo("2") == 0) {
                selectItem(scanner);
            } else if (option.compareTo("3") == 0) {
                attackCombo();
            } else if (option.compareTo("4") == 0) {
                showHand();
            } else if (option.compareTo("5") == 0) {
                showNextCard();
            } else if (option.compareTo("6") == 0) {
                insertCard();
            } else if (option.compareTo("7") == 0) {
                showCollectableItems();
            } else if (option.compareTo("8") == 0) {
               enterGraveYard();
            } else if (option.compareTo("9") == 0) {
                endTurn();
            }else if (option.compareTo("10") == 0) {
                showhelp();
            } else if (option.compareTo("11") == 0) {
                return ;
            } else {
                System.out.println("InValid Command");
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
        }
    }

    public int handlItemUseMenuEvents(Scanner scanner) {
        while (true) {
            showItemUseMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                showItemInfo();
            } else if (option.compareTo("2") == 0) {
                useItem();
            } else if (option.compareTo("3") == 0) {
                return BACK;
            } else {
                System.out.println("InValid Command");
            }
        }
    }

    public int handleGraveYardMenuEvents(Scanner scanner) {
        while (true) {
            showGraveYardMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
              showCardsInGraveYard();
            } else if (option.compareTo("2") == 0) {
                showCardInfoInGraveYard();
            } else if (option.compareTo("3") == 0) {
               return BACK;
            } else {
                System.out.println("InValid Command");
            }
        }
    }

    public void setPlayer() {
    }

    public void setOpponent() {
    }

    public void gameInfo() {
    }

    public void selectCard(Scanner scanner) {
        System.out.println("Enter In Game Card ID : ");
        String cardID = scanner.next();
        try {
            battleEnvironmentPresenter.selectCardPresenter(cardID);
        }catch ()
    }

    public void cardMoveTo(int x, int y) {
    }

    public void attack(int opponentCardID, int myCardID) {
    }

    public void attackCombo(int opponentCardID, int myCardID) {
    }

    public void showCardsInGraveYard(){
    }

    public void useSpecialPower(int x, int y) {
    }

    public void insertCard(String cardName, int x, int y) {
    }

    public void endTurn() {
    }

    public void selectItem(Scanner scanner) {
    }

    public void enterGraveYard() {
    }

    public void useItem(){}

    public void endGame() {
    }

    public void showMyMinions() {
    }

    public void showOpponentMinions() {
    }

    public void showCardInfoInGraveYard(String cardID) {
    }

    public void showHand() {
    }

    public void showNextCard() {
    }

    public void showItemInfo() {
    }

    public void showMainBattleMenu() {
        System.out.println("\n<<  YOUR TURN  >>");
        System.out.println("1.Select An Card In Ground");
        System.out.println("2.Select An Item From Your Items");
        System.out.println("3.Attack Combo");
        System.out.println("4.Show hand");
        System.out.println("5.Show Next Card");
        System.out.println("6.Insert A Card From Your Hand");
        System.out.println("7.Show collectables");
        System.out.println("8.Enter Grave Yard");
        System.out.println("9.End Turn");
        System.out.println("10.Help");
        System.out.println("11.Exit From Battle");
    }
    public void showCardInGroundMenu() {
        System.out.println("\n--->> " + battleEnvironmentPresenter.getBattle().getPlayer1CardSelected().getName()+" Selected :");
        System.out.println("    1.Move Your Card");
        System.out.println("    2.Attack");
        System.out.println("    3.Use Special Power");
        System.out.println("    4.Back");
    }
    public void showItemUseMenu() {
        System.out.println("\n--->> " + battleEnvironmentPresenter.getBattle().getPlayer1ItemSelected().getName()+" Selected :");
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

    public void showCollectableItems() {
    }

    public void showhelp() {

    }


}
