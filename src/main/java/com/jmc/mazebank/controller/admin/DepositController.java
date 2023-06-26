package com.jmc.mazebank.controller.admin;

import com.jmc.mazebank.models.Client;
import com.jmc.mazebank.models.Models;
import com.jmc.mazebank.views.ClientCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class DepositController implements Initializable {
    public TextField PAddress_fld;
    public Button pa_search_btn;
    public ListView<Client> result_list_view;
    public TextField dp_amount_fld;
    public Button deposit_btn;
    public Label err_lbl;
    private Client  client;
    ObservableList<Client>  searchResult;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pa_search_btn.setOnAction(event -> onClientSearch());
        deposit_btn.setOnAction(event -> onDeposit());

        setTextFormatter();
 }

    private  void onClientSearch()
    {
         searchResult = Models.getInstance().searchClient(PAddress_fld.getText());
    if(searchResult.size()>0) {
        result_list_view.setItems(searchResult);
        result_list_view.setCellFactory(e -> new ClientCellFactory());
        client = searchResult.get(0);
    }
    else {
        PAddress_fld.setText("");
        err_lbl.setStyle("-fx-text-fill: EE0000");
        err_lbl.setText("No Such Payee Address");
    }

    }


    private void onDeposit()
    {

        if(!dp_amount_fld.getText().isEmpty())
        {
            double amount=Double.parseDouble(dp_amount_fld.getText());
            double newBalance=amount+client.getSavingAccount().get().getBalance().get();
            Models.getInstance().getDatabaseDriver().depositSaving(client.pAddressProperty().get(),newBalance);
            err_lbl.setText("Successful");
        }
        else {
            err_lbl.setStyle("-fx-text-fill: #EE0000");
            err_lbl.setText("Enter Amount");
        }

        emptyField();

    }

    private  void emptyField()
    {
        PAddress_fld.setText("");
        dp_amount_fld.setText("");
        searchResult.clear();

    }

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

       dp_amount_fld.setTextFormatter(new TextFormatter<>(filter));
    }


}

