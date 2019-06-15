package Presenter;
import Exceptions.RepeatedUserNameException;
import Exceptions.UserNotFoundException;
import Exceptions.WrongPasswordException;
import Model.Account;

import java.io.IOException;

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

}
