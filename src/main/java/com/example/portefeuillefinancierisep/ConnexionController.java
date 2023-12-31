package com.example.portefeuillefinancierisep;

import Modele.UserModele;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ConnexionController {

    private UserModele user = new UserModele();

    @FXML
    private TextField email_text;
    @FXML
    private TextField mdp_text;
    @FXML
    private Label msg_error;

    @FXML
    private void ConnexionButtonClick() throws IOException {
        String email = email_text.getText();
        String password = mdp_text.getText();
        String userSalt = user.getUserSalt(email);
        String hashedPassword = hashPassword(password, userSalt);
        try{
        if (user.checkUserPassword(email, hashedPassword)) {
            msg_error.setTextFill(Color.GREEN);
            msg_error.setText("Connexion réussie.");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("home-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(scene);
            ((Stage) this.email_text.getScene().getWindow()).close();
            stage.show();
            stage.setResizable(false);
        } else {
            msg_error.setTextFill(Color.RED);
            msg_error.setText("Échec de la connexion. Vérifiez vos identifiants.");
        }} catch (RuntimeException e) {
            msg_error.setText("Erreur de connexion avec la base de données");        }
    }
    @FXML
    protected void InscriptionButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("inscription-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Inscription");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
