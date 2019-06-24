package Presenter;
import Exceptions.*;
import Model.Account;
import Model.Asset;
import Model.Shop;

import java.io.IOException;
import java.util.ArrayList;

public interface AccountManageable {

    default void loginPresenter(String userName, String password){
        Account account;
        try {
            account = Account.login(userName,password);
        }catch (UserNotFoundException | WrongPasswordException e) {
            throw e;
        }
        CurrentAccount.setCurrentAccount(account);
    }

    default void createAccountPresenter(String userName,String password){
        Account account = null;
        try {
            account = Account.createAccount(userName, password);
        }catch (RepeatedUserNameException e){
         throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        CurrentAccount.setCurrentAccount(account);
    }

    default void changePassWord(String passWord){
        CurrentAccount.getCurrentAccount().setPassword(passWord);
    }

    default void changeUserName(String userName){
        CurrentAccount.getCurrentAccount().setName(userName);
    }

    default void deleteAccount(){
        Account.deleteAccount(CurrentAccount.getCurrentAccount());
    }

    default void saveAccount(){
        try {
            CurrentAccount.getCurrentAccount().saveInToFile("Data/AccountsData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void logout(){
        CurrentAccount.setCurrentAccount(null);
    }

    default void sell(Asset sellingAsset) {
            Shop.sell(CurrentAccount.getCurrentAccount(),sellingAsset);
    }

    default void buy(Asset buyingAsset) throws InsufficientMoneyInBuyFromShopException, MaximumNumberOfItemsInBuyException {
        Shop.buy(getCurrentAccount(),buyingAsset);
    }

    default Account getCurrentAccount(){
        return CurrentAccount.getCurrentAccount();
    }

    default ArrayList<Asset> getAllAssets(){
        try {
            return Asset.getAssetsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
