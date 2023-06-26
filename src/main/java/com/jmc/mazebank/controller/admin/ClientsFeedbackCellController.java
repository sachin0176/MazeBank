package com.jmc.mazebank.controller.admin;

import com.jmc.mazebank.models.ClientsFeedback;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsFeedbackCellController implements Initializable {
    private final ClientsFeedback clientsFeedback;
    public Label pAddress_lbl;
    public Label dt_lbl;
    public Label message_lbl;
    public Button dlt_btn;

    public ClientsFeedbackCellController(ClientsFeedback clientsFeedback)
    {
        this.clientsFeedback=clientsFeedback;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.pAddress_lbl.textProperty().bind(clientsFeedback.getSender());
        this.message_lbl.textProperty().bind(clientsFeedback.getMessage());
        this.dt_lbl.textProperty().bind(clientsFeedback.getDate().asString());
    }
}
