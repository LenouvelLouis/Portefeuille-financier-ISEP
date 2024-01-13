package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BarreNavigationController {
    @FXML
    private AnchorPane FenetreAffichage;
    private UserInfo user;

    @FXML
    public void Affichage_Add_Wallet() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("add-wallet-view.fxml"));
        FenetreAffichage.getChildren().setAll(anchorPane);
    }

    public void Affichage_Dashboard() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("dashboard-view.fxml"));
        FenetreAffichage.getChildren().setAll(anchorPane);
    }

    public void Affichage_Profile() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("profile-view.fxml"));
        FenetreAffichage.getChildren().setAll(anchorPane);
    }

    public void Affichage_Add_fond() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("addFunds-view.fxml"));
        FenetreAffichage.getChildren().setAll(anchorPane);
    }

    public void Affichage_transasction() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("transaction-view.fxml"));
        FenetreAffichage.getChildren().setAll(anchorPane);
    }

    public void Redirection_Home() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("home-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Home");
        stage.setScene(scene);
        ((Stage) this.FenetreAffichage.getScene().getWindow()).close();
        stage.show();
        stage.setResizable(false);
    }


    public void initializeUser(UserInfo u) throws IOException {
        this.user=u;
        this.Affichage_Dashboard();
    }
}
