package Controller;

import Datas.SoundDatas;
import Exceptions.*;
import Model.*;
import Presenter.*;
import View.Main;
import com.jfoenix.controls.*;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CollectionController implements Initializable, ScreenManager, AccountManageable, DialogThrowable, ImageComparable {
    public ImageView back;
    public AnchorPane anchorPane;
    public JFXTabPane tabPane;
    public ImageView selectedCardImageInEditDeckTab;
    public ImageView selectedCardImageInCollectionTab;
    public Tab collectionTab;
    public FlowPane collectionFlowPane;
    public JFXTextField collectionSearchField;
    public FlowPane decksFlowPane;
    public JFXTextField decksSearchField;
    public ImageView exportDeckButton;
    public ImageView deleteDeckButton;
    public ImageView editDeckButton;
    public ImageView selectedDeckImage;
    public Label selectedDeckName;
    public ImageView createButton;
    public ImageView importButton;
    public Tab editDeckTab;
    public ImageView cardActionButton;
    public JFXTextField editDeckSearchField;
    public JFXTabPane editDeckTabPane;
    public FlowPane userCollectionFlowPane;
    public FlowPane deckCollectionFlowPane;
    public JFXTextField deckRenameField;
    public JFXToggleButton setMainDeckButton;
    public Tab customCardTab;
    public Tab decksTab;
    public Label selectedDeckNameInEditTab;
    public Tab userCollectionTab;
    public Tab deckCollectionTab;
    public ImageView deckRenameButton;
    public ImageView backgroundImage;
    public JFXTextArea nameText;
    public JFXTextArea typeText;
    public JFXTextArea APText;
    public JFXTextArea targetText;
    public JFXTextArea buffsText;
    public JFXTextArea HPText;
    public JFXTextArea attackTypeText;
    public JFXTextArea specialPowerText;
    public JFXTextArea rangeText;
    public JFXTextArea specialPowerActivationText;
    public JFXTextArea specialPowerCoolDownText;
    public JFXTextArea priceText;
    public ImageView createCardButton;
    public JFXTextArea specialPowerCooldown;
    public JFXRadioButton cells;
    public JFXRadioButton player;
    public JFXRadioButton enemy;
    public JFXRadioButton spell;
    public JFXRadioButton minion;
    public JFXRadioButton hero;
    public JFXRadioButton melee;
    public JFXRadioButton ranged;
    public JFXRadioButton hybrid;
    private Asset selectedAssetElement = null;
    private Deck selectedDeckElement = null;

    public void setBackButtonOnMouseEntered() {
        back.setImage(new Image("file:images/hover_back_button_corner.png"));
    }
    public void setBackButtonOnMousePressed() {
    SoundDatas.playSFX(SoundDatas.PAGE_CHANGING);
    back.setImage(new Image("file:images/pressed_back_button_corner.png")); }
    public void setBackButtonOnMouseExited() {
        back.setImage(new Image("file:images/button_back_corner.png"));
    }
    public void setBackButtonOnMouseReleased() throws IOException { loadPageOnStackPane(back.getParent(), "FXML/MainMenu.fxml", "ltr"); }

    public void setImportButtonOnMouseOver() {
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
        importButton.setImage(new Image("file:images/hover_import_button.png"));
    }
    public void setImportButtonOnMousePressed() {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        importButton.setImage(new Image("file:images/pressed_import_button.png"));
    }
    public void setImportButtonOnMouseReleased() {
        importDeck();
    }
    public void setImportButtonOnMouseExited() {
        importButton.setImage(new Image("file:images/import_button.png"));
    }


    public void setCreateButtonOnMouseOver() {
    SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
    createButton.setImage(new Image("file:images/hover_Create_button.png")); }
    public void setCreateButtonOnMousePressed() {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        createButton.setImage(new Image("file:images/pressed_Create_button.png"));
    }
    public void setCreateButtonOnMouseReleased() {
        createAccountDialog();
    }
    public void setCreateButtonOnMouseExited() { createButton.setImage(new Image("file:images/Create_button.png")); }


    public void setExportButtonOnMouseOver() {
        if (selectedDeckElement != null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
            exportDeckButton.setImage(new Image("file:images/hover_export_button.png"));
        } else {
            exportDeckButton.setImage(new Image("file:images/unavailable_export_button.png"));
        }
    }
    public void setExportButtonOnMousePressed() {
        if (selectedDeckElement != null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
            exportDeckButton.setImage(new Image("file:images/pressed_export_button.png"));
            exportDeck(selectedDeckElement);
        } else {
            exportDeckButton.setImage(new Image("file:images/unavailable_export_button.png"));
        }
    }
    public void setExportButtonOnMouseExited() {
        if (selectedDeckElement != null) {
            exportDeckButton.setImage(new Image("file:images/available_export_button.png"));
        } else {
            exportDeckButton.setImage(new Image("file:images/unavailable_export_button.png"));
        }
    }

    public void setEditButtonOnMouseOver() {
        if (selectedDeckElement != null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
            editDeckButton.setImage(new Image("file:images/hover_edit_button.png"));
        } else {
            editDeckButton.setImage(new Image("file:images/unavailable_edit_button.png"));
        }
    }
    public void setEditButtonOnMousePressed() {
        if (selectedDeckElement != null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
            editDeckButton.setImage(new Image("file:images/pressed_edit_button.png"));
            loadEditDeckTab();
            updateEachDeckCollectionTabs();
            editDeckTab.setDisable(false);
            tabPane.getSelectionModel().select(editDeckTab);
        } else {
            editDeckButton.setImage(new Image("file:images/unavailable_edit_button.png"));
        }
    }
    public void setEditButtonOnMouseExited() {
        if (selectedDeckElement != null) {
            editDeckButton.setImage(new Image("file:images/available_edit_button.png"));
        } else {
            editDeckButton.setImage(new Image("file:images/unavailable_edit_button.png"));
        }
    }


    public void setDeleteDeckButtonOnMouseOver() {
        if (selectedDeckElement != null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
            deleteDeckButton.setImage(new Image("file:images/hover_delete_button.png"));
        } else {
            deleteDeckButton.setImage(new Image("file:images/unavailable_delete_button.png"));
        }
    }
    public void setDeleteDeckButtonOnMousePressed() {
        if (selectedDeckElement != null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
            deleteDeckButton.setImage(new Image("file:images/pressed_delete_button.png"));
            confirmationDialog("Confirmation","Are You Sure ?").setOnAction(event -> {
                deleteDeck(selectedDeckElement);
                setRightPanelToDefaultInDecksTabPane();
                showOneButtonInformationDialog("Delete Message","Your Deck Removed Successfully!!!",false);
                fillFlowPaneDeckCollection(decksFlowPane,getCurrentAccount().getDecks());
            });
        } else {
            deleteDeckButton.setImage(new Image("file:images/unavailable_delete_button.png"));
        }
    }
    public void setDeleteDeckButtonOnMouseExited() {
        if (selectedDeckElement != null) {
            deleteDeckButton.setImage(new Image("file:images/available_delete_button.png"));
        } else {
            deleteDeckButton.setImage(new Image("file:images/unavailable_delete_button.png"));
        }
    }

    public void setRenameDeckButtonOnMouseOver() {
        if (selectedDeckElement != null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
            deckRenameButton.setImage(new Image("file:images/hover_change_button.png"));
        } else {
            deckRenameButton.setImage(new Image("file:images/default_action_button.png"));
        }
    }
    public void setRenameDeckButtonOnMousePressed() {
        if (selectedDeckElement != null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
            deckRenameButton.setImage(new Image("file:images/pressed_change_button.png"));
            if(!deckRenameField.getText().equals("")) {
                try {
                    renameDeck(selectedDeckElement, deckRenameField.getText());
                } catch (RepeatedDeckException e) {
                    showOneButtonErrorDialog("Rename Error","This Name Had Been Token!!!");
                    return;
                }
                updateEachDeckCollectionTabs();
                fillFlowPaneDeckCollection(decksFlowPane,getCurrentAccount().getDecks());
            }
        } else {
            deckRenameButton.setImage(new Image("file:images/default_action_button.png"));
        }
    }
    public void setRenameDeckButtonOnMouseExited() {
        if (selectedDeckElement != null) {
            deckRenameButton.setImage(new Image("file:images/change_button.png"));
        } else {
            deckRenameButton.setImage(new Image("file:images/default_action_button.png"));
        }
    }

    public void setCardActionButtonOnMousePressed() {
        if (selectedAssetElement != null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
            if (userCollectionTab.isSelected()) {
                cardActionButton.setImage(new Image("file:images/pressed_add_button.png"));
            } else if (deckCollectionTab.isSelected()) {
                cardActionButton.setImage(new Image("file:images/pressed_remove_button.png"));
            }
        }else {
            cardActionButton.setImage(new Image("file:images/unhover_button.png"));
        }
    }
    public void setCardActionButtonOnMouseMoved() {
        if (selectedAssetElement == null) {
            cardActionButton.setImage(new Image("file:images/unhover_button.png"));
        } else {
            SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
            if (userCollectionTab.isSelected()) {
                cardActionButton.setImage(new Image("file:images/hover_add_button.png"));
            } else if (deckCollectionTab.isSelected()) {
                cardActionButton.setImage(new Image("file:images/hover_remove_button.png"));
            }
        }
    }
    public void setCardActionButtonOnMouseExited() {
        if (selectedAssetElement == null) {
            cardActionButton.setImage(new Image("file:images/unhover_button.png"));
        } else {
            if (userCollectionTab.isSelected()) {
                cardActionButton.setImage(new Image("file:images/available_add_button.png"));
            } else if (deckCollectionTab.isSelected()) {
                cardActionButton.setImage(new Image("file:images/available_remove_button.png"));
            }
        }
    }
    public void setCardActionButtonOnMouseReleased() {
        if (selectedAssetElement != null ) {
            if (userCollectionTab.isSelected()) {
                    try {
                        addToDeck(selectedAssetElement,selectedDeckElement);
                        updateEachDeckCollectionTabs();
                    } catch (IllegalHeroAddToDeckException i) {
                        showOneButtonErrorDialog("Adding Error", "You Can't Add More Than One Hero To Deck!!!");
                        return;
                    } catch (IllegalCardAddToDeckException m) {
                        showOneButtonErrorDialog("Adding Error", "Your Deck Is Full , You Can't Add Anything!!!");
                        return;
                    }
                    showOneButtonInformationDialog("Adding Message","Card/Item Added To Deck Successfully!!!",false);
            } else if (deckCollectionTab.isSelected()) {
                confirmationDialog("Confirmation", "Are you sure to remove " + selectedAssetElement.getName() + " from this deck").setOnMousePressed(event -> {
                    removeAssetFromDeck(selectedAssetElement,selectedDeckElement);
                    updateEachDeckCollectionTabs();
                    setRightPanelToDefaultInEditTabPane();
                });
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedDeckElement = null;
        selectedAssetElement = null;
        collectionTab.setGraphic(new ImageView(new Image("file:images/tab_collection_button.png")));
        decksTab.setGraphic(new ImageView(new Image("file:images/tab_deck_button.png")));
        editDeckTab.setGraphic(new ImageView(new Image("file:images/tab_selected_button_unavailable.png")));
        customCardTab.setGraphic(new ImageView(new Image("file:images/tab_customCard_button.png")));
        tabPane.setOnMouseClicked(event -> {
            if (isInRightTabCoordination(event.getX(), event.getY(), 10, 6, 160, 40)) {
                SoundDatas.playSFX(SoundDatas.TAB_PRESS);
                collectionTab.setGraphic(new ImageView(new Image("file:images/tab_collection_pressed_button.png")));
                decksTab.setGraphic(new ImageView(new Image("file:images/tab_deck_button.png")));
                if(!editDeckTab.isDisable()) {
                    editDeckTab.setGraphic(new ImageView(new Image("file:images/tab_selected_button.png")));
                }
                customCardTab.setGraphic(new ImageView(new Image("file:images/tab_customCard_button.png")));
            } else if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 165 + 15, 6, 160, 40)) {
                SoundDatas.playSFX(SoundDatas.TAB_PRESS);
                collectionTab.setGraphic(new ImageView(new Image("file:images/tab_collection_button.png")));
                decksTab.setGraphic(new ImageView(new Image("file:images/tab_deck_pressed_button.png")));
                if(!editDeckTab.isDisable()) {
                    editDeckTab.setGraphic(new ImageView(new Image("file:images/tab_selected_button.png")));
                }
                customCardTab.setGraphic(new ImageView(new Image("file:images/tab_customCard_button.png")));
            } else if (!editDeckTab.isDisable() && isInRightTabCoordination(event.getX(), event.getY(), 10 + 340 + 15, 6, 160, 40)) {
                SoundDatas.playSFX(SoundDatas.TAB_PRESS);
                collectionTab.setGraphic(new ImageView(new Image("file:images/tab_collection_button.png")));
                decksTab.setGraphic(new ImageView(new Image("file:images/tab_deck_button.png")));
                editDeckTab.setGraphic(new ImageView(new Image("file:images/tab_selected_pressed_button.png")));
                customCardTab.setGraphic(new ImageView(new Image("file:images/tab_customCard_button.png")));
            } else if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 520 + 15, 6, 160, 40)) {
                SoundDatas.playSFX(SoundDatas.TAB_PRESS);
                collectionTab.setGraphic(new ImageView(new Image("file:images/tab_collection_button.png")));
                decksTab.setGraphic(new ImageView(new Image("file:images/tab_deck_button.png")));
                if(!editDeckTab.isDisable()) {
                    editDeckTab.setGraphic(new ImageView(new Image("file:images/tab_selected_button.png")));
                }
                customCardTab.setGraphic(new ImageView(new Image("file:images/tab_customCard_pressed_button.png")));
            }
        });
        tabPane.setOnMouseMoved(event -> {
            if (isInRightTabCoordination(event.getX(), event.getY(), 10, 6, 160, 46)) {
                collectionTab.setGraphic(new ImageView(new Image("file:images/tab_collection_hover_button.png")));
            } else if (!collectionTab.isSelected()) {
                collectionTab.setGraphic(new ImageView(new Image("file:images/tab_collection_button.png")));
            } else {
                collectionTab.setGraphic(new ImageView(new Image("file:images/tab_collection_pressed_button.png")));
            }
            if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 165 + 15, 6, 160, 40)) {
                decksTab.setGraphic(new ImageView(new Image("file:images/tab_deck_hover_button.png")));
            } else if (!decksTab.isSelected()) {
                decksTab.setGraphic(new ImageView(new Image("file:images/tab_deck_button.png")));
            } else {
                decksTab.setGraphic(new ImageView(new Image("file:images/tab_deck_pressed_button.png")));
            }
            if(!editDeckTab.isDisable()) {
                if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 340 + 15, 6, 160, 40)) {
                    editDeckTab.setGraphic(new ImageView(new Image("file:images/tab_selected_hover_button.png")));
                } else if (!editDeckTab.isSelected()) {
                    editDeckTab.setGraphic(new ImageView(new Image("file:images/tab_selected_button.png")));
                } else {
                    editDeckTab.setGraphic(new ImageView(new Image("file:images/tab_selected_pressed_button.png")));
                }
            }
            if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 520 + 15, 6, 160, 40)) {
                customCardTab.setGraphic(new ImageView(new Image("file:images/tab_customCard_hover_button.png")));
            } else if (!customCardTab.isSelected()) {
                customCardTab.setGraphic(new ImageView(new Image("file:images/tab_customCard_button.png")));
            } else {
                customCardTab.setGraphic(new ImageView(new Image("file:images/tab_customCard_pressed_button.png")));
            }
        });
        collectionTab.setOnSelectionChanged(event -> setRightPanelToDefaultInDecksTabPane());
        decksTab.setOnSelectionChanged(event -> setRightPanelToDefaultInDecksTabPane());
        editDeckTab.setOnSelectionChanged(event -> setRightPanelToDefaultInDecksTabPane());
        customCardTab.setOnSelectionChanged(event -> setRightPanelToDefaultInDecksTabPane());

        fillFlowPaneAssetCollection(collectionFlowPane, getCurrentAccount().getCollection().getAssets());
        fillFlowPaneDeckCollection(decksFlowPane,getCurrentAccount().getDecks());
        collectionSearchField.textProperty().addListener((observable, oldValue, newValue) -> fillFlowPaneAssetCollection(collectionFlowPane, Asset.searchAndGetAssetCollectionFromCollection(getCurrentAccount().getCollection().getAssets(), newValue)));
        decksSearchField.textProperty().addListener((observable, oldValue, newValue) -> fillFlowPaneDeckCollection(decksFlowPane, Deck.searchAndGetDecksFromCollection(getCurrentAccount().getDecks(), newValue)));
        editDeckTab.setDisable(true);
    }


    public void fillFlowPaneAssetCollection(FlowPane flowPane, ArrayList<Asset> assets) {
        flowPane.getChildren().clear();
        for (int i = 0; i < assets.size(); i++) {
            ImageView imageView = null;
            try {
                imageView = new ImageView(new File(assets.get(i).getAssetImageAddress().substring(5)).toURI().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            imageView.setFitWidth(250);
            imageView.setFitHeight (320);
            Pane pane = new Pane();
            pane.setOnMouseEntered(event -> pane.setStyle("-fx-background-color: #949494;"));
            pane.setOnMouseExited(event -> pane.setStyle("-fx-background-color: -fx-primary;"));
            pane.setOnMousePressed(event -> pane.setStyle("-fx-background-color: #2c2c2c;"));
            pane.setOnMouseReleased(event -> {
                SoundDatas.playSFX(SoundDatas.SELECT_ITEM);
                selectedAssetElement = new Asset().searchAssetFromCardImage(assets, ((ImageView) pane.getChildren().get(0)).getImage());
                if(collectionTab.isSelected()) {
                    selectedCardImageInCollectionTab.setImage(new Image(selectedAssetElement.getAssetImageAddress()));
                }else if(editDeckTab.isSelected()){
                    if(userCollectionTab.isSelected()){
                        cardActionButton.setImage(new Image("file:images/available_add_button.png"));
                    }else if(deckCollectionTab.isSelected()){
                        cardActionButton.setImage(new Image("file:images/available_remove_button.png"));
                    }
                    selectedCardImageInEditDeckTab.setImage(new Image(selectedAssetElement.getAssetImageAddress()));
                }
                pane.setStyle("-fx-background-color: -fx-primary;");
            });
            pane.getChildren().add(imageView);
            flowPane.getChildren().add(pane);
        }
    }

    public void fillFlowPaneDeckCollection(FlowPane flowPane, ArrayList<Deck> decks) {
        flowPane.getChildren().clear();
        for (int i = 0; i < decks.size(); i++) {
            ImageView imageView = new ImageView();
            if (!decks.get(i).isThisMainDeck(getCurrentAccount())) {
                imageView.setImage(new Image("file:images/deck_background.png"));
            } else {
                imageView.setImage(new Image("file:images/mainDeck_background.png"));
            }
            imageView.setFitWidth(250);
            imageView.setFitHeight(320);
            Label label = new Label(decks.get(i).getName());
            label.setStyle("-fx-font-family:Microsoft Tai Le; -fx-font-size: 20.0; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
            VBox pane = new VBox();
            pane.setPrefWidth(270);
            pane.setPrefHeight(375);
            pane.setAlignment(Pos.CENTER);
            pane.setSpacing(5);
            pane.setOnMouseEntered(event -> pane.setStyle("-fx-background-color: #949494;"));
            pane.setOnMouseExited(event -> pane.setStyle("-fx-background-color: -fx-primary;"));
            pane.setOnMousePressed(event -> pane.setStyle("-fx-background-color: #2c2c2c;"));
            pane.setOnMouseReleased(event -> {
                SoundDatas.playSFX(SoundDatas.SELECT_ITEM);
                selectedDeckElement = Deck.findDeck(decks, getLabelOfEachCell(pane).getText());
                updateRightPanelInDecksTab();
                pane.setStyle("-fx-background-color: -fx-primary;");
            });
            pane.getChildren().addAll(imageView,label);
            flowPane.getChildren().add(pane);
        }
    }

    public boolean isInRightTabCoordination(double x, double y, double tabX, double tabY, int tabWidth, int tabHeight) {
        return (x > tabX && x < tabX + tabWidth && y > tabY && y < tabY + tabHeight);
    }

    public void updateRightPanelInDecksTab() {
        if (!selectedDeckElement.isThisMainDeck(getCurrentAccount())) {
            selectedDeckImage.setImage(new Image("file:images/deck_background.png"));
        } else {
            selectedDeckImage.setImage(new Image("file:images/mainDeck_background.png"));
        }
        selectedDeckName.setText(selectedDeckElement.getName());
        exportDeckButton.setImage(new Image("file:images/available_export_button.png"));
        deleteDeckButton.setImage(new Image("file:images/available_delete_button.png"));
        editDeckButton.setImage(new Image("file:images/available_edit_button.png"));
    }

    public void updateEachDeckCollectionTabs() {
        selectedDeckNameInEditTab.setText(selectedDeckElement.getName());
        deckRenameButton.setImage(new Image("file:images/change_button.png"));
        if (selectedDeckElement.isThisMainDeck(getCurrentAccount())){
            setMainDeckButton.setSelected(true);
        }else {
            setMainDeckButton.setSelected(false);
        }
        fillFlowPaneAssetCollection(deckCollectionFlowPane,selectedDeckElement.getAllAssets());
        fillFlowPaneAssetCollection(userCollectionFlowPane,getCurrentAccount().getCollection().getAssets());
    }

    public void setRightPanelToDefaultInDecksTabPane() {
        selectedDeckImage.setImage(new Image("file:images/card_shadow_map.png"));
        exportDeckButton.setImage(new Image("file:images/unavailable_export_button.png"));
        deleteDeckButton.setImage(new Image("file:images/unavailable_delete_button.png"));
        editDeckButton.setImage(new Image("file:images/unavailable_edit_button.png"));
        selectedCardImageInCollectionTab.setImage(new Image("file:images/card_glow_line.png"));
        selectedDeckName.setText("");
    }

    public void disableEditTabPane(){
        deckRenameButton.setImage(new Image("file:images/default_action_button.png"));
        editDeckTab.setGraphic(new ImageView(new Image("file:images/tab_selected_button_unavailable.png")));
        editDeckTab.setDisable(true);
        selectedDeckElement = null;
    }

    public void setRightPanelToDefaultInEditTabPane() {
        cardActionButton.setImage(new Image("file:images/unhover_button.png"));
        selectedCardImageInEditDeckTab.setImage(new Image("file:images/card_glow_line.png"));
        selectedAssetElement = null;
    }

    public void loadEditDeckTab(){
        editDeckTab.setGraphic(new ImageView(new Image("file:images/tab_selected_button.png")));
        userCollectionTab.setGraphic(new ImageView(new Image("file:images/tab_yourCollection.png")));
        deckCollectionTab.setGraphic(new ImageView(new Image("file:images/tab_deckCollection.png")));
        editDeckTabPane.setOnMouseClicked(event -> {
            if (isInRightTabCoordination(event.getX(), event.getY(), 10, 6, 160, 46)) {
                userCollectionTab.setGraphic(new ImageView(new Image("file:images/tab_yourCollection_pressed.png")));
                deckCollectionTab.setGraphic(new ImageView(new Image("file:images/tab_deckCollection.png")));
            } else if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 160 + 15, 6, 160, 40)) {
                deckCollectionTab.setGraphic(new ImageView(new Image("file:images/tab_deckCollection_pressed.png")));
                userCollectionTab.setGraphic(new ImageView(new Image("file:images/tab_yourCollection.png")));
            }
        });
        editDeckTabPane.setOnMouseMoved(event -> {
            if (isInRightTabCoordination(event.getX(), event.getY(), 10, 6, 160, 46)) {
                userCollectionTab.setGraphic(new ImageView(new Image("file:images/tab_yourCollection_hover.png")));
            } else if (!userCollectionTab.isSelected()) {
                userCollectionTab.setGraphic(new ImageView(new Image("file:images/tab_yourCollection.png")));
            } else {
                userCollectionTab.setGraphic(new ImageView(new Image("file:images/tab_yourCollection_pressed.png")));
            }
            if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 160 + 15, 6, 160, 40)) {
                deckCollectionTab.setGraphic(new ImageView(new Image("file:images/tab_deckCollection_hover.png")));
            } else if (!deckCollectionTab.isSelected()) {
                deckCollectionTab.setGraphic(new ImageView(new Image("file:images/tab_deckCollection.png")));
            } else {
                deckCollectionTab.setGraphic(new ImageView(new Image("file:images/tab_deckCollection_pressed.png")));
            }
        });
        userCollectionTab.setOnSelectionChanged(event -> setRightPanelToDefaultInEditTabPane());
        deckCollectionTab.setOnSelectionChanged(event -> setRightPanelToDefaultInEditTabPane());
        editDeckSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(userCollectionTab.isSelected()) {
                fillFlowPaneAssetCollection(userCollectionFlowPane, Asset.searchAndGetAssetCollectionFromCollection(getCurrentAccount().getCollection().getAssets(), newValue));
            }else{
                fillFlowPaneAssetCollection(deckCollectionFlowPane, Asset.searchAndGetAssetCollectionFromCollection(selectedDeckElement.getAllAssets(), newValue));
            }
        });
        updateEachDeckCollectionTabs();
        setRightPanelToDefaultInEditTabPane();
        if (selectedDeckElement.isThisMainDeck(getCurrentAccount())){
            setMainDeckButton.setSelected(true);
        }else {
            setMainDeckButton.setSelected(false);
        }
        selectingMainDeckEventHandler();

        editDeckTab.setDisable(true);
        editDeckTab.setOnClosed(event -> {
            disableEditTabPane();
            setRightPanelToDefaultInEditTabPane();
        });

    }

    public Label getLabelOfEachCell(Pane pane) {
        for (Node child : pane.getChildren()) {
            if (child instanceof Label) {
                return (Label) child;
            }
        }
        throw new DeckNotFoundException("");
    }

    public void createAccountDialog(){
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setPrefHeight(60);
        Text header = new Text("Deck Name");
        header.setStyle("-fx-text-fill: #ff0000;  -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold ;");
        dialogLayout.setHeading(header);
        dialogLayout.setStyle("-fx-background-color: #acf5ff; -fx-text-fill: #ffffff");
        JFXDialog dialog = new JFXDialog(Main.getStackPane(), dialogLayout, JFXDialog.DialogTransition.CENTER, true);
        JFXButton yesButton = new JFXButton("create");
        yesButton.setButtonType(JFXButton.ButtonType.RAISED);
        JFXTextField jfxTextField = new JFXTextField();
        jfxTextField.setPromptText("Name");
        jfxTextField.setStyle("-jfx-focus-color : #2b23a9;");
        yesButton.setStyle("-fx-background-color: #37b400; -fx-text-fill: #ffffff; -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold ;");
        yesButton.setOnMouseReleased(event -> {
            if (!jfxTextField.getText().equals("")) {
                SoundDatas.playSFX(SoundDatas.DIALOG_YES_BUTTON);
                dialog.close();
            }else {
                SoundDatas.playSFX(SoundDatas.DIALOG_NO_BUTTON);
            }
        });
        JFXButton noButton = new JFXButton("cancle");
        noButton.setButtonType(JFXButton.ButtonType.RAISED);
        noButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold ;");
        noButton.setOnAction(event -> {
            SoundDatas.playSFX(SoundDatas.DIALOG_NO_BUTTON);
            dialog.close();
        });
        dialog.setOnDialogClosed(event -> Main.getStackPane().getChildren().remove(dialog));
        dialogLayout.setActions(jfxTextField,yesButton,noButton);
        dialog.show();

        yesButton.setOnAction(event -> {
            if (!jfxTextField.getText().equals("")){
                try {
                    createDeck(jfxTextField.getText());
                }catch (RepeatedDeckException e){
                    showOneButtonErrorDialog("Create Deck Error","You Had Made This Deck Before!!!");
                    return;
                }
                showOneButtonInformationDialog("Message","Your Deck Created Successfully!!!",false);
                fillFlowPaneDeckCollection(decksFlowPane,getCurrentAccount().getDecks());
            }
        });
    }

    public void selectingMainDeckEventHandler(){
        setMainDeckButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue && !oldValue){
                try {
                    chooseMainDeck(selectedDeckElement);
                } catch (InvalidSelectMainDeckException e) {
                    showOneButtonErrorDialog("Select Main Deck Error"," Your Deck Is InValid!!!");
                    setMainDeckButton.setSelected(false);
                    return;
                }
                showOneButtonInformationDialog("Message","Your Deck was Chosen Successfully!!!",false);
                fillFlowPaneDeckCollection(decksFlowPane,getCurrentAccount().getDecks());
            }else
            if(oldValue && !newValue) {
                removeMainDeck();
                showOneButtonInformationDialog("Warning","You Can't Play Until Choosing Main Deck!!!",true);
                fillFlowPaneDeckCollection(decksFlowPane,getCurrentAccount().getDecks());
            }
        });
    }

    public void importDeck(){
        FileChooser importDeck = new FileChooser();
        importDeck.setInitialDirectory(new File("../"));
        importDeck.setTitle("import deck");
        importDeck.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json Files","*.json"));
        File selectedFile = importDeck.showOpenDialog(anchorPane.getScene().getWindow());
        if (selectedFile!=null){
            try {
                Deck.importDeck(getCurrentAccount(), selectedFile.getPath());
            }catch (RepeatedDeckException e){
                showOneButtonErrorDialog("Import Error","File Name Had Been Token Before!!!");
                return;
            }
            fillFlowPaneDeckCollection(decksFlowPane,getCurrentAccount().getDecks());
        }
    }

    public void exportDeck(Deck deck){
        DirectoryChooser exportDeck = new DirectoryChooser();
        exportDeck.setInitialDirectory(new File("../"));
        exportDeck.setTitle("export deck");
        File selectedFile = exportDeck.showDialog(anchorPane.getScene().getWindow());
        if (selectedFile!=null){
            Deck.exportDeck(deck,selectedFile.getPath()+"//"+deck.getName()+".json");
            showOneButtonInformationDialog("Export Message","The Deck Exported Successfully To "+selectedFile.getPath()+" .",false);
        }
    }

    public void CreateCard() {
        final int DEFAULT_MANA = 3;
        int minionID = 3040;
        int heroID = 2010;
        int spellID = 4020;

        int price = Integer.valueOf(priceText.getText());
        int range = Integer.valueOf(rangeText.getText());
        int AP = Integer.valueOf(APText.getText());
        int HP = Integer.valueOf(HPText.getText());
        int coolDown = Integer.valueOf(specialPowerCoolDownText.getText());

        ToggleGroup typeGroup = new ToggleGroup();
        hero.setToggleGroup(typeGroup);
        minion.setToggleGroup(typeGroup);
        spell.setToggleGroup(typeGroup);

        ToggleGroup targetGroup=new ToggleGroup();
        enemy.setToggleGroup(targetGroup);
        player.setToggleGroup(targetGroup);
        cells.setToggleGroup(targetGroup);

        ToggleGroup attackTypeGroup = new ToggleGroup();
        melee.setToggleGroup(attackTypeGroup);
        ranged.setToggleGroup(attackTypeGroup);
        hybrid.setToggleGroup(attackTypeGroup);

        AttackType attackType = null;
        if (melee.isSelected()) {
            attackType = AttackType.MELEE;
        } else if (ranged.isSelected()) {
            attackType = AttackType.RANGED;
        } else if (hybrid.isSelected()) {
            attackType = AttackType.HYBRID;
        }

        Spell.TargetType targetType = null;
        if (enemy.isSelected()) {
            targetType = Spell.TargetType.ENEMY;
        } else if (player.isSelected()) {
            targetType = Spell.TargetType.PLAYER;
        } else if (cells.isSelected()) {
            targetType = Spell.TargetType.CELLS;
        }
        else {
            targetType = Spell.TargetType.WHOLE_OF_GROUND;
        }


        if (minion.isSelected()) {

            Minion minion = new Minion(nameText.getText(), nameText.getText(), price, minionID, range, AP, HP, DEFAULT_MANA, attackType);
            minionID++;
//            Asset.saveCardsToJsonDatabase(minion); todo add to main cards dataBase
            getCurrentAccount().getCollection().getAssets().add(minion);
        }
        if (hero.isSelected()) {
            Hero hero = new Hero(nameText.getText(), price, heroID, range, AP, HP, DEFAULT_MANA, coolDown, attackType);
            heroID++;
//            Asset.saveCardsToJsonDatabase(hero); todo add to main cards dataBase
            getCurrentAccount().getCollection().getAssets().add(hero);

        }
        if (spell.isSelected()) {
            Spell spell = new Spell(nameText.getText(), nameText.getText(), price, spellID, DEFAULT_MANA, targetType);
            spellID++;
//            Asset.saveCardsToJsonDatabase(spell); todo add to main cards dataBase
            getCurrentAccount().getCollection().getAssets().add(spell);
        }
    }

}
