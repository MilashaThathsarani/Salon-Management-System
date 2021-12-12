package controller;

import controller.Controller.clientController;
import controller.Controller.schduleController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Client;
import model.Invoice;
import model.Invoice1;
import model.InvoiceTm;
import util.DataBaseAccessCode;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleController extends schduleController {

    public Label lblDate;
    public Label lblTime;
    public AnchorPane scheduleContext;
    public TableView<InvoiceTm> tblSchedule;
    public TableColumn colClientId;
    public TableColumn colClientName;
    public TableColumn ColInvoicceId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TextField txtSearch;

    public void initialize(){
        loadDateAndTime();
        colDate.setStyle("-fx-alignment:CENTER;");
        colTime.setStyle("-fx-alignment:CENTER;");
        colClientId.setStyle("-fx-alignment:CENTER;");
        ColInvoicceId.setStyle("-fx-alignment:CENTER;");

        colClientId.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colClientName.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        ColInvoicceId.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTime.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");

        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        ColInvoicceId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));


        try {

            loadAllData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblSchedule.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            try {
                loadDetailUi(newValue.getInvoiceId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

       /* txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    search(txtSearch.getText());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }

    private void loadDetailUi(String invoiceId) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Detail.fxml"));
        Parent load = loader.load();
        DetailController controller=loader.getController();
        controller.loadAllData(invoiceId);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
        stage.centerOnScreen();
    }

    private void loadAllData() throws SQLException, ClassNotFoundException {
        ObservableList<InvoiceTm> tmList = FXCollections.observableArrayList();
        for (Invoice1 tempInvoice:new DataBaseAccessCode().getAllInvoices()
             ) {
            tmList.add(
                    new InvoiceTm(tempInvoice.getClientId(),
                            tempInvoice.getClientName(),
                            tempInvoice.getInvoiceId(),
                            tempInvoice.getDate(),
                            tempInvoice.getTime())
            );
            
        }
        tblSchedule.setItems(tmList);
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
        Stage window = (Stage) scheduleContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardAdmin.fxml"))));
        window.centerOnScreen();
    }

   public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       // search(txtSearch.getText());
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
      /*  ArrayList<InvoiceTm> invoiceTms = schduleController.searchClient(value);
        ObservableList<InvoiceTm> obList = FXCollections.observableArrayList();
        for (InvoiceTm invoiceTm:invoiceTms) {
            obList.add(new InvoiceTm(
                    invoiceTm.getClientId(),
                    invoiceTm.getClientName(),
                    invoiceTm.getInvoiceId(),
                    invoiceTm.getDate(),
                    invoiceTm.getTime()

            ));
        }
        tblSchedule.getItems().setAll(obList);*/
    }
}
