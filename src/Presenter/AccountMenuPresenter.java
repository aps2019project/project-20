package Presenter;

import Exceptions.InvalidSelectMainDeckException;
import Model.Account;
import Model.Deck;
import Model.MatchHistory;

import java.util.ArrayList;

public class AccountMenuPresenter {


    public void showLeaderBoardPresenter() {
         //Account.sortAccounts(AccountDatas.getAccounts());
    }

    public ArrayList<MatchHistory> showMatchHistoryPresenter(){
       return CurrentAccount.getCurrentAccount().getMatchHistories();
    }

    public void beforeEnterBattleCheck(){
        if(!CurrentAccount.getCurrentAccount().getMainDeck().isValidOfMainDeck()){
            throw new InvalidSelectMainDeckException("");
        }
    }
}
