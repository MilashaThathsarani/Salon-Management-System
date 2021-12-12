package controller;

import controller.Controller.clientController;
import controller.Controller.userController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Client;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class UserController {
    public AnchorPane userContext;
    public TextField txtUserName;
    public TextField txtFullName;
    public TextField txtEmail;
    public TextField txtPassword;
    public TextField txtRollType;

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) userContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LogInForm.fxml"))));
        window.centerOnScreen();
    }

  /*  public void userNameOnAction(ActionEvent actionEvent) {
        txtFullName.requestFocus();
    }

    public void fullNameOnAction(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }

    public void emailOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }*/

    /*public void passwordOnAction(ActionEvent actionEvent){
        txtRollType.requestFocus();
    }*/

    public void addOnAction(ActionEvent actionEvent) {
        try {
            User u1 = new User(
                    txtUserName.getText(),
                    txtFullName.getText(),
                    txtEmail.getText(),
                    txtPassword.getText()
            );

            if (new userController().saveUser(u1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            }else
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void userName(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,40}$";
            String typeText = txtUserName.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtUserName.setStyle("-fx-text-fill:#474787");
                    txtFullName.requestFocus();
                }else{
                    txtUserName.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void fullName(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,40}$";
            String typeText = txtFullName.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtFullName.setStyle("-fx-text-fill:#474787");
                    txtEmail.requestFocus();
                }else{
                    txtFullName.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void password(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[0-9][-]?[0-9]*$";
            String typeText = txtPassword.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtPassword.setStyle("-fx-text-fill:#474787");
                }else{
                    txtPassword.setStyle("-fx-text-fill: red");
                }
            }
        }
    }
}
