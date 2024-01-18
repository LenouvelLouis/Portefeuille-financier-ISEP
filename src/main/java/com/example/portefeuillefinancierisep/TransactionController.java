package com.example.portefeuillefinancierisep;

import Info.TransactionInfo;
import Info.TransactionTypeInfo;
import Info.UserInfo;
import Info.WalletInfo;
import Modele.TransactionModele;
import Modele.UserModele;
import Modele.WalletModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.sql.Timestamp;
import java.util.*;

public class TransactionController {
    @FXML
    TextField realvalue;
    @FXML
    ComboBox comboBoxSellAction;
    @FXML
    Label lableSellAction;
    @FXML
    Label walletmsg;
    @FXML
    Button acheter;
    @FXML
    Button vendre;
    private UserInfo user;

    private TransactionModele transaction = new TransactionModele(); //lien vers le modele transaction

    private WalletModele walletModele =new WalletModele(); //lien vers le modele wallet

    private UserModele userModele=new UserModele(); //lien vers le modele user
    private ArrayList<TransactionTypeInfo> entreprise; //liste des entreprise
    private ArrayList<TransactionTypeInfo> crypto; //liste des crypto

    private HashMap<String,HashMap<String,Float>> sellActionByWalletvalaible = new HashMap<>(); //wallet,action,value

    @FXML
    Button btnRealvalue;
    @FXML
    ComboBox<String> wallet;

    @FXML
    ComboBox<String> listtype;

    @FXML
    ComboBox<String> libelle_type;

    @FXML
    TextField value;

    @FXML
    Label labelrealvalue;
    @FXML
    Label msg_error;

    private boolean realvalueDisplay=false;

    private final ArrayList<String> type = new ArrayList<>(Arrays.asList("actions", "crypto")); //liste des types
    private List<WalletInfo> w; //liste des wallet

    /**
     * Initialisation de l'utilisateur
     * @param u
     */
    public void initializeUser(UserInfo u) {
        this.user=u;
        this.entreprise =transaction.getEntreprise();
        this.crypto=transaction.getCrypto();
        this.w=walletModele.getWalletInfo(this.user);
        this.displayTransactionInterface();
        this.initWallet();
        this.initType();
        this.initEntreprise();
        this.initSellActionByWallet();
        this.value.setText("10");
    }

    /**
     * Affichage des actions disponibles pour la vente
     */
    private void initSellActionByWallet() {
        for(WalletInfo w : this.w){
            ArrayList<TransactionInfo> transactionInfos = transaction.getTransactionByWallet(w.getId());
            HashMap sellAction=new HashMap<String,Float>();
            for (TransactionInfo t : transactionInfos){
                Float value = this.historyValue(t,transactionInfos,sellAction);
                sellAction.put(t.getLibelle_type(),value);
            }
            this.sellActionByWalletvalaible.put(w.getNom(),sellAction);
        }
    }

    /**
     * Récupération de l'historique des actions
     */
    private Float historyValue(TransactionInfo t, ArrayList<TransactionInfo> transactionInfos, HashMap sellAction) {
        Float value =0f;
        for(TransactionInfo t2 : transactionInfos){
            if(t2.getLibelle_type().equals(t.getLibelle_type()) && t2.getType().equals(t.getType())){
                value=value+t2.getValue();
            }
        }
        return value;
    }

    /**
     * Affichage de l'interface de transaction
     */
    private void displayTransactionInterface(){
        if(this.w.isEmpty()){
            this.HideTransactionInterface();
            msg_display(Color.RED,"Veulliez ajouter un nouveau wallet");
            this.rezizeTransactionInterface();
        }
        else if(this.user.getFond()==0){
            this.HideTransactionInterface();
            msg_display(Color.RED,"Veuillez ajouter des fonds");
            this.rezizeTransactionInterface();
        }
    }

    /**
     * Masquage de l'interface de transaction
     */
    private void HideTransactionInterface(){
        this.libelle_type.setVisible(false);
        this.value.setVisible(false);
        this.btnRealvalue.setVisible(false);
        this.labelrealvalue.setVisible(false);
        this.wallet.setVisible(false);
        this.listtype.setVisible(false);
        this.acheter.setVisible(false);
        this.vendre.setVisible(false);
        this.walletmsg.setVisible(false);
    }

    /**
     * Affichage dU message d'erreur
     */
    private void rezizeTransactionInterface(){
        msg_error.layoutYProperty().setValue(150);
    }

    /**
     * selection du Type
     */
    public void selectType() {
        String nom = listtype.getValue();
        if(nom.equals("actions")){ //si le type est actions
            libelle_type.getItems().clear();
            this.initEntreprise(); //initialisation des entreprises
        }
        else{
            libelle_type.getItems().clear();
            this.initCrypto(); //initialisation des crypto
        }
    }


    public void displayRealValue(){
        this.realvalueDisplay=!this.realvalueDisplay;
        String nameType =libelle_type.getValue();
        String montant=value.getText();
        Float value = 0f;
        try {
            value=Float.parseFloat(montant);
        }catch (RuntimeException e){
            msg_display(Color.RED,"Veuillez saisir une valeur numérique");
            return;
        }
        TransactionTypeInfo t = this.findTransactionTypeByName(nameType);
        if(this.chekvalue()){
            Float cours = (float) t.getValue();
            this.realvalue.setText(String.valueOf((value/cours)));
            this.labelrealvalue.setVisible(realvalueDisplay);
            this.realvalue.setVisible(realvalueDisplay);
        }
    }

    private TransactionTypeInfo findTransactionTypeByName(String nameType) {
        for(TransactionTypeInfo t : this.entreprise){
            if(t.getName().equals(nameType)){
                return t;
            }
        }
        for(TransactionTypeInfo t : this.crypto){
            if(t.getName().equals(nameType)){
                return t;
            }
        }
        return null;
    }

    private boolean chekvalue(){
        try{
            Float.parseFloat(value.getText());
            return true;
        }catch (RuntimeException e){
            msg_display(Color.RED,"Veuillez saisir une valeur numérique");
            return false;
        }
    }

    /**
     * Affichage des crypto
     */
    private void initCrypto(){
        for(TransactionTypeInfo s : crypto){
            libelle_type.getItems().add(s.getName());
        }
        libelle_type.setValue(crypto.getFirst().getName());
    }

    /**
     * Affichage des entreprises
     */
    private void initEntreprise(){
        for(TransactionTypeInfo s : entreprise){
            libelle_type.getItems().add(s.getName());
        }
        libelle_type.setValue(entreprise.getFirst().getName());
    }

    /**
     * Initialisation du type
     */
    private void initType(){
        listtype.setValue(type.getFirst());
        for(String s : type){
            listtype.getItems().add(s);
        }
    }

    /**
     * Initialisation des wallets
     */
    private void initWallet(){
        if(!this.w.isEmpty()){
            wallet.setValue(this.w.getFirst().getNom());
            for(WalletInfo w :this.w){
                wallet.getItems().add(w.getNom());
            }
        }
    }

    /**
     * Acheter une action
     */
    public void buy(ActionEvent actionEvent) {
        if(!this.chekvalue()){ //si la valeur n'est pas un nombre
            return;
        }
        Float buyValue = Float.parseFloat(value.getText());
        if(this.user.getFond()-buyValue<0){ //si les fonds sont insuffisants
            msg_display(Color.RED,"Fonds insufisants");
            return;
        }
        String nomWallet = wallet.getValue();
        WalletInfo walletInfo = this.findWalletByName(nomWallet);
        String typeInfo = listtype.getValue();
        String libele=libelle_type.getValue();
        Timestamp date = new Timestamp(System.currentTimeMillis());
        this.displayRealValue();
        Float realvalue = Float.parseFloat(this.realvalue.getText());
        TransactionTypeInfo cours = this.findTransactionTypeByName(libele);
        TransactionInfo t = new TransactionInfo(walletInfo.getId(),buyValue,date,typeInfo,libele,realvalue,cours.getValue());
        try {
            if(typeInfo.equals("actions")){ //si le type est actions
                this.walletModele.updateTotaleActions(walletInfo,walletInfo.getTotale_action()+buyValue); //mise à jour du total des actions
            }
            if(typeInfo.equals("crypto")){
                this.walletModele.updateTotaleCrypto(walletInfo,walletInfo.getTotale_crypto()+buyValue); //mise à jour du total des crypto
            }
            this.walletModele.updateTotal(walletInfo,walletInfo.getTotale()+buyValue); //mise à jour du total
            this.userModele.updateFunds(this.user.getId(),this.user.getFond()-buyValue); //mise à jour des fonds
            this.transaction.addTransaction(t); //ajout de la transaction
            msg_display(Color.GREEN,"Transaction effectuée");
        }
        catch (RuntimeException e){
            msg_display(Color.RED,"Erreur lors de la transaction");
        }

    }

    /**
     * Vendre une action
     */
    public void sell(ActionEvent actionEvent) {
        if(!this.chekvalue()){ //si la valeur n'est pas un nombre
            return;
        }
        Float sellValue = Float.parseFloat(value.getText());
        String nomWallet = wallet.getValue();
        WalletInfo walletInfo = this.findWalletByName(nomWallet);
        String typeInfo = listtype.getValue();
        if(!this.checkSellAction(sellValue,nomWallet,typeInfo)){ //si l'action n'est pas disponible
            this.initSellActionCombo();
            String text =this.comboBoxSellAction.getItems().isEmpty()?"Aucune action disponible":"Autres actions disponibles";
            if (!this.comboBoxSellAction.getItems().isEmpty()){
                this.comboBoxSellAction.setVisible(true); //affichage de la liste des actions disponibles
                this.lableSellAction.setText(text);
            }
            this.lableSellAction.setVisible(true);
            this.lableSellAction.setText(text);
            msg_display(Color.RED,"Vous ne pouvez pas vendre cette action"); //affichage d'un message d'erreur
            return;
        }
        if(typeInfo.equals("actions") && walletInfo.getTotale_action()-sellValue<0){ //si les fonds sont insuffisants
            msg_display(Color.RED,"Fonds insufisants sur votre wallet");
            return;
        }
        if(typeInfo.equals("crypto") && walletInfo.getTotale_crypto()-sellValue<0){ //si les fonds sont insuffisants
            msg_display(Color.RED,"Fonds insufisants sur votre wallet");
            return;
        }

        String libele=libelle_type.getValue();
        this.displayRealValue();
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Float realvalue = Float.parseFloat(this.realvalue.getText());
        TransactionTypeInfo cours = this.findTransactionTypeByName(libele);
        TransactionInfo t = new TransactionInfo(walletInfo.getId(),-sellValue,date,typeInfo,libele, realvalue,cours.getValue());
        try {
            if(typeInfo.equals("actions")){
                this.walletModele.updateTotaleActions(walletInfo,walletInfo.getTotale_action()-sellValue);
            }
            if(typeInfo.equals("crypto")){
                this.walletModele.updateTotaleCrypto(walletInfo,walletInfo.getTotale_crypto()-sellValue);
            }
            this.walletModele.updateTotal(walletInfo,walletInfo.getTotale()-sellValue);
            this.userModele.updateFunds(this.user.getId(), this.user.getFond() + sellValue);
            this.transaction.addTransaction(t);
            msg_display(Color.GREEN,"Transaction effectuée");
        }
        catch (RuntimeException e){
            msg_display(Color.RED,"Erreur lors de la transaction");
        }
    }

    /**
     * Affichage des actions disponibles pour la vente
     */
    private void initSellActionCombo() {
        this.comboBoxSellAction.getItems().clear();
        String nomWallet = wallet.getValue();
        HashMap<String,Float> sellAction = this.sellActionByWalletvalaible.get(nomWallet);
        for (Map.Entry<String, Float> entry : sellAction.entrySet()) {
            String key = entry.getKey();
            Float value = entry.getValue();
            if(value>0){
                this.comboBoxSellAction.getItems().add(key+" : "+value+"€");
            }
        }
        this.comboBoxSellAction.setValue("Vos actions");
    }

    /**
     * Vérification si l'action est disponible
     */
    private boolean checkSellAction(Float sellValue, String nomWallet, String typeInfo) {
        HashMap<String,Float> sellAction = this.sellActionByWalletvalaible.get(nomWallet);
        Float value = sellAction.get(libelle_type.getValue());
        if(value==null){
            return false;
        }
        if(value<sellValue){
            return false;
        }
        return true;
    }

    /**
     * Recupération du wallet par son nom
     */
    public WalletInfo findWalletByName(String name){
        for (WalletInfo w :this.w){
            if(w.getNom().equals(name)){
                return w;
            }
        }
        return null;
    }

    /**
     * Affichage des messages d'erreur
     */
    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color);
        msg_error.setText(msg);
    }
}
