package com.jmc.mazebank.controller.client;

import com.jmc.mazebank.models.Models;
import com.jmc.mazebank.models.Transaction;
import com.jmc.mazebank.views.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    public ListView<Transaction> transaction_listview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       transaction_listview.setItems(Models.getInstance().getAllTransactions());

        transaction_listview.setCellFactory(e -> new TransactionCellFactory());

    }


}
