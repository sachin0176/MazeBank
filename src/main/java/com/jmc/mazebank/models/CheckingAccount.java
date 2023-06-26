package com.jmc.mazebank.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CheckingAccount extends  Account{
    //The number of transactions a client is allowed to do per day.
private  final IntegerProperty transactionLimit;

public  CheckingAccount(String owner,String accounNumber,Double balance ,int limit)
{
    super(owner,accounNumber,balance);
    this.transactionLimit=new SimpleIntegerProperty(this,"Transaction Limit",limit);
}

    public IntegerProperty getTransactionLimit() {
        return transactionLimit;
    }

    public String toString()
    {
        return getAccountNumber().get();
    }
}
