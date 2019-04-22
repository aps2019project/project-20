package View;

import Exceptions.RepeatedUserNameException;
import Presenter.AccountPresenter;

import java.util.Scanner;

public class CreateAccount {
    private final static int EXIT = 123;
    private final static int BACK = 12;
    private final static int BACK_TO_FIRST_PAGE = 13;

    private AccountPresenter accountPresenter = new AccountPresenter();

    public int handleEvents(Scanner scanner) {
        while (true) {
            showMenu();

            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                switch (createAccount(scanner)) {
                    case EXIT:
                        return EXIT;
                    case BACK:
                        return BACK;
                    case BACK_TO_FIRST_PAGE:
                        return BACK_TO_FIRST_PAGE;
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

    public int createAccount(Scanner scanner) {
        System.out.println("Enter Your UserName :");
        String userName = scanner.next();
        System.out.println("Enter Your Password :");
        String password = scanner.next();
        try {
            accountPresenter.createAccountPresenter(userName, password);
        } catch (RepeatedUserNameException i) {
            showMessage(1);
            return 0;
        }
        AccountMenu accountMenu = new AccountMenu();
        switch (accountMenu.handleEvents(scanner)) {
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
        switch (number) {
            case 2:
                System.out.println("Your password Should Have At Least 8 Digits And Consists Of Words And Numbers!!!");
                break;
            case 1:
                System.out.println("This Account Has Already Been Made!!!");
                break;
            case 0:
                System.out.println("InValid Command");
                break;
        }
    }

    public void showHelp() {
        new Help() {
            @Override
            public void show() {
                System.out.println("1 ----> You Can Create Account Here");
                System.out.println("3 ----> You Can Go Back");
                System.out.println("4 ----> You Can Exit From Game");
            }
        }.show();
    }

    public void showMenu() {
        System.out.println("\n<<Create Account>>\n");
        System.out.println("1.Enter New UserName And Password");
        System.out.println("2.Help");
        System.out.println("3.Back");
        System.out.println("4.Exit");
    }

}
