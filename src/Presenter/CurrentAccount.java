package Presenter;
import Model.*;

public class CurrentAccount {

    private static Account currentAccount;

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Account currentAccount) {
        CurrentAccount.currentAccount = currentAccount;
    }
}
