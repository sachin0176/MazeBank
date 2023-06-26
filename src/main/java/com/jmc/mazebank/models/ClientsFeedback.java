package com.jmc.mazebank.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class ClientsFeedback {

    private final StringProperty sender;
    private final ObjectProperty<LocalDate> date;
    private final  StringProperty message;

    public ClientsFeedback(String sender,LocalDate date,String message )
    {
        this.sender=new SimpleStringProperty(this, "Sender",sender);
        this.date= new SimpleObjectProperty<>(this, "Date", date);
        this.message=new SimpleStringProperty(this,"Message",message);
    }

    public StringProperty getSender() {
        return sender;
    }

    public StringProperty getMessage() {
        return message;
    }

    public ObjectProperty<LocalDate> getDate()
    {
        return date;
    }
}
