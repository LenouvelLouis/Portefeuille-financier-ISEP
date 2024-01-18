package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import Info.WalletInfo;
import Modele.UserModele;
import Modele.WalletModele;
import Service.CheckDataService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
    ComboBox<String> listwallet;
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

    private CheckDataService checkDataService = new CheckDataService(); // Service de vérification des données saisies
    private BarreNavigationController barreNavigationController; // Controleur de la barre de navigation

    /**
     * Méthode appelée lors du clic sur l'engrenage
     */
    public void initializeUser(UserInfo user, BarreNavigationController barreNavigationController) {
        this.u=user;
        nom_text.setText(this.u.getNom());
        prenom_text.setText(this.u.getPrenom());
        tel_text.setText(this.u.getTel());
        mail_text.setText(this.u.getMail());
        this.barreNavigationController=barreNavigationController;
        this.displayWalletInfo();
    }

    /**
     * Méthode qui affiche les informations du wallet
     */
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


    /**
     * Méthode qui fait disparaitre les informations du wallet
     */
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
        buttonSave.layoutYProperty().setValue(329);
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Sauvegarder"
     */
    public void editInformation(){
        nom = nom_text.getText();
        prenom = prenom_text.getText();
        tel = tel_text.getText();
        mail = mail_text.getText();
        if (!isChampNotEmpty()) { // Vérification que tous les champs sont remplis
            msg_display(Color.RED,"Veuillez saisir tous les champs");
            return;
        }
        if (!checkDataService.isValidFormatNomPrenom(nom,prenom)) { // Vérification du format du nom et du prénom
            msg_display(Color.RED,"Nom ou prénom non valide");
            return;
        }

        if (!checkDataService.isValidPhoneNumber(tel)) { // Vérification du format du numéro de téléphone
            msg_display(Color.RED,"Numéro de téléphone non valide");
            return;
        }

        if (!checkDataService.isValidFormatEmail(mail)) { // Vérification du format de l'adresse mail
            msg_display(Color.RED,"Adresse mail non valide");
            return;
        }

        if (!this.u.getMail().equals(mail) && user.is_user_create(mail)) { // Vérification que le compte n'existe pas déjà
            msg_display(Color.RED,"Ce compte existe déjà");
            return;
        }
        try {
            this.u.setMail(mail); // Récupération des données saisies
            this.u.setNom(nom);
            this.u.setPrenom(prenom);
            this.u.setTel(tel);
            this.user.updateUserInfo(this.u); // Mise à jour des données dans la base de données
            msg_display(Color.GREEN,"Mise à jour des informations !"); // Affichage d'un message de succès
            barreNavigationController.initializeUser(this.u);
        }catch (RuntimeException | IOException e){
            msg_display(Color.RED,"Erreur lors de la mise à jour de vos données");
            System.out.println(e.getMessage());
        }


    }

    /**
     * Méthode appelée lors de la sélection d'un wallet dans la liste déroulante "listwallet"
     */
    public void selectWallet() {
        String nom = listwallet.getValue().toString(); // Récupération du nom du wallet sélectionné
        WalletInfo w = this.findWalletByName(nom); // Recherche du wallet dans la liste des wallets
        wallettotale.setText(String.valueOf(w.getTotale()));
        wallettotale_action.setText(String.valueOf(w.getTotale_action())); // Affichage des informations du wallet
        wallettotale_crypto.setText(String.valueOf(w.getTotale_crypto()));
    }

    /**
     * Méthode utilisée pour trouver un wallet dans la liste des wallets
     */
    public WalletInfo findWalletByName(String name){
        for (WalletInfo w :walletInfoList){
            if(w.getNom().equals(name)){
                return w;
            }
        }
        return null;
    }

    /**
     * Méthode de vérification que tous les champs sont remplis
     */
    public boolean isChampNotEmpty() {
        return !nom.isEmpty() && !prenom.isEmpty() && !tel.isEmpty();
    }

    /**
     * Méthode permettant d'afficher un message d'erreur
     */
    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color);
        msg_error.setText(msg);
    }

}
