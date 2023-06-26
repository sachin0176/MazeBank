package com.jmc.mazebank.controller.admin;

import com.jmc.mazebank.models.Models;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public BorderPane admin_parent;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Models.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
           switch (newValue){
               case CLIENTS -> admin_parent.setCenter(Models.getInstance().getViewFactory().getClientsView());
               case DEPOSIT -> admin_parent.setCenter(Models.getInstance().getViewFactory().getDepositView());
               case FEEDBACK -> admin_parent.setCenter(Models.getInstance().getViewFactory().getFeedbackView());
               default -> admin_parent.setCenter(Models.getInstance().getViewFactory().getCreateClientView());
           }

        });
    }
}
