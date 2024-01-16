package com.example.portefeuillefinancierisep;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {

    @FXML
    Button auth;

    /**
     * Méthode qui permet de changer de fenêtre pour aller sur la page de connexion
     */
    @FXML
    protected void ConnexionButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("auth-view.fxml")); // On récupère le fichier FXML
            Scene scene = new Scene(loader.load()); // On crée une nouvelle scène
            Stage stage = new Stage(); // On crée une nouvelle fenêtre
            stage.setTitle("Connexion");
            stage.setScene(scene);
            ((Stage) this.auth.getScene().getWindow()).close(); // On ferme la fenêtre actuelle
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Méthode qui permet de changer de fenêtre pour aller sur la page d'inscription
     */
    @FXML
    protected void InscriptionButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("inscription-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Inscription");
            stage.setScene(scene);
            ((Stage) this.auth.getScene().getWindow()).close();
            stage.show(); // On affiche la nouvelle fenêtre
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
