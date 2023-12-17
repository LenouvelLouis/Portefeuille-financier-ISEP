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

    }

    @FXML
    public void displaywallet() throws SQLException {
        initpercentagedisplay();
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
        for(WalletInfo w :  walletInfoList){
            if(w.getType()=="action")
                action+=w.getWalletValueInfoList().getFirst().getValue();
            else
                crypto+= w.getWalletValueInfoList().getFirst().getValue();
        }
        total=action+crypto;
        double actionPercentage =(action*100)/total;
        double cryptoPercentage = (crypto*100)/total;

        Label percentageLabelActions = new Label("Actions" + " : " + actionPercentage + "%");
        Label percentageLabelCrypto = new Label("Crypto" + " : " + cryptoPercentage + "%");
        StackPane blockActions = new StackPane();
        StackPane blockCrypto = new StackPane();
        blockActions.setStyle("-fx-background-color: black; -fx-padding: 10px;");
        blockActions.getChildren().add(percentageLabelActions);
        blockCrypto.setStyle("-fx-background-color: black; -fx-padding: 10px;");
        blockCrypto.getChildren().add(percentageLabelCrypto);
        blockActions.setMinWidth(actionPercentage*5);  // Vous devrez ajuster ce facteur selon vos besoins
        blockCrypto.setMinWidth(cryptoPercentage*5);  // Vous devrez ajuster ce facteur selon vos besoins
        allocation.getChildren().addAll(blockActions,blockCrypto);
    }

}
