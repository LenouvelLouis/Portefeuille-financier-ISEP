package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import Modele.UserModele;
import Modele.WalletModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class AddWalletController {

    private UserInfo userInfo;

    @FXML
    TextField nameWallet;

    @FXML
    Label msg_error;

    @FXML
    Button createWallet;

    private WalletModele walletModele = new WalletModele();

    public void initializeUser(UserInfo u) {
        this.userInfo = u;
    }


    public void create_wallet(ActionEvent event) throws IOException {
        Button boutonClique = (Button) event.getSource();
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
        msg_display(Color.GREEN,"Votre portefeuille a bien été créé");
        nameWallet.clear();
        if(boutonClique==createWallet){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("auth-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Connexion");
            stage.setScene(scene);
            ((Stage) this.msg_error.getScene().getWindow()).close();
            stage.show();  
            stage.setResizable(false);
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("inscription-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Connexion");
            stage.setScene(scene);
            ((Stage) this.msg_error.getScene().getWindow()).close();
            stage.show();
            stage.setResizable(false);
        }
    }

    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color);
        msg_error.setText(msg);
    }

}
