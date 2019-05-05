package Datas;

import Model.Account;

import java.util.ArrayList;

public class AccountDatas {

    private static ArrayList<Account> accounts = new ArrayList<>();

    public static void setAccounts(ArrayList<Account> accounts) {
        AccountDatas.accounts = accounts;
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Account SearchAccount(String username) {
        for (Account account : accounts) {
            if (account.getUserName().compareTo(username) == 0) {
                return account;
            }
        }

        return null;
    }

    public void SetAccount() {
    }

}
