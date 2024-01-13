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

    @FXML
    protected void handleAddFunds() {
        try {
            float amount = this.u.getFond()+Float.parseFloat(fundsField.getText());
            int userId = this.u.getId();
            userModel.updateFunds(userId, amount);
            this.u.setFond(amount);
            BarreNavigationController barreNavigationController = new BarreNavigationController();
            barreNavigationController.initializeUser(this.u);
            msg_display(Color.GREEN,"Fonds ajoutés : " + amount + " €");
        } catch (NumberFormatException e) {
            msg_display(Color.RED,"Veuillez entrer un montant valide");
        } catch (Exception e) {
            msg_display(Color.RED,"Erreur lors de la mise à jour des fonds");
        }
    }

    private void msg_display(Paint color, String msg)
    {
        msg_error.setTextFill(color);
        msg_error.setText(msg);
    }

    public void initializeUser(UserInfo u) {
        this.u=u;
    }
}
