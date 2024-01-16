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

    private BarreNavigationController barreNavigationController;

    private WalletModele walletModele = new WalletModele();
    public void initializeUser(UserInfo u, BarreNavigationController barreNavigationController) {
        this.userInfo = u;
        this.barreNavigationController = barreNavigationController;
    }


    public void create_wallet(ActionEvent event) throws IOException {
        String name=this.nameWallet.getText().toLowerCase();
        if(name.isEmpty()){
            msg_display(Color.RED,"Veuillez saisir un nom de portefeuille");
            return;
        }

        if (walletModele.is_wallet_create(this.userInfo,name)) {
            msg_display(Color.RED,"Ce portefeuille existe déjà");
            return;
        }

        walletModele.create_wallet(this.userInfo, name);
        this.barreNavigationController.add_wallet();
        msg_display(Color.GREEN,"Votre portefeuille a bien été créé");
        nameWallet.clear();
    }

    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color);
        msg_error.setText(msg);
    }

}
