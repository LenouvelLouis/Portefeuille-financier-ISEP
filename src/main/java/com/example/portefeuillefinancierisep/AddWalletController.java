package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import Modele.WalletModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class AddWalletController {

    private UserInfo userInfo;

    @FXML
    TextField nameWallet;

    @FXML
    Label msg_error;

    @FXML
    Button createWallet;

    private BarreNavigationController barreNavigationController; //BarreNavigationController est une classe qui permet de faire le lien entre les différentes pages


    private WalletModele walletModele = new WalletModele(); //WalletModele est une classe qui permet de faire le lien entre la base de données et le programme

    /**
     * Initialisation de l'utilisateur
     * @param u
     * @param barreNavigationController
     */
    public void initializeUser(UserInfo u, BarreNavigationController barreNavigationController) {
        this.userInfo = u;
        this.barreNavigationController = barreNavigationController;
    }

    /**
     * Création d'un portefeuille
     * @param event
     * @throws IOException
     */
    public void create_wallet(ActionEvent event) throws IOException {
        String name=this.nameWallet.getText().toLowerCase();
        if(name.isEmpty()){ //Si le nom du portefeuille est vide
            msg_display(Color.RED,"Veuillez saisir un nom de portefeuille"); //Affichage d'un message d'erreur
            return;
        }

        if (walletModele.is_wallet_create(this.userInfo,name)) { //Si le portefeuille existe déjà
            msg_display(Color.RED,"Ce portefeuille existe déjà"); //Affichage d'un message d'erreur
            return;
        }

        walletModele.create_wallet(this.userInfo, name); //Création du portefeuille
        this.barreNavigationController.add_wallet(); //Ajout du portefeuille dans la barre de navigation
        msg_display(Color.GREEN,"Votre portefeuille a bien été créé"); //Affichage d'un message de confirmation
        nameWallet.clear();
    }
    /**
     * Affichage d'un message d'erreur
     * @param color
     * @param msg
     */

    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color); //Couleur du message
        msg_error.setText(msg); //Message
    }

}
