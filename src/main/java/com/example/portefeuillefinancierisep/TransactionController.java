package com.example.portefeuillefinancierisep;

import Info.TransactionInfo;
import Info.TransactionTypeInfo;
import Info.UserInfo;
import Info.WalletInfo;
import Modele.TransactionModele;
import Modele.UserModele;
import Modele.WalletModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TransactionController {
    private UserInfo user;

    private TransactionModele transaction = new TransactionModele();

    private WalletModele walletModele =new WalletModele();

    private UserModele userModele=new UserModele();
    private ArrayList<TransactionTypeInfo> entreprise;
    private ArrayList<TransactionTypeInfo> crypto;

    private Boolean realvalueDisplay = false;

    @FXML
    Button btnRealvalue;
    @FXML
    ComboBox<String> wallet;

    @FXML
    ComboBox<String> listtype;

    @FXML
    ComboBox<String> libelle_type;

    @FXML
    Label labeltype;

    @FXML
    TextField value;

    @FXML
    TextField realvalue;

    @FXML
    Label labelrealvalue;
    @FXML
    Label msg_error;
    @FXML
    AnchorPane paneTransaction;
    @FXML
    AnchorPane paneGlobale;

    private final ArrayList<String> type = new ArrayList<>(Arrays.asList("actions", "crypto"));
    private List<WalletInfo> w;

    public void initializeUser(UserInfo u) {
        this.user=u;
        this.entreprise =transaction.getEntreprise();
        this.crypto=transaction.getCrypto();
        this.w=walletModele.getWalletInfo(this.user);
        this.displayTransactionInterface();
        this.initWallet();
        this.initType();
        this.initEntreprise();
        this.value.setText("10");
    }

    private void displayTransactionInterface(){
        if(this.w.isEmpty()){
            this.paneTransaction.setVisible(false);
            msg_display(Color.RED,"Veulliez jouter un nouveau wallet");
            this.rezizeTransactionInterface();
        }
        else if(this.user.getFond()==0){
            this.paneTransaction.setVisible(false);
            msg_display(Color.RED,"Veuillez ajouter des fonds");
            this.rezizeTransactionInterface();
        }
    }

    private void rezizeTransactionInterface(){
        msg_error.layoutYProperty().setValue(150);
        paneGlobale.setPrefHeight(200);
    }

    public void selectType() {
        String nom = listtype.getValue();
        if(nom.equals("actions")){
            libelle_type.getItems().clear();
            this.initEntreprise();
        }
        else{
            libelle_type.getItems().clear();
            this.initCrypto();
        }
    }

    public void displayRealValue(){
        this.realvalueDisplay=!this.realvalueDisplay;
        if(this.chekvalue()){
            this.realvalue.setText(value.getText());
            this.labelrealvalue.setVisible(realvalueDisplay);
            this.realvalue.setVisible(realvalueDisplay);
        }
    }

    private boolean chekvalue(){
        try{
            Float.parseFloat(value.getText());
            return true;
        }catch (RuntimeException e){
            msg_display(Color.RED,"Veuillez saisir une valeur numérique");
            return false;
        }
    }

    private void initCrypto(){
        labeltype.setText("Crypto");
        for(TransactionTypeInfo s : crypto){
            libelle_type.getItems().add(s.getName());
        }
        libelle_type.setValue(crypto.getFirst().getName());
    }

    private void initEntreprise(){
        labeltype.setText("Entreprise");
        for(TransactionTypeInfo s : entreprise){
            libelle_type.getItems().add(s.getName());
        }
        libelle_type.setValue(entreprise.getFirst().getName());
    }

    private void initType(){
        listtype.setValue(type.getFirst());
        for(String s : type){
            listtype.getItems().add(s);
        }
    }

    private void initWallet(){
        if(!this.w.isEmpty()){
            wallet.setValue(this.w.getFirst().getNom());
            for(WalletInfo w :this.w){
                wallet.getItems().add(w.getNom());
            }
        }
    }

    public void buy(ActionEvent actionEvent) {
        if(!this.chekvalue()){
            return;
        }
        Float buyValue = Float.parseFloat(value.getText());
        if(this.user.getFond()-buyValue<0){
            msg_display(Color.RED,"Fonds insufisants");
            return;
        }
        String nomWallet = wallet.getValue();
        WalletInfo walletInfo = this.findWalletByName(nomWallet);
        String typeInfo = listtype.getValue();
        String libele=libelle_type.getValue();
        Calendar calendar = Calendar.getInstance();
        java.util.Date today = calendar.getTime();
        TransactionInfo t = new TransactionInfo(walletInfo.getId(),buyValue,new java.sql.Date(today.getTime()),typeInfo,libele);
        try {
            if(typeInfo.equals("actions")){
                this.walletModele.updateTotaleActions(walletInfo,walletInfo.getTotale()+buyValue);
            }
            if(typeInfo.equals("crypto")){
                this.walletModele.updateTotaleCrypto(walletInfo,walletInfo.getTotale()+buyValue);
            }
            this.walletModele.updateTotal(walletInfo,walletInfo.getTotale()+buyValue);
            this.userModele.updateFunds(this.user.getId(),this.user.getFond()-buyValue);
            this.transaction.addTransaction(t);
        }
        catch (RuntimeException e){
            msg_display(Color.RED,"Erreur lors de la transaction");
        }

    }

    public void sell(ActionEvent actionEvent) {
        if(!this.chekvalue()){
            return;
        }
        Float sellValue = Float.parseFloat(value.getText());
        String nomWallet = wallet.getValue();
        WalletInfo walletInfo = this.findWalletByName(nomWallet);
        String typeInfo = listtype.getValue();
        if(typeInfo.equals("actions") && walletInfo.getTotale_action()-sellValue<0){
            msg_display(Color.RED,"Fonds insufisants sur votre wallet");
            return;
        }
        if(typeInfo.equals("crypto") && walletInfo.getTotale_crypto()-sellValue<0){
            msg_display(Color.RED,"Fonds insufisants sur votre wallet");
            return;
        }
        String libele=libelle_type.getValue();
        Calendar calendar = Calendar.getInstance();
        java.util.Date today = calendar.getTime();
        TransactionInfo t = new TransactionInfo(walletInfo.getId(),-sellValue,new java.sql.Date(today.getTime()),typeInfo,libele);
        try {
            if(typeInfo.equals("actions")){
                this.walletModele.updateTotaleActions(walletInfo,walletInfo.getTotale()-sellValue);
            }
            if(typeInfo.equals("crypto")){
                this.walletModele.updateTotaleCrypto(walletInfo,walletInfo.getTotale()-sellValue);
            }
            this.walletModele.updateTotal(walletInfo,walletInfo.getTotale()-sellValue);
            this.userModele.updateFunds(this.user.getId(), this.user.getFond() + sellValue);
            this.transaction.addTransaction(t);
        }
        catch (RuntimeException e){
            msg_display(Color.RED,"Erreur lors de la transaction");
        }
    }

    public WalletInfo findWalletByName(String name){
        for (WalletInfo w :this.w){
            if(w.getNom().equals(name)){
                return w;
            }
        }
        return null;
    }

    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color);
        msg_error.setText(msg);
    }
}
