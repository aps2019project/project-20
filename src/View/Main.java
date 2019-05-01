package View;

import Datas.AccountDatas;
import Model.Shop;

import java.util.Scanner;

public class Main {

    private static AccountDatas accountDatas = new AccountDatas();
    private static Shop shop = new Shop();

    public static void main(String[] args) {
        accountDatas.SetAccount();
        shop.fillShopData();

        Scanner scanner = new Scanner(System.in);
        FirstPage firstPage = new FirstPage();
        firstPage.handleEvents(scanner);
    }

    public static AccountDatas getAccountDatas() {
        return accountDatas;
    }

    public static Shop getShop() {
        return shop;
    }
}
