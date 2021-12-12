package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.Controller.clientController;
import controller.Controller.dressingEventController;
import controller.Controller.salonItemController;
import controller.Controller.treatmentController;
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
import model.Client;
import model.DressingEvent;
import model.Treatment;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class TreatmentController extends treatmentController {
    public JFXTextField txtTreatmentCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPrice;
    public TableView tblTreatment;
    public AnchorPane treatmentContext;
    public Label lblDate;
    public Label lblTime;
    public TableColumn colTreatmentCode;
    public TableColumn colDescription;
    public TableColumn colPrice;
    public TableColumn colDate;
    public TableColumn colTime;
    public TextField txtSearch;
    public TextField lblSalonItemId;
    public JFXButton btnTreatments;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();

        colDate.setStyle("-fx-alignment:CENTER;");
        colTime.setStyle("-fx-alignment:CENTER;");
        colPrice.setStyle("-fx-alignment:CENTER;");

        colTreatmentCode.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDescription.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colPrice.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTime.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");

        colTreatmentCode.setCellValueFactory(new PropertyValueFactory<>("treatmentCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        setTreatmentsToTable(new treatmentController().getAllTreatment());

        setTreatmentId();

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });
        btnTreatments.setDisable(true);
    }

    private void setTreatmentId() throws SQLException, ClassNotFoundException {
        lblSalonItemId.setText(new treatmentController().getTreatmentsIds());
    }


    private void setTreatmentsToTable(ArrayList<Treatment> treatments) {
        ObservableList<Treatment> obList = FXCollections.observableArrayList();
        treatments.forEach(e -> {
            obList.add(
                    new Treatment(e.getTreatmentCode(), e.getDescription(), e.getPrice(), e.getDate(),e.getTime()));
        });
        tblTreatment.setItems(obList);
    }


    public void treatmentAddOnAction(ActionEvent actionEvent) {
        try {
            Treatment t1 = new Treatment(
                    lblSalonItemId.getText(),
                    txtDescription.getText(),
                    txtPrice.getText(),
                    lblDate.getText(),
                    lblTime.getText()
            );

            if (new treatmentController().saveTreatment(t1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                setTreatmentId();
                clearText();
            }else
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();

            setTreatmentsToTable(new treatmentController().getAllTreatment());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void clearText() {
        txtDescription.setText("");
        txtPrice.setText("");
    }

    public void treatmentUpdateOnAction(ActionEvent actionEvent) {
        try {
            Treatment t = new Treatment(
                    txtTreatmentCode.getText(),
                    txtDescription.getText(),
                    txtPrice.getText(),
                    null,
                    null
            );


            if (new treatmentController().updateTreatment(t)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                setTreatmentsToTable(new treatmentController().getAllTreatment());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            new Alert(Alert.AlertType.WARNING, e.getMessage());
        }
    }


    public void treatmentRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new treatmentController().deleteTreatment(txtTreatmentCode.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

       setTreatmentsToTable(new treatmentController().getAllTreatment());

    }

    public void selectTreatmentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        txtDescription.requestFocus();
        String treatmentCode = txtTreatmentCode.getText();

        Treatment t1= new treatmentController().getTreatment(treatmentCode);
        if (t1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(t1);

        }
    }

    private void setData(Treatment t1) {
        txtTreatmentCode.setText(t1.getTreatmentCode());
        txtDescription.setText(t1.getDescription());
        txtPrice.setText(String.valueOf(t1.getPrice()));
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
        Stage window = (Stage) treatmentContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardAdmin.fxml"))));
        window.centerOnScreen();
    }

    public void searchOnAction(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value){
        try {
            ArrayList<Treatment> treatments = treatmentController.searchTreatment(value);
            ObservableList<Treatment> obList = FXCollections.observableArrayList();
            for (Treatment treatment:treatments) {
                obList.add(new Treatment(
                        treatment.getTreatmentCode(),
                        treatment.getDescription(),
                        treatment.getPrice(),
                        treatment.getDate(),
                        treatment.getTime()
                ));
            }
            tblTreatment.getItems().setAll(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void moveToPrice(ActionEvent actionEvent) {
        txtPrice.requestFocus();
    }

    public void DescriptionKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,40}$";
            String typeText = txtDescription.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches) {
                    txtDescription.setStyle("-fx-text-fill:#474787");
                    txtPrice.requestFocus();
                } else {
                    txtPrice.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void PriceKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,20}$";
            String typeText = txtDescription.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches) {
                    txtPrice.setStyle("-fx-text-fill:#474787");
                    btnTreatments.setDisable(false);
                } else {
                    txtPrice.setStyle("-fx-text-fill: red");
                    btnTreatments.setDisable(true);
                }
            }
        }
    }
}
