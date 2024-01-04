package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import Info.WalletInfo;
import Modele.UserModele;
import Modele.WalletModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class ProfileController {
    private UserInfo u;

    private List<WalletInfo> walletInfoList;

    private UserModele user = new UserModele();

    private WalletModele wallet =new WalletModele();

    @FXML
    TextField email_text;
    @FXML
    TextField nom_text;
    @FXML
    TextField prenom_text;
    @FXML
    TextField tel_text;
    @FXML
    Label msg_error;
    @FXML
    ComboBox listwallet;
    @FXML
    TextField wallettotale;
    @FXML
    TextField wallettotale_crypto;
    @FXML
    TextField wallettotale_action;
    @FXML
    Label labelWallet;
    @FXML
    Separator separatorWallet;
    @FXML
    Button buttonSave;
    @FXML
    AnchorPane paneProfile;
    public void initializeUser(UserInfo user) {
        this.u=user;
        email_text.setText(this.u.getMail());
        nom_text.setText(this.u.getNom());
        prenom_text.setText(this.u.getPrenom());
        tel_text.setText(this.u.getTel());
        this.displayWalletInfo();
    }

    public void displayWalletInfo(){
        this.walletInfoList=wallet.getWalletInfo(this.u);
        if(!walletInfoList.isEmpty()){
            listwallet.setValue(walletInfoList.getFirst().getNom());
            wallettotale.setText(String.valueOf(walletInfoList.getFirst().getTotale()));
            wallettotale_action.setText(String.valueOf(walletInfoList.getFirst().getTotale_action()));
            wallettotale_crypto.setText(String.valueOf(walletInfoList.getFirst().getTotale_crypto()));
            for(WalletInfo w :walletInfoList){
                listwallet.getItems().add(w.getNom());
            }
        }
        else{
            this.displayWithoutWallet();
        }

    }

    public void displayWithoutWallet(){
        listwallet.setVisible(false);
        wallettotale.setVisible(false);
        wallettotale_action.setVisible(false);
        wallettotale_crypto.setVisible(false);
        labelWallet.setVisible(false);
        separatorWallet.setVisible(false);
        msg_error.layoutXProperty().setValue(76);
        msg_error.layoutYProperty().setValue(247);
        buttonSave.layoutXProperty().setValue(76);
        buttonSave.layoutYProperty().setValue(329);
        paneProfile.setPrefHeight(400);
    }

    public void selectWallet() {
        String nom = listwallet.getValue().toString();
        WalletInfo w = this.findWalletByName(nom);
        wallettotale.setText(String.valueOf(w.getTotale()));
        wallettotale_action.setText(String.valueOf(w.getTotale_action()));
        wallettotale_crypto.setText(String.valueOf(w.getTotale_crypto()));
    }

    public WalletInfo findWalletByName(String name){
        for (WalletInfo w :walletInfoList){
            if(w.getNom().equals(name)){
                return w;
            }
        }
        return null;
    }
}
