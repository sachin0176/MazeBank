package com.jmc.mazebank.views;

import com.jmc.mazebank.controller.admin.ClientsFeedbackCellController;
import com.jmc.mazebank.models.ClientsFeedback;
import com.jmc.mazebank.models.Models;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ClientsFeedbackCellFactory extends ListCell<ClientsFeedback> {
    @Override
    protected  void updateItem(ClientsFeedback clientsFeedback,boolean empty)
    {
        super.updateItem(clientsFeedback,empty);
        if(empty)
        {
            setText(null);
            setGraphic(null);
        }
        else {

            FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/Admin/ClientsFeedbackCell.fxml"));
            ClientsFeedbackCellController controller=new ClientsFeedbackCellController(clientsFeedback);
            loader.setController(controller);
            setText(null);
            try{
                setGraphic(loader.load());
                controller.dlt_btn.setOnAction(event -> {
                    setGraphic(null);
                    Models.getInstance().getDatabaseDriver().deleteClientsFeedback(clientsFeedback.getSender().get());
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
