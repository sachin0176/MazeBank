package com.jmc.mazebank.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Client {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAddress;
    private final ObjectProperty<CheckingAccount> checkingAccount;
    private final ObjectProperty<SavingAccount> savingAccount;

    private final ObjectProperty<LocalDate> dateCreated;


    public Client(String firstName,String lastName,String payeeAddress,CheckingAccount checkingAccount,SavingAccount savingAccount,LocalDate date)
    {
        this.firstName=new SimpleStringProperty(this,"First Name",firstName);
        this.lastName=new SimpleStringProperty(this,"Last Name",lastName);
        this.payeeAddress=new SimpleStringProperty(this,"Payee Address",payeeAddress);
        this.checkingAccount=new SimpleObjectProperty<>(this,"Checking Account",checkingAccount);
        this.savingAccount=new SimpleObjectProperty<>(this,"Saving Account",savingAccount);
        this.dateCreated=new SimpleObjectProperty<>(this,"Date", date);

    }


    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty pAddressProperty() {
        return payeeAddress;
    }

    public ObjectProperty<CheckingAccount> getCheckingAccount() {
        return checkingAccount;
    }

    public ObjectProperty<SavingAccount> getSavingAccount() {
        return savingAccount;
    }

    public ObjectProperty<LocalDate> getDateCreated()
    {
        return dateCreated;
    }



}
