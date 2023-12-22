package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import Modele.UserModele;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InscriptionController {

    private UserModele user =new UserModele();

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

    String email, nom, prenom, password,tel;

    @FXML
    protected void SeConnecterButtonClick() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("auth-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }
    @FXML
    protected void CreationCompteButtonClick() throws SQLException {

        email = email_text.getText();
        nom = nom_text.getText();
        prenom = prenom_text.getText();
        password = password_text.getText();
        tel = tel_text.getText();

        if(!isChampNotEmpty())
        {
            msg_error.setTextFill(Color.RED);
            msg_error.setText("Veuillez saisir tout les champs");
            return;
        }

        if(!isValidFormatEmail())
        {
            msg_error.setTextFill(Color.RED);
            msg_error.setText("Adresse mail non valide");
            return;
        }

        if(!isValidFormatNomPrenom())
        {
            msg_error.setTextFill(Color.RED);
            msg_error.setText("Nom ou prénom non valide");
            return;
        }

        if(!isValidPhoneNumber())
        {
            msg_error.setTextFill(Color.RED);
            msg_error.setText("Numéros de téléphone non valide");
            return;
        }

        if(user.is_user_create(email))
        {
            msg_error.setTextFill(Color.RED);
            msg_error.setText("Ce compte exixste déja");
            return;
        }

        user.create_user(new UserInfo(nom,prenom,tel,email,password));
        msg_error.setTextFill(Color.GREEN);
        msg_error.setText("Votre compte a bien été créer");

        email_text.clear();
        nom_text.clear();
        prenom_text.clear();
        password_text.clear();
        tel_text.clear();

    }

    public boolean isValidFormatEmail()
    {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidFormatNomPrenom()
    {
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

    public boolean isChampNotEmpty()
    {
        return !email.isEmpty() && !nom.isEmpty() && !prenom.isEmpty() && !password.isEmpty() && !tel.isEmpty();
    }
}
