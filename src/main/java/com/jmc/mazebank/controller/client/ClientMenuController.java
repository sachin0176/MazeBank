package com.jmc.mazebank.controller.client;

import com.jmc.mazebank.models.Models;
import com.jmc.mazebank.views.ClientMenuOption;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button dashboard_btn;
    public Button transaction_btn;
    public Button account_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button fed_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            addListener();
    }
    private void addListener()
    {
        dashboard_btn.setOnAction(e-> onDashboard());
        transaction_btn.setOnAction(e->onTransaction());
        account_btn.setOnAction(event -> onAccount());
        profile_btn.setOnAction(event -> onProfile());
        logout_btn.setOnAction(event -> onLogout());
        fed_btn.setOnAction(event -> onFeedback());
    }
    private void onDashboard()
    {

        Models.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.DASHBOARD);
             if(Models.getInstance().getLatestTransactions().isEmpty() && Models.getInstance().getAllTransactions().isEmpty())
            {
                Models.getInstance().setLatestTransactions();
                Models.getInstance().setAllTransactions();
            }

    }
    private void onTransaction()
    {

        Models.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.TRANSACTIONS);


    }

    private void onAccount()
    {
        Models.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.ACCOUNTS);
    }


    private void onProfile()
    {
        Models.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.PROFILE);
    }
    private void onLogout()
    {
        //get stage
        Stage stage=(Stage)dashboard_btn.getScene().getWindow();

        //Close the client window
        Models.getInstance().getViewFactory().closeStage(stage);
        //Show login window
        Models.getInstance().getViewFactory().showLoginWindow();

        //set client login success flag to false

        Models.getInstance().setClientLoginSuccessFlag(false);
    }

    private  void onFeedback()
    {
        Models.getInstance().getViewFactory().showFeedbackView();
    }
}
