package Presenter;

public class BattleEnvironment{
//    private Model.Account player;
//   private Model.Account opponentOrAI;


    public void setPlayer(){}
    public void setOpponent(){}
    public void gameInfoPresenter(){}
    public void selectCardPresenter(String cardID){}
    public void cardMoveToPresenter(int x, int y){}
    public void attackPresenter( int opponentCardID, int myCardID){}
    public void attackComboPresenter(int opponentCardID, int myCardID){}
    public void counterAttackPresenter(int opponentCardID, int myCardID){}
    public void useSpecialPowerPresenter(int x, int y){}
    public void insertCardPresenter(String cardName, int x, int y){}
    public void endTurnPresenter(){}
    public void selectItemPresenter(int collectableID){}
   // public void useItemPresenter(Model.Item selectedItem){}
    public void enterGraveYardPresenter(){}
    public void endGamePresenter(){}

    public void showMyMinionsPresenter(){}
    public void showOpponentMinionsPresenter(){}
    public void showCardInfoPresenter(String cardID){}
    public void showHandPresenter(){}
    public void showNextCardPresenter(){}
    public void showItemInfoPresenter(){}
    public void showMenuPresenter(){}
    public void showCollectableItemsPresenter(){}
    public void showhelpPresenter(){}
}
