package controller;

import com.jfoenix.controls.JFXTextField;
import controller.Controller.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class MakeClientInvoiceController extends clientController{
    public Label lblTime;
    public Label lblDate;
    public AnchorPane invoiceContext;
    public JFXTextField txtClientName;
    public ComboBox cmbClientIds;
    public JFXTextField txtClientAddress;
    public JFXTextField txtDueDate;
    public JFXTextField txtServiceType;
    public ComboBox cmbTreatments;
    public ComboBox cmbRentItems;
    public ComboBox cmbDressingEVENTS;
    public ComboBox cmbProducts;
    public JFXTextField txtServiceName;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtPrice;
    public JFXTextField txtQty;
    public JFXTextField txtClientContact;
    public TableView <CartTm> tblInvoice;
    public Label lblTotal;
    static ArrayList<Client> clientList = new ArrayList();
    public TableColumn colServiceId;
    public TableColumn colServiceName;
    public TableColumn colQtyOnHand;
    public TableColumn colPrice;
    public TableColumn colQty;
    public TableColumn colDueDate;
    public TextField txtInvoice;
    public String cmbValue;
    public int SelectedCmb;
    public TableColumn colTotal;
    public DatePicker txtDueDatePicker;

    public void initialize() {
        loadDateAndTime();

        colServiceId.setStyle("-fx-alignment:CENTER;");
        colPrice.setStyle("-fx-alignment:CENTER;");
        colQtyOnHand.setStyle("-fx-alignment:CENTER;");
        colQty.setStyle("-fx-alignment:CENTER;");
        colDueDate.setStyle("-fx-alignment:CENTER;");

        colServiceId.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colServiceName.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colQtyOnHand.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colPrice.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colQty.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDueDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTotal.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");


        colServiceId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        colServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("cost"));

        try {
            setInvoiceId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbClientIds.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setClientData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });


        cmbTreatments.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        textFieldClear();
                        cmbValue=((String) newValue);
                        SelectedCmb=1;
                        setTreatmentDate((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        cmbRentItems.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        textFieldClear();
                        cmbValue=((String) newValue);
                        SelectedCmb=2;

                        setRentItemData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        cmbDressingEVENTS.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        textFieldClear();
                        cmbValue=((String) newValue);
                        SelectedCmb=3;

                        setDressingEventData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        cmbProducts.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        textFieldClear();
                        cmbValue=((String) newValue);
                        SelectedCmb=4;

                        setProductsData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        try {
            loadClientIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            loadTreatmentIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            loadRentItemIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            loadDressingEventIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            loadProductIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setInvoiceId() throws SQLException, ClassNotFoundException {
            txtInvoice.setText(new InvoiceController().getInvoiceIds());
        }


    private void setProductsData(String productCode) throws SQLException, ClassNotFoundException {
        Product p1 = new productController().getProduct(productCode);
        if (p1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result");
        } else {
            txtServiceName.setText(p1.getDescription());
            txtQtyOnHand.setText(String.valueOf(p1.getQtyOnHand()));
            txtPrice.setText(String.valueOf(p1.getPrice()));
        }
    }

    private void setDressingEventData(String dressingEventCode) throws SQLException, ClassNotFoundException {
        DressingEvent d1 = new dressingEventController().getDressingEvent(dressingEventCode);
        if (d1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result");
        } else {
            txtServiceName.setText(d1.getDescription());
            txtQtyOnHand.setText("0");
            txtQty.setText("0");
            txtPrice.setText(String.valueOf(d1.getPrice()));
        }

    }

    private void setRentItemData(String rentItemCode) throws SQLException, ClassNotFoundException {
        RentItem r1 = new rentItemController().getRentItem(rentItemCode);
        if (r1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result");
        } else {
            txtServiceName.setText(r1.getDescription());
            txtQtyOnHand.setText(String.valueOf(r1.getQtyOnHand()));
            txtPrice.setText(String.valueOf(r1.getPrice()));
        }
    }

    private void setTreatmentDate(String treatmentCode) throws SQLException, ClassNotFoundException {
        Treatment t1 = new treatmentController().getTreatment(treatmentCode);
        if (t1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result");
        } else {
            txtServiceName.setText(t1.getDescription());
            txtQtyOnHand.setText("0");
            txtQty.setText("0");
            txtPrice.setText(t1.getPrice());
        }

    }
    private void setClientData(String clientId) throws SQLException, ClassNotFoundException {
        Client c1 = new clientController().getClient(clientId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result");
        } else {
           txtClientName.setText(c1.getClientName());
           txtClientAddress.setText(c1.getClientAddress());
           txtClientContact.setText(c1.getClientContact());
           txtServiceType.setText(c1.getServiceType());
        }
    }

    private void loadProductIds() throws SQLException, ClassNotFoundException {
       ArrayList<String> productIds = new productController().getProductIds();
        productIds.add("None");
        cmbProducts.getItems().clear();
        cmbProducts.getItems().addAll(productIds);
    }

    private void loadDressingEventIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> dressingEventIds = new dressingEventController().getDressingEventIds();
        dressingEventIds.add("None");
        cmbDressingEVENTS.getItems().clear();
        cmbDressingEVENTS.getItems().addAll(dressingEventIds);

    }

    private void loadRentItemIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> rentItemIds = new rentItemController().getRentItemIds();
        rentItemIds.add("None");
        cmbRentItems.getItems().clear();
        cmbRentItems.getItems().addAll(rentItemIds);
    }

    private void loadTreatmentIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> treatmentIds = new treatmentController().getTreatmentIds();
        treatmentIds.add("None");
        cmbTreatments.getItems().clear();
        cmbTreatments.getItems().addAll(treatmentIds);

    }

    private void loadClientIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> clientIds = new clientController().getClientIds();
        cmbClientIds.getItems().clear();
        cmbClientIds.getItems().addAll(clientIds);
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
        Stage window = (Stage) invoiceContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardCashier.fxml"))));
        window.centerOnScreen();
    }

    ObservableList<CartTm> obList = FXCollections.observableArrayList();
    public void addtoLitOnAction(ActionEvent actionEvent) {
        String serviceName = txtServiceName.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double price = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        String dueDate= String.valueOf(txtDueDatePicker.getValue());
        double cost = (qty * price);

        if (qtyOnHand < qty) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        if (SelectedCmb == 1) {
            CartTm tm = new CartTm(
                cmbValue,
                serviceName,
                0,
                price,
                0,
                "-----",
                price
            );
            int rowNumber = isExists(tm);

            if ( rowNumber==-1) {
                obList.add(tm);
            } else {
                new Alert(Alert.AlertType.WARNING, "Treatment can not be duplicate").show();
               /* CartTm temp = obList.get(rowNumber);
                CartTm newTm = new CartTm(
                        temp.getServiceId(),
                        temp.getServiceName(),
                        0,
                        price,
                        0,
                        "-----",
                        price+temp.getCost()
                );*/

                //obList.remove(rowNumber);
                //obList.add(newTm);
            }
                tblInvoice.setItems(obList);
            calculate();
        }
        if(SelectedCmb==2){
            CartTm tm = new CartTm(
                    cmbValue,
                    serviceName,
                    qtyOnHand,
                    price,
                    qty,
                    dueDate,
                    cost
            );int rowNumber = isExists(tm);

            if ( rowNumber==-1) {
                obList.add(tm);
            } else {
                CartTm temp = obList.get(rowNumber);
                CartTm newTm = new CartTm(
                        temp.getServiceId(),
                        temp.getServiceName(),
                        temp.getQtyOnHand(),
                        temp.getPrice(),
                        temp.getQty(),
                        temp.getDueDate(),
                        cost+temp.getCost()
                );

                obList.remove(rowNumber);
                obList.add(newTm);
            }
            tblInvoice.setItems(obList);
            calculate();

        }
        if(SelectedCmb==3){
        CartTm tm = new CartTm(
                cmbValue,
                serviceName,
                0,
                price,
                0,
                "-----",
                price
        );int rowNumber = isExists(tm);

        if ( rowNumber==-1) {
            obList.add(tm);
        } else {
            CartTm temp = obList.get(rowNumber);
            CartTm newTm = new CartTm(
                    temp.getServiceId(),
                    temp.getServiceName(),
                    0,
                    price,
                    0,
                    "-----",
                    price + temp.getCost()
            );

            obList.remove(rowNumber);
            obList.add(newTm);
        }
            tblInvoice.setItems(obList);
            calculate();

        }
        if(SelectedCmb==4) {
            CartTm tm = new CartTm(
                    cmbValue,
                    serviceName,
                    qtyOnHand,
                    price,
                    qty,
                    "-----",
                    cost
            );
            int rowNumber = isExists(tm);

            if (rowNumber == -1) {
                obList.add(tm);
            } else {
                CartTm temp = obList.get(rowNumber);
                CartTm newTm = new CartTm(
                        temp.getServiceId(),
                        temp.getServiceName(),
                        temp.getQtyOnHand(),
                        temp.getPrice(),
                        temp.getQty(),
                        "null",
                        cost + temp.getCost()
                );

                obList.remove(rowNumber);
                obList.add(newTm);
            }

                tblInvoice.setItems(obList);
                calculate();

        }
       /* int rowNumber = isExists(tm);

        if ( rowNumber==-1) {
            obList.add(tm);
        } else {
            CartTm temp = obList.get(rowNumber);
            CartTm newTm = new CartTm(
                    temp.getServiceId(),
                    temp.getServiceName(),
                    0,
                    price,
                    0,
                    "-----",
                    cost+temp.getCost()
            );

            obList.remove(rowNumber);
            obList.add(newTm);*/

    }

    private int isExists(CartTm tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getServiceId().equals(obList.get(i).getServiceId())){
                return i;
            }
        }
        return -1;
    }
    void calculate() {
        double ttl = 0;
        for (CartTm tm : obList
        ) {
            ttl += tm.getCost();
        }
        lblTotal.setText(ttl+"/=");
    }
    public void textFieldClear(){
        txtServiceName.clear();
        txtQty.clear();
        txtQtyOnHand.clear();
        txtPrice.clear();
    }

    public void totalOnAction(ActionEvent actionEvent) {
    }

    public void confirmInvoiceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<InvoiceDetail> invoices = new ArrayList<>();
        for (CartTm tempTm : obList
        ) {
            invoices.add(
                    new InvoiceDetail(
                            tempTm.getServiceId(),
                            tempTm.getServiceName(),
                            tempTm.getQty(),
                            tempTm.getCost(),
                            tempTm.getDueDate()

                    )
            );

        }

        Invoice invoice = new Invoice(
                txtInvoice.getText(),
                (String) cmbClientIds.getValue(),
                lblDate.getText(),
                lblTime.getText(),
                invoices

        );
        if (new InvoiceController().placeInvoice(invoice)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
            showInvoice();
            setInvoiceId();
            clearText();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }

    private void clearText() {
        cmbClientIds.setValue("");
        cmbTreatments.setValue("");
        cmbRentItems.setValue("");
        cmbDressingEVENTS.setValue("");
        cmbProducts.setValue("");
        txtClientName.setText("");
        txtClientAddress.setText("");
        txtClientContact.setText("");
        txtServiceType.setText("");
        txtServiceName.setText("");
        txtQtyOnHand.setText("");
        txtPrice.setText("");
        txtQty.setText("");

        for ( int i = 0; i<tblInvoice.getItems().size(); i++) {
            tblInvoice.getItems().clear();
        }
    }

    private void showInvoice() {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/BeanReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            ObservableList<CartTm> items = tblInvoice.getItems();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanArrayDataSource(items.toArray()));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
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
                }else{
                    txtQty.setStyle("-fx-text-fill: red");
                }
            }
        }

    }
}
