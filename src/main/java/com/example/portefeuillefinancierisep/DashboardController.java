package com.example.portefeuillefinancierisep;

import Info.TransactionInfo;
import Info.UserInfo;
import Info.WalletInfo;
import Modele.TransactionModele;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

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

    public void initializeUser(UserInfo user, ArrayList<WalletInfo> walletInfos) {
        this.user = user;
        this.walletInfos = walletInfos;
        if(walletInfos.isEmpty()){
            this.displaywithoutWallet();
            this.msg_display(Paint.valueOf("red"), "Vous n'avez pas de portefeuille");
            return;
        }
        this.initDataPoints();
        this.iniTotale();
        this.initDate();
        this.initGain();
        this.initPercent();
    }

    private void displaywithoutWallet() {
        PanePercent.setVisible(false);
        PaneChart.setVisible(false);
    }

    private void initPercent() {
        ArrayList<Float> actionsPercents = new ArrayList<>();
        ArrayList<Float> cryptosPercents = new ArrayList<>();
        for (WalletInfo walletInfo : walletInfos){
            Float actions =(walletInfo.getTotale_action()*100)/walletInfo.getTotale();
            Float cryptos =(walletInfo.getTotale_crypto()*100)/walletInfo.getTotale();
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

    private void initGain() {
        ArrayList<Float> gains = new ArrayList<>();
        for (WalletInfo walletInfo : walletInfos) {
            ArrayList<TransactionInfo> transactionInfos = transactionModele.getTransactionByWallet(walletInfo.getId());
            if(transactionInfos.isEmpty()){
                continue;
            }
            float départ = transactionInfos.getFirst().getValue();
            float arrivée = walletInfo.getTotale();
            float gain = ((arrivée - départ) / (float) départ)*100 ;
            gains.add(gain);
        }
        float value = 0f;
        for (Float gain : gains) {
            value += gain;
        }
        value = value/gains.size();
        if(isEmptyWallet()){
            Gain.setText("0%");
            Gain.setStyle("-fx-text-fill: white;");
            return;
        }

        if (this.walletInfos.size()==1 && isOneTransaction()) {
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

    private boolean isOneTransaction() {
        for(WalletInfo walletInfo : walletInfos) {
            if(transactionModele.getTransactionByWallet(walletInfo.getId()).size() != 1) {
                return false;
            }
        }
        return true;
    }

    private void initDate() {
        if (isEmptyWallet()) {
            labelDate.setText("Aucune transaction");
            return;
        }
        Timestamp lastDate = this.getLastDate();
        Timestamp firstDate = this.getFirstDate();
        Date datefirst = new Date(firstDate.getTime());
        Date datelast = new Date(lastDate.getTime());
        labelDate.setText(datefirst.toString() + " - " + datelast.toString());
    }

    private boolean isEmptyWallet() {
        for(WalletInfo walletInfo : walletInfos) {
            if(!transactionModele.getTransactionByWallet(walletInfo.getId()).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private Timestamp getFirstDate() {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        for (WalletInfo walletInfo : walletInfos) {
            ArrayList<TransactionInfo> transactionInfos = transactionModele.getTransactionByWallet(walletInfo.getId());
            for (TransactionInfo transactionInfo : transactionInfos) {
                if(transactionInfo.getDate().before(date)){
                    date = transactionInfo.getDate();
                }
            }
        }
        return date;
    }

    private Timestamp getLastDate() {
        Timestamp date =new Timestamp(0);
        for (WalletInfo walletInfo : walletInfos) {
            ArrayList<TransactionInfo> transactionInfos = transactionModele.getTransactionByWallet(walletInfo.getId());
            for (TransactionInfo transactionInfo : transactionInfos) {
                if(transactionInfo.getDate().after(date)){
                    date = transactionInfo.getDate();
                }
            }
        }
        return date;
    }

    private void iniTotale() {
        if (walletInfos.isEmpty()) {
            Totale.setText("0 €");
            return;
        }
        Float totale = 0f;
        for (WalletInfo walletInfo : walletInfos) {
            totale += walletInfo.getTotale();
        }
        Totale.setText(totale.toString() + " €");
    }

    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color);
        msg_error.setText(msg);
    }

    private void initDataPoints() {
        chart.lookup(".chart-title").setStyle("-fx-text-fill: white;");
        chart.setStyle("-fx-tick-mark-fill: white; -fx-text-fill: white;");
        if(walletInfos.isEmpty()){
            return;
        }
        if(walletInfos.size() == 1) {
            chart.setTitle("Evolution du portefeuille");
        }
        else {
            chart.setTitle("Evolution des portefeuilles");
        }
        for (WalletInfo walletInfo : walletInfos) {
            ArrayList<TransactionInfo> transactionInfos = transactionModele.getTransactionByWallet(walletInfo.getId());
            XYChart.Series<String, Float> series = new XYChart.Series<>();
            for (TransactionInfo transactionInfo : transactionInfos) {
                Float value = this.historiqueWallet(transactionInfo,transactionInfos);
                String date = transactionInfo.getDate().toString();
                series.getData().add(new XYChart.Data<>(date, value));
            }
            if(transactionInfos.isEmpty()){
               return;
            }
            chart.getData().add(series);
        }
    }

    private Float historiqueWallet(TransactionInfo transactionInfo, ArrayList<TransactionInfo> transactionInfos) {
        if (transactionInfos.getFirst().equals(transactionInfo)) {
            return transactionInfo.getValue();
        }
        Float value = 0f;
        for (TransactionInfo transactionInfo1 : transactionInfos) {
            if (transactionInfo1.getDate().before(transactionInfo.getDate()) || transactionInfo1.equals(transactionInfo)) {
                value += transactionInfo1.getValue();
            }
        }
        return value;
    }
}
