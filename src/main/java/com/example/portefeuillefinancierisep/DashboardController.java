package com.example.portefeuillefinancierisep;

import Info.WalletInfo;
import Modele.WalletModele;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardController {
    @FXML
    Button dashboardbutton;

    @FXML
    Button walletbutton;

    @FXML
    VBox listwallet;

    @FXML
    VBox allocation;

    Boolean displaywallet=false;

    WalletModele walletModele =new WalletModele();

    List<WalletInfo> walletInfoList = new ArrayList<>();

    public DashboardController() throws SQLException {
        walletInfoList=walletModele.getAllWallet("john.doe@example.com");
        initpercentagedisplay();
    }

    @FXML
    public void displaywallet() throws SQLException {
        displaywallet=!displaywallet;
        listwallet.setVisible(displaywallet);
        if (displaywallet) {
            walletbutton.setText("Portefeuille ˅");
        } else {
            walletbutton.setText("Portefeuille >");
            return;
        }
        if(walletInfoList.isEmpty()){
            walletbutton.setVisible(false);
        }
        listwallet.getChildren().clear();
        for(WalletInfo w:walletInfoList){
            Label walletlabel=new Label(w.getName());
            String color = "-fx-text-fill: white;";
            walletlabel.setStyle(color);
            walletlabel.setOnMouseClicked(e->{
                try {
                    this.displaywallet();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            listwallet.getChildren().add(walletlabel);
        }
    }

    public void initpercentagedisplay(){
        double action=0;
        double crypto=0;
        double total;
        /*for(WalletInfo w :  walletInfoList){
            if(w.getType().equals("action"))
                action+=w.getWalletValueInfoList().getFirst().getValue();
            else
                crypto+= w.getWalletValueInfoList().getFirst().getValue();
        }*/
        total=action+crypto;
        double actionPercentage =(action*100)/total;
        double cryptoPercentage = (crypto*100)/total;
        String color = "-fx-text-fill: white;";
        Label percentageLabelActions = new Label("Actions \u00B7 " +actionPercentage +"%");
        Label percentageLabelCrypto = new Label("Crypto \u00B7 " +cryptoPercentage +"%");
        percentageLabelActions.setStyle(color);
        percentageLabelCrypto.setStyle(color);

        StackPane blockActions = new StackPane();
        blockActions.setStyle("-fx-background-color: #3d888e; -fx-padding: 10px;");
        blockActions.getChildren().add(percentageLabelActions);
        blockActions.setMaxWidth(110);
        StackPane blockCrypto = new StackPane();
        blockCrypto.setStyle("-fx-background-color: #6f50e5; -fx-padding: 10px;");
        blockCrypto.getChildren().add(percentageLabelCrypto);
        blockCrypto.setMaxWidth(110);
        allocation.getChildren().add(blockCrypto);
        allocation.getChildren().add(blockActions);
    }

}
