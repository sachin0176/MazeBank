package com.jmc.mazebank.controller.client;

import com.jmc.mazebank.models.Models;
import com.jmc.mazebank.models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {





    public FontAwesomeIconView in_icon;
    public FontAwesomeIconView out_icon;
    public Label dt_lbl;
    public Label sender_lbl;
    public Label receiver_lbl;
    public Label amount_lbl;
    public Button message_btn;
    private  final Transaction transaction;

    public TransactionCellController(Transaction transaction)
    {
        this.transaction=transaction;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sender_lbl.textProperty().bind(transaction.senderProperty());
        receiver_lbl.textProperty().bind(transaction.receiverProperty());
        amount_lbl.textProperty().bind(transaction.amountProperty().asString());
        dt_lbl.textProperty().bind(transaction.getDate().asString());
        message_btn.setOnAction(event -> Models.getInstance().getViewFactory().showMessageWindow(transaction.senderProperty().get(),transaction.getMessage().get()));
        transactionIcon();
    }

    private  void transactionIcon()
    {
        if(transaction.senderProperty().get().equals(Models.getInstance().getClient().pAddressProperty().get()))
        {
            in_icon.setFill(Color.rgb(240,240,240));
            out_icon.setFill(Color.RED);
        }
        else {
            in_icon.setFill(Color.GREEN);
            out_icon.setFill(Color.rgb(240,240,240));
        }
    }
}
