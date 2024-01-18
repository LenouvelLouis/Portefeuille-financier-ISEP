package Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Class qui permet de vérifier le format des données saisies par l'utilisateur
 */
public class CheckDataService {


    /**
     * Méthode permettant de vérifier le format de l'adresse mail
     * @return Vrai si le format est valide, faux sinon
     */
    public boolean isValidFormatEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; // Expression régulière
        Pattern pattern = Pattern.compile(regex); // Création du pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Méthode permettant de vérifier le format du nom et du prénom
     * @return Vrai si le format est valide, faux sinon
     */
    public boolean isValidFormatNomPrenom(String nom, String prenom) {
        String regex = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher_nom = pattern.matcher(nom);
        Matcher matcher_prenom = pattern.matcher(prenom);
        return matcher_nom.matches() && matcher_prenom.matches(); // Vérification du format du nom et du prénom
    }


    /**
     * Méthode permettant de vérifier le format du numéro de téléphone
     * @return Vrai si le format est valide, faux sinon
     */
    public boolean isValidPhoneNumber(String tel) {
        String regex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tel);
        return matcher.matches();
    }
}
