package com.jmc.mazebank.controller.admin;

import com.jmc.mazebank.models.Models;
import com.jmc.mazebank.views.AdminMenuOption;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable
{
    public Button client_create_btn;
    public Button clients_btn;
    public Button deposit_btn;
    public Button logout_btn;
    public Button fd_btn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();

    }

    private void addListener(){
        client_create_btn.setOnAction(event -> onCreateClient());
        clients_btn.setOnAction(event -> onClients());
        deposit_btn.setOnAction(event -> onDeposit());
        logout_btn.setOnAction(event -> onLogout());
        fd_btn.setOnAction(event -> onFeedback());

    }
    private void onCreateClient(){
        Models.getInstance().getClients().clear();
        Models.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.CREATE_CLIENT);
    }

   private void onClients (){

        Models.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.CLIENTS);

       if(Models.getInstance().getClients().isEmpty()){
           Models.getInstance().setClients();
       }
   }

   private void onDeposit()
   {
       Models.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.DEPOSIT);
   }

    private void onLogout()
    {
        //get stage
        Stage stage=(Stage)clients_btn.getScene().getWindow();

        //Close the client window
        Models.getInstance().getViewFactory().closeStage(stage);
        //Show login window
        Models.getInstance().getViewFactory().showLoginWindow();

        //set client login success flag to false

        Models.getInstance().setAdminLoginSuccessFlag(false);
    }


    private void onFeedback()
    {
        Models.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.FEEDBACK);
    }
}
