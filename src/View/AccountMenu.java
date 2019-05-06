package View;

import Datas.AccountDatas;
import Exceptions.InvalidSelectMainDeckException;
import Model.Account;
import Model.MatchHistory;
import Presenter.AccountMenuPresenter;

import java.util.ArrayList;
import java.util.Scanner;

import Presenter.CurrentAccount;

public class AccountMenu {
    private final static int EXIT = 123;
    private final static int BACK = 12;
    private final static int BACK_TO_FIRST_PAGE = 13;
    private AccountMenuPresenter accountMenuPresenter = new AccountMenuPresenter();

    public int handleEvents(Scanner scanner) {
        while (true) {
            showMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                CollectionMenu collectionMenu = new CollectionMenu();
                collectionMenu.handleEvents(scanner);
            } else if (option.compareTo("2") == 0) {
                ShopMenu shopMenu = new ShopMenu();
                shopMenu.handleEvents(scanner);
            } else if (option.compareTo("3") == 0) {
                try{
                    accountMenuPresenter.beforeEnterBattleCheck();
                }catch (InvalidSelectMainDeckException e){
                    System.out.println("Selected Deck Is InValid");
                    continue;
                }
                BeforeBattleMenu beforeBattleMenu = new BeforeBattleMenu();
                beforeBattleMenu.handleEvents(scanner);
            } else if (option.compareTo("4") == 0) {
                showMatchHistory();
            } else if (option.compareTo("5") == 0) {
                showLeaderBoard();
            } else if (option.compareTo("6") == 0) {
                save();
            } else if (option.compareTo("7") == 0) {
                AccountManagement accountManagement = new AccountManagement();
                switch (accountManagement.handleManageAccountEvents(scanner)) {
                    case BACK_TO_FIRST_PAGE:
                        return BACK_TO_FIRST_PAGE;
                    case BACK:
                        break;
                }
            } else if (option.compareTo("8") == 0) {
                showHelp();
            } else if (option.compareTo("9") == 0) {
                logout();
                return BACK;
            } else if (option.compareTo("10") == 0) {
                return EXIT;
            } else {
                System.out.println("InValid Command");
            }
        }
    }

    public void save() {
    }

    public void logout() {
    }

    public void showMenu() {
        System.out.println("\n<<<GameMenu>>>");
        System.out.println("User : " + CurrentAccount.getCurrentAccount().getName() + "\n");
        System.out.println("1.Collection");
        System.out.println("2.Shop");
        System.out.println("3.Battle");
        System.out.println("4.Match History");
        System.out.println("5.Leader Board");
        System.out.println("6.Save");
        System.out.println("7.My Profile");
        System.out.println("8.Help");
        System.out.println("9.Logout");
        System.out.println("10.Exit");
    }

    public void showLeaderBoard() {
        accountMenuPresenter.showLeaderBoardPresenter();
        int i = 1;
        for (Account account : AccountDatas.getAccounts()) {
            System.out.println(i++ + " : UserName : " + account.getName() + " - Wins : " + account.getNumberOfWins());
        }
    }

    public void showMatchHistory() {
        ArrayList<MatchHistory> matchHistories = accountMenuPresenter.showMatchHistoryPresenter();
        int i = 1;
        for (MatchHistory matchHistory : matchHistories) {
            System.out.println(i++ + " : Opponent : " + matchHistory.getOpponentName() + " - Result : " + matchHistory.getResult() + " - Time : " + matchHistory.getTime());
        }
    }

    public void showHelp() {
        new Help() {
            @Override
            public void show() {
                System.out.println("1 ----> All Of Your Cards Or Items Are Here");
                System.out.println("2 ----> You Can Buy Card Or Item From Shop");
                System.out.println("3 ----> You Can Start Battle Here");
                System.out.println("4 ----> Your Match Histories Are Here");
                System.out.println("5 ----> You Can See Leaders Here");
                System.out.println("6 ----> You Can Save Your Game");
                System.out.println("7 ----> You Can Change Your Profile Here");
                System.out.println("9 ----> You Can Log Out From Your Account");
                System.out.println("10 ----> You Can Exit From Game");
            }
        }.show();
    }
}
