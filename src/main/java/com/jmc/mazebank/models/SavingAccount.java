package com.jmc.mazebank.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SavingAccount extends  Account{
    //The withdrawal limit from the savings account
    private final DoubleProperty withdrawalLimi;

    public SavingAccount(String owner ,String accNum,double balance,double wLimit)
    {
        super(owner,accNum,balance);
        this.withdrawalLimi=new SimpleDoubleProperty(this,"Withdrawal Limit",wLimit);
    }

    public DoubleProperty getWithdrawalLimi() {
        return withdrawalLimi;
    }

    public String toString()
    {
        return getAccountNumber().get();
    }
}
