package controller;

import com.jfoenix.controls.JFXButton;
import controller.Controller.userController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

public class LogInFormController {
    public TextField txtUserName;
    public TextField txtPassword;
    public AnchorPane logInContext;
    public JFXButton userOnAction;
    public  User user;


    public void logInOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        /*if (txtUserName.getText().equalsIgnoreCase("Admin") && txtPassword.getText().equalsIgnoreCase("1234")) {
            Stage window = (Stage) logInContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardAdmin.fxml"))));
            window.centerOnScreen();

        }
        if (txtUserName.getText().equalsIgnoreCase("Cashier") && txtPassword.getText().equalsIgnoreCase("6789")) {
            Stage window = (Stage) logInContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardCashier.fxml"))));
            window.centerOnScreen();
        }*/
       if (txtUserName.getText().equals("admin")&&txtPassword.getText().equals("1234")) {
            Stage window = (Stage) logInContext.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardAdmin.fxml")));
            window.setScene(scene);
            //scene.setFill(TRANSPARENT);
            window.setScene(scene);
           window.centerOnScreen();
            window.show();
        }
        else if (isValid(txtUserName.getText())){
           if (passwordCheck(txtPassword.getText())){
               Stage window = (Stage) logInContext.getScene().getWindow();
               window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardCashier.fxml"))));
               window.centerOnScreen();
           }else {
               new Alert(Alert.AlertType.WARNING,"Incorrect UserName,Password. Try Again...");
           }
        }
        else {
            new Alert(Alert.AlertType.WARNING,"Incorrect UserName,Password. Try Again...");
        }

    }
    private boolean passwordCheck(String text){
        return user.getPassword().equals(text);
    }

    private boolean isValid(String text) throws SQLException, ClassNotFoundException {
        ArrayList<User> allUser = new userController().getAllUser();
        for (User u : allUser) {
            if (u.getUserName().equalsIgnoreCase(text)) {
                user = u;
                return true;
            }
        }
        return false;
    }

    public void moveOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void UserOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) logInContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/User.fxml"))));
        window.centerOnScreen();
    }
}

