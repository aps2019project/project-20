package Controller;

import Client.Client;
import Datas.SoundDatas;
import Model.Account;
import Model.Asset;
import Model.AssetContainer;
import Model.AuctionElement;
import Presenter.*;
import com.gilecode.yagson.YaGson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class ShopController implements Initializable, ScreenManager, AccountManageable, DialogThrowable, ImageComparable, TextFieldValidator {
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
    public Tab auctionTab;
    public ScrollPane auctionScrollPane;
    public VBox auctionVBox;
    public ImageView saleButton;
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

    public void setSaleButtonOnMousePressed() {
        if (selectedElement != null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
            saleButton.setImage(new Image("file:images/button_sale_pressed.png"));
        } else {
            saleButton.setImage(new Image("file:images/button_sale_unavailable.png"));
        }
    }

    public void setSaleButtonOnMouseMoved() {
        if (selectedElement == null) {
            saleButton.setImage(new Image("file:images/button_sale_unavailable.png"));
        } else {
            SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
            saleButton.setImage(new Image("file:images/button_sale_hover.png"));
        }
    }

    public void setSaleButtonOnMouseExited() {
        if (selectedElement == null) {
            saleButton.setImage(new Image("file:images/button_sale_unavailable.png"));
        } else {
            saleButton.setImage(new Image("file:images/button_sale_available.png"));
        }
    }

    public void setSaleButtonOnMouseReleased() {
        if (selectedElement != null) {
            Client.getWriter().println("auctionBuildRequest " + new YaGson().toJson(new AuctionElement(selectedElement), AuctionElement.class));
            tabPane.getSelectionModel().select(auctionTab);
            setSaleButtonOnMouseMoved();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client.setClientShopController(this);
        Client.getWriter().println("getShopCollection");
        Client.waitForListener();
        Client.getWriter().println("getAuctionCollection");
        shopContainers = new YaGson().fromJson(Client.getMessageListener().getDataFromServer(), new TypeToken<java.util.Collection<AssetContainer>>(){}.getType());
        shopAssetCollection = AssetContainer.getAssetArrayList(shopContainers);
        buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy.png")));
        sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell.png")));
        auctionTab.setGraphic(new ImageView(new Image("file:images/tab_auction.png")));
        tabPane.setOnMouseClicked(event -> {
            if (isInRightTabCoordination(event.getX(), event.getY(), 10, 6, 160, 46)) {
                SoundDatas.playSFX(SoundDatas.TAB_PRESS);
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy_pressed.png")));
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell.png")));
                auctionTab.setGraphic(new ImageView(new Image("file:images/tab_auction.png")));
            } else if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 160 + 15, 6, 160, 40)) {
                SoundDatas.playSFX(SoundDatas.TAB_PRESS);
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell_pressed.png")));
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy.png")));
                auctionTab.setGraphic(new ImageView(new Image("file:images/tab_auction.png")));
            } else if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 340 + 15, 6, 160, 40)) {
                SoundDatas.playSFX(SoundDatas.TAB_PRESS);
                auctionTab.setGraphic(new ImageView(new Image("file:images/tab_auction_pressed.png")));
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell.png")));
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy.png")));
            }
        });
        tabPane.setOnMouseMoved(event -> {
            if (isInRightTabCoordination(event.getX(), event.getY(), 10, 6, 160, 46)) {
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy_hover.png")));
            } else if (!buyTab.isSelected()) {
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy.png")));
            } else {
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy_pressed.png")));
            }
            if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 160 + 15, 6, 160, 40)) {
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell_hover.png")));
            } else if (!sellTab.isSelected()) {
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell.png")));
            } else {
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell_pressed.png")));
            }
            if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 340 + 15, 6, 160, 40)) {
                auctionTab.setGraphic(new ImageView(new Image("file:images/tab_auction_hover.png")));
            } else if (!auctionTab.isSelected()) {
                auctionTab.setGraphic(new ImageView(new Image("file:images/tab_auction.png")));
            } else {
                auctionTab.setGraphic(new ImageView(new Image("file:images/tab_auction_pressed.png")));
            }
        });
        buyTab.setOnSelectionChanged(event -> setRightPanelToDefault());
        sellTab.setOnSelectionChanged(event -> setRightPanelToDefault());
        assetPrice.setVisible(false);
        updateShopCollectionAndAccountCollection(getCurrentAccount(), shopContainers);
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
                saleButton.setImage(new Image("file:images/button_sale_available.png"));
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
                imageView1.setFitWidth(50);
                imageView1.setFitHeight(30);
                imageView1.setLayoutX(102);
                imageView1.setLayoutY(285);
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
                pane.getChildren().addAll(imageView, imageView1, label);
                label.setLayoutX(120);
                label.setLayoutY(285);
                buyFlowPane.getChildren().add(pane);
            }
        }
    }

    public void createAuctionPane(AuctionElement auctionElement) {
        Pane pane = new Pane();pane.setPrefHeight(248);pane.setPrefWidth(780);
        ImageView assetImageView = new ImageView(new Image(auctionElement.getAuctionAsset().getAssetImageAddress()));
        assetImageView.setLayoutX(12);assetImageView.setPickOnBounds(true);
        assetImageView.setLayoutY(6);assetImageView.setPreserveRatio(true);
        assetImageView.setFitWidth(202);
        assetImageView.setFitHeight(234);
        Label l1 = new Label("Owner");
        l1.setTextFill(Color.WHITE);
        l1.setLayoutX(257);l1.setLayoutY(39);
        l1.setStyle("-fx-font-family: 'Cambria Math'; -fx-font-size: 17;");
        Label l2 = new Label("Last Offer");
        l2.setTextFill(Color.WHITE);
        l2.setLayoutX(249);l2.setLayoutY(131);
        l2.setStyle("-fx-font-family: 'Cambria Math'; -fx-font-size: 17;");
        Label l3 = new Label("Last Price");
        l3.setTextFill(Color.WHITE);
        l3.setLayoutX(249);l3.setLayoutY(178);
        l3.setStyle("-fx-font-family: 'Cambria Math'; -fx-font-size: 17;");
        Label l4 = new Label("Base Price");
        l4.setTextFill(Color.WHITE);
        l4.setLayoutX(247);l4.setLayoutY(84);
        l4.setStyle("-fx-font-family: 'Cambria Math'; -fx-font-size: 17;");
        Label ownerNameLabel = new Label(auctionElement.getAuctionAsset().getOwner().getName());
        ownerNameLabel.setLayoutX(370);
        ownerNameLabel.setLayoutY(40);
        ownerNameLabel.setStyle("-fx-font-family: 'Cambria Math'; -fx-font-size: 17;");
        Label basePriceLabel = new Label(String.valueOf(auctionElement.getAuctionAsset().getPrice() / 2));
        basePriceLabel.setLayoutX(355);
        basePriceLabel.setLayoutY(85);
        basePriceLabel.setStyle("-fx-font-family: 'Cambria Math'; -fx-font-size: 17;");
        Label customerNameLabel = new Label(auctionElement.getCustomer() != null ? auctionElement.getCustomer().getName() : "");
        customerNameLabel.setLayoutX(371);
        customerNameLabel.setLayoutY(133);
        customerNameLabel.setStyle("-fx-font-family: 'Cambria Math'; -fx-font-size: 17;");
        Label offeredPriceLable = new Label(auctionElement.getRecommendedPrice() != null ? String.valueOf(auctionElement.getRecommendedPrice()) : "");
        offeredPriceLable.setLayoutX(357);
        offeredPriceLable.setLayoutY(178);
        offeredPriceLable.setStyle("-fx-font-family: 'Cambria Math'; -fx-font-size: 17;");
        JFXProgressBar jfxBar = new JFXProgressBar();
        jfxBar.setStyle("-fx-background-color: #ffffff;");
        jfxBar.setLayoutX(506);jfxBar.setLayoutY(97);
        jfxBar.setPrefWidth(226);jfxBar.setPrefWidth(226);
        JFXProgressBar jfxBarInf = new JFXProgressBar();
        jfxBarInf.setStyle("-fx-background-color: #ffffff;");
        jfxBarInf.setLayoutX(506);jfxBarInf.setLayoutY(97);
        jfxBarInf.setPrefWidth(226);jfxBarInf.setPrefWidth(226);
        jfxBarInf.setProgress(-1.0f);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(jfxBarInf.progressProperty(), 0), new KeyValue(jfxBar.progressProperty(), 0)),
                new KeyFrame(Duration.millis(AuctionElement.getDefaultTimeWaiting()), new KeyValue(jfxBarInf.progressProperty(), 1), new KeyValue(jfxBar.progressProperty(), 1)));
        timeline.play();
        JFXTextField offerPriceTextField = new JFXTextField();
        offerPriceTextField.setFocusColor(Color.WHITE);
        offerPriceTextField.setUnFocusColor(Color.BLACK);
        offerPriceTextField.setLabelFloat(true);
        offerPriceTextField.setPromptText("Offer Price");
        setTextFieldRequiredFieldValidator(offerPriceTextField, "\\D*", "Input Integer Please!!!");
        offerPriceTextField.setLayoutX(507);
        offerPriceTextField.setLayoutY(39);
        JFXButton offerButton = new JFXButton("Offer");offerButton.setTextAlignment(TextAlignment.CENTER);offerButton.setTextFill(Color.WHITE);
        offerButton.setButtonType(JFXButton.ButtonType.RAISED);offerButton.setMnemonicParsing(false);
        offerButton.setLayoutX(684);
        offerButton.setLayoutY(43);
        offerButton.setRipplerFill(Color.WHITE);
        offerButton.setStyle("-fx-border-width: 4;");
        offerButton.setOnAction(event -> {
            if (offerPriceTextField.getText().equals("")) {
                showOneButtonErrorDialog("Offer Error", "Your Field Is Empty!!!");
            } else if ((Integer.parseInt(offerPriceTextField.getText()) < (!offeredPriceLable.getText().equals("") ? Integer.parseInt(offeredPriceLable.getText()) : 0)) || (Integer.parseInt(offerPriceTextField.getText()) < Integer.parseInt(basePriceLabel.getText())/2)) {
                showOneButtonErrorDialog("Offer Error", "You Should Bet More Money!!!");
            } else {
                Client.getWriter().println("auctionPriceRequest " + new YaGson().toJson(new AuctionElement(auctionElement.getAuctionAsset(), getCurrentAccount(), Integer.parseInt(offerPriceTextField.getText())), AuctionElement.class));
            }
        });
        if (auctionElement.getAuctionAsset().getOwner().getName().equals(getCurrentAccount().getName())) {
            offerPriceTextField.setDisable(true);
            offerButton.setDisable(true);
        }
        pane.getChildren().addAll(l1,l2,l3,l4,assetImageView, ownerNameLabel, customerNameLabel, basePriceLabel, offeredPriceLable, offerButton, offerPriceTextField,jfxBar,jfxBarInf);
        auctionVBox.getChildren().add(pane);
    }

    public boolean isInRightTabCoordination(double x, double y, double tabX, double tabY, int tabWidth, int tabHeight) {
        return (x > tabX && x < tabX + tabWidth && y > tabY && y < tabY + tabHeight);
    }

    public void updateShopCollectionAndAccountCollection(Account newAccount, ArrayList<AssetContainer> newShopContainers) {
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

    public void updateAuctionView(ArrayList<AuctionElement> auctionElements) {
        auctionVBox.getChildren().clear();
        if (auctionElements != null) {
            for (AuctionElement auctionElement : auctionElements) {
                createAuctionPane(auctionElement);
            }
        }
    }

    public void updateAuctionBuyOrSell(Account newAccount) {
        CurrentAccount.setCurrentAccount(newAccount);
        fillFlowPaneAccountCollection(CurrentAccount.getCurrentAccount().getCollection().getAssets());
        accountBudget.setText(CurrentAccount.getCurrentAccount().getBudget() + " DR");
    }

    public void setRightPanelToDefault() {
        actionButton.setImage(new Image("file:images/default_action_button.png"));
        cardImage.setImage(new Image("file:images/card_glow_line.png"));
        saleButton.setImage(new Image("file:images/button_sale_unavailable.png"));
        assetPrice.setVisible(false);
        selectedElement = null;
    }

    public ArrayList<Asset> getShopAssetCollection() {
        return shopAssetCollection;
    }

}