package com.jmc.mazebank.controller.client;

import com.jmc.mazebank.models.Models;
import com.jmc.mazebank.models.Transaction;
import com.jmc.mazebank.views.TransactionCellFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label saving_bal;
    public Label saving_acc_num;
    public Label income_lbl;
    public Label expenses_lbl;
    public ListView<Transaction> transaction_listview;
    public TextField payee_fld;
    public TextField  amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;
    public Label err_lbl;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindData();
        setTextFormatter();
        initLatestTransactionsList();
        transaction_listview.setItems(Models.getInstance().getLatestTransactions());

        transaction_listview.setCellFactory(e -> new TransactionCellFactory());

        send_money_btn.setOnAction(event -> onSendMoney()
        );

        accountSummary();
    }




    private  void bindData(){
        user_name.textProperty().bind(Bindings.concat("Hi, ").concat(Models.getInstance().getClient().firstNameProperty()));
        login_date.setText("Today, "+ LocalDate.now());
        checking_bal.textProperty().bind(Models.getInstance().getCheckingAccountData(Models.getInstance().getClient().pAddressProperty().get()).getBalance().asString());
        checking_acc_num.textProperty().bind(Models.getInstance().getClient().getCheckingAccount().get().getAccountNumber());
        saving_acc_num.textProperty().bind(Models.getInstance().getClient().getSavingAccount().get().getAccountNumber());
        saving_bal.textProperty().bind(Models.getInstance().getClient().getSavingAccount().get().getBalance().asString());
    }


    private  void  initLatestTransactionsList()
    {
       if(Models.getInstance().getLatestTransactions().isEmpty())
        {
            Models.getInstance().setLatestTransactions();
        }
    }



    private  void onSendMoney()
    {

        String receiver=payee_fld.getText();

        String sender =Models.getInstance().getClient().pAddressProperty().get();
        ResultSet resultSet=Models.getInstance().getDatabaseDriver().searchClient(receiver);

        try{
            if(resultSet.isBeforeFirst())
            {
                if(!amount_fld.getText().isEmpty()) {
                    double amount = Double.parseDouble(amount_fld.getText());
                    double svBalance=Models.getInstance().getClient().getSavingAccount().get().getBalance().get();

                    if(svBalance>=amount) {
                        Models.getInstance().getDatabaseDriver().updateSavingCheckingBalance(receiver, amount, "ADD");

                        //Subtract from sender's saving account
                        Models.getInstance().getDatabaseDriver().updateSavingCheckingBalance(sender, amount, "SUB");
                        // Update the saving account balance ìn the client object

                        Models.getInstance().getClient().getSavingAccount().get().setBalance(Models.getInstance().getDatabaseDriver().getSavingAccountBalance(sender));
                        //records new transaction
                        String message = message_fld.getText();
                        Models.getInstance().getDatabaseDriver().newTransaction(sender, receiver, amount, message);

                        err_lbl.setStyle("-fx-text-fill:#0000EE");
                        err_lbl.setText("Successful");

                        Models.getInstance().getLatestTransactions().clear();
                        Models.getInstance().getAllTransactions().clear();
                        initLatestTransactionsList();
                        if (Models.getInstance().getAllTransactions().isEmpty()) {
                            Models.getInstance().setAllTransactions();
                        }

                    }
                    else {
                        err_lbl.setText("Insufficient Balance");
                    }
                }
                else
                    err_lbl.setText("Enter Amount");
            }
            else
                err_lbl.setText("No Such Payee Address");

        } catch (SQLException e) {
            e.printStackTrace();
        }


        //clear all field
        payee_fld.setText("");
        amount_fld.setText("");
        message_fld.setText("");
    }


    // Method calculates all incomes and expenses

    private  void accountSummary()
    {
        double incomes=0;
        double expenses=0;
      if(Models.getInstance().getAllTransactions().isEmpty())
        {
            Models.getInstance().setAllTransactions();
        }

        for(Transaction transaction: Models.getInstance().getAllTransactions())
        {
            if(transaction.senderProperty().get().equals(Models.getInstance().getClient().pAddressProperty().get()))
            {
                expenses+=transaction.amountProperty().get();
            }
            else {
                incomes+=transaction.amountProperty().get();
            }
        }
        income_lbl.setText("+ ₹"+ incomes);
        expenses_lbl.setText("- ₹"+expenses);
    }

    // balance fld only for double value

private void setTextFormatter()
{
    UnaryOperator<TextFormatter.Change> filter = t -> {

        if (t.isReplaced())
            if (t.getText().matches("[^0-9]"))
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

    amount_fld.setTextFormatter(new TextFormatter<>(filter));
}


}
