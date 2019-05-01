package View;

import Exceptions.AssetNotFoundException;
import Exceptions.InsufficientMoneyInBuyFromShopException;
import Exceptions.MaximumNumberOfItemsInBuyException;
import Presenter.CurrentAccount;
import Presenter.ShopMenuPresenter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ShopMenu {

    private final static int BACK = 12;
    private ShopMenuPresenter shopMenuPresenter = new ShopMenuPresenter();

    public int handleEvents(Scanner scanner) {
        while (true) {
            showMenu();
            String option = scanner.next();
            if (option.compareTo("1") == 0) {
                showShopCollection();
            } else if (option.compareTo("2") == 0) {
                showMyCollection();
            } else if (option.compareTo("3") == 0) {
                searchInShop(scanner);
            } else if (option.compareTo("4") == 0) {
                searchInMyCollection(scanner);
            } else if (option.compareTo("5") == 0) {
                buy(scanner);
            } else if (option.compareTo("6") == 0) {
                sell(scanner);
            } else if (option.compareTo("7") == 0) {
                showHelp();
            } else if (option.compareTo("8") == 0) {
                return BACK;
            } else {
                showMessage(0);
            }
        }
    }

    public void sell(Scanner scanner) {
        System.out.println("    Input Card/Item ID : ");
        int ID;
        try {
        ID = scanner.nextInt();
            shopMenuPresenter.sellPresenter(ID);
        }catch (InputMismatchException i){
            showMessage(7);
            return;
        }
        catch (AssetNotFoundException e) {
            showMessage(2);
            return;
        }
        showMessage(9);
    }

    public void buy(Scanner scanner) {
        System.out.println("    Input Card/Item Name : ");
        String name = scanner.next();
        try {
            shopMenuPresenter.buyPresenter(name);
        } catch (AssetNotFoundException e) {
            showMessage(1);
            return;
        } catch (InsufficientMoneyInBuyFromShopException i) {
            showMessage(3);
            return;
        } catch (MaximumNumberOfItemsInBuyException m) {
            showMessage(4);
            return;
        }
        showMessage(5);
    }

    public void searchInMyCollection(Scanner scanner) {
        System.out.println("    Input Card/Item Name : ");
        String name = scanner.next();
        int ID;
        try {
            ID = shopMenuPresenter.searchInMyCollectionPresenter(name);
        } catch (AssetNotFoundException e) {
            showMessage(2);
            return;
        }
        System.out.println("     ID : " +ID);
    }

    public void searchInShop(Scanner scanner) {
        System.out.println("    Input Card/Item Name : ");
        String name = scanner.next();
        int ID;
        try {
            ID = shopMenuPresenter.searchInShopPresenter(name);
        } catch (AssetNotFoundException e) {
            showMessage(1);
            return;
        }
        System.out.println("     ID : " + ID);

    }

    public void showMyCollection() {
        shopMenuPresenter.showMyCollectionPresenter();
    }

    public void showShopCollection() {
        shopMenuPresenter.showShopCollectionPresenter();
    }

    public void showMenu() {
        System.out.println("\n-->> Shop :");
        System.out.println("    Your Budget : "+ CurrentAccount.getCurrentAccount().getBudget()+" DR\n");
        System.out.println("    1.Show Shop Collection");
        System.out.println("    2.Show My Collection");
        System.out.println("    3.Search Card Or Item In Shop");
        System.out.println("    4.Search Card Or Item In My Collection");
        System.out.println("    5.Buy Card Or Item");
        System.out.println("    6.Sell Card Or Item From My Collection");
        System.out.println("    7.Help");
        System.out.println("    8.Back");
    }

    public void showMessage(int number) {
        switch (number) {
            case 1:
                System.out.println("        There Is No Card/Item With This Name In Shop!!!");
                break;
            case 2:
                System.out.println("        There Is No Card/Item With This Name In Your Collection!!!");
                break;
            case 3:
                System.out.println("        You Haven't Enough Budget To Buy!!!");
                break;
            case 4:
                System.out.println("        You Can't Buy More Than Three Items!!!");
                break;
            case 5:
                System.out.println("        You Have Bought Card/Item Successfully!!!");
                break;
            case 6:
                System.out.println("        This Card/Item Was Bought Successfully!!!");
                break;
            case 9:
                System.out.println("        This Card/Item Sold Successfully!!!");
                break;
            case 7:
                System.out.println("        Please Enter Integer ID!!!");
                break;
            case 0:
                System.out.println("    InValid Command");
                break;
        }

    }

    public void showHelp() {
        new Help(){
            @Override
            public void show(){

                System.out.println("1 ----> You Can See Available Card And Items In Shop");
                System.out.println("2 ----> You Can See Your Card And Items");
                System.out.println("3 ----> You Can Search for Card Or Item In Shop");
                System.out.println("4 ----> You Can Search for Card Or Item In Your Collection");
                System.out.println("5 ----> You Can Buy Something Here");
                System.out.println("6 ----> You Can Sell Your Cards Or Items Here");
                System.out.println("8 ----> Back To Account Menu");

            }
        }.show();
    }
}
