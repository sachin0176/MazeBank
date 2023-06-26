package com.jmc.mazebank.controller;

import com.jmc.mazebank.models.Models;
import com.jmc.mazebank.views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;
    public Label payee_address_lbl;
    public TextField payee_address_fld;
    public PasswordField password_fld;
    public Button login_btn;
    public Label error_lbl;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.ADMIN,AccountType.CLIENT));
        acc_selector.setValue(Models.getInstance().getViewFactory().getLoginAccountType());

        acc_selector.valueProperty().addListener(observable -> setAcc_selector());
        login_btn.setOnAction(event ->onLogin());
    }

    public void onLogin()
    {
        Stage stage=(Stage)error_lbl.getScene().getWindow();

       if(Models.getInstance().getViewFactory().getLoginAccountType()==AccountType.CLIENT) {
           //Evaluate Client Login Credentials

           Models.getInstance().evaluateClientCred(payee_address_fld.getText(), password_fld.getText());

           if (Models.getInstance().isClientLoginSuccessFlag()) {
               Models.getInstance().getViewFactory().showClientWindow();
               Models.getInstance().getLatestTransactions().clear();
               Models.getInstance().getAllTransactions().clear();
               if(Models.getInstance().getLatestTransactions().isEmpty())
               {
                   Models.getInstance().setLatestTransactions();
               }
               if(Models.getInstance().getAllTransactions().isEmpty())
               {
                   Models.getInstance().setAllTransactions();
               }
               //Close the Login Stage
               Models.getInstance().getViewFactory().closeStage(stage);
           }
           else{
               payee_address_fld.setText("");
               password_fld.setText("");
               error_lbl.setText("No Such Login Credentials");
           }

       }
       else
       {//Evaluate Admin Login Credentials
        Models.getInstance().evaluateAdminCred(payee_address_fld.getText(),password_fld.getText());
        if(Models.getInstance().isAdminLoginSuccessFlag())
        {
            Models.getInstance().getViewFactory().showAdminWindow();
            //close the login stage
            Models.getInstance().getViewFactory().closeStage(stage);
        }
        else{
            payee_address_fld.setText("");
            password_fld.setText("");
            error_lbl.setText("No Such Login Credentials");
        }
       }

    }

    public void setAcc_selector() {
        Models.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue());
        if (acc_selector.getValue() == AccountType.ADMIN) {
            payee_address_lbl.setText("Username:");

        }
        else {
            payee_address_lbl.setText("Payee Address:");

        }
    }
}
