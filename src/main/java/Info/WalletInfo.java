package Info;

import java.util.ArrayList;

/**
 * Class qui permet de stocker les informations d'un utilisateur
 */
public class WalletInfo {

    private int id;
    private String nom;

    public int getId() {
        return id;
    }

    private int id_user;

    private float totale;

    private float totale_action;

    private ArrayList<TransactionInfo> transactionInfos;

    public String getNom() {
        return nom;
    }


    public float getTotale() {
        return totale;
    }

    public float getTotale_action() {
        return totale_action;
    }

    public float getTotale_crypto() {
        return totale_crypto;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    public void setTotale_action(float totale_action) {
        this.totale_action = totale_action;
    }

    public void setTotale_crypto(float totale_crypto) {
        this.totale_crypto = totale_crypto;
    }

    private float totale_crypto;

    public WalletInfo(int id,String nom, int id_user, float totale, float totale_action, float totale_crypto) {
        this.id=id;
        this.nom = nom;
        this.id_user = id_user;
        this.totale = totale;
        this.totale_action = totale_action;
        this.totale_crypto = totale_crypto;
    }

    public void setTransaction(ArrayList<TransactionInfo> transactionByWallet) {
        this.transactionInfos = transactionByWallet;
    }

    public ArrayList<TransactionInfo> getTransaction() {
        return transactionInfos;
    }
}
