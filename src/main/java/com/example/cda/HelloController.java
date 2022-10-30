package com.example.cda;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import java.io.FileWriter;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HelloController {
    @FXML
    private Button helloBtn, loginBtn;
    @FXML
    private Label warningLabel;
    @FXML
    private ComboBox userTypeMenu;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    protected void onLoginBtnClick(ActionEvent e) throws IOException {

        Stage stage = (Stage) loginBtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank() || userTypeMenu.getValue() == null) {
            warningLabel.setText("Invalid input");
        } else {
            FileWriter writer = new FileWriter("user.txt");
            writer.write(usernameField.getText() + "\n");
            writer.write(userTypeMenu.getValue().toString());
            writer.flush();
            writer.close();
            warningLabel.setText("okay");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
        }
    }


    @FXML
    protected void onHelloBtnClick(ActionEvent e) throws IOException {
        Stage stage = (Stage) helloBtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }


}