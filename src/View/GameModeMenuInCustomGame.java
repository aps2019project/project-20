package View;

import Exceptions.HeroNotFoundException;
import Model.Hero;
import Presenter.BattleEnvironmentPresenter;
import Presenter.GameMenuPresenter;

import java.util.Scanner;

public class GameModeMenuInCustomGame {
    private final static int BACK = 12;
    private final static int BACK_TO_ACCOUNT_MENU = 13;
    public int handleEvents(Scanner scanner) {
        while (true) {
            showGameModeMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                if(selectModeToPlay(scanner)==BACK_TO_ACCOUNT_MENU){
                    return BACK_TO_ACCOUNT_MENU;
                }
            } else if (option.compareTo("2") == 0) {
                if(selectModeToPlay(scanner)==BACK_TO_ACCOUNT_MENU){
                    return BACK_TO_ACCOUNT_MENU;
                }
            } else if (option.compareTo("3") == 0) {
                if(selectModeToPlay(scanner)==BACK_TO_ACCOUNT_MENU){
                    return BACK_TO_ACCOUNT_MENU;
                }
            }else if (option.compareTo("4") == 0) {
                return BACK;
            } else {
                System.out.println("InValid Command");
            }
        }
    }

    public void showGameModeMenu(){
        System.out.println("\n--->> Battle --->> Single PLayer --->> Choose Game Mode : ");
        System.out.println("            1.Until Death");
        System.out.println("            2.Single Flag Mode");
        System.out.println("            3.Collecting Flag Mode");
        System.out.println("            4.Back");
    }

    public int selectModeToPlay(Scanner scanner){
        System.out.println("           Enter Name Of Hero : ");
        Hero customHero;
        try {
            customHero = Hero.searchHeroForCustomGame(scanner.next());
        }catch (HeroNotFoundException e){
            System.out.println("           Your Hero Not Found!!!");
            return 0;
        }
        new BattleEnvironment(new BattleEnvironmentPresenter(new GameMenuPresenter().prepareForSingleGame(0,customHero))).handleMainBattleMenuEvents(scanner);
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
