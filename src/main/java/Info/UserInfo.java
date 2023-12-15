package Info;

public class UserInfo {
    String nom,prenom,tel,mail,h_mdp;

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTel() {
        return tel;
    }

    public String getMail() {
        return mail;
    }

    public String getH_mdp() {
        return h_mdp;
    }

    public UserInfo(String nom, String prenom, String tel, String mail, String h_mdp){
        this.nom=nom;
        this.prenom=prenom;
        this.tel=tel;
        this.mail=mail;
        this.h_mdp=h_mdp;
    }
}
