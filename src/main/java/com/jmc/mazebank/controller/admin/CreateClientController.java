package com.jmc.mazebank.controller.admin;

import com.jmc.mazebank.models.Models;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class CreateClientController  implements Initializable {
    public TextField fname_fld;
    public TextField lname_fld;
    public TextField password_fld;
    public CheckBox payee_add_box;
    public Label payee_add_lbl;
    public CheckBox ch_acc_box;
    public TextField ch_balance_fld;
    public CheckBox sv_acc_box;
    public TextField sv_balance_fld;
    public Button create_new_client_btn;
    public Label error_lbl;

    private String payeeAddress;
    private boolean createCheckingAccountFlag=false;
    private boolean createSavingsAccountFlag=false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

       setTextFormatter();

        create_new_client_btn.setOnAction(event -> createClient());

        payee_add_box.selectedProperty().addListener((observable, oldValue, newValue) ->
        {
            if(newValue)
            {
                payeeAddress=createPayeeAddress();
                onCreatePayeeAddress();
            }
        });

        ch_acc_box.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue)
                createCheckingAccountFlag=true;
        });

        sv_acc_box.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue)
                createSavingsAccountFlag=true;
        });
    }


    private  void  createClient()
    {
        if(createCheckingAccountFlag)
        {
            createAccounts("Checking");
        }
        if(createSavingsAccountFlag) {
            createAccounts("Saving");
        }
        if(!fname_fld.getText().isEmpty()&& !lname_fld.getText().isEmpty()&& !password_fld.getText().isEmpty()) {
            String fName = fname_fld.getText();
            String lName = lname_fld.getText();
            String password = password_fld.getText();
            Models.getInstance().getDatabaseDriver().createClient(fName, lName, payeeAddress, password, LocalDate.now());
            error_lbl.setStyle("-fx-text-fill:blue;-fx-font-size:1.5em;-fx-font-weight:bold;");
            error_lbl.setText("Client Created Successfully");
        }
        else {
            error_lbl.setText("Unsuccessful");
        }

        emptyField();
    }
    private  void createAccounts(String accountType)
    {

        // generate account number
        String firstSection ="9113";
        String lastSection=Integer.toString((new Random()).nextInt(9911) + 1000);

        String accountNumber =firstSection+" "+lastSection;
        //create a checking account
        if(accountType.equals("Checking"))
        {double balance=Double.parseDouble(ch_balance_fld.getText());
            Models.getInstance().getDatabaseDriver().createCheckingAccount(payeeAddress,accountNumber,balance,10);
        }
        else {
            double balance=Double.parseDouble(sv_balance_fld.getText());
            Models.getInstance().getDatabaseDriver().createSavingsAccounts(payeeAddress,accountNumber,balance,2000);

        }


    }

    private  void onCreatePayeeAddress()
    {
            payee_add_lbl.setText(payeeAddress);
    }

    private  String  createPayeeAddress()
    {int id;
        char fchar;
        if(!fname_fld.getText().isEmpty()&& !lname_fld.getText().isEmpty()) {
         id = Models.getInstance().getDatabaseDriver().getLastClientsId() + 1;
         fchar = fname_fld.getText().toLowerCase().charAt(0);
            return "@"+fchar+lname_fld.getText()+id;
    }
        return null;
    }
    private  void emptyField()
    {
        fname_fld.setText("");
        lname_fld.setText("");
        password_fld.setText("");
        payee_add_box.setSelected(false);
        payee_add_lbl.setText("");
        ch_acc_box.setSelected(false);
        ch_balance_fld.setText("");
        sv_acc_box.setSelected(false);
        sv_balance_fld.setText("");




    }

    //balance_fld only for double value
    private void setTextFormatter(){

        UnaryOperator<TextFormatter.Change> filter = t -> {

            if (t.isReplaced())
                if(t.getText().matches("[^0-9]"))
                    t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));


            if (t.isAdded()) {
                if (t.getControlText().contains(".")) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText("");
                    }
                } else if (t.getText().matches("[^0-9.]")) {
                    t.setText("");
                }
            }

            return t;
        };
        sv_balance_fld.setTextFormatter(new TextFormatter<>(filter));
        ch_balance_fld.setTextFormatter(new TextFormatter<>(filter));

    }
}
