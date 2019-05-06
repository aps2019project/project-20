package Presenter;
import Exceptions.RepeatedUserNameException;
import Exceptions.UserNotFoundException;
import Exceptions.WrongPasswordException;
import Model.Account;

public class AccountPresenter {

    public void loginPresenter(String userName, String password){
        Account account;
        try {
            account = Account.login(userName,password);
        }catch (UserNotFoundException | WrongPasswordException e) {
            throw e;
        }
        CurrentAccount.setCurrentAccount(account);
    }

    public void createAccountPresenter(String userName,String password){
        Account account;
        try {
            account = Account.createAccount(userName, password);
        }catch (RepeatedUserNameException e){
         throw e;
        }
        CurrentAccount.setCurrentAccount(account);
    }

    public void changePassWord(String passWord){
        CurrentAccount.getCurrentAccount().setPassword(passWord);
    }

    public void changeUserName(String userName){
        CurrentAccount.getCurrentAccount().setName(userName);
    }

    public void deleteAccount(){
        Account.deleteAccount(CurrentAccount.getCurrentAccount());
    }



}
