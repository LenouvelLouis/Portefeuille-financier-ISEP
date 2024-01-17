package com.example.portefeuillefinancierisep;

import Info.TransactionInfo;
import Info.TransactionTypeInfo;
import Info.UserInfo;
import Info.WalletInfo;
import Modele.TransactionModele;
import Modele.WalletModele;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DashboardController {
    @FXML
    AnchorPane PanePercent;
    @FXML
    AnchorPane PaneChart;
    @FXML
    Label msg_error;
    @FXML
    Label valueCrypto;
    @FXML
    Label valueAction;
    @FXML
    Label Gain;
    @FXML
    Label labelDate;
    @FXML
    Label Totale;
    @FXML
    LineChart<String, Float> chart;

    private UserInfo user;
    private ArrayList<WalletInfo> walletInfos = new ArrayList<>();

    private TransactionModele transactionModele= new TransactionModele();

    private WalletModele walletModele = new WalletModele();

    /**
     * Méthode qui initialise la page de dashboard
     */
    public void initializeUser(UserInfo user, ArrayList<WalletInfo> walletInfos) {
        this.user = user;
        this.walletInfos = walletInfos;
        if(walletInfos.isEmpty()){
            this.displaywithoutWallet();
            this.msg_display(Paint.valueOf("red"), "Vous n'avez pas de portefeuille");
            return;
        }
        this.updateInfoWallet();
        this.initDataPoints();
        this.iniTotale();
        this.initDate();
        this.initGain();
        this.initPercent();
    }

    /**
     * Méthode qui met à jour les informations des portefeuille
     */
    private void updateInfoWallet() {
        for (WalletInfo walletInfo : walletInfos) {
            walletInfo.setTotale(walletModele.getTotale(walletInfo.getId()));
            walletInfo.setTotale_action(walletModele.getTotaleAction(walletInfo.getId()));
            walletInfo.setTotale_crypto(walletModele.getTotaleCrypto(walletInfo.getId()));
        }
    }

    private void displaywithoutWallet() {
        PanePercent.setVisible(false);
        PaneChart.setVisible(false);
    }

    /**
     * Méthode qui initialise le pourcentage d'actions et de cryptomonnaies
     */
    private void initPercent() {
        ArrayList<Float> actionsPercents = new ArrayList<>();
        ArrayList<Float> cryptosPercents = new ArrayList<>();
        for (WalletInfo walletInfo : walletInfos){
            if(walletInfo.getTotale_action()==0 && walletInfo.getTotale_crypto()==0){
                continue;
            }
            Float actions =(walletInfo.getTotale_action()*100)/walletInfo.getTotale();
            Float cryptos =(walletInfo.getTotale_crypto()*100)/walletInfo.getTotale();
            if(actions.isNaN() || cryptos.isNaN()){
                continue;
            }
            actionsPercents.add(actions);
            cryptosPercents.add(cryptos);
        }
        Float percentCrypto = 0f;
        Float percentAction = 0f;
        for (Float actionsPercent : actionsPercents) {
            percentAction += actionsPercent;
        }
        for (Float cryptosPercent : cryptosPercents) {
            percentCrypto += cryptosPercent;
        }
        if(isEmptyWallet()){
            valueAction.setText("0%");
            valueCrypto.setText("0%");
            return;
        }
        percentAction = percentAction/actionsPercents.size();
        percentCrypto = percentCrypto/cryptosPercents.size();
        valueAction.setText(percentAction.toString() + "%");
        valueCrypto.setText(percentCrypto.toString() + "%");
    }

    /**
     * Méthode qui initialise le gain
     */
    private void initGain() {
        ArrayList<Float> gains = new ArrayList<>();
        for (WalletInfo walletInfo : walletInfos) { // Pour chaque portefeuille
            ArrayList<TransactionInfo> transactionInfos = transactionModele.getTransactionByWallet(walletInfo.getId());
            if(transactionInfos.isEmpty()){ // Si le portefeuille est vide
                continue;
            }
            float départ = transactionInfos.getFirst().getValue(); // On récupère la valeur de la première transaction
            float arrivée = this.getPatrimoine(transactionInfos); // On récupère la valeur du portefeuille
            float gain = ((arrivée - départ) / (float) départ)*100 ; // On calcule le gain
            gains.add(gain);
        }
        float value = 0f;
        for (Float gain : gains) {
            value += gain;
        }
        value = value/gains.size(); // On calcule la moyenne des gains
        if(isEmptyWallet()){ // Si le portefeuille est vide
            Gain.setText("0%");
            Gain.setStyle("-fx-text-fill: white;");
            return;
        }

        if (this.walletInfos.size()==1 && isOneTransaction()) { // Si l'utilisateur n'a qu'un portefeuille et qu'une transaction
            Gain.setText("0%");
            Gain.setStyle("-fx-text-fill: white;");
            return;
        }

        if(value<0){
            Gain.setText("- "+value + "%");
            Gain.setStyle("-fx-text-fill: red;");
        }
        else{
            Gain.setText("+ "+value + "%");
            Gain.setStyle("-fx-text-fill: green;");
        }
    }

    /**
     * Méthode qui initialise le patrimoine
     */
    private float getPatrimoine(ArrayList<TransactionInfo> transactionInfos) {
        float patrimoine = 0f;
        for (TransactionInfo transactionInfo : transactionInfos) { // Pour chaque transaction
                patrimoine += transactionInfo.getRealvalue()*(float)transactionInfo.getValue_cours(); // On ajoute la valeur de la transaction
        }
        return patrimoine;
    }

    /**
     * Méthode qui vérifie si l'utilisateur n'a qu'une transaction
     */
    private boolean isOneTransaction() {
        for(WalletInfo walletInfo : walletInfos) {
            if(transactionModele.getTransactionByWallet(walletInfo.getId()).size() != 1) { // Si le portefeuille n'a pas qu'une transaction
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode qui initialise la date
     */
    private void initDate() {
        if (isEmptyWallet()) {
            labelDate.setText("Aucune transaction");
            return;
        }
        Timestamp lastDate = this.getLastDate();
        Timestamp firstDate = this.getFirstDate();
        Date datefirst = new Date(firstDate.getTime());
        Date datelast = new Date(lastDate.getTime());
        labelDate.setText(datefirst.toString() + " - " + datelast.toString()); // On affiche la date
    }

    /**
     * Méthode qui vérifie si le portefeuille est vide
     */
    private boolean isEmptyWallet() {
        for(WalletInfo walletInfo : walletInfos) {
            if(!transactionModele.getTransactionByWallet(walletInfo.getId()).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode qui renvoie la première date des transactions
     */
    private Timestamp getFirstDate() {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        for (WalletInfo walletInfo : walletInfos) {
            ArrayList<TransactionInfo> transactionInfos = transactionModele.getTransactionByWallet(walletInfo.getId());
            for (TransactionInfo transactionInfo : transactionInfos) {
                if(transactionInfo.getDate().before(date)){ // Si la date de la transaction est avant la date actuelle
                    date = transactionInfo.getDate(); // On change la date
                }
            }
        }
        return date;
    }

    /**
     * Méthode qui renvoie la dernière date des transactions
     */
    private Timestamp getLastDate() {
        Timestamp date =new Timestamp(0);
        for (WalletInfo walletInfo : walletInfos) {
            ArrayList<TransactionInfo> transactionInfos = transactionModele.getTransactionByWallet(walletInfo.getId());
            for (TransactionInfo transactionInfo : transactionInfos) {
                if(transactionInfo.getDate().after(date)){ // Si la date de la transaction est après la date actuelle
                    date = transactionInfo.getDate(); // On change la date
                }
            }
        }
        return date;
    }

    /**
     * Méthode qui initialise le totale
     */
    private void iniTotale() {
        if (walletInfos.isEmpty()) {
            Totale.setText("0 €");
            return;
        }
        Float totale = 0f;
        for (WalletInfo walletInfo : walletInfos) {
            ArrayList<TransactionInfo> transactionInfos = transactionModele.getTransactionByWallet(walletInfo.getId());
            totale += this.getPatrimoine(transactionInfos); // On ajoute la valeur du portefeuille
        }
        Totale.setText(totale.toString() + " €"); // On affiche la valeur du portefeuille
    }

    /**
     * Méthode qui affiche un message d'erreur
     */
    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color);
        msg_error.setText(msg);
    }

    /**
     * Méthode qui initialise les données du graphique
     */
    private void initDataPoints() {
        chart.lookup(".chart-title").setStyle("-fx-text-fill: white;");
        chart.setStyle("-fx-tick-mark-fill: white; -fx-text-fill: white;");
        if(walletInfos.isEmpty()){
            return;
        }
        if(walletInfos.size() == 1) { // Si l'utilisateur n'a qu'un portefeuille
            chart.setTitle("Evolution du portefeuille"); // On change le titre du graphique
        }
        else {
            chart.setTitle("Evolution des portefeuilles");
        }
        for (WalletInfo walletInfo : walletInfos) {
            ArrayList<TransactionInfo> transactionInfos = transactionModele.getTransactionByWallet(walletInfo.getId());
            XYChart.Series<String, Float> series = new XYChart.Series<>(); // On crée une nouvelle série
            series.setName(walletInfo.getNom()); // On change le nom de la série
            for (TransactionInfo transactionInfo : transactionInfos) {
                Float value = this.historiqueWallet(transactionInfo,transactionInfos); // On récupère la valeur du portefeuille
                String date = transactionInfo.getDate().toString(); // On récupère la date de la transaction
                series.getData().add(new XYChart.Data<>(date, value)); // On ajoute la date et la valeur à la série
            }
            if(transactionInfos.isEmpty()){
               return;
            }
            chart.getData().add(series);
        }
    }

    /**
     * Méthode qui renvoie l'historique du portefeuille
     */
    private Float historiqueWallet(TransactionInfo transactionInfo, ArrayList<TransactionInfo> transactionInfos) {
        if (transactionInfos.getFirst().equals(transactionInfo)) {
            return transactionInfo.getRealvalue()*(float)transactionInfo.getValue_cours(); // On renvoie la valeur de la transaction
        }
        Float value = 0f;
        for (TransactionInfo transactionInfo1 : transactionInfos) {
            if (transactionInfo1.getDate().before(transactionInfo.getDate()) || transactionInfo1.equals(transactionInfo)) {
                value += transactionInfo1.getRealvalue()*(float)transactionInfo1.getValue_cours(); // On ajoute la valeur de la transaction
                }
            }
        return value;
    }



}
