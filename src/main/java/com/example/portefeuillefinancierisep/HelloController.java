package com.example.portefeuillefinancierisep;

import Modele.UserModele;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;


public class HelloController {
    @FXML
    private Label welcomeText;

    //Il faudra déclarer les modele sql de cette manière dès qu'on veut les utiliser
    private UserModele u =new UserModele();



    @FXML
    protected void onHelloButtonClick() throws SQLException {
        welcomeText.setText("Welcome to JavaFX Application!");
        //exemple d'appel au fichier modele qui fera appel à la base de données
        u.getAllUsers();
    }




}