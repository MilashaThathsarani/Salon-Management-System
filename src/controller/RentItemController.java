package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.Controller.clientController;
import controller.Controller.rentItemController;
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
import model.RentItem;
import model.Treatment;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class RentItemController extends rentItemController {
    public JFXTextField txtRentItemCode;
    public JFXTextField Description;
    public JFXTextField txtPrice;
    public JFXTextField txtQtyOnHand;
    public AnchorPane rentItemContext;
    public Label lblDate;
    public Label lblTime;
    public TableView <RentItem> tblRentItem;
    public TableColumn colRentItemCode;
    public TableColumn colDescription;
    public TableColumn colPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colDate;
    public TableColumn colTime;
    public TextField txtSearch;
    public TextField lbRentItemId;
    public JFXButton btnRentItem;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();
        colDate.setStyle("-fx-alignment:CENTER;");
        colTime.setStyle("-fx-alignment:CENTER;");
        colQtyOnHand.setStyle("-fx-alignment:CENTER;");
        colPrice.setStyle("-fx-alignment:CENTER;");

        colRentItemCode.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDescription.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colPrice.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colQtyOnHand.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTime.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");

        colRentItemCode.setCellValueFactory(new PropertyValueFactory<>("rentItemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        setRentItemsToTable(new rentItemController().getAllRentItem());

        setRentItemId();

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
        btnRentItem.setDisable(true);
    }

    private void setRentItemId() throws SQLException, ClassNotFoundException {
        lbRentItemId.setText(new rentItemController().getRentItemsIds());
    }

    private void setRentItemsToTable(ArrayList<RentItem> rentItems) {
        ObservableList<RentItem> obList = FXCollections.observableArrayList();
        rentItems.forEach(e -> {
            obList.add(
                    new RentItem(e.getRentItemCode(), e.getDescription(), e.getPrice(), e.getQtyOnHand(), e.getDate(),e.getTime()));
        });
        tblRentItem.setItems(obList);
    }

    public void clientRenItemOnAction(ActionEvent actionEvent) {
        try {
           RentItem r1 = new RentItem(
                    lbRentItemId.getText(),
                    Description.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    parseInt(txtQtyOnHand.getText()),
                    lblDate.getText(),
                    lblTime.getText()
            );

            if (new rentItemController().saveRentItem(r1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                setRentItemId();
                clearText();
            }else
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();

            setRentItemsToTable(new rentItemController().getAllRentItem());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void clearText() {
        Description.setText("");
        txtPrice.setText("");
        txtQtyOnHand.setText("");
    }

    public void updateRentItemOnAction(ActionEvent actionEvent) {
        try {
            RentItem r = new RentItem(
                    txtRentItemCode.getText(),
                    Description.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    parseInt(txtQtyOnHand.getText()),
                    null,
                    null
            );


            if (new rentItemController().updateRentItem(r)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                setRentItemsToTable(new rentItemController().getAllRentItem());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            new Alert(Alert.AlertType.WARNING, e.getMessage());
        }
    }

    public void rentItemRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new rentItemController().deleteRentItem(txtRentItemCode.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

       setRentItemsToTable(new rentItemController().getAllRentItem());
    }


    public void selectRentItemtOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String rentItemCode = txtRentItemCode.getText();

        RentItem r1= new rentItemController().getRentItem(rentItemCode);
        if (r1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(r1);
        }
    }

    private void setData(RentItem r1) {
        txtRentItemCode.setText(r1.getRentItemCode());
        Description.setText(r1.getDescription());
        txtPrice.setText(String.valueOf(r1.getPrice()));
        txtQtyOnHand.setText(String.valueOf(r1.getQtyOnHand()));
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
        Stage window = (Stage) rentItemContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardAdmin.fxml"))));
        window.centerOnScreen();
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search(txtSearch.getText());
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
        ArrayList<RentItem> rentItems = rentItemController.searchRentItem(value);
        ObservableList<RentItem> obList = FXCollections.observableArrayList();
        for (RentItem rentItem:rentItems) {
            obList.add(new RentItem(
                    rentItem.getRentItemCode(),
                    rentItem.getDescription(),
                    rentItem.getPrice(),
                    rentItem.getQtyOnHand(),
                    rentItem.getDate(),
                    rentItem.getTime()
            ));
        }
        tblRentItem.getItems().setAll(obList);
    }

    public void DescriptionKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,40}$";
            String typeText = Description.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches) {
                    Description.setStyle("-fx-text-fill:#474787");
                    txtPrice.requestFocus();
                } else {
                    Description.setStyle("-fx-text-fill: red");
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
                    txtQtyOnHand.requestFocus();
                } else {
                    txtPrice.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void QtyOnHandKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[0-9][0-9]*$";
            String typeText = txtQtyOnHand.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtQtyOnHand.setStyle("-fx-text-fill:#474787");
                    btnRentItem.setDisable(false);
                }else{
                    txtQtyOnHand.setStyle("-fx-text-fill: red");
                    btnRentItem.setDisable(true);
                }
            }
        }
    }
}
