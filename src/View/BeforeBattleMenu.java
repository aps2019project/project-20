package View;

import java.util.Scanner;

public class BeforeBattleMenu {
    private final static int BACK = 12;
    private final static int BACK_TO_ACCOUNT_MENU = 13;
    public int handleEvents(Scanner scanner) {
        while (true) {
            showBeforeBattleMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                SinglePlayerMenu singlePlayerMenu = new SinglePlayerMenu();
                switch (singlePlayerMenu.handleEvents(scanner)){
                    case BACK:
                        break;
                    case BACK_TO_ACCOUNT_MENU:
                        return BACK_TO_ACCOUNT_MENU;
                }
            } else if (option.compareTo("2") == 0) {
                MultyPlayerMenu multyPlayerMenu = new MultyPlayerMenu();
                switch (multyPlayerMenu.handleEvents(scanner)){
                    case BACK:
                        break;
                    case BACK_TO_ACCOUNT_MENU:
                        return BACK_TO_ACCOUNT_MENU;
                }
            } else if (option.compareTo("3") == 0) {
                return BACK;
            } else {
                System.out.println("InValid Command");
            }
        }
    }

    public void showBeforeBattleMenu(){
        System.out.println("\n--->>Battle : ");
        System.out.println("    1.Single Player");
        System.out.println("    2.Multi Player");
        System.out.println("    3.Back");
    }

    public void showhelp(){
        new Help(){
            @Override
            public void show() {
                System.out.println("    You Can Play Solo or Multi Here");
                System.out.println("    3 ----> Go Back");
            }
        }.show();
    }
}
