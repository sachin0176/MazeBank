package com.jmc.mazebank.controller.client;

import com.jmc.mazebank.models.Models;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

import java.util.ResourceBundle;

public class CFeedbackController implements Initializable {
    public TextArea fd_fld;
    public Button send_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        send_btn.setOnAction(event -> sendFeedback());
    }

    private void sendFeedback()
    {
        Stage stage=(Stage) send_btn.getScene().getWindow();
       if(!fd_fld.getText().isEmpty()) {
           String message = fd_fld.getText();
           String pAddress = Models.getInstance().getClient().pAddressProperty().get();
           Models.getInstance().getDatabaseDriver().sendClientFeedback(pAddress, message);
           Models.getInstance().getViewFactory().closeStage(stage);
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Feedback submitted");
           Stage alertStage=(Stage) alert.getDialogPane().getScene().getWindow();
           alertStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
           alert.setContentText("Thank you for your feedback!");
           alert.showAndWait();
       }
       else
           Models.getInstance().getViewFactory().closeStage(stage);

    }
}
