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
import model.RentItem;
import model.Staff;
import model.Supplier;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class SupplierController extends supplierController {
    public JFXTextField txtSupplierId;
    public JFXTextField txtSupplierName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtQty;
    public JFXTextField txtProductName;
    public AnchorPane supplierContext;
    public Label lblDate;
    public Label lblTime;
    public TableView tblSupplier;
    public TableColumn colSupplierId;
    public TableColumn colSupplierName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colProductName;
    public TableColumn colQty;
    public TableColumn colDate;
    public TableColumn colTime;
    public TextField txtSearch;
    public TextField lblSupplierId;
    public JFXButton btnSupplier;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();

        colDate.setStyle("-fx-alignment:CENTER;");
        colTime.setStyle("-fx-alignment:CENTER;");
        colQty.setStyle("-fx-alignment:CENTER;");

        colSupplierId.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colSupplierName.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colAddress.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colContact.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colProductName.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colQty.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTime.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");


        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("productQty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        setSuppliersToTable(new supplierController().getAllSuppliers());

        setSupplierId();

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
        btnSupplier.setDisable(true);
    }

    private void setSupplierId() throws SQLException, ClassNotFoundException {
        lblSupplierId.setText(new supplierController().getSupplierIds());
    }

    private void setSuppliersToTable(ArrayList<Supplier> suppliers) {
        ObservableList<Supplier> obList = FXCollections.observableArrayList();
        suppliers.forEach(e -> {
            obList.add(
                    new Supplier(e.getSupplierId(),e.getSupplierName(),e.getAddress(),e.getContact(),e.getProductName(),e.getProductQty(),e.getDate(),e.getTime()));
        });
        tblSupplier.setItems(obList);
    }

    public void selectSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String supplierId = txtSupplierId.getText();

        Supplier s1= new supplierController().getSupplier(supplierId);
        if (s1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(s1);
        }
    }

    private void setData(Supplier s1) {
        txtSupplierId.setText(s1.getSupplierId());
        txtSupplierName.setText(s1.getSupplierName());
        txtAddress.setText(s1.getAddress());
        txtContact.setText(s1.getContact());
        txtProductName.setText(s1.getProductName());
        txtQty.setText(String.valueOf(s1.getProductQty()));
    }

    public void supplierAddOnAction(ActionEvent actionEvent) {
        try {
            Supplier s1 = new Supplier(
                    lblSupplierId.getText(),
                    txtSupplierName.getText(),
                    txtAddress.getText(),
                    txtContact.getText(),
                    txtProductName.getText(),
                    parseInt(txtQty.getText()),
                    lblDate.getText(),
                    lblTime.getText()
            );

            if (new supplierController().saveSupplier(s1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                setSupplierId();
                clearText();
            }else
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();

            setSuppliersToTable(new supplierController().getAllSuppliers());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void clearText() {
        txtSupplierName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtQty.setText("");
        txtProductName.setText("");
    }

    public void supplierUpdateOnAction(ActionEvent actionEvent) {
        try {
            Supplier s = new Supplier(
                    txtSupplierId.getText(),
                    txtSupplierName.getText(),
                    txtAddress.getText(),
                    txtContact.getText(),
                    txtProductName.getText(),
                    parseInt(txtQty.getText()),
                    null,
                    null
            );


            if (new supplierController().updateSupplier(s)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                setSuppliersToTable(new supplierController().getAllSuppliers());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            new Alert(Alert.AlertType.WARNING, e.getMessage());
        }
    }


    public void supplierRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new supplierController().deleteSupplier(txtSupplierId.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

        setSuppliersToTable(new supplierController().getAllSuppliers());
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
        Stage window = (Stage) supplierContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardAdmin.fxml"))));
        window.centerOnScreen();
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search(txtSearch.getText());
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliers = supplierController.searchSupplier(value);
        ObservableList<Supplier> obList = FXCollections.observableArrayList();
        for (Supplier supplier:suppliers) {
            obList.add(new Supplier(
                supplier.getSupplierId(),
                supplier.getSupplierName(),
                supplier.getAddress(),
                supplier.getContact(),
                supplier.getProductName(),
                supplier.getProductQty(),
                supplier.getDate(),
                supplier.getTime()
            ));
        }
        tblSupplier.getItems().setAll(obList);
    }

    public void NameKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,40}$";
            String typeText = txtSupplierName.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches) {
                    txtSupplierName.setStyle("-fx-text-fill:#474787");
                    txtAddress.requestFocus();
                } else {
                    txtSupplierName.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void AddressKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z0-9/ ]{3,50}$";
            String typeText = txtAddress.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtAddress.setStyle("-fx-text-fill:#474787");
                    txtContact.requestFocus();
                }else{
                    txtAddress.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void ContactKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[0-9][-]?[0-9]*$";
            String typeText = txtContact.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtContact.setStyle("-fx-text-fill:#474787");
                    txtProductName.requestFocus();
                }else{
                    txtContact.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void ProductNameKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,50}$";
            String typeText = txtProductName.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtProductName.setStyle("-fx-text-fill:#474787");
                    txtQty.requestFocus();
                }else{
                    txtProductName.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void QtyKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[0-9][0-9]*$";
            String typeText = txtQty.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtQty.setStyle("-fx-text-fill:#474787");
                    btnSupplier.setDisable(false);
                }else{
                    txtQty.setStyle("-fx-text-fill: red");
                    btnSupplier.setDisable(true);
                }
            }
        }
    }
}
