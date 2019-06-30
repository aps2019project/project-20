//package View;
//
//import Presenter.AccountManager;
//import Presenter.CurrentAccount;
//
//import java.util.Scanner;
//
//public class AccountManagement {
//    private final static int BACK = 12;
//    private final static int BACK_TO_FIRST_PAGE = 13;
//    private AccountManager accountPresenter = new AccountManager();
//
//    public int handleManageAccountEvents(Scanner scanner) {
//        while (true) {
//            showManageAccountMenu();
//            String option = scanner.next();
//            if (option.compareTo("1") == 0) {
//                changeUserName(scanner);
//            } else
//            if (option.compareTo("2") == 0) {
//                changePassWord(scanner);
//            } else
//            if (option.compareTo("3") == 0) {
//                deleteAccount();
//                return BACK_TO_FIRST_PAGE;
//            }
//            else
//            if (option.compareTo("4") == 0) {
//                return BACK;
//            } else {
//                System.out.println("InValid Command");
//            }
//        }
//    }
//
//    public void changeUserName(Scanner scanner){
//        System.out.println("Enter New Username :");
//        String userName = scanner.next();
//        accountPresenter.changeUserName(userName);
//        showMessage(1);
//    }
//    public void changePassWord(Scanner scanner){
//        System.out.println("Enter New Username :");
//        String password = scanner.next();
//        accountPresenter.changePassWord(password);
//        showMessage(2);
//    }
//    public void deleteAccount(){
//        accountPresenter.deleteAccount();
//        showMessage(3);
//    }
//    public void showManageAccountMenu(){
//        System.out.println("\n --->>> Edit My Profile :");
//        System.out.println("    UserName : " + CurrentAccount.getCurrentAccount().getName());
//        System.out.println("    PassWord : " + CurrentAccount.getCurrentAccount().getPassword()+"\n");
//        System.out.println("    1.Change UserName");
//        System.out.println("    2.Change PassWord");
//        System.out.println("    3.Delete Account");
//        System.out.println("    4.Back");
//    }
//    public void showMessage(int number) {
//        switch (number) {
//            case 1:
//                System.out.println("        Your Username Changed Successfully!!!");
//                break;
//            case 2:
//                System.out.println("        Your Password Changed Successfully!!!");
//                break;
//            case 3:
//                System.out.println("        Your Account Deleted Successfully!!!");
//                break;
//            case 0:
//                System.out.println("    InValid Command");
//                break;
//        }
//    }
//}
