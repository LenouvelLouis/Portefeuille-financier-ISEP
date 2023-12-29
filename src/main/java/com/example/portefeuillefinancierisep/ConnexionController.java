package com.example.portefeuillefinancierisep;

import Modele.UserModele;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ConnexionController {

    private UserModele user = new UserModele();

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label msg_error;

    @FXML
    private void connexionButtonClick() {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        // Récupérer le sel associé à l'utilisateur (à implémenter dans UserModele)
        String userSalt = user.getUserSalt(email);
        String hashedPassword = hashPassword(password, userSalt);

        if (user.checkUserPassword(email, hashedPassword)) {
            msg_error.setTextFill(Color.GREEN);
            msg_error.setText("Connexion réussie.");
            // Ici, vous pouvez ajouter des actions supplémentaires en cas de connexion réussie
        } else {
            msg_error.setTextFill(Color.RED);
            msg_error.setText("Échec de la connexion. Vérifiez vos identifiants.");
            // Ici, vous pouvez ajouter des actions supplémentaires en cas d'échec de connexion
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
