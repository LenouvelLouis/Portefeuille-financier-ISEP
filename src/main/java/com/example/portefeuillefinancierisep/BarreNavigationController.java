package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import Info.WalletInfo;
import Modele.WalletModele;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private Label scrollingLabel;

    @FXML
    private Button buttonCours;

    private String message = "        Bitcoin atteint un nouveau sommet historique, dépassant les attentes des investisseurs.              Les marchés des actions réagissent positivement aux dernières régulations sur les cryptomonnaies.              Une importante société de technologie blockchain annonce une percée innovante, stimulant les investissements dans le secteur.              Les actions de grandes entreprises de cryptomonnaies chutent suite à des inquiétudes réglementaires croissantes.     ";
    private int charIndex = 0;

    @FXML
    private void initialize() {
        startScrollingMessage();
    }

    private void startScrollingMessage() {
        // Vous pouvez ajuster cette valeur pour changer la longueur du texte affiché
        int displayLength = 25; // Par exemple, augmenter à 20 caractères

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
            String displayed;
            if (charIndex + displayLength > message.length()) {
                displayed = message.substring(charIndex) + message.substring(0, displayLength - (message.length() - charIndex));
            } else {
                displayed = message.substring(charIndex, charIndex + displayLength);
            }
            scrollingLabel.setText(displayed);
            charIndex = (charIndex + 1) % message.length();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
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
        controller.initializeUser(this.user,this.walletInfos);
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
            this.walletList.setValue("Selectionner un portefeuille");
            this.walletList.getItems().clear();
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
        if(name==null ||name.equals("Selectionner un portefeuille") ){
            return;
        }
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
        this.walletList.getItems().clear();
        for(WalletInfo w:this.walletInfos){
            if(!this.walletList.getItems().contains(w.getNom())){
                this.walletList.getItems().add(w.getNom());
            }
        }
    }
}
