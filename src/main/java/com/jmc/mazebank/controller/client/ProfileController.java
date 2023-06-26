package com.jmc.mazebank.controller.client;

import com.jmc.mazebank.models.Models;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public Label fName_lbl;
    public Label lName_lbl;
    public Label pAddress_lbl;
    public Label sv_acc_num;
    public Label sv_wl;
    public Label sv_dc;
    public Label ch_acc_lbl;
    public Label ch_trans_l;
    public Label ch_dc;
    public Button change_psw_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    bindData();
    onClick();
    }

    private void bindData()
    {
        fName_lbl.textProperty().bind(Models.getInstance().getClient().firstNameProperty());
        lName_lbl.textProperty().bind(Models.getInstance().getClient().lastNameProperty());
        pAddress_lbl.textProperty().bind(Models.getInstance().getClient().pAddressProperty());
        sv_acc_num.textProperty().bind(Models.getInstance().getClient().getSavingAccount().get().getAccountNumber());
        sv_wl.textProperty().bind(Models.getInstance().getClient().getSavingAccount().get().getWithdrawalLimi().asString());
        sv_dc.textProperty().bind(Models.getInstance().getClient().getDateCreated().asString());
        ch_acc_lbl.textProperty().bind(Models.getInstance().getClient().getCheckingAccount().get().getAccountNumber());
        ch_trans_l.textProperty().bind(Models.getInstance().getClient().getCheckingAccount().get().getTransactionLimit().asString());
        ch_dc.textProperty().bind(Models.getInstance().getClient().getDateCreated().asString());


    }

    private  void onClick()
    {

        change_psw_btn.setOnAction(event ->Models.getInstance().getViewFactory().getPasswordResetView()
           );


    }
}


