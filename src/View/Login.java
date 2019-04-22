package View;

import java.util.Scanner;

import Exceptions.UserNotFoundException;
import Exceptions.WrongPasswordException;
import Presenter.AccountPresenter;

public class Login {
    private final static int EXIT = 123;
    private final static int BACK = 12;
    private final static int BACK_TO_FIRST_PAGE = 13;
    private AccountPresenter accountPresenter = new AccountPresenter();

    public int handleEvents(Scanner scanner) {
        while (true) {
            showMenu();

            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                switch (login(scanner)){
                    case EXIT:
                        return EXIT;
                    case BACK_TO_FIRST_PAGE:
                        return BACK;
                }
            } else if (option.compareTo("2") == 0) {
                showHelp();
            } else if (option.compareTo("3") == 0) {
                return BACK;
            } else if (option.compareTo("4") == 0) {
                return EXIT;
            } else {
                showMessage(0);
            }
        }
    }

    public int login(Scanner scanner) {
        System.out.println("Enter UserName :");
        String userName = scanner.next();
        System.out.println("Enter Password :");
        String password = scanner.next();
        try {
            accountPresenter.loginPresenter(userName, password);
        }catch (UserNotFoundException e) {
            showMessage(1);
            return BACK;
        } catch (WrongPasswordException e) {
            showMessage(2);
            return BACK;
        }
        AccountMenu accountMenu = new AccountMenu();
        switch (accountMenu.handleEvents(scanner)){
            case EXIT:
                return EXIT;
            case BACK:
                return BACK;
            case BACK_TO_FIRST_PAGE:
                return BACK_TO_FIRST_PAGE;
        }
        return 0;
    }

    public void showMessage(int number) {
        switch (number){
            case 1:
                System.out.println("User Not Found!!!");
                break;
            case 2:
                System.out.println("Password Is Not Correct!!!");
                break;
            case 0:
                System.out.println("InValid Command");
                break;
        }
    }

    public void showHelp() {
        new Help(){
            @Override
            public void show(){
                System.out.println("1 ----> You Can Login Here");
                System.out.println("3 ----> You Can Go Back");
                System.out.println("4 ----> You Can Exit From Game");
            }
        }.show();
    }

    public void showMenu(){
        System.out.println("\n<<<Login>>>\n");
        System.out.println("1.Enter UserName And Password");
        System.out.println("2.Help");
        System.out.println("3.Back");
        System.out.println("4.Exit");
    }

}
