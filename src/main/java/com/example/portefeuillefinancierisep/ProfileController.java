package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import Info.WalletInfo;
import Modele.UserModele;
import Modele.WalletModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileController {
    private UserInfo u;

    private List<WalletInfo> walletInfoList;

    private UserModele user = new UserModele();

    private WalletModele wallet =new WalletModele();
    String nom, prenom, tel,mail;
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
    @FXML
    Label labelAction;
    @FXML
    Label labelCrypto;
    @FXML
    Label labelTotale;
    @FXML
    TextField mail_text;


    public void initializeUser(UserInfo user) {
        this.u=user;
        nom_text.setText(this.u.getNom());
        prenom_text.setText(this.u.getPrenom());
        tel_text.setText(this.u.getTel());
        mail_text.setText(this.u.getMail());
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
        labelAction.setVisible(false);
        labelCrypto.setVisible(false);
        labelTotale.setVisible(false);
        msg_error.layoutXProperty().setValue(76);
        msg_error.layoutYProperty().setValue(247);
        buttonSave.layoutXProperty().setValue(76);
        buttonSave.layoutYProperty().setValue(329);
        paneProfile.setPrefHeight(400);
    }

    public void editInformation(){
        nom = nom_text.getText();
        prenom = prenom_text.getText();
        tel = tel_text.getText();
        mail = mail_text.getText();
        if (!isChampNotEmpty()) {
            msg_display(Color.RED,"Veuillez saisir tous les champs");
            return;
        }
        if (!isValidFormatNomPrenom()) {
            msg_display(Color.RED,"Nom ou prénom non valide");
            return;
        }

        if (!isValidPhoneNumber()) {
            msg_display(Color.RED,"Numéro de téléphone non valide");
            return;
        }

        if (!isValidFormatEmail()) {
            msg_display(Color.RED,"Adresse mail non valide");
            return;
        }

        if (!this.u.getMail().equals(mail) && user.is_user_create(mail)) {
            msg_display(Color.RED,"Ce compte existe déjà");
            return;
        }
        try {
            this.u.setMail(mail);
            this.u.setNom(nom);
            this.u.setPrenom(prenom);
            this.u.setTel(tel);
            this.user.updateUserInfo(this.u);
            msg_display(Color.GREEN,"Mise à jour des informations !");
            this.redirection();
        }catch (RuntimeException | IOException e){
            msg_display(Color.RED,"Erreur lors de la mise à jour de vos données");
            System.out.println(e.getMessage());
        }


    }

    public void redirection() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("home-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Home");
        stage.setScene(scene);
        ((Stage) this.nom_text.getScene().getWindow()).close();
        stage.show();
        stage.setResizable(false);
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


    public boolean isValidFormatNomPrenom() {
        String regex = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher_nom = pattern.matcher(nom);
        Matcher matcher_prenom = pattern.matcher(prenom);
        return matcher_nom.matches() && matcher_prenom.matches();
    }

    public boolean isValidPhoneNumber() {
        String regex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tel);
        return matcher.matches();
    }

    public boolean isValidFormatEmail() {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    public boolean isChampNotEmpty() {
        return !nom.isEmpty() && !prenom.isEmpty() && !tel.isEmpty();
    }

    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color);
        msg_error.setText(msg);
    }

}
