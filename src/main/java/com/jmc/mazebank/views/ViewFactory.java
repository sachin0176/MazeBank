package com.jmc.mazebank.views;

import com.jmc.mazebank.controller.admin.AdminController;
import com.jmc.mazebank.controller.client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {

    private  AccountType loginAccountType;


    //Client View
    private  final ObjectProperty<ClientMenuOption> clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane transactionView;
    private AnchorPane accountView;



    //Admin view
    private  final ObjectProperty<AdminMenuOption> adminSelectedMenuItem;
    private  AnchorPane  createClientView;
    private AnchorPane clientsView;
    private  AnchorPane depositView;
    private  AnchorPane profileView;
    private AnchorPane feedbackView;
    public  ViewFactory()
    {
        this.loginAccountType=AccountType.CLIENT;
        this.clientSelectedMenuItem=new SimpleObjectProperty<>();
        this.adminSelectedMenuItem=new SimpleObjectProperty<>();
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    public ObjectProperty<ClientMenuOption> getClientSelectedMenuItem() {

        return clientSelectedMenuItem;
    }

    /*
    client View Section
     */
    public AnchorPane getDashboardview() {
       if(dashboardView ==null)
       {
           try{
               dashboardView =new FXMLLoader(getClass().getResource("/FXML/Client/Dashboard.fxml")).load();
           }
           catch(Exception e)
           {
               e.printStackTrace();
           }
       }
       return dashboardView;
    }

    public AnchorPane getTransactionView() {

        if(transactionView ==null)
        {
            try {
                transactionView =new FXMLLoader(getClass().getResource("/FXML/Client/Transaction.fxml")).load();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return transactionView;
    }

    public AnchorPane getAccountview() {

        if(accountView==null)
        {
            try{
                accountView=new FXMLLoader(getClass().getResource("/FXML/Client/Accounts.fxml")).load();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return accountView;
    }


    public AnchorPane getProfileView()
    {
        if(profileView==null)
        {
            try {
                {
                    profileView=new FXMLLoader(getClass().getResource("/FXML/Client/Profile.fxml")).load();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return profileView;

    }

    public void getPasswordResetView()
    {
         FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/Client/changePassword.fxml"));
        createStage(loader);

    }

   public  void showFeedbackView()
   {
       FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/Client/CFeedback.fxml"));
       createStage(loader);
   }

    public void showClientWindow()
    {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/Client/Client.fxml"));
        ClientController clientController=new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }

    /*
    * Admin view section
    *
     */

    public ObjectProperty<AdminMenuOption> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getCreateClientView() {
           if(createClientView==null)
           {
             try{
                 createClientView=new FXMLLoader(getClass().getResource("/FXML/Admin/CreateClient.fxml")).load();
             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }
           }
        return createClientView;
    }

    public AnchorPane getClientsView() {

        if(clientsView==null)
        {
            try{
                clientsView=new FXMLLoader(getClass().getResource("/FXML/Admin/Clients.fxml")).load();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return clientsView;
    }

    public AnchorPane getDepositView() {
        if(depositView==null)
        {
            try{
                depositView=new FXMLLoader(getClass().getResource("/FXML/Admin/Deposit.fxml")).load();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return depositView;
    }


    public  AnchorPane getFeedbackView()
    {
        if(feedbackView==null)
        {
            try
            {
                feedbackView=new FXMLLoader(getClass().getResource("/FXML/Admin/ClientsFeedback.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return feedbackView;
    }
    public void showAdminWindow()
    {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/Admin/Admin.fxml"));
        AdminController adminController=new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }
    public void showLoginWindow()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
        createStage(loader);
    }

    public void showMessageWindow(String pAddress, String message)
    {
        StackPane pane=new StackPane();
        HBox hBox=new HBox(5);
        Label sender=new Label(pAddress);
        Label receiver =new Label(message);
        hBox.getChildren().addAll(sender,receiver);
        pane.getChildren().add(hBox);
        hBox.setAlignment(Pos.CENTER);
        Scene scene=new Scene(pane,300,100);
        Stage stage=new Stage();
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Message");

        stage.setScene(scene);
        stage.show();
    }
    private void createStage(FXMLLoader loader)
    {
        Scene scene=null;

        try{
            scene=new Scene(loader.load());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Stage stage=new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
        stage.setResizable(false);
        stage.setTitle("Maze Bank");
        stage.show();
    }

    public void closeStage(Stage stage)
    {
        stage.close();
    }
}
