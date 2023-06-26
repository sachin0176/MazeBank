package com.jmc.mazebank.models;

import com.jmc.mazebank.views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Models {

    private static Models models;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;

    //client data section
    private final Client client;
    private boolean clientLoginSuccessFlag;
    private final ObservableList<Transaction> latestTransactions;
    private final ObservableList<Transaction> allTransactions;



    // admin data section

    private boolean adminLoginSuccessFlag;
    private final ObservableList<Client> clients;

    private final ObservableList<ClientsFeedback> clientsFeedbacks;
    private Models()
    {
        this.databaseDriver=new DatabaseDriver();
        this.viewFactory=new ViewFactory();

        //client data section
        clientLoginSuccessFlag=false;
        client=new Client("","","",null,null,null);
        this.latestTransactions=FXCollections.observableArrayList();
        this.allTransactions=FXCollections.observableArrayList();

        //admin data section
            adminLoginSuccessFlag=false;
            this.clients= FXCollections.observableArrayList();
            this.clientsFeedbacks=FXCollections.observableArrayList();
    }

    public static  synchronized  Models getInstance(){
        if(models==null)
        {
            models=new Models();
        }
        return models;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;

    }


    /*
    * Client method section
    * */


    public boolean isClientLoginSuccessFlag() {
        return clientLoginSuccessFlag;
    }

    public void setClientLoginSuccessFlag(boolean clientLoginSuccessFlag) {
        this.clientLoginSuccessFlag = clientLoginSuccessFlag;
    }

    public Client getClient() {
        return client;

    }

    public void evaluateClientCred(String pAddress,String password)
    {
        CheckingAccount checkingAccount;
        SavingAccount savingAccount;
        ResultSet resultSet=databaseDriver.getClientResult(pAddress,password);

        try{

            if (resultSet.isBeforeFirst()) {
                this.client.firstNameProperty().set(resultSet.getString("FirstName"));
                this.client.lastNameProperty().set(resultSet.getString("LastName"));
                this.client.pAddressProperty().set(resultSet.getString("PayeeAddress"));
                String[] dateParts =resultSet.getString("Date").split("-");
                LocalDate date=LocalDate.of(Integer.parseInt(dateParts[0]),Integer.parseInt(dateParts[1]),Integer.parseInt(dateParts[2]));
                this.client.getDateCreated().set(date);
                checkingAccount=getCheckingAccountData(pAddress);
                savingAccount=getSavingAccountData(pAddress);
                this.client.getCheckingAccount().set(checkingAccount);
                this.client.getSavingAccount().set(savingAccount);
                this.clientLoginSuccessFlag=true;
                resultSet.close();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private  void preparedTransaction(ObservableList<Transaction> transactions,int limit)
    {
        ResultSet resultSet= databaseDriver.getTransactions(this.client.pAddressProperty().get(),limit);
        try{
            while (resultSet.next()) {

                String sender=resultSet.getString("Sender");
                String receiver =resultSet.getString("Receiver");
                double amount=resultSet.getDouble("Amount");
                String[] dateParts =resultSet.getString("Date").split("-");
                LocalDate date=LocalDate.of(Integer.parseInt(dateParts[0]),Integer.parseInt(dateParts[1]),Integer.parseInt(dateParts[2]));
                String message=resultSet.getString("Message");
                transactions.add(new Transaction(sender,receiver,amount,date,message));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void setLatestTransactions() {
        preparedTransaction(this.latestTransactions,4);
    }

    public ObservableList<Transaction> getLatestTransactions() {
        return latestTransactions;
    }

    public void setAllTransactions() {
       preparedTransaction(this.allTransactions,-1);
    }

    public ObservableList<Transaction> getAllTransactions() {
        return allTransactions;
    }
    /*
    * admin method
    * */

    public boolean isAdminLoginSuccessFlag() {
        return adminLoginSuccessFlag;
    }

    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag) {
        this.adminLoginSuccessFlag = adminLoginSuccessFlag;
    }

    public void evaluateAdminCred(String username,String password)
    {
        ResultSet resultSet= getDatabaseDriver().getAdminResult(username,password);

        try{
           if(resultSet.isBeforeFirst()){
               this.adminLoginSuccessFlag=true;
           }
        resultSet.close();
        }catch ( Exception e)
        {
            e.printStackTrace();
        }
    }


    public ObservableList<Client> getClients() {
        return clients;
    }

    public void setClients(){
        CheckingAccount checkingAccount;
        SavingAccount savingAccount;
        ResultSet resultSet=databaseDriver.getAllClientsData();
        try {
            while (resultSet.next()) {
                String fName=resultSet.getString("FirstName");
                String lName=resultSet.getString("LastName");
                String pAddress=resultSet.getString("PayeeAddress");
                String[] dateParts =resultSet.getString("Date").split("-");
                LocalDate date=LocalDate.of(Integer.parseInt(dateParts[0]),Integer.parseInt(dateParts[1]),Integer.parseInt(dateParts[2]));
                checkingAccount=getCheckingAccountData(pAddress);
                savingAccount=getSavingAccountData(pAddress);
                this.clients.add(new Client(fName,lName,pAddress,checkingAccount,savingAccount,date));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ObservableList<Client> searchClient(String pAddress){
        ObservableList<Client> searchResult=FXCollections.observableArrayList();
        ResultSet resultSet;
        try {
            resultSet=databaseDriver.searchClient(pAddress);
            if(resultSet.isBeforeFirst()) {
                CheckingAccount checkingAccount = getCheckingAccountData(pAddress);
                SavingAccount savingAccount = getSavingAccountData(pAddress);
                String fName = resultSet.getString("FirstName");
                String lName = resultSet.getString("LastName");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));

                searchResult.add(new Client(fName, lName, pAddress, checkingAccount, savingAccount, date));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return searchResult;
    }

    public ObservableList<ClientsFeedback> getClientsFeedbacks() {
        return clientsFeedbacks;
    }

    public void setClientsFeedbacks()
    {
        ResultSet resultSet= databaseDriver.getAllClientsFeedback();

        try{
            while(resultSet.next())
            {
                String sender=resultSet.getString("PayeeAddress");
                String message=resultSet.getString("Message");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                this.clientsFeedbacks.add(new ClientsFeedback(sender,date,message));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /*
    * Utility Methods Section
    * */


    public CheckingAccount getCheckingAccountData(String pAddress)
    {
        CheckingAccount checkingAccount;
        ResultSet resultSet=databaseDriver.getCheckingAccountData(pAddress);
        try{
            String accNum=resultSet.getString("AccountNumber");
            double balance=resultSet.getDouble("Balance");
            int tLimit=resultSet.getInt("TransactionLimit");
            checkingAccount=new CheckingAccount(pAddress,accNum,balance,tLimit);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return checkingAccount;
    }

    public SavingAccount getSavingAccountData(String pAddress)
    {
        SavingAccount savingAccount;
        ResultSet resultSet=databaseDriver.getSavingsAccountData(pAddress);
        try{
            String accNum=resultSet.getString("AccountNumber");
            double balance=resultSet.getDouble("Balance");
            double wLimit=resultSet.getDouble("WithdrawalLimit");
            savingAccount=new SavingAccount(pAddress,accNum,balance,wLimit);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return savingAccount;
    }
}
