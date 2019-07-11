package Client;

import Controller.LeaderBoardController;
import Datas.SoundDatas;
import Model.*;
import Presenter.CurrentAccount;
import Presenter.DialogThrowable;
import Presenter.ScreenManager;
import com.gilecode.yagson.YaGson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public class ClientListener extends Thread implements ScreenManager, DialogThrowable {
    private boolean kill;
    private String dataFromServer;


    @Override
    public void run() {
        kill = false;
        try {
            Scanner clientScanner = new Scanner(Client.getClient().getInputStream());
            while (!kill) {
                if (clientScanner.hasNextLine()) {
                    String serverMessage = clientScanner.nextLine();
                    System.out.println(serverMessage);
                    //Add Listeners
                    if (serverMessage.matches("forceDisconnection")) {
                        showOneButtonCloseConnectionErrorDialog();
                        return;
                    }
                    if (serverMessage.matches("loginSuccess .+")) {
                        CurrentAccount.setCurrentAccount(new YaGson().fromJson(serverMessage.substring(13), Account.class));
                        openPageOnNewStageInThread(Client.getStackPane().getScene(), "../View/FXML/MainMenu.fxml", true);
                    }
                    if (serverMessage.matches("loginError userNotFound")) {
                        showOneButtonErrorDialogInThread("Login Error", "User Not Found!!!");
                        Client.closeConnection();
                        return;
                    }
                    if (serverMessage.matches("loginError wrongPassword")) {
                        showOneButtonErrorDialogInThread("Login Error", "Password Is Not Correct!!!");
                        Client.closeConnection();
                        return;
                    }
                    if (serverMessage.matches("loginError repeatedLogin")) {
                        showOneButtonErrorDialogInThread("Login Error", "You Have Already SignedIn Before!!!");
                        Client.closeConnection();
                        return;
                    }
                    if (serverMessage.matches("signUpSuccess .+")) {
                        CurrentAccount.setCurrentAccount(new YaGson().fromJson(serverMessage.substring(14), Account.class));
                        openPageOnNewStageInThread(Client.getStackPane().getScene(), "../View/FXML/MainMenu.fxml", true);
                    }
                    if (serverMessage.matches("signUpError repeatedAccount")) {
                        showOneButtonErrorDialogInThread("signUp Error", "This Account Has Already Been Made!!!");
                        Client.closeConnection();
                        return;
                    }
                    if (serverMessage.matches("leaderTable .+")) {
                        dataFromServer = serverMessage.substring(12);
                        synchronized (Client.getrLock()) {
                            Client.getrLock().notify();
                        }
                    }
                    if (serverMessage.matches("updateLeaderTable .+")) {
                        synchronized (LeaderBoardController.getMyList()) {
                            LeaderBoardController.getMyList().setAll(new YaGson().fromJson(serverMessage.substring(18), Account[].class));
                        }
                    }
                    if (serverMessage.matches("shopCollection .+")) {
                        dataFromServer = serverMessage.substring(15);
                        synchronized (Client.getrLock()) {
                            Client.getrLock().notify();
                        }
                    }
                    if (serverMessage.matches("updateShopCollection .+")) {
                        Platform.runLater(() -> Client.getClientShopController().updateShopCollection(new YaGson().fromJson(serverMessage.substring(21), new TypeToken<Collection<AssetContainer>>() {}.getType())));
                    }
                    if (serverMessage.matches("auctionCollection .+")){
                        Platform.runLater(() -> Client.getClientShopController().updateAuctionView(new YaGson().fromJson(serverMessage.substring(18),new TypeToken<Collection<AuctionElement>>(){}.getType())));
                    }
                    if (serverMessage.matches("AuctionBuild Error")){
                       showOneButtonErrorDialogInThread("Auction Build Error","You Cannot Hold Auction For One Type Of Asset Twice!!!");
                    }
                    if (serverMessage.matches("AuctionNotSuccess")){
                        showOneButtonInformationDialogInThread("Auction Result","No One Bought Your Asset",false);
                    }
                    if (serverMessage.matches("auctionSold .+")){
                        showOneButtonInformationDialogInThread("Auction Result","Your Asset Sold Successfully.",false);
                        Platform.runLater(() -> {
                            Client.getClientShopController().updateAuctionBuyOrSell(new YaGson().fromJson(serverMessage.substring(12), Account.class));
                        });
                    }
                    if (serverMessage.matches("auctionWin .+")){
                        showOneButtonInformationDialogInThread("Auction Result","Your Won Asset Successfully.",false);
                        Platform.runLater(() -> {
                            Client.getClientShopController().updateAuctionBuyOrSell(new YaGson().fromJson(serverMessage.substring(11), Account.class));
                        });
                    }
                    if (serverMessage.matches("buySuccess .+")) {
                        Platform.runLater(() -> {
                            String[] args = serverMessage.substring(11).split(" \\|\\| ");
                            Client.getClientShopController().updateShopCollectionAndAccountCollection(new YaGson().fromJson(args[0], Account.class), new YaGson().fromJson(args[1], new TypeToken<Collection<AssetContainer>>() {
                            }.getType()));
                        });
                    }

                    if (serverMessage.matches("sellSuccess .+")) {
                        Platform.runLater(() -> {
                            String[] args = serverMessage.substring(12).split(" \\|\\| ");
                            Client.getClientShopController().updateShopCollectionAndAccountCollection(new YaGson().fromJson(args[0], Account.class), new YaGson().fromJson(args[1], new TypeToken<Collection<AssetContainer>>() {
                            }.getType()));
                            Client.getClientShopController().setRightPanelToDefault();
                        });
                    }

                    if (serverMessage.matches("buyError InsufficientMoney")) {
                        showOneButtonErrorDialogInThread("Buy Error", "You Haven't Enough Budget To Buy!!!");
                    }
                    if (serverMessage.matches("buyError MaximumNumberOfItems")) {
                        showOneButtonErrorDialogInThread("Buy Error", "You Can't Buy More Than Three Items!!!");
                    }
                    if (serverMessage.matches("buyError NotEnoughQuantity")) {
                        showOneButtonErrorDialogInThread("Buy Error", "there Is No Asset Of This Type In Shop Anymore!!!");
                    }
                    if (serverMessage.matches("saveSuccess")) {
                        showOneButtonInformationDialogInThread("Save Message", "Changes Saved Successfully.", false);
                    }
                    if (serverMessage.matches("onlinePlayersTable .+")) {
                        dataFromServer = serverMessage.substring(19);
                        synchronized (Client.getrLock()) {
                            Client.getrLock().notify();
                        }
                    }
                    if (serverMessage.matches("updateOnlinePlayersTable .+")) {
                        Client.getOnlinePlayersTableController().updateOnlinePlayersTableButtons(new YaGson().fromJson(serverMessage.substring(25), PlayerViewer[].class));
                    }
                    if (serverMessage.matches("matchQuest .+")) {
                        showBattleInvitation(serverMessage.substring(11));
                    }
                    if (serverMessage.matches("requestDeclined")) {
                        showOneButtonInformationDialogInThread("Answer", "Your Request Was Rejected", true);
                        synchronized (Client.getrLock()) {
                            Client.getrLock().notify();
                        }
                    }
                    if (serverMessage.matches("startingBattle;.+")) {
                        synchronized (Client.getrLock()) {
                            Client.getrLock().notify();
                        }
                        Battle newBattle = new YaGson().fromJson(serverMessage.split(";")[2], Battle.class);
                        startNewGameInThread(newBattle, Integer.valueOf(serverMessage.split(";")[1]));
                    }
                    if (isBattleCommand(serverMessage)) {
                        setDataFromServer(serverMessage.split(";")[1]);
                        synchronized (Client.getrLock()) {
                            Client.getrLock().notify();
                        }
                    }
                    if (serverMessage.matches("endGame;")) {
                        synchronized (Client.getrLock()) {
                            Client.getrLock().notify();
                        }
                        Thread.sleep(4000);
                        loadPageOnStackPaneInThread(Client.getBattleGroundController().battleGroundAnchorPane, "../View/FXML/MainMenu.fxml", "ltr");
                    }
                    if (serverMessage.matches("opponentAction;.+")) {
                        handleOpponentAction(serverMessage);
                    }

                    if (serverMessage.matches("Chat;.+")) {
                        if (Client.getChatController() != null) {
                            Platform.runLater(() -> {
                                Client.getChatController().appendTextToConversation(serverMessage.substring(5));
                            });
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isBattleCommand(String serverMessage) {
        return serverMessage.matches("(selectCard;.+)|(applyHeroSpecialPower;.+)|(useItem;.+)|(insertCard;.+)|(cardMoveTo;.+)|(attack;.+)|(endTurn;.+)");
//        return serverMessage.matches("selectCard;.+") || serverMessage.matches("applyHeroSpecialPower;.+") || serverMessage.matches("useItem;.+") || serverMessage.matches("insertCard;.+") || serverMessage.matches("cardMoveTo;.+") || serverMessage.matches("attack;.+") || serverMessage.matches("endTurn;.+");
    }

    private void handleOpponentAction(String serverMessage) {
        switch (serverMessage.split(";")[1]) {
            case "selectCard":
                int i = Integer.valueOf(serverMessage.split(";")[2]);
                int j = Integer.valueOf(serverMessage.split(";")[3]);
                Client.getBattleGroundController().setBattle(new YaGson().fromJson(serverMessage.split(";")[4], Battle.class));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Client.getBattleGroundController().updateOpponentSelectedCardCoordinates(i, j);
                    }
                });
                break;
            case "applyHeroSpecialPower":
                Client.getBattleGroundController().setBattle(new YaGson().fromJson(serverMessage.split(";")[2], Battle.class));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Client.getBattleGroundController().showOpponentSpecialPowerUsing();
                    }
                });
                break;
            case "insertCard":
                int i0 = Integer.valueOf(serverMessage.split(";")[2]);
                int j0 = Integer.valueOf(serverMessage.split(";")[3]);
                Card handCard = new YaGson().fromJson(serverMessage.split(";")[4], Card.class);
                Client.getBattleGroundController().setBattle(new YaGson().fromJson(serverMessage.split(";")[5], Battle.class));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Client.getBattleGroundController().showInsertAnimation(i0, j0, handCard, false);
                        Client.getBattleGroundController().updateGroundCells();
                    }
                });
                break;
            case "cardMoveTo":
                int i1 = Integer.valueOf(serverMessage.split(";")[2]);
                int j1 = Integer.valueOf(serverMessage.split(";")[3]);
                Client.getBattleGroundController().setBattle(new YaGson().fromJson(serverMessage.split(";")[4], Battle.class));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Client.getBattleGroundController().showMove(i1, j1, false);
                        Client.getBattleGroundController().updateFlagLabels();
                    }
                });
                break;
            case "attack":
                Warrior attackedWarrior = new YaGson().fromJson(serverMessage.split(";")[2], Warrior.class);
                int i2 = Integer.valueOf(serverMessage.split(";")[3]);
                int j2 = Integer.valueOf(serverMessage.split(";")[4]);
                Client.getBattleGroundController().setBattle(new YaGson().fromJson(serverMessage.split(";")[5], Battle.class));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Client.getBattleGroundController().showAttackAnimation(attackedWarrior, i2, j2, false);
                        Client.getBattleGroundController().updateGroundCells();
                    }
                });
                break;
            case "endTurn":
                Client.getBattleGroundController().setBattle(new YaGson().fromJson(serverMessage.split(";")[2], Battle.class));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Client.getBattleGroundController().updateManaGemImages();
                        Client.getBattleGroundController().setWhoseTurnLabel();
                        Client.getBattleGroundController().updateGroundCells();
                        Client.getBattleGroundController().updateCellEffects();
                    }
                });
                break;
            case "endGame":
                Client.getBattleGroundController().showEndGame(Integer.valueOf(serverMessage.split(";")[2]));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadPageOnStackPaneInThread(Client.getBattleGroundController().battleGroundAnchorPane, "../View/FXML/MainMenu.fxml", "ltr");
        }
    }


    private void showBattleInvitation(String inviterName) {
        //todo change sound and graphic
        Platform.runLater(() -> {
            SoundDatas.playSFX(SoundDatas.NORMAL_DIALOG);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setPrefHeight(60);
            Text header = new Text("Battle Invitation");
            header.setStyle("-fx-text-fill: #ff0000;  -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold ;");
            dialogLayout.setHeading(header);
            Text footer = new Text(inviterName + " Invites To The Battle.");
            footer.setStyle("-fx-text-fill: #000000;  -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold ;");
            dialogLayout.setBody(footer);
            dialogLayout.setStyle("-fx-background-color: #acf5ff; -fx-text-fill: #ffffff");
            JFXDialog dialog = new JFXDialog(Client.getStackPane(), dialogLayout, JFXDialog.DialogTransition.CENTER, true);
            JFXButton yesButton = new JFXButton("accept");
            yesButton.setButtonType(JFXButton.ButtonType.RAISED);
            yesButton.setStyle("-fx-background-color: #37b400; -fx-text-fill: #ffffff; -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold ;");
            yesButton.setOnMouseReleased(event -> {
                SoundDatas.playSFX(SoundDatas.DIALOG_YES_BUTTON);
                Client.getWriter().println("acceptBattleMessage " + inviterName);
                dialog.close();
            });
            JFXButton noButton = new JFXButton("decline");
            noButton.setButtonType(JFXButton.ButtonType.RAISED);
            noButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold ;");
            noButton.setOnAction(event -> {
                SoundDatas.playSFX(SoundDatas.DIALOG_NO_BUTTON);
                Client.getWriter().println("rejectBattleMessage " + inviterName);
                dialog.close();
            });
            dialog.setOnDialogClosed(event -> Client.getStackPane().getChildren().remove(dialog));
            dialogLayout.setActions(yesButton, noButton);
            dialog.show();
        });
    }

    private void showOneButtonCloseConnectionErrorDialog() {
        Platform.runLater(() -> {
            SoundDatas.playSFX(SoundDatas.ERROR_DIALOG);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            Text header = new Text("Connection Error");
            Text footer = new Text("the Connection Failed!!!");
            header.setStyle("-fx-text-fill: #ff0000;  -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
            footer.setStyle("-fx-text-fill: #000000;  -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
            dialogLayout.setHeading(header);
            dialogLayout.setBody(footer);
            dialogLayout.setStyle("-fx-background-color: #ffee00; -fx-text-fill: #ffffff");
            JFXDialog dialog = new JFXDialog(Client.getStackPane(), dialogLayout, JFXDialog.DialogTransition.CENTER, true);
            dialog.setStyle("-fx-background-image: url('file:images/error.png')");
            JFXButton button = new JFXButton("Okey");
            button.setButtonType(JFXButton.ButtonType.RAISED);
            button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
            button.setOnAction(event -> {
                SoundDatas.playSFX(SoundDatas.DIALOG_NO_BUTTON);
                dialog.close();
            });
            dialog.setOnDialogClosed(event -> Client.getStackPane().getChildren().remove(dialog));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed(event -> {
                Client.closeConnection();
                openPageOnNewStageInThread(Client.getStackPane().getScene(), "../View/FXML/SignIn.fxml", false);
            });
        });
    }

    public void kill() {
        this.kill = true;
    }

    public String getDataFromServer() {
        return dataFromServer;
    }

    public void setDataFromServer(String dataFromServer) {
        this.dataFromServer = dataFromServer;
    }

}
