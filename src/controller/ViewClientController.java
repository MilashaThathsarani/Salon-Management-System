package controller;

import controller.Controller.clientController;
import controller.Controller.rentItemController;
import controller.Controller.viewClientController;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Client;
import model.RentItem;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class ViewClientController extends viewClientController {
    public AnchorPane viewClientContext;
    public Label lblDate;
    public Label lblTime;
    public TableView tblClient;
    public TableColumn colClientId;
    public TableColumn colClientName;
    public TableColumn colClientAddress;
    public TableColumn colClientContact;
    public TableColumn colClientServiceType;
    public TableColumn colDate;
    public TableColumn colTime;
    public TextField txtSearch;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();

        colDate.setStyle("-fx-alignment:CENTER;");
        colTime.setStyle("-fx-alignment:CENTER;");

        colClientId.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colClientName.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colClientAddress.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colClientContact.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colClientServiceType.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTime.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");


        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        colClientAddress.setCellValueFactory(new PropertyValueFactory<>("clientAddress"));
        colClientContact.setCellValueFactory(new PropertyValueFactory<>("clientContact"));
        colClientServiceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("clientDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        setClientsToTable(new viewClientController().getAllClient());

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
    }

    private void setClientsToTable(ArrayList<Client> clients) {
        ObservableList<Client> obList = FXCollections.observableArrayList();
        clients.forEach(e -> {
            obList.add(
                    new Client(e.getClientId(), e.getClientName(), e.getClientAddress(), e.getClientContact(), e.getServiceType(),e.getClientDate(),e.getTime()));
        });
        tblClient.setItems(obList);
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
        Stage window = (Stage) viewClientContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardAdmin.fxml"))));
        window.centerOnScreen();
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search(txtSearch.getText());
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
        ArrayList<Client> clients = viewClientController.searchClient(value);
        ObservableList<Client> obList = FXCollections.observableArrayList();
        for (Client client:clients) {
            obList.add(new Client(
                    client.getClientId(),
                    client.getClientName(),
                    client.getClientAddress(),
                    client.getClientContact(),
                    client.getServiceType(),
                    client.getClientDate(),
                    client.getTime()
            ));
        }
        tblClient.getItems().setAll(obList);
    }
}
