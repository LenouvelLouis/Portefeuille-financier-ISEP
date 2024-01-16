package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Modele.UserModele;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class FundsController {

    @FXML
    private TextField fundsField;

    @FXML
    private Label msg_error;

    private UserModele userModel = new UserModele();
    private UserInfo u;
    private BarreNavigationController barreNavigationController;

    /**
     * Ajout des fonds au portefeuille
     */
    @FXML
    protected void handleAddFunds() {
        try {
            float amount = this.u.getFond()+Float.parseFloat(fundsField.getText()); // On récupère le montant à ajouter
            int userId = this.u.getId(); // On récupère l'id de l'utilisateur
            userModel.updateFunds(userId, amount); // On met à jour les fonds de l'utilisateur
            this.u.setFond(amount); // On met à jour les fonds dans l'objet UserInfo
            barreNavigationController.initializeUser(this.u); // On met à jour les fonds dans la barre de navigation
            msg_display(Color.GREEN,"Fonds ajoutés : " + fundsField.getText() + " €"); // On affiche un message de succès
            fundsField.clear(); // On vide le champ de saisie
        } catch (NumberFormatException e) { // Si le montant n'est pas valide
            msg_display(Color.RED,"Veuillez entrer un montant valide");
        } catch (Exception e) { // Si une erreur est survenue
            msg_display(Color.RED,"Erreur lors de la mise à jour des fonds");
        }
    }

    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color);
        msg_error.setText(msg);
    }

    public void initializeUser(UserInfo u, BarreNavigationController barreNavigationController) {
        this.u=u;
        this.barreNavigationController=barreNavigationController;
    }
}
