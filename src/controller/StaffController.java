package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.Controller.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class StaffController extends staffController {
    public JFXTextField txtStaffId;
    public JFXTextField txtStaffName;
    public JFXTextField txtStaffContact;
    public JFXTextField txtStaffAddress;
    public JFXTextField txtSalary;
    public ComboBox cmbPosition;
    public JFXTextField txtStaffEducation;
    public AnchorPane staffContext;
    public Label lblDate;
    public Label lblTime;
    public TableView <Staff>lblStaff;
    public TableColumn colStaffId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colEducation;
    public TableColumn colPosition;
    public TableColumn colSalary;
    public TableColumn colDate;
    public TableColumn colTime;
    public TextField txtSearch;
    public JFXTextField txtPosition;
    public TextField lblStaffId;
    public JFXButton btnStaff;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();

        colDate.setStyle("-fx-alignment:CENTER;");
        colTime.setStyle("-fx-alignment:CENTER;");

        colStaffId.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colName.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colAddress.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colContact.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colPosition.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colEducation.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colSalary.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTime.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");


        colStaffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEducation.setCellValueFactory(new PropertyValueFactory<>("education"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        setStaffToTable(new staffController().getAllStaff());

        setStaffId();

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    search(newValue);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        btnStaff.setDisable(true);
    }

    private void setStaffId() throws SQLException, ClassNotFoundException {
        lblStaffId.setText(new staffController().getStaffIds());
    }

    private void setStaffToTable(ArrayList<Staff> staff) {
        ObservableList<Staff> obList = FXCollections.observableArrayList();
        staff.forEach(e -> {
            obList.add(
                    new Staff(e.getStaffId(), e.getStaffName(),e.getAddress(), e.getContact(),e.getEducation(),e.getPosition(),e.getSalary(),e.getDate(),e.getTime()));
        });
        lblStaff.setItems(obList);
    }

    public void selectIdCollection(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String staffId = txtStaffId.getText();

        Staff s1= new staffController().getStaff(staffId);
        if (s1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(s1);
        }
    }

    private void setData(Staff s1) {
        txtStaffId.setText(s1.getStaffId());
        txtStaffName.setText(s1.getStaffName());
        txtStaffAddress.setText(s1.getAddress());
        txtStaffContact.setText(s1.getContact());
        txtStaffEducation.setText(s1.getEducation());
        txtPosition.setText(s1.getPosition());
        txtSalary.setText(String.valueOf(s1.getSalary()));

    }

    public void staffAddOnAction(ActionEvent actionEvent) {
        try {
            Staff s1 = new Staff(
                    lblStaffId.getText(),
                    txtStaffName.getText(),
                    txtStaffAddress.getText(),
                    txtStaffContact.getText(),
                    txtStaffEducation.getText(),
                    txtPosition.getText(),
                    Double.parseDouble(txtSalary.getText()),
                    lblDate.getText(),
                    lblTime.getText()
            );

            if (new staffController().saveStaff(s1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                setStaffId();
                clearText();
            }else
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();

            setStaffToTable(new staffController().getAllStaff());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void clearText() {
        txtStaffName.setText("");
        txtStaffAddress.setText("");
        txtStaffContact.setText("");
        txtStaffEducation.setText("");
        txtPosition.setText("");
        txtSalary.setText("");
    }

    public void staffUpdateOnAction(ActionEvent actionEvent) {
        try {
            Staff s = new Staff(
                    txtStaffId.getText(),
                    txtStaffName.getText(),
                    txtStaffAddress.getText(),
                    txtStaffContact.getText(),
                    txtStaffEducation.getText(),
                    txtPosition.getText(),
                    Double.parseDouble(txtSalary.getText()),
                    null,
                    null
            );


            if (new staffController().updateStaff(s)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                setStaffToTable(new staffController().getAllStaff());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            new Alert(Alert.AlertType.WARNING, e.getMessage());
        }
    }

    public void staffRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new staffController().deleteStaff(txtStaffId.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

        setStaffToTable(new staffController().getAllStaff());
    }



    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) staffContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardAdmin.fxml"))));
        window.centerOnScreen();
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search(txtSearch.getText());
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
        ArrayList<Staff> staff = staffController.searchStaff(value);
        ObservableList<Staff> obList = FXCollections.observableArrayList();
        for (Staff staff1:staff) {
            obList.add(new Staff(
                    staff1.getStaffId(),
                    staff1.getStaffName(),
                    staff1.getAddress(),
                    staff1.getContact(),
                    staff1.getEducation(),
                    staff1.getPosition(),
                    staff1.getSalary(),
                    staff1.getDate(),
                    staff1.getTime()

            ));
        }
        lblStaff.getItems().setAll(obList);
    }

    public void NameKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,40}$";
            String typeText = txtStaffName.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches) {
                    txtStaffName.setStyle("-fx-text-fill:#474787");
                    txtStaffAddress.requestFocus();
                } else {
                    txtStaffName.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void AddressKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z0-9/ ]{3,50}$";
            String typeText = txtStaffAddress.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtStaffAddress.setStyle("-fx-text-fill:#474787");
                    txtStaffContact.requestFocus();
                }else{
                    txtStaffAddress.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void ContactKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[0-9][-]?[0-9]*$";
            String typeText = txtStaffContact.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtStaffContact.setStyle("-fx-text-fill:#474787");
                    txtStaffEducation.requestFocus();
                }else{
                    txtStaffContact.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void EducationKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,20}$";
            String typeText = txtStaffEducation.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtStaffEducation.setStyle("-fx-text-fill:#474787");
                    txtPosition.requestFocus();
                }else{
                    txtStaffEducation.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void PositionKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,30}$";
            String typeText = txtPosition.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtPosition.setStyle("-fx-text-fill:#474787");
                    txtSalary.requestFocus();
                }else{
                    txtPosition.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void SalaryKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[1-9][0-9]*([.][0-9]{2})?$";
            String typeText = txtSalary.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtSalary.setStyle("-fx-text-fill:#474787");
                    btnStaff.setDisable(false);
                }else{
                    txtSalary.setStyle("-fx-text-fill: red");
                    btnStaff.setDisable(true);
                }
            }
        }
    }
}
