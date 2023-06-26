package com.jmc.mazebank.controller.client;

import com.jmc.mazebank.models.Models;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;

import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class AccountsController implements Initializable {
    public Label ch_acc_num;
    public Label ch_acc_trn_lm;
    public Label ch_acc_dt;
    public Label ch_acc_bal;
    public Label sv_acc_num;
    public Label sv_acc_wt;
    public Label sv_acc_dt;
    public Label sv_acc_bal;
    public TextField amount_to_ch_fld;
    public Button trans_to_sv_btn;
    public TextField amount_to_sv_fld;
    public Button trans_to_ch_btn;
    public Label err_lbl;
    public TextArea mess_fld;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    bindSavingAccount();
    bindCheckingAccount();
    onSendMoney();
    setTextFormatter();

    }

    private void onSendMoney()
    {
        trans_to_ch_btn.setOnAction(event ->{ onSendMoneySAtoCA();
        updateTransaction();
        });
        trans_to_sv_btn.setOnAction(event ->{ onSendMoneyCAtoSA();
        updateTransaction();
        }
        );
    }


    private  void bindSavingAccount(){

       sv_acc_num.textProperty().bind(Models.getInstance().getClient().getSavingAccount().get().getAccountNumber());
       sv_acc_bal.textProperty().bind(Models.getInstance().getClient().getSavingAccount().get().getBalance().asString());
        sv_acc_dt.textProperty().bind(Models.getInstance().getClient().getDateCreated().asString());
        sv_acc_wt.textProperty().bind(Models.getInstance().getClient().getSavingAccount().get().getWithdrawalLimi().asString());

    }

    private void bindCheckingAccount(){
     ch_acc_num.textProperty().bind(Models.getInstance().getClient().getCheckingAccount().get().getAccountNumber());
    ch_acc_dt.textProperty().bind(Models.getInstance().getClient().getDateCreated().asString());
    ch_acc_bal.textProperty().bind(Models.getInstance().getClient().getCheckingAccount().get().getBalance().asString());
        ch_acc_trn_lm.textProperty().bind(Models.getInstance().getClient().getCheckingAccount().get().getTransactionLimit().asString());

    }

    // Send money from saving account to checking account
    private void onSendMoneySAtoCA(){
        double amount,svBalance=Models.getInstance().getClient().getSavingAccount().get().getBalance().get();
            if(!amount_to_sv_fld.getText().isEmpty()) {
                amount = Double.parseDouble(amount_to_sv_fld.getText());
                if(svBalance>=amount) {
                    String sender = Models.getInstance().getClient().pAddressProperty().get();
                    Models.getInstance().getDatabaseDriver().updateSavingCheckingBalance(sender, amount, "SUB");
                    Models.getInstance().getDatabaseDriver().updateCheckingAccountBalance(sender, amount, "ADD");
                    Models.getInstance().getClient().getSavingAccount().get().setBalance(Models.getInstance().getDatabaseDriver().getSavingAccountBalance(sender));
                    Models.getInstance().getClient().getCheckingAccount().get().setBalance(Models.getInstance().getDatabaseDriver().getCheckingAccountBalance(sender));
                    amount_to_sv_fld.setText("");
                    err_lbl.setStyle("-fx-text-fill:#0000EE");
                    err_lbl.setText("Successful");
                    String message=mess_fld.getText();
                    Models.getInstance().getDatabaseDriver().newTransaction(sender,"Checking Account",amount,message);
                }
                else {
                    err_lbl.setText("Insufficient Balance");
                }
            }
            else
                err_lbl.setText("Enter Amount");

    }

    //Send money from checking to saving account
    private void onSendMoneyCAtoSA(){
        double amount,chBalance=Models.getInstance().getClient().getCheckingAccount().get().getBalance().get();
        if(!amount_to_ch_fld.getText().isEmpty()) {
            amount = Double.parseDouble(amount_to_ch_fld.getText());
            if(chBalance>=amount) {
                String sender = Models.getInstance().getClient().pAddressProperty().get();
                Models.getInstance().getDatabaseDriver().updateCheckingAccountBalance(sender, amount, "SUB");
                Models.getInstance().getDatabaseDriver().updateSavingCheckingBalance(sender, amount, "ADD");
                Models.getInstance().getClient().getSavingAccount().get().setBalance(Models.getInstance().getDatabaseDriver().getSavingAccountBalance(sender));
                Models.getInstance().getClient().getCheckingAccount().get().setBalance(Models.getInstance().getDatabaseDriver().getCheckingAccountBalance(sender));
                amount_to_ch_fld.setText("");
                err_lbl.setStyle("-fx-text-fill:#0000EE");
                err_lbl.setText("Successful");
                String message=mess_fld.getText();
                Models.getInstance().getDatabaseDriver().newTransaction(sender,"Saving Account",amount,message);
                mess_fld.setText("");
            }
            else {
                err_lbl.setText("Insufficient Balance");
            }

        }
        else
            err_lbl.setText("Enter Amount");

    }

    //Transaction history update
    private  void updateTransaction()
    {
        Models.getInstance().getAllTransactions().clear();
        Models.getInstance().getLatestTransactions().clear();
        if(Models.getInstance().getAllTransactions().isEmpty()&&Models.getInstance().getLatestTransactions().isEmpty())
        {
            Models.getInstance().setLatestTransactions();
            Models.getInstance().setAllTransactions();
        }
    }

   // balance fld only for double value
    private  void setTextFormatter()
    {
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

        amount_to_ch_fld.setTextFormatter(new TextFormatter<>(filter));
        amount_to_sv_fld.setTextFormatter(new TextFormatter<>(filter));

    }

}
