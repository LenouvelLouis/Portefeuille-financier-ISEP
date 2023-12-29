package com.example.portefeuillefinancierisep;

import Modele.UserModele;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class AuthController {

    private UserModele user =new UserModele();

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

    @FXML
    Label msg_error;
    @FXML
    TextField email_text;
    @FXML
    TextField mdp_text;

    String email, mdp;


    @FXML
    protected void ConnexionButtonClick() throws SQLException {
            mdp = mdp_text.getText();
            email = email_text.getText();
            if (user.is_login_valid(email, mdp)) {
                msg_error.setText("Connexion ok");
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(this.getClass().getResource("auth-view.fxml"));
                    Scene scene = new Scene(loader.load());
                    Stage stage = new Stage();
                    stage.setTitle("Connexion");
                    stage.setScene(scene);
                    stage.show();
                    stage.setResizable(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                msg_error.setText("Votre email ou mot de passe est incorect");
            }

        }

    }
