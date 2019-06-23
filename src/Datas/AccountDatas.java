package Datas;

import Exceptions.UserNotFoundException;
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

    public static Account searchAccount(String username) {
        for (Account account : accounts) {
            if (account.getName().compareTo(username) == 0) {
                return account;
            }
        }
        throw new UserNotFoundException("User not found.");
    }

    public void SetAccount() {
    }

}
