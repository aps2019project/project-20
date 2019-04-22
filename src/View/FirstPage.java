package View;

import java.util.Scanner;

public class FirstPage {
    private final static int EXIT=123;

    public void handleEvents(Scanner scanner){
        while (true){
            showMenu();
            String option = scanner.next();
            if (option.matches("1")){
                Login login = new Login();
                switch (login.handleEvents(scanner)){
                    case EXIT:
                        return;
                    default:
                        break;
                }
            }else
            if (option.matches("2")){
                CreateAccount createAccount = new CreateAccount();
                switch (createAccount.handleEvents(scanner)){
                    case EXIT:
                        return;
                    default:
                        break;
                }
            }else
            if (option.matches("3")){
                return;
            }else{
                System.out.println("InValid Command");
            }
        }
    }
    public void showMenu(){
        System.out.println("\n<<<Welcome to Duelist!!!>>>\n");
        System.out.println("1.Login");
        System.out.println("2.Create Account");
        System.out.println("3.Exit");
    }
}
