module com.jmc.mazebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.jmc.mazebank to javafx.fxml;
    exports com.jmc.mazebank;
    exports com.jmc.mazebank.controller;
    exports com.jmc.mazebank.controller.admin;
    exports com.jmc.mazebank.controller.client;
    exports com.jmc.mazebank.models;
    exports com.jmc.mazebank.views;
}