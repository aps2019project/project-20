package Controller;

import Exceptions.InsufficientMoneyInBuyFromShopException;
import Exceptions.MaximumNumberOfItemsInBuyException;
import Model.Asset;
import Presenter.*;
import com.jfoenix.controls.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private ArrayList<Asset> accountAssetCollection = new ArrayList<>();
    private Asset selectedElement = null;

    public void setBackButtonOnMouseEntered() {
        back.setImage(new Image("file:images/hover_back_button_corner.png"));
    }

    public void setBackButtonOnMousePressed() {
        back.setImage(new Image("file:images/pressed_back_button_corner.png"));
    }

    public void setBackButtonOnMouseExited() {
        back.setImage(new Image("file:images/button_back_corner.png"));
    }

    public void setBackButtonOnMouseReleased() throws IOException {
        loadPageOnStackPane(back.getParent(), "FXML/MainMenu.fxml", "ltr");
    }


    public void setActionButtonOnMousePressed() {
        if (selectedElement != null) {
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
                        try {
                            buy(selectedElement);
                            updateAccountCollectionStatus();
                        } catch (InsufficientMoneyInBuyFromShopException i) {
                            showOneButtonErrorDialog("Buy Error", "You Haven't Enough Budget To Buy!!!");
                        } catch (MaximumNumberOfItemsInBuyException m) {
                            showOneButtonErrorDialog("Buy Error", "You Can't Buy More Than Three Items!!!");
                        }
                    });

            } else if (sellTab.isSelected()) {
                confirmationDialog("Confirmation", "Are you sure to sell " + selectedElement.getName() + " " + selectedElement.getPrice() + " DR ?").setOnMousePressed(event -> {
                    sell(selectedElement);
                    updateAccountCollectionStatus();
                    setRightPanelToDefault();
                });
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        shopAssetCollection = getAllAssets();
        accountAssetCollection = getCurrentAccount().getCollection().getAssets();
        buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy.png")));
        sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell.png")));
        tabPane.setOnMouseClicked(event -> {
            if (ShopController.this.isInRightTabCoordination(event.getX(), event.getY(), 10, 6, 160, 46)) {
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy_pressed.png")));
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell.png")));
            } else if (ShopController.this.isInRightTabCoordination(event.getX(), event.getY(), 10 + 160 + 15, 6, 160, 40)) {
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
        fillFlowPaneCollection(buyFlowPane, shopAssetCollection);
        updateAccountCollectionStatus();
        buySearchField.textProperty().addListener((observable, oldValue, newValue) -> fillFlowPaneCollection(buyFlowPane, Asset.searchAndGetAssetCollectionFromCollection(shopAssetCollection, newValue)));
        sellSearchField.textProperty().addListener((observable, oldValue, newValue) -> fillFlowPaneCollection(sellFlowPane, Asset.searchAndGetAssetCollectionFromCollection(accountAssetCollection, newValue)));


    }


    public void fillFlowPaneCollection(FlowPane flowPane, ArrayList<Asset> assets) {
        flowPane.getChildren().clear();
        for (int i = 0; i < assets.size(); i++) {
            ImageView imageView = new ImageView(new Image(assets.get(i).getCardImageAddress()));
            imageView.setFitWidth(250);
            imageView.setFitHeight(320);
            Pane pane = new Pane();
            pane.setOnMouseEntered(event -> pane.setStyle("-fx-background-color: #949494;"));
            pane.setOnMouseExited(event -> pane.setStyle("-fx-background-color: -fx-primary;"));
            pane.setOnMousePressed(event -> pane.setStyle("-fx-background-color: #2c2c2c;"));
            pane.setOnMouseReleased(event -> {
                //todo rippler
                selectedElement = new Asset().searchAssetFromCardImage(assets,((ImageView) pane.getChildren().get(0)).getImage());
                assetPrice.setText(selectedElement.getPrice() + " DR");
                assetPrice.setVisible(true);
                cardImage.setImage(new Image(selectedElement.getCardImageAddress()));
                actionButton.setImage(new Image("file:images/button_primary_glow.png"));
                pane.setStyle("-fx-background-color: -fx-primary;");
            });
            pane.getChildren().add(imageView);
            flowPane.getChildren().add(pane);
        }
    }

    public boolean isInRightTabCoordination(double x, double y, double tabX, double tabY, int tabWidth, int tabHeight) {
        return (x > tabX && x < tabX + tabWidth && y > tabY && y < tabY + tabHeight);
    }

    public void updateAccountCollectionStatus() {
        accountBudget.setText(getCurrentAccount().getBudget() + " DR");
        fillFlowPaneCollection(sellFlowPane, accountAssetCollection);
    }

    public void setRightPanelToDefault() {
        actionButton.setImage(new Image("file:images/default_action_button.png"));
        cardImage.setImage(new Image("file:images/card_glow_line.png"));
        assetPrice.setVisible(false);
        selectedElement = null;
    }


}
