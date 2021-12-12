package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.Controller.InvoiceController;
import controller.Controller.clientController;
import controller.Controller.rentItemController;
import controller.Controller.supplierController;
import db.DbConnection;
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
import model.RentItem;
import model.Supplier;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ClientSaveController extends clientController {

    public Label lblTime;
    public Label lblDate;
    public JFXTextField txtClientId;
    public JFXTextField txtClientName;
    public JFXTextField txtClientAddress;
    public JFXTextField txtClientContact;
    public ComboBox cmbServiceType;
    public TableColumn colClientId;
    public TableColumn colClientName;
    public TableColumn colClientContact;
    public TableColumn colServiceType;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableView <Client>colClient;
    public TableColumn colClientAddress;
    public AnchorPane clientContext;
    public JFXTextField txtServiceType;
    public TextField txtSearch;
    public TextField lblClientId;
    public JFXButton btnClient;

    /*LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z ]{3,40}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,50}$");
    Pattern contactPattern = Pattern.compile("^[0-9][-]?[0-9]*$");
    Pattern servicePattern = Pattern.compile("^[A-z ]{3,80}$");
    //Pattern salaryPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");*/

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();

        colDate.setStyle("-fx-alignment:CENTER;");
        colTime.setStyle("-fx-alignment:CENTER;");

        colClientId.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colClientName.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colClientAddress.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colClientContact.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colServiceType.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTime.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");

        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        colClientAddress.setCellValueFactory(new PropertyValueFactory<>("clientAddress"));
        colClientContact.setCellValueFactory(new PropertyValueFactory<>("clientContact"));
        colServiceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("clientDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        setClientsToTable(new clientController().getAllClient());

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
        setClientId();

        btnClient.setDisable(true);
    }


    /*private void storeValidations() {
        map.put(txtClientName, namePattern);
        map.put(txtClientAddress, addressPattern);
        map.put(txtClientContact, contactPattern);
        map.put(txtServiceType, servicePattern);
    }*/
    private void setClientId() throws SQLException, ClassNotFoundException {
        lblClientId.setText(new clientController().getClientsIds());
    }

    private void setClientsToTable(ArrayList<Client> clients) {
        ObservableList<Client> obList = FXCollections.observableArrayList();
        clients.forEach(e -> {
            obList.add(
                    new Client(e.getClientId(), e.getClientName(), e.getClientAddress(), e.getClientContact(), e.getServiceType(),e.getClientDate(),e.getTime()));
        });
        colClient.setItems(obList);
    }

    public void selectClientOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String clientId = txtClientId.getText();

        Client c= new clientController().getClient(clientId);
        if (c==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c);
        }

    }

    private void setData(Client c) {
        txtClientId.setText(c.getClientId());
        txtClientName.setText(c.getClientName());
        txtClientAddress.setText(c.getClientAddress());
        txtClientContact.setText(c.getClientContact());
        txtServiceType.setText(c.getServiceType());
    }


    public void clientAddOnAction(ActionEvent actionEvent) {
        try {
            Client c1 = new Client(
                    lblClientId.getText(),
                    txtClientName.getText(),
                    txtClientAddress.getText(),
                    txtClientContact.getText(),
                    txtServiceType.getText(),
                    lblDate.getText(),
                    lblTime.getText()
            );

            if (new clientController().saveClient(c1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                setClientId();
                clearText();
            }else
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();

            setClientsToTable(new clientController().getAllClient());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void clearText() {
        txtClientName.setText("");
        txtClientAddress.setText("");
        txtClientContact.setText("");
        txtServiceType.setText("");
    }

    public void clientUpdateOnAction(ActionEvent actionEvent) {
        try {
            Client c2 = new Client(
                    txtClientId.getText(),
                    txtClientName.getText(),
                    txtClientAddress.getText(),
                    txtClientContact.getText(),
                    txtServiceType.getText(),
                    lblDate.getText(),
                    lblTime.getText()
            );

            if (new clientController().updateClient(c2)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                setClientsToTable(new clientController().getAllClient());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            new Alert(Alert.AlertType.WARNING,e.getMessage());
        }
    }

    public void clientRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new clientController().deleteClient(txtClientId.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

       setClientsToTable(new clientController().getAllClient());
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

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search(txtSearch.getText());
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
        ArrayList<Client> clients = clientController.searchClient(value);
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
        colClient.getItems().setAll(obList);
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) clientContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardCashier.fxml"))));
        window.centerOnScreen();
    }

    public void ClientNameKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,40}$";
            String typeText = txtClientName.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtClientName.setStyle("-fx-text-fill:#474787");
                    txtClientAddress.requestFocus();
                }else{
                    txtClientName.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void ClientAddressKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z0-9/ ]{3,50}$";
            String typeText = txtClientAddress.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtClientAddress.setStyle("-fx-text-fill:#474787");
                    txtClientContact.requestFocus();
                }else{
                    txtClientAddress.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void ClientContactKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[0-9][-]?[0-9]*$";
            String typeText = txtClientContact.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtClientContact.setStyle("-fx-text-fill:#474787");
                    txtServiceType.requestFocus();
                }else{
                    txtClientContact.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void ServiceTypeKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,80}$";
            String typeText = txtServiceType.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtServiceType.setStyle("-fx-text-fill:#474787");
                    btnClient.setDisable(false);
                }else{
                    txtServiceType.setStyle("-fx-text-fill: red");
                    btnClient.setDisable(true);
                }
            }
        }
    }
}
