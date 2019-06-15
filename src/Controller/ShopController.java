package Controller;

import Model.Asset;
import Presenter.ScreenManager;
import View.Main;
import com.jfoenix.controls.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShopController implements Initializable, ScreenManager {
    public ImageView back;
    public AnchorPane anchorPane;
    public Tab buyTab;
    public Tab sellTab;
    public JFXTabPane tabPane;
    public ImageView cardImage;
    public ImageView actionButton;
    public Label accountBudget;
    public Label assetPrice;
    public GridPane shopCollection;
    public GridPane accountCollection;
    public JFXTextField buySearchField;
    public JFXTextField sellSearchField;
    private Asset selectedElement = null;

    public void setBackButtonOnMouseEntered() {
        back.setImage(new Image("file:images/hover_back_button_corner.png"));
    }

    public void setBackButtonOnMousePressed() { back.setImage(new Image("file:images/pressed_back_button_corner.png")); }

    public void setBackButtonOnMouseExited() {
        back.setImage(new Image("file:images/button_back_corner.png"));
    }

    public void setBackButtonOnMouseReleased() throws IOException { loadPageOnStackPane(back.getParent(), "FXML/MainMenu.fxml", "ltr"); }

    public void setActionButtonOnMousePressed(){
        if (selectedElement!=null){
            if(buyTab.isSelected()){
                actionButton.setImage(new Image("file:images/pressed_buy_button.png"));
            }else if(sellTab.isSelected()){
                actionButton.setImage(new Image("file:images/pressed_sell_button.png"));
            }
        }
    }


    public void setActionButtonOnMouseMoved(){
        assetPrice.setVisible(false);
        if (selectedElement==null){
            actionButton.setImage(new Image("file:images/default_action_button.png"));
        }else {
            if(buyTab.isSelected()){
                actionButton.setImage(new Image("file:images/hover_buy_button.png"));
            }else if(sellTab.isSelected()){
                actionButton.setImage(new Image("file:images/hover_sell_button.png"));
            }
        }
    }

    public void setActionButtonOnMouseExited() {
        if (selectedElement==null){
            actionButton.setImage(new Image("file:images/default_action_button.png"));
        }else{
            assetPrice.setVisible(true);
            actionButton.setImage(new Image("file:images/button_primary_glow.png"));
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
            } else if(!buyTab.isSelected()){
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy.png")));
            }else{
                buyTab.setGraphic(new ImageView(new Image("file:images/tab_buy_pressed.png")));
            }
            if (ShopController.this.isInRightTabCoordination(event.getX(), event.getY(), 10 + 160 + 15, 6, 160, 40)) {
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell_hover.png")));
            } else if(!sellTab.isSelected()){
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell.png")));
            }else {
                sellTab.setGraphic(new ImageView(new Image("file:images/tab_sell_pressed.png")));
            }
        });

        assetPrice.setVisible(false);




    }

    public boolean isInRightTabCoordination(double x, double y, double tabX, double tabY, int tabWidth, int tabHeight) {
        return (x > tabX && x < tabX + tabWidth && y > tabY && y < tabY + tabHeight);
    }

}
