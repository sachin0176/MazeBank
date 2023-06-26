package com.jmc.mazebank.controller.admin;

import com.jmc.mazebank.models.Client;
import com.jmc.mazebank.models.Models;
import com.jmc.mazebank.views.ClientCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController  implements Initializable {
    public  ListView<Client>  clients_list_view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initClientsList();
        clients_list_view.setItems(Models.getInstance().getClients());
        clients_list_view.setCellFactory(e-> new ClientCellFactory());

    }

    private  void initClientsList()
    {
        if(Models.getInstance().getClients().isEmpty())
        {
            Models.getInstance().setClients();
        }

    }



}
