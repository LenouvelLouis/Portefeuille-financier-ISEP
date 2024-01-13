package Info;

public class UserInfo {

    private int id;
    private String nom;
    private String prenom;
    private String tel;
    private String mail;
    private String h_mdp; // Mot de passe haché
    private String salt; // Sel sous forme de chaîne de caractères

    private Float fond;

    public Float getFond() {
        return fond;
    }

    public int getId() {
        return id;
    }

    public UserInfo(int id, String nom, String prenom, String tel, String mail, String h_mdp, String salt,Float f) {
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.mail = mail;
        this.h_mdp = h_mdp;
        this.salt = salt;
        this.fond=f;
    }
    public UserInfo(String nom, String prenom, String tel, String mail, String h_mdp, String salt) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.mail = mail;
        this.h_mdp = h_mdp;
        this.salt = salt;
    }

    // Getters
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getTel() { return tel; }
    public String getMail() { return mail; }
    public String getH_mdp() { return h_mdp; }
    public String getSalt() { return salt; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setTel(String tel) { this.tel = tel; }
    public void setMail(String mail) { this.mail = mail; }
    public void setH_mdp(String h_mdp) { this.h_mdp = h_mdp; }
    public void setSalt(String salt) { this.salt = salt; }

    public void setFond(float amount) {
        this.fond = amount;
    }
}
