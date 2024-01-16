package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import Info.WalletInfo;
import Modele.WalletModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class BarreNavigationController {
    @FXML
    ComboBox<String> walletList;
    @FXML
    Label labelUser;
    @FXML
    private AnchorPane FenetreAffichage;
    private UserInfo user;

    private WalletModele walletModele=new WalletModele();

    private ArrayList<WalletInfo> walletInfos;

    @FXML
    public void Affichage_Add_Wallet() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add-wallet-view.fxml"));
        AnchorPane anchorPane = loader.load();
        AddWalletController controller = loader.getController();
        controller.initializeUser(this.user,this);
        FenetreAffichage.getChildren().setAll(anchorPane);
    }

    /**
     * Affichage de la page d'accueil
     * @throws IOException
     */
    public void Affichage_Dashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
        AnchorPane anchorPane = loader.load();
        DashboardController controller = loader.getController();
        controller.initializeUser(this.user,this.user.getWalletInfos());
        FenetreAffichage.getChildren().setAll(anchorPane);
    }

    /**
     * Affichage de la page de profil
     * @throws IOException
     */
    public void Affichage_Profile() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile-view.fxml"));
        AnchorPane anchorPane = loader.load();
        ProfileController controller = loader.getController();
        controller.initializeUser(this.user,this);
        FenetreAffichage.getChildren().setAll(anchorPane);
    }
    /**
     * Affichage de la page de profil
     * @throws IOException
     */
    public void Affichage_Add_fond() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addFunds-view.fxml"));
        AnchorPane anchorPane = loader.load();
        FundsController controller = loader.getController();
        controller.initializeUser(this.user,this);
        FenetreAffichage.getChildren().setAll(anchorPane);
    }

    /**
     * Affichage de la page de profil
     * @throws IOException
     */
    public void Affichage_transasction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transaction-view.fxml"));
        AnchorPane anchorPane = loader.load();
        TransactionController controller = loader.getController();
        controller.initializeUser(this.user);
        FenetreAffichage.getChildren().setAll(anchorPane);
    }

    /**
     * Affichage de la page de profil
     * @throws IOException
     */
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


    /**
     * Affichage de la page de profil
     * @throws IOException
     */
    public void initializeUser(UserInfo u) throws IOException {
        this.user=u;
        this.user.setWalletInfos(walletModele.getWalletInfo(this.user));
        this.walletInfos=this.user.getWalletInfos();
        this.displayWallet();
        if(this.labelUser!=null){
            this.labelUser.setText(this.user.getNom()+" "+this.user.getPrenom());
        }
    }

    /**
     * Affichage de la page de profil
     * @throws IOException
     */
    private void displayWallet() {
        if(this.walletList!=null){
            if(this.walletInfos.isEmpty()){
                this.walletList.getItems().add("Aucun portefeuille");
                return;
            }
            this.walletList.getItems().clear();
            this.walletList.setValue("Selectionner un portefeuille");
            for(WalletInfo w:this.walletInfos){
                this.walletList.getItems().add(w.getNom());
            }
        }
    }

    /**
     * Selection d'un portefeuille
     * @throws IOException
     */
    public void selectWallet(ActionEvent actionEvent) throws IOException {
        if(this.walletInfos.isEmpty()){
            return;
        }
        String name =walletList.getValue();
        WalletInfo walletInfo = this.findWallet(name);
        ArrayList<WalletInfo> w = new ArrayList<>();
        w.add(walletInfo);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
        AnchorPane anchorPane = loader.load();
        DashboardController controller = loader.getController();
        controller.initializeUser(this.user,w);
        FenetreAffichage.getChildren().setAll(anchorPane);
    }

    /**
     * recherche d'un portefeuille
     */
    private WalletInfo findWallet(String name) {
        for(WalletInfo w:this.walletInfos){
            if(w.getNom().equals(name)){
                return w;
            }
        }
        return null;
    }

    /**
     * Mise à jour de la liste des portefeuilles
     */
    public void add_wallet() {
        this.walletInfos=walletModele.getWalletInfo(this.user);
        this.displayWallet();
    }
}
