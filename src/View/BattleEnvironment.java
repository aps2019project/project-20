package View;


import Model.Battle;
import Presenter.BattleEnvironmentPresenter;

import java.util.Scanner;

public class BattleEnvironment {
    private final static int BACK_TO_ACCOUNT_MENU = 13;
    private BattleEnvironmentPresenter battleEnvironmentPresenter;

    public BattleEnvironment(BattleEnvironmentPresenter battleEnvironmentPresenter) {
        this.battleEnvironmentPresenter = battleEnvironmentPresenter;
    }

    public int handleEvents(Scanner scanner) {
//        while (true) {
//            showStoryGameMenu();
//            String option = scanner.next();
//            if (option.compareTo("1") == 0) {
//                if (selectLevelToPlay(scanner, 1) == BACK_TO_ACCOUNT_MENU) {
//                    return BACK_TO_ACCOUNT_MENU;
//                }
//            } else if (option.compareTo("2") == 0) {
//                if (selectLevelToPlay(scanner, 2) == BACK_TO_ACCOUNT_MENU) {
//                    return BACK_TO_ACCOUNT_MENU;
//                }
//            } else if (option.compareTo("3") == 0) {
//                if (selectLevelToPlay(scanner, 3) == BACK_TO_ACCOUNT_MENU) {
//                    return BACK_TO_ACCOUNT_MENU;
//                }
//            } else if (option.compareTo("4") == 0) {
//                return BACK;
//            } else {
//                System.out.println("InValid Command");
//            }
//        }
        return BACK_TO_ACCOUNT_MENU;
    }

    public void setPlayer() {
    }

    public void setOpponent() {
    }

    public void gameInfo() {
    }

    public void selectCard(String CardID) {
    }

    public void cardMoveTo(int x, int y) {
    }

    public void attack(int opponentCardID, int myCardID) {
    }

    public void attackCombo(int opponentCardID, int myCardID) {
    }

    public void counterAttack(int opponentCardID, int myCardID) {
    }

    public void useSpecialPower(int x, int y) {
    }

    public void insertCard(String cardName, int x, int y) {
    }

    public void endTurn() {
    }

    public void selectItem(int collectableCardID) {
    }

    //    public  void useItem(Model.Item playerItemSelected){}
    public void enterGraveYard() {
    }

    public void endGame() {
    }

    public void showMyMinions() {
    }

    public void showOpponentMinions() {
    }

    public void showCardInfo(String cardID) {
    }

    public void showHand() {
    }

    public void showNextCard() {
    }

    public void showItemInfo() {
    }

    public void showMenu() {
    }

    public void showCollectableItems() {
    }

    public void showhelp() {
    }


}
