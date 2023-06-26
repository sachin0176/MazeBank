package com.jmc.mazebank.controller.client;

import com.jmc.mazebank.models.Models;
import javafx.fxml.Initializable;

import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController  implements Initializable {
public BorderPane client_parent;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Models.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case TRANSACTIONS -> client_parent.setCenter(Models.getInstance().getViewFactory().getTransactionView());
                case ACCOUNTS -> client_parent.setCenter(Models.getInstance().getViewFactory().getAccountview());
                case PROFILE -> client_parent.setCenter(Models.getInstance().getViewFactory().getProfileView());
                default -> client_parent.setCenter(Models.getInstance().getViewFactory().getDashboardview());
            }
        });
    }
}
