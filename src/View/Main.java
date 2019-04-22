package View;

import AccountDatas.AccountDatas;

import java.util.Scanner;

public class Main {

    private static AccountDatas accountDatas = new AccountDatas();
    public static void main(String[] args) {
        accountDatas.SetAccount();

        Scanner scanner = new Scanner(System.in);
        FirstPage firstPage = new FirstPage();
        firstPage.handleEvents(scanner);
    }

}
