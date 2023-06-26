package com.jmc.mazebank.controller.client;

import com.jmc.mazebank.models.Models;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangePwPaController implements Initializable {

    public PasswordField old_fld;

    public PasswordField new_fld;
    public Button reset_btn;
    public Label err_lbl;
    public PasswordField conf_fld;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reset_btn.setOnAction(event -> updatePassword());
    }

    private void updatePassword(){
        String pAddress= Models.getInstance().getClient().pAddressProperty().get();
        String password=old_fld.getText();
        ResultSet resultSet=Models.getInstance().getDatabaseDriver().getClientResult(pAddress,password);
       Stage stage=(Stage)err_lbl.getScene().getWindow();

      try{
          if(resultSet.isBeforeFirst())
          {
              if(new_fld.getText().matches( "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$")) {
                  if (new_fld.getText().equals(conf_fld.getText())) {
                      Models.getInstance().getDatabaseDriver().updatePassword(pAddress, conf_fld.getText());
                      err_lbl.setStyle("-fx-text-fill: #0000FF;");
                      err_lbl.setText("Successful");

                     Models.getInstance().getViewFactory().closeStage(stage);

                  } else {
                      err_lbl.setText("Incorrect Password");
                  }
              }
                  else {
                      err_lbl.setText("At least one all [A-Z],[a-z],[0-9],[!@#$%^&*]\nA minimum length of 8 characters");
              }


          }
          else
              err_lbl.setText("Wrong Old Password");


      } catch (SQLException e) {
          e.printStackTrace();
      }

    }



}
