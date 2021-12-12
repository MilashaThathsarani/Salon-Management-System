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
import model.Product;
import model.SalonItem;
import model.Supplier;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class SalonItemController extends salonItemController {
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public AnchorPane salonItemContext;
    public TableView tblSalonItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQtyOnHand;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colCost;
    public TableColumn colUnitPrice;
    public JFXTextField txtUnitPrice;
    public TextField txtSearch;
    public TextField lblSalonItemId;
    public JFXButton btnSalonItem;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();

        colDate.setStyle("-fx-alignment:CENTER;");
        colTime.setStyle("-fx-alignment:CENTER;");
        colUnitPrice.setStyle("-fx-alignment:CENTER;");
        colQtyOnHand.setStyle("-fx-alignment:CENTER;");
        colItemCode.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDescription.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colUnitPrice.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colQtyOnHand.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTime.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colCost.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");


        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        setItemsToTable(new salonItemController().getAllSalonItem());
        setSalonItemId();

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
        btnSalonItem.setDisable(true);
    }
    private void setSalonItemId() throws SQLException, ClassNotFoundException {
        lblSalonItemId.setText(new salonItemController().getSalonItemIds());
    }

    private void setItemsToTable(ArrayList<SalonItem> salonItems) {
        ObservableList<SalonItem> obList = FXCollections.observableArrayList();
        salonItems.forEach(e -> {
            obList.add(
                    new SalonItem(e.getItemCode(),e.getDescription(),e.getUnitPrice(),e.getQtyOnHand(),e.getDate(),e.getTime()));
        });
        tblSalonItem.setItems(obList);
    }

    public void selectItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode = txtItemCode.getText();

        SalonItem s1= new salonItemController().getSalonItem(itemCode);
        if (s1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(s1);
        }
    }

    private void setData(SalonItem s1) {
        txtItemCode.setText(s1.getItemCode());
        txtDescription.setText(s1.getDescription());
        txtUnitPrice.setText(String.valueOf(s1.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(s1.getQtyOnHand()));
    }

    public void itemAddOnAction(ActionEvent actionEvent) {
        try {
            SalonItem s1 = new SalonItem(
                    lblSalonItemId.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    parseInt(txtQtyOnHand.getText()),
                    lblDate.getText(),
                    lblTime.getText()
            );

            if (new salonItemController().saveSalonItem(s1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                setSalonItemId();
                clearText();
            }else
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();

            setItemsToTable(new salonItemController().getAllSalonItem());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void clearText() {
        txtDescription.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHand.setText("");
    }


    public void itemUpdateOnAction(ActionEvent actionEvent) {
        try {
            SalonItem s = new SalonItem(
                    txtItemCode.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    parseInt(txtQtyOnHand.getText()),
                    null,
                    null
            );


            if (new salonItemController().updateSalonItem(s)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                setItemsToTable(new salonItemController().getAllSalonItem());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            new Alert(Alert.AlertType.WARNING, e.getMessage());
        }
    }

    public void itemRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new salonItemController().deleteSalonItem(txtItemCode.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

        setItemsToTable(new salonItemController().getAllSalonItem());
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
        Stage window = (Stage) salonItemContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardAdmin.fxml"))));
        window.centerOnScreen();
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search(txtSearch.getText());
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
        ArrayList<SalonItem> salonItems = salonItemController.searchItem(value);
        ObservableList<SalonItem> obList = FXCollections.observableArrayList();
        for (SalonItem salonItem:salonItems) {
            obList.add(new SalonItem(
                    salonItem.getItemCode(),
                    salonItem.getDescription(),
                    salonItem.getUnitPrice(),
                    salonItem.getQtyOnHand(),
                    salonItem.getDate(),
                    salonItem.getTime()
            ));
        }
       tblSalonItem.getItems().setAll(obList);
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
                    txtUnitPrice.requestFocus();
                } else {
                    txtDescription.setStyle("-fx-text-fill: red");
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
                    btnSalonItem.setDisable(false);
                }else{
                    txtQtyOnHand.setStyle("-fx-text-fill: red");
                    btnSalonItem.setDisable(true);
                }
            }
        }
    }

    public void UnitPriceKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[1-9][0-9]*([.][0-9]{2})?$";
            String typeText = txtUnitPrice.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches) {
                    txtUnitPrice.setStyle("-fx-text-fill:#474787");
                    txtQtyOnHand.requestFocus();
                } else {
                    txtUnitPrice.setStyle("-fx-text-fill: red");
                }
            }
        }
    }
}
