package View;

import java.util.Scanner;

public class SinglePlayerMenu {
    private final static int BACK = 12;
    private final static int BACK_TO_ACCOUNT_MENU = 13;
    public int handleEvents(Scanner scanner) {
        while (true) {
            showSinglePlayerMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                StoryGameMenu storyGameMenu = new StoryGameMenu();
                switch (storyGameMenu.handleEvents(scanner)){
                    case BACK:
                        break;
                    case BACK_TO_ACCOUNT_MENU:
                        return BACK_TO_ACCOUNT_MENU;
                }
            } else if (option.compareTo("2") == 0) {
                GameModeMenu gameModeMenu = new GameModeMenu();
                switch (gameModeMenu.handleEvents(scanner)){
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

    public void showSinglePlayerMenu(){
        System.out.println("\n--->> Battle --->> Single PLayer : ");
        System.out.println("        1.Story");
        System.out.println("        2.Custom Game");
        System.out.println("        3.Back");
    }

    public void showhelp(){
        new Help(){
            @Override
            public void show() {
                System.out.println("1 ----> You Can Play 3 Levels Here With 3 Different Heroes");
                System.out.println("2 ----> You Can Play Customized Game here");
                System.out.println("3 ----> Go Back");
            }
        }.show();
    }
}
