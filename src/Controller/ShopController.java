package Controller;

import Client.Client;
import Datas.SoundDatas;
import Model.Account;
import Model.Asset;
import Model.AssetContainer;
import Presenter.*;
import com.gilecode.yagson.YaGson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.*;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class ShopController implements Initializable, ScreenManager, AccountManageable, DialogThrowable, ImageComparable {
    public ImageView back;
    public AnchorPane anchorPane;
    public Tab buyTab;
    public Tab sellTab;
    public JFXTabPane tabPane;
    public ImageView cardImage;
    public ImageView actionButton;
    public Label accountBudget;
    public Label assetPrice;
    public FlowPane buyFlowPane;
    public FlowPane sellFlowPane;
    public JFXTextField buySearchField;
    public JFXTextField sellSearchField;
    public ScrollPane sellScrollPane;
    public ScrollPane buyScrollPane;
    private ArrayList<Asset> shopAssetCollection = new ArrayList<>();
    private ArrayList<AssetContainer> shopContainers = new ArrayList<>();
    private Asset selectedElement = null;

    public void setBackButtonOnMouseEntered() {
        back.setImage(new Image("file:images/hover_back_button_corner.png"));
    }

    public void setBackButtonOnMousePressed() {
        SoundDatas.playSFX(SoundDatas.PAGE_CHANGING);
        back.setImage(new Image("file:images/pressed_back_button_corner.png"));
    }

    public void setBackButtonOnMouseExited() {
        back.setImage(new Image("file:images/button_back_corner.png"));
    }

    public void setBackButtonOnMouseReleased() throws IOException {
        Client.getWriter().println("exitFromPage");
        loadPageOnStackPane(back.getParent(), "../View/FXML/MainMenu.fxml", "ltr");
    }


    public void setActionButtonOnMousePressed() {
        if (selectedElement != null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
            if (buyTab.isSelected()) {
                actionButton.setImage(new Image("file:images/pressed_buy_button.png"));
            } else if (sellTab.isSelected()) {
                actionButton.setImage(new Image("file:images/pressed_sell_button.png"));
            }
        }
    }

    public void setActionButtonOnMouseMoved() {
        assetPrice.setVisible(false);
        if (selectedElement == null) {
            actionButton.setImage(new Image("file:images/default_action_button.png"));
        } else {
            SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
            if (buyTab.isSelected()) {
                actionButton.setImage(new Image("file:images/hover_buy_button.png"));
            } else if (sellTab.isSelected()) {
                actionButton.setImage(new Image("file:images/hover_sell_button.png"));
            }
        }
    }

    public void setActionButtonOnMouseExited() {
        if (selectedElement == null) {
            actionButton.setImage(new Image("file:images/default_action_button.png"));
        } else {
            assetPrice.setVisible(true);
            actionButton.setImage(new Image("file:images/button_primary_glow.png"));
        }
    }

    public void setActionButtonOnMouseReleased() {
        if (selectedElement != null) {
            if (buyTab.isSelected()) {
                confirmationDialog("Confirmation", "Are you sure to buy " + selectedElement.getName() + " " + selectedElement.getPrice() + " DR ?").setOnMousePressed(event -> {
                    Client.getWriter().println("buy " + new YaGson().toJson(selectedElement, Asset.class));
                });
            } else if (sellTab.isSelected()) {
                confirmationDialog("Confirmation", "Are you sure to sell " + selectedElement.getName() + " " + selectedElement.getPrice() + " DR ?").setOnMousePressed(event -> {
                    Client.getWriter().println("sell " + new YaGson().toJson(selectedElement, Asset.class));
                });
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client.setClientShopController(this);
        Client.getWriter().println("getShopCollection");
        Client.waitForListener();
        shopContainers = new YaGson().fromJson(Client.getMessageListener().getDataFromServer(), new TypeToken<java.util.Collection<AssetContainer>>() {
        }.getType());
        shopAssetCollection = AssetContainer.getAssetArrayList(shopContainers);
        buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy.png")));
        sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell.png")));
        tabPane.setOnMouseClicked(event -> {
            if (ShopController.this.isInRightTabCoordination(event.getX(), event.getY(), 10, 6, 160, 46)) {
                SoundDatas.playSFX(SoundDatas.TAB_PRESS);
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy_pressed.png")));
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell.png")));
            } else if (ShopController.this.isInRightTabCoordination(event.getX(), event.getY(), 10 + 160 + 15, 6, 160, 40)) {
                SoundDatas.playSFX(SoundDatas.TAB_PRESS);
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell_pressed.png")));
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy.png")));
            }
        });
        tabPane.setOnMouseMoved(event -> {
            if (ShopController.this.isInRightTabCoordination(event.getX(), event.getY(), 10, 6, 160, 46)) {
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy_hover.png")));
            } else if (!buyTab.isSelected()) {
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy.png")));
            } else {
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy_pressed.png")));
            }
            if (ShopController.this.isInRightTabCoordination(event.getX(), event.getY(), 10 + 160 + 15, 6, 160, 40)) {
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell_hover.png")));
            } else if (!sellTab.isSelected()) {
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell.png")));
            } else {
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell_pressed.png")));
            }
        });
        buyTab.setOnSelectionChanged(event -> setRightPanelToDefault());
        sellTab.setOnSelectionChanged(event -> setRightPanelToDefault());
        assetPrice.setVisible(false);
        update(getCurrentAccount(), shopContainers);
        buySearchField.textProperty().addListener((observable, oldValue, newValue) -> fillShopFlowPaneCollection(AssetContainer.searchAndGetContainerCollectionFromCollection(shopContainers, newValue)));
        sellSearchField.textProperty().addListener((observable, oldValue, newValue) -> fillFlowPaneAccountCollection(Asset.searchAndGetAssetCollectionFromCollection(getCurrentAccount().getCollection().getAssets(), newValue)));
    }

    public void fillFlowPaneAccountCollection(ArrayList<Asset> assets) {
        sellFlowPane.getChildren().clear();
        for (int i = 0; i < assets.size(); i++) {
            //todo fix bug which null the imageView
            ImageView imageView = new ImageView(new Image(assets.get(i).getAssetImageAddress()));
            imageView.setFitWidth(250);
            imageView.setFitHeight(320);
            Pane pane = new Pane();
            pane.setOnMouseEntered(event -> pane.setStyle("-fx-background-color: #949494;"));
            pane.setOnMouseExited(event -> pane.setStyle("-fx-background-color: -fx-primary;"));
            pane.setOnMousePressed(event -> pane.setStyle("-fx-background-color: #2c2c2c;"));
            pane.setOnMouseReleased(event -> {
                SoundDatas.playSFX(SoundDatas.SELECT_ITEM);
                selectedElement = new Asset().searchAssetFromCardImage(assets, ((ImageView) pane.getChildren().get(0)).getImage());
                assetPrice.setText(selectedElement.getPrice() + " DR");
                assetPrice.setVisible(true);
                cardImage.setImage(new Image(selectedElement.getAssetImageAddress()));
                actionButton.setImage(new Image("file:images/button_primary_glow.png"));
                pane.setStyle("-fx-background-color: -fx-primary;");
            });
            pane.getChildren().add(imageView);
            sellFlowPane.getChildren().add(pane);
        }
    }

    public void fillShopFlowPaneCollection(ArrayList<AssetContainer> assetContainers) {
        buyFlowPane.getChildren().clear();
        for (int i = 0; i < assetContainers.size(); i++) {
            if (assetContainers.get(i).getQuantity() != 0) {
                ImageView imageView = new ImageView(new Image(assetContainers.get(i).getAsset().getAssetImageAddress()));
                imageView.setFitWidth(250);
                imageView.setFitHeight(320);
                Label label = new Label(String.valueOf(assetContainers.get(i).getQuantity()));
                label.setStyle("-fx-font-family:Microsoft Tai Le; -fx-font-size: 20.0; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
                ImageView imageView1 = new ImageView(new Image("file:images/unit_stats_instructional_bg@2x.png"));
                imageView1.setFitWidth(50);imageView1.setFitHeight(30);imageView1.setLayoutX(100);imageView1.setLayoutY(285);
                Pane pane = new Pane();
                pane.setPrefWidth(250);
                pane.setPrefHeight(320);
                pane.setOnMouseEntered(event -> pane.setStyle("-fx-background-color: #949494;"));
                pane.setOnMouseExited(event -> pane.setStyle("-fx-background-color: -fx-primary;"));
                pane.setOnMousePressed(event -> pane.setStyle("-fx-background-color: #2c2c2c;"));
                pane.setOnMouseReleased(event -> {
                    SoundDatas.playSFX(SoundDatas.SELECT_ITEM);
                    selectedElement = new Asset().searchAssetFromCardImage(shopAssetCollection, ((ImageView) pane.getChildren().get(0)).getImage());
                    assetPrice.setText(selectedElement.getPrice() + " DR");
                    assetPrice.setVisible(true);
                    cardImage.setImage(new Image(selectedElement.getAssetImageAddress()));
                    actionButton.setImage(new Image("file:images/button_primary_glow.png"));
                    pane.setStyle("-fx-background-color: -fx-primary;");
                });
                pane.getChildren().addAll(imageView,imageView1, label);
                label.setLayoutX(120);label.setLayoutY(285);
                buyFlowPane.getChildren().add(pane);
            }
        }
    }

    public boolean isInRightTabCoordination(double x, double y, double tabX, double tabY, int tabWidth, int tabHeight) {
        return (x > tabX && x < tabX + tabWidth && y > tabY && y < tabY + tabHeight);
    }

    public void update(Account newAccount, ArrayList<AssetContainer> newShopContainers) {
        CurrentAccount.setCurrentAccount(newAccount);
        shopContainers = newShopContainers;
        shopAssetCollection = AssetContainer.getAssetArrayList(shopContainers);
        accountBudget.setText(getCurrentAccount().getBudget() + " DR");
        fillShopFlowPaneCollection(newShopContainers);
        fillFlowPaneAccountCollection(getCurrentAccount().getCollection().getAssets());
    }

    public void updateShopCollection(ArrayList<AssetContainer> newShopContainers) {
        shopContainers = newShopContainers;
        shopAssetCollection = AssetContainer.getAssetArrayList(shopContainers);
        fillShopFlowPaneCollection(newShopContainers);
    }

    public void setRightPanelToDefault() {
        actionButton.setImage(new Image("file:images/default_action_button.png"));
        cardImage.setImage(new Image("file:images/card_glow_line.png"));
        assetPrice.setVisible(false);
        selectedElement = null;
    }

    public ArrayList<Asset> getShopAssetCollection() {
        return shopAssetCollection;
    }

}
