package Info;

public class WalletInfo {
    private String nom;

    private String mail_user;

    private float totale;

    private float totale_action;

    @Override
    public String toString() {
        return "WalletInfo{" +
                "nom='" + nom + '\'' +
                ", mail_user='" + mail_user + '\'' +
                ", totale=" + totale +
                ", totale_action=" + totale_action +
                ", totale_crypto=" + totale_crypto +
                '}';
    }

    public String getNom() {
        return nom;
    }

    public String getMail_user() {
        return mail_user;
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

    private float totale_crypto;

    public WalletInfo(String nom, String mail_user, float totale, float totale_action, float totale_crypto) {
        this.nom = nom;
        this.mail_user = mail_user;
        this.totale = totale;
        this.totale_action = totale_action;
        this.totale_crypto = totale_crypto;
    }
}
