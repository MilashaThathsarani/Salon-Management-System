package controller;

import controller.Controller.removeController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import util.DataBaseAccessCode;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
public class RemoveInvoiceController extends removeController {

    public TableView tblRemove;
    public TableColumn colInvoiceId;
    public TableColumn colClientId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TextField txtSearch;
    public TableColumn colName;
    public AnchorPane removeContext;
    public TableView tblInvoice;

    //int cartSelectedRowForRemove = -1;//

    public void initialize() throws SQLException, ClassNotFoundException {
        //loadDateAndTime();

        colDate.setStyle("-fx-alignment:CENTER;");
        colTime.setStyle("-fx-alignment:CENTER;");
        colInvoiceId.setStyle("-fx-alignment:CENTER;");
        colClientId.setStyle("-fx-alignment:CENTER;");

        colInvoiceId.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colClientId.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTime.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");

        colInvoiceId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("cId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        loadAllData();
        /*tblRemove.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });*/
        setInvoiceToTable(new removeController().getAllInvoice());
    }

    private void loadAllData() throws SQLException, ClassNotFoundException {
    }

    private void setInvoiceToTable(ArrayList<InvoiceDB> allInvoice) throws SQLException, ClassNotFoundException {
        ArrayList<InvoiceDB> invoiceDBS= new removeController().getAllInvoice();
        ObservableList<InvoiceDB> obList = FXCollections.observableArrayList();
        invoiceDBS.forEach(e -> {
            System.out.println(e.getcId());
            obList.add(
                    new InvoiceDB(
                            e.getInvoiceId(),
                            e.getcId(),
                            e.getInvoiceDate(),
                            e.getTime()
                    ));
        });
        tblInvoice.setItems(obList);

    }

   /* private void loadDateAndTime() {
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
    }*/

    public void invoiceRemoveOnAction(ActionEvent actionEvent) {
        /*if (cartSelectedRowForRemove== -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else {
            obList.remove(cartSelectedRowForRemove);
            calculate();
            tblRemove.refresh();
        }
    }
    ObservableList<InvoiceTm> obList = FXCollections.observableArrayList();*/

   /* private void calculate() {
    }*/
    }

    public void searchOnAction(ActionEvent actionEvent) {
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) removeContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardCashier.fxml"))));
        window.centerOnScreen();
    }
}
