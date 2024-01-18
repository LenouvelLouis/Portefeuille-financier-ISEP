package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import Modele.UserModele;
import Service.CheckDataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InscriptionController {

    private UserModele user = new UserModele(); // Modèle de la table user

    @FXML
    TextField email_text;
    @FXML
    TextField nom_text;
    @FXML
    TextField prenom_text;
    @FXML
    TextField password_text;
    @FXML
    TextField tel_text;
    @FXML
    Label msg_error;

    private CheckDataService checkDataService = new CheckDataService(); // Service de vérification des données saisies

    String email, nom, prenom, password, tel; // Variables pour stocker les données saisies

    /**
     * Méthode appelée lors du clic sur le bouton "Se connecter"
     * @throws IOException
     */
    @FXML
    protected void SeConnecterButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("auth-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Connexion");
        stage.setScene(scene);
        ((Stage) this.email_text.getScene().getWindow()).close();
        stage.show();
        stage.setResizable(false);
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Créer un compte"
     * @throws IOException
     */
    @FXML
    protected void CreationCompteButtonClick() throws IOException {
        email = email_text.getText();
        nom = nom_text.getText(); // Récupération des données saisies
        prenom = prenom_text.getText();
        password = password_text.getText();
        tel = tel_text.getText();

        if (!isChampNotEmpty()) { // Vérification que tous les champs sont remplis
            msg_display(Color.RED,"Veuillez saisir tous les champs");
            return;
        }

        if (!checkDataService.isValidFormatEmail(email)) { // Vérification du format de l'adresse mail
            msg_display(Color.RED,"Adresse mail non valide");
            return;
        }

        if (!checkDataService.isValidFormatNomPrenom(nom,prenom)) { // Vérification du format du nom et du prénom
            msg_display(Color.RED,"Nom ou prénom non valide");
            return;
        }

        if (!checkDataService.isValidPhoneNumber(tel)) { // Vérification du format du numéro de téléphone
            msg_display(Color.RED,"Numéro de téléphone non valide");
            return;
        }

        if (user.is_user_create(email)) { // Vérification que le compte n'existe pas déjà
            msg_display(Color.RED,"Ce compte existe déjà");
            return;
        }

        // Génération du sel
        String salt = generateSalt(7);

        // Hachage du mot de passe avec le sel
        password = hashPassword(password, salt);

        user.create_user(new UserInfo(nom, prenom, tel, email, password, salt)); // Création du compte
        msg_display(Color.GREEN,"Votre compte a bien été créé");
        clearFormFields(); // Réinitialisation des champs
        this.SeConnecterButtonClick(); // Redirection vers la page de connexion
    }

    /**
     * Méthode permettant de hacher le mot de passe avec le sel
     * @param passwordToHash Mot de passe à hacher
     * @param salt Sel
     * @return Mot de passe haché
     */
    private String hashPassword(String passwordToHash, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Algorithme de hachage
            byte[] hash = digest.digest((salt + passwordToHash).getBytes()); // Hachage du mot de passe avec le sel
            return Base64.getEncoder().encodeToString(hash); // Encodage du mot de passe haché
        } catch (NoSuchAlgorithmException e) { // Exception
            throw new RuntimeException("Erreur lors du hachage du mot de passe", e); // Affichage de l'erreur
        }
    }

    /**
     * Méthode permettant de générer un sel
     * @param length Longueur du sel
     * @return Sel
     */
    private String generateSalt(int length) {
        SecureRandom random = new SecureRandom(); // Générateur de nombre aléatoire
        byte[] salt = new byte[length]; // Tableau de bytes
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt).substring(0, length); // Encodage du sel
    }

    /**
     * Méthode permettant de vérifier que tous les champs sont remplis
     * @return Vrai si tous les champs sont remplis, faux sinon
     */
    public boolean isChampNotEmpty() {
        return !email.isEmpty() && !nom.isEmpty() && !prenom.isEmpty() && !password.isEmpty() && !tel.isEmpty();
    }

    /**
     * Méthode permettant de réinitialiser les champs du formulaire
     */
    private void clearFormFields() {
        email_text.clear();
        nom_text.clear();
        prenom_text.clear();
        password_text.clear();
        tel_text.clear();
    }

    /**
     * Méthode permettant d'afficher un message d'erreur
     * @param color Couleur du message
     * @param msg Message à afficher
     */
    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color);
        msg_error.setText(msg);
    }
}
