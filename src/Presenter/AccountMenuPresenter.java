package Presenter;

import Datas.AccountDatas;
import Model.Account;
import Model.MatchHistory;

import java.util.ArrayList;

public class AccountMenuPresenter {

    public void save() {}
    public void logout() {}

    public void showLeaderBoardPresenter() {
         Account.sortAccounts(AccountDatas.getAccounts());
    }

    public ArrayList<MatchHistory> showMatchHistoryPresenter(){
       return CurrentAccount.getCurrentAccount().getMatchHistories();
    }
}
