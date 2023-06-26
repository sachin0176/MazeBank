package com.jmc.mazebank.views;

import com.jmc.mazebank.controller.admin.ClientCellController;
import com.jmc.mazebank.models.Client;
import com.jmc.mazebank.models.Models;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class ClientCellFactory extends ListCell<Client> {
    @Override
    protected void updateItem(Client client, boolean empty) {
        super.updateItem(client, empty);
        if(empty)
        {
            setText(null);
            setGraphic(null);

        }
        else {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/Admin/ClientCell.fxml"));
            ClientCellController clientCellController=new ClientCellController(client);
            loader.setController(clientCellController);
            setText(null);
            try{
                setGraphic(loader.load());
                clientCellController.dlt_btn.setOnAction(event -> {
                    setGraphic(null);
                   Models.getInstance().getDatabaseDriver().deleteAccount(client.pAddressProperty().get());
                  Models.getInstance().getClients().clear();
                  if(Models.getInstance().getClients().isEmpty())
                      Models.getInstance().setClients();
                });
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
