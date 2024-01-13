package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import Modele.UserModele;
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

    private UserModele user = new UserModele();

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

    String email, nom, prenom, password, tel;

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

    @FXML
    protected void CreationCompteButtonClick() throws IOException {
        email = email_text.getText();
        nom = nom_text.getText();
        prenom = prenom_text.getText();
        password = password_text.getText();
        tel = tel_text.getText();

        if (!isChampNotEmpty()) {
            msg_display(Color.RED,"Veuillez saisir tous les champs");
            return;
        }

        if (!isValidFormatEmail()) {
            msg_display(Color.RED,"Adresse mail non valide");
            return;
        }

        if (!isValidFormatNomPrenom()) {
            msg_display(Color.RED,"Nom ou prénom non valide");
            return;
        }

        if (!isValidPhoneNumber()) {
            msg_display(Color.RED,"Numéro de téléphone non valide");
            return;
        }

        if (user.is_user_create(email)) {
            msg_display(Color.RED,"Ce compte existe déjà");
            return;
        }

        // Génération du sel
        String salt = generateSalt(7);

        // Hachage du mot de passe avec le sel
        password = hashPassword(password, salt);

        user.create_user(new UserInfo(nom, prenom, tel, email, password, salt));
        msg_display(Color.GREEN,"Votre compte a bien été créé");
        clearFormFields();
        this.SeConnecterButtonClick();
    }

    private String hashPassword(String passwordToHash, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((salt + passwordToHash).getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du hachage du mot de passe", e);
        }
    }

    private String generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt).substring(0, length);
    }

    public boolean isValidFormatEmail() {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidFormatNomPrenom() {
        String regex = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher_nom = pattern.matcher(nom);
        Matcher matcher_prenom = pattern.matcher(prenom);
        return matcher_nom.matches() && matcher_prenom.matches();
    }

    public boolean isValidPhoneNumber() {
        String regex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tel);
        return matcher.matches();
    }

    public boolean isChampNotEmpty() {
        return !email.isEmpty() && !nom.isEmpty() && !prenom.isEmpty() && !password.isEmpty() && !tel.isEmpty();
    }

    private void clearFormFields() {
        email_text.clear();
        nom_text.clear();
        prenom_text.clear();
        password_text.clear();
        tel_text.clear();
    }

    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color);
        msg_error.setText(msg);
    }
}
