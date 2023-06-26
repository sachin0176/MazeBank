package com.jmc.mazebank.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Account {
    private final StringProperty owner;
    private final StringProperty accountNumber;
    private final DoubleProperty balance;

    public Account(String owner,String accountNumber,Double balance)
    {
        this.owner=new SimpleStringProperty(this,"Owner",owner);
        this.accountNumber=new SimpleStringProperty(this,"Account Number",accountNumber);
        this.balance=new SimpleDoubleProperty(this,"Balance",balance);

    }


    public StringProperty getOwner() {
        return owner;
    }

    public StringProperty getAccountNumber() {
        return accountNumber;
    }

    public DoubleProperty getBalance() {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance.set(balance);
    }
}
