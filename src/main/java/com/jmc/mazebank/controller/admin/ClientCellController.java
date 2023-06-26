package com.jmc.mazebank.controller.admin;

import com.jmc.mazebank.models.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {
    public Label fName_lbl;
    public Label lname_lbl;
    public Label pAddress_lbl;
    public Label ch_acc_lbl;
    public Label sv_acc_lbl;
    public Label dt_lbl;
    public  Button dlt_btn;

    private final Client client;

    public ClientCellController(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fName_lbl.textProperty().bind(client.firstNameProperty());
        lname_lbl.textProperty().bind(client.lastNameProperty());
        pAddress_lbl.textProperty().bind(client.pAddressProperty());
        ch_acc_lbl.textProperty().bind(client.getCheckingAccount().asString());
        dt_lbl.textProperty().bind(client.getDateCreated().asString());
        sv_acc_lbl.textProperty().bind(client.getSavingAccount().asString());


    }

}
