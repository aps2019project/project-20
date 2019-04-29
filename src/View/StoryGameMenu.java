package View;

import java.util.Scanner;

public class StoryGameMenu {
    private final static int BACK = 12;
    private final static int BACK_TO_ACCOUNT_MENU = 13;
    public int handleEvents(Scanner scanner) {
        while (true) {
            showStoryGameMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                if(selectLevelToPlay(1)==BACK_TO_ACCOUNT_MENU){
                    return BACK_TO_ACCOUNT_MENU;
                }
            } else if (option.compareTo("2") == 0) {
                if(selectLevelToPlay(2)==BACK_TO_ACCOUNT_MENU){
                    return BACK_TO_ACCOUNT_MENU;
                }
            } else if (option.compareTo("3") == 0) {
                if(selectLevelToPlay(3)==BACK_TO_ACCOUNT_MENU){
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
        System.out.println("\n--->> Battle --->> Single PLayer --->> Story : ");
        System.out.println("            1.Enemy || Hero : White Ghoul   Mode : Until Death  Reward : 500 DR ");
        System.out.println("            2.Enemy || Hero : Zahhak   Mode : Single Flag Mode  Reward : 1000 DR ");
        System.out.println("            3.Enemy || Hero : Arash   Mode : Collecting Flag Mode  Reward : 1500 DR ");
        System.out.println("            4.Back");
    }

    public int selectLevelToPlay(int levelNumber){
        return BACK_TO_ACCOUNT_MENU;
    }

    public void showhelp(){
        new Help(){
            @Override
            public void show() {
                System.out.println("            1,2,3 ----> Choose You Level");
                System.out.println("            4 ----> Go Back");
            }
        }.show();
    }
}
