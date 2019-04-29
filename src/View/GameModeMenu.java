package View;

import java.util.Scanner;

public class GameModeMenu {
    private final static int BACK = 12;
    private final static int BACK_TO_ACCOUNT_MENU = 13;
    public int handleEvents(Scanner scanner) {
        while (true) {
            showStoryGameMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                if(selectModeToPlay(scanner,1)==BACK_TO_ACCOUNT_MENU){
                    return BACK_TO_ACCOUNT_MENU;
                }
            } else if (option.compareTo("2") == 0) {
                if(selectModeToPlay(scanner,2)==BACK_TO_ACCOUNT_MENU){
                    return BACK_TO_ACCOUNT_MENU;
                }
            } else if (option.compareTo("3") == 0) {
                if(selectModeToPlay(scanner,3)==BACK_TO_ACCOUNT_MENU){
                    return BACK_TO_ACCOUNT_MENU;
                }
            }else if (option.compareTo("4") == 0) {
                return BACK;
            } else {
                System.out.println("InValid Command");
            }
        }
    }

    public void showStoryGameMenu(){
        System.out.println("\n--->> Battle --->> Single PLayer --->> Choose Game Mode : ");
        System.out.println("            1.Until Death");
        System.out.println("            2.Single Flag Mode");
        System.out.println("            3.Collecting Flag Mode");
        System.out.println("            4.Back");
    }

    public int selectModeToPlay(Scanner scanner,int levelNumber){
        return BACK_TO_ACCOUNT_MENU;
    }

    public void showhelp(){
        new Help(){
            @Override
            public void show() {
                System.out.println("            1,2,3 ----> Choose You Mode");
                System.out.println("            4 ----> Go Back");
            }
        }.show();
    }
}
