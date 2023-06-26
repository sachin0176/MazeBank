package com.jmc.mazebank.controller.admin;

import com.jmc.mazebank.models.ClientsFeedback;
import com.jmc.mazebank.models.Models;
import com.jmc.mazebank.views.ClientsFeedbackCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsFeedbackController implements Initializable {

    public ListView<ClientsFeedback> feedback_listview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initClientsFeedBackList();
        feedback_listview.setItems(Models.getInstance().getClientsFeedbacks());
       feedback_listview.setCellFactory(e->new ClientsFeedbackCellFactory());
    }

    private  void initClientsFeedBackList(){
        if(Models.getInstance().getClientsFeedbacks().isEmpty())
            Models.getInstance().setClientsFeedbacks();
    }
}
