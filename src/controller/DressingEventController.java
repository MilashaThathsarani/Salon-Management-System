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
import model.Client;
import model.DressingEvent;
import model.RentItem;
import model.Treatment;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class DressingEventController extends dressingEventController {
    public JFXTextField txtDressingEventCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPrice;
    public AnchorPane dressingEventContext;
    public Label lblDate;
    public Label lblTime;
    public TableView <DressingEvent> tblDressingEvent;
    public TableColumn colDressingEvent;
    public TableColumn colDescription;
    public TableColumn colPrice;
    public TableColumn colDate;
    public TableColumn colTime;
    public TextField txtSearch;
    public TextField lbRentItemId;
    public JFXButton btnDressingEvent;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();
        colDate.setStyle("-fx-alignment:CENTER;");
        colTime.setStyle("-fx-alignment:CENTER;");
        colPrice.setStyle("-fx-alignment:CENTER;");

        colDressingEvent.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDescription.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colPrice.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTime.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");

        colDressingEvent.setCellValueFactory(new PropertyValueFactory<>("dressingEventCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        setDressingEventsToTable(new dressingEventController().getAllEvents());

        setRentItemId();

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });
        btnDressingEvent.setDisable(true);
    }

    private void setRentItemId() throws SQLException, ClassNotFoundException {
        lbRentItemId.setText(new dressingEventController().getDressingEventItemIds());
    }

    private void setDressingEventsToTable(ArrayList<DressingEvent> events) {
        ObservableList<DressingEvent> obList = FXCollections.observableArrayList();
        events.forEach(e -> {
            obList.add(
                    new DressingEvent(e.getDressingEventCode(), e.getDescription(),e.getPrice(), e.getDate(),e.getTime()));
        });
        tblDressingEvent.setItems(obList);
    }


    public void dressingEventAddOnAction(ActionEvent actionEvent) {
        try {
            DressingEvent d1 = new DressingEvent(
                    lbRentItemId.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    lblDate.getText(),
                    lblTime.getText()
            );

            if (new dressingEventController().saveEvent(d1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                setRentItemId();
                clearText();
            }else
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();

            setDressingEventsToTable(new dressingEventController().getAllEvents());

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

    public void dressingEventUpdateOnAction(ActionEvent actionEvent) {
        try {
            DressingEvent d = new DressingEvent(
                    txtDressingEventCode.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    null,
                    null
            );


            if (new dressingEventController().updateEvent(d)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                setDressingEventsToTable(new dressingEventController().getAllEvents());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            new Alert(Alert.AlertType.WARNING, e.getMessage());
        }
    }

    public void dressingEventRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new dressingEventController().deleteEvent(txtDressingEventCode.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

        setDressingEventsToTable(new dressingEventController().getAllEvents());
    }

    public void selectEventOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String  dressingEventCode = txtDressingEventCode.getText();

        DressingEvent d1= new dressingEventController().getDressingEvent( dressingEventCode);
        if (d1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(d1);
        }
    }

    private void setData(DressingEvent d1) {
        txtDressingEventCode.setText(d1.getDressingEventCode());
        txtDescription.setText(d1.getDescription());
        txtPrice.setText(String.valueOf(d1.getPrice()));
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
        Stage window = (Stage) dressingEventContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardAdmin.fxml"))));
        window.centerOnScreen();
    }

    public void searchOnAction(ActionEvent actionEvent) {
      search(txtSearch.getText());
    }

    private void search(String value){
        try {
            ArrayList<DressingEvent> events = dressingEventController.searchEvents(value);
            ObservableList<DressingEvent> obList = FXCollections.observableArrayList();
            for (DressingEvent event:events) {
                obList.add(new DressingEvent(
                        event.getDressingEventCode(),
                        event.getDescription(),
                        event.getPrice(),
                        event.getDate(),
                        event.getTime()
                ));
            }
            tblDressingEvent.getItems().setAll(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void DesriptionKey(KeyEvent keyEvent) {
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
                    txtDescription.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void PriceKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[1-9][0-9]*([.][0-9]{2})?$";
            String typeText = txtPrice.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches) {
                    txtPrice.setStyle("-fx-text-fill:#474787");
                    btnDressingEvent.setDisable(false);
                } else {
                    txtPrice.setStyle("-fx-text-fill: red");
                    btnDressingEvent.setDisable(true);
                }
            }
        }
    }
    }

