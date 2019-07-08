package Client;
import Model.Card;
import Model.Warrior;
import Presenter.DialogThrowable;
import Presenter.ScreenManager;
import com.gilecode.yagson.YaGson;

import java.io.IOException;
import java.util.Scanner;

public class ClientListener extends Thread implements ScreenManager, DialogThrowable {
    private String DataFromServer;

    public void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void run() {
        try {
            Scanner clientScanner = new Scanner(Client.getClient().getInputStream());
            while (true) {
                if (clientScanner.hasNextLine()) {
                    String serverMessage = clientScanner.nextLine();
//                    System.out.println(serverMessage);

                    //Battle Listeners
                    if (serverMessage.matches("applyHeroSpecialPower;.+|useItem;.+|insertCard;.+|cardMoveTo;.+|attack;.+|")) {
                        setDataFromServer(serverMessage.split(";")[1]);
                        synchronized (Client.getrLock()) {
                            Client.getrLock().notify();
                        }
                    }
                    if (serverMessage.matches("opponentAction;.+")) {
                        switch (serverMessage.split(";")[1]) {
                            case "applyHeroSpecialPower":
                                Client.getBattleGroundController().showOpponentSpecialPowerUsing();
                                break;
                            case "insertCard":
                                int i = Integer.valueOf(serverMessage.split(";")[2]);
                                int j = Integer.valueOf(serverMessage.split(";")[3]);
                                Card handCard = new YaGson().fromJson(serverMessage.split(";")[4], Card.class);
                                Client.getBattleGroundController().showInsertAnimation(i, j, handCard, false);
                                Client.getBattleGroundController().updateGroundCells();
                                break;
                            case "cardMoveTo":
                                int i1 = Integer.valueOf(serverMessage.split(";")[2]);
                                int j1 = Integer.valueOf(serverMessage.split(";")[3]);
                                Client.getBattleGroundController().showMove(i1, j1, false);
                                break;
                            case "attack":
                                Warrior attackedWarrior = new YaGson().fromJson(serverMessage.split(";")[2], Warrior.class);
                                int i2 = Integer.valueOf(serverMessage.split(";")[3]);
                                int j2 = Integer.valueOf(serverMessage.split(";")[4]);
                                //TODO sent battle isn't used.
                                Client.getBattleGroundController().showAttackAnimation(attackedWarrior, i2, j2, false);
                                Client.getBattleGroundController().updateGroundCells();
                        }
                    }

//                    if (serverMessage.matches("loginSuccess .+")) {
//                        CurrentAccount.setCurrentAccount(new YaGson().fromJson(serverMessage.substring(13), Account.class));
//                        openPageOnNewStageInThread(Client.getStackPane().getScene(),"../View/FXML/MainMenu.fxml",true);
//                    }
//                    if (serverMessage.matches("loginError userNotFound")) {
//                        showOneButtonErrorDialogInThread("Login Error","User Not Found!!!");
//                        Client.closeConnection();
//                        return;
//                    }
//                    if (serverMessage.matches("loginError wrongPassword")) {
//                        showOneButtonErrorDialogInThread("Login Error","Password Is Not Correct!!!");
//                        Client.closeConnection();
//                        return;
//                    }
//                    if (serverMessage.matches("signUpSuccess .+")) {
//                        CurrentAccount.setCurrentAccount(new YaGson().fromJson(serverMessage.substring(14), Account.class));
//                        openPageOnNewStageInThread(Client.getStackPane().getScene(),"../View/FXML/MainMenu.fxml",true);
//                    }
//                    if (serverMessage.matches("signUpError repeatedAccount")) {
//                        showOneButtonErrorDialogInThread("signUp Error","This Account Has Already Been Made!!!");
//                        Client.closeConnection();
//                        return;
//                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDataFromServer() {
        return DataFromServer;
    }

    public void setDataFromServer(String dataFromServer) {
        this.DataFromServer = dataFromServer;
    }

}
