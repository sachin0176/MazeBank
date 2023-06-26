package com.jmc.mazebank.models;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {

    private Connection con;

    public DatabaseDriver()
    {
        try{

            con= DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
    * client section
    * */

    public ResultSet getClientResult(String pAddress,String password)
    {
        Statement statement;
        ResultSet resultSet=null;
        try{
            statement=this.con.createStatement();
            resultSet= statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress='"+pAddress+"' AND Password='"+password+"';");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getTransactions(String pAddress,int limit)
    {
        Statement statement;
        ResultSet resultSet;
        try{
            statement=this.con.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM Transactions" +
                    " WHERE Sender='"+pAddress+"' OR Receiver='"+pAddress+"' ORDER BY ID DESC LIMIT " +limit+ "; ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  resultSet;
    }

    // Creates and records new transaction

    public void newTransaction(String sender ,String receiver,double amount,String message)
    {
        Statement statement;

        try{
            LocalDate date=LocalDate.now();
            statement=this.con.createStatement();
            statement.executeUpdate("INSERT INTO "+
                    " Transactions (Sender,Receiver,Amount,Message,Date) "+
                    " VALUES('"+sender+"','"+receiver+"','"+amount+"','"+message+"','"+date+"');");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method returns saving account balance
    public double getSavingAccountBalance(String pAddress)
    {
        Statement statement;
        ResultSet resultSet;
        double balance;
        try{
            statement=this.con.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM SavingsAccount WHERE Owner='"+pAddress+"';");
            balance=resultSet.getDouble("Balance");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return balance;
    }

    public double getCheckingAccountBalance(String pAddress)
    {
        Statement statement;
        ResultSet resultSet;
        double balance;
        try{
            statement=this.con.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM CheckingAccount WHERE Owner='"+pAddress+"';");
            balance=resultSet.getDouble("Balance");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return balance;
    }



        //Method to either add or subtract balance from given pAddress
    public void updateSavingCheckingBalance(String pAddress, double amount, String operation){
        Statement statement;
        ResultSet resultSet;
        try{
            statement=this.con.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM SavingsAccount WHERE Owner='"+pAddress+"';");
            double newBalance;
            if(operation.equals("ADD")) {
                 newBalance = resultSet.getDouble("Balance") + amount;
                statement.executeUpdate("UPDATE SavingsAccount SET Balance= '" + newBalance + "' WHERE Owner='" + pAddress + "';");
            }
            else{
                if(resultSet.getDouble("Balance")>=amount)
                {
                    newBalance = resultSet.getDouble("Balance") - amount;
                    statement.executeUpdate("UPDATE SavingsAccount SET Balance = '"+newBalance+"' WHERE Owner='" + pAddress + "';");

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCheckingAccountBalance(String pAddress, double amount, String operation){
        Statement statement;
        ResultSet resultSet;
        try{
            statement=this.con.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM CheckingAccount WHERE Owner='"+pAddress+"';");
            double newBalance;
            if(operation.equals("ADD")) {
                newBalance = resultSet.getDouble("Balance") + amount;
                statement.executeUpdate("UPDATE CheckingAccount SET Balance= '" + newBalance + "' WHERE Owner='" + pAddress + "';");
            }
            else{
                if(resultSet.getDouble("Balance")>=amount)
                {
                    newBalance = resultSet.getDouble("Balance") - amount;
                    statement.executeUpdate("UPDATE CheckingAccount SET Balance = '"+newBalance+"' WHERE Owner='" + pAddress + "';");

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePassword(String pAddress, String password)
    {
        Statement statement;
        //ResultSet resultSet;
        try{
            statement=this.con.createStatement();
            statement.executeUpdate("UPDATE Clients SET Password='"+ password+"' WHERE PayeeAddress='"+pAddress+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendClientFeedback(String pAddress,String message)
    {
        Statement statement;
        try{
            LocalDate date=LocalDate.now();
            statement=this.con.createStatement();
            statement.executeUpdate(" INSERT INTO "
                    +" ClientsFeedback (PayeeAddress,Date,Message) "+ " VALUES('"+pAddress+"','"+date+"','"+message+"') ;");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /*
    * admin section
    *
    * */

    public ResultSet getAdminResult(String username,String password)
    {
        Statement statement;
        ResultSet resultSet=null;
        try{
            statement=this.con.createStatement();
            resultSet= statement.executeQuery("SELECT * FROM Admins " +
                    "WHERE Username='"+username+"' AND Password='"+password+"';");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void createClient(String fName, String lName, String pAddress,String password, LocalDate date)
    {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO "+
                   "Clients(FirstName,LastName,PayeeAddress,Password,Date)"+
                    "VALUES('"+fName+"','"+lName+"','"+pAddress+"','"+password+"', '"+date.toString()+"');");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void createCheckingAccount(String owner,String accNumber,double balance,int tLimit)
    {
        try{
            Statement statement=con.createStatement();
            statement.executeUpdate("INSERT INTO "+
                    "CheckingAccount(Owner,AccountNumber,Balance,TransactionLimit)"+
                    "VALUES('"+owner+"','"+accNumber+"','"+balance+"','"+tLimit+"');");

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void createSavingsAccounts(String owner,String accNumber,double balance,double WLimit)
    {
        try{
            Statement statement=con.createStatement();
            statement.executeUpdate("INSERT INTO "+
                    "SavingsAccount(Owner,AccountNumber,WithdrawalLimit,Balance)"+
                    "VALUES('"+owner+"','"+accNumber+"','"+WLimit+"','"+balance+"');");

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ResultSet getAllClientsData()
    {
        Statement statement;
        ResultSet resultSet;
        try{
            statement=this.con.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM Clients ORDER BY ID DESC;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  resultSet;
    }



    public void depositSaving(String pAddress,double amount){
        Statement statement;

        try{
            statement=this.con.createStatement();
           statement.executeUpdate("UPDATE SavingsAccount SET Balance='"+amount+"' WHERE Owner='"+pAddress+"';");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteAccount(String pAddress)
    {
        Statement statement;
        ResultSet resultSet1,resultSet2;
        try{
            statement=this.con.createStatement();
            statement.executeUpdate("DELETE FROM  Clients WHERE PayeeAddress='"+pAddress+"';");
            resultSet1=statement.executeQuery("SELECT *  FROM SavingsAccount WHERE Owner='"+pAddress+"'; ");
            if(resultSet1.isBeforeFirst())
            {
                statement.executeUpdate("DELETE FROM  SavingsAccount WHERE Owner='"+pAddress+"';");
            }

            resultSet2=statement.executeQuery("SELECT *  FROM CheckingAccount WHERE Owner='"+pAddress+"'; ");
            if(resultSet2.isBeforeFirst())
            {
                statement.executeUpdate("DELETE FROM  CheckingAccount WHERE Owner='"+pAddress+"';");
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllClientsFeedback()
    {
        Statement statement;
        ResultSet resultSet;

        try{
            statement=this.con.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM ClientsFeedback ORDER BY ID DESC");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }


    public void deleteClientsFeedback(String pAddress)
    {
        Statement statement;
        try{
            statement=this.con.createStatement();
            statement.executeUpdate("DELETE FROM ClientsFeedback WHERE PayeeAddress='"+pAddress+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /*
    * utility section
    * */

    public ResultSet searchClient(String pAddress)
    {
        Statement statement;
        ResultSet resultSet;
        try{
            statement=this.con.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress='"+pAddress+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public int getLastClientsId()
    {
        Statement statement;
        ResultSet resultSet;
        int id=0;

        try{
            statement=con.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name='Clients';");
            id=resultSet.getInt("seq");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return id;
    }

    public ResultSet getCheckingAccountData(String pAddress)
    {
        Statement statement;
        ResultSet resultSet;
        try {
            statement=this.con.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM CheckingAccount WHERE Owner='"+pAddress+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  resultSet;
    }


    public ResultSet getSavingsAccountData(String pAddress)
    {
        Statement statement;
        ResultSet resultSet;
        try {
            statement=this.con.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM SavingsAccount WHERE Owner='"+pAddress+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  resultSet;
    }
}
