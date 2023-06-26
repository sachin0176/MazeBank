package com.jmc.mazebank;

import com.jmc.mazebank.models.Models;

import javafx.application.Application;

import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage)  {
        Models.getInstance().getViewFactory().showLoginWindow();


    }
}
