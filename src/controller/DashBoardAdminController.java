package controller;

import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Client;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DashBoardAdminController {

    public StackPane adminContext;
    public Label lblClient;
    public Label lblTreatment;
    public Label lblRentItem;
    public Label lblDressingEvent;
    public Label lblProducts;
    public Label lblStaff;
    public Label lblSupplier;
    public Label lblInvoice;
    public Label lblSalonItems;
    public AnchorPane dashboard;
    public Label lblInvoiceDetails;
    public Label lblTime;
    public Label lblDate;

    public void initialize() throws SQLException, ClassNotFoundException {
        DateAndTime();
        getClient();
        getTreatment();
        getRentItem();
        getDressingEvent();
        getProduct();
        getStaff();
        getSupplier();
        getInvoice();
        getSalonItem();
        getInvoiceDetails();
    }

    private void DateAndTime() {
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

    private void getInvoiceDetails() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT COUNT(*) FROM `Invoice Detail`").executeQuery();
        if (rst.next()) {
            String sum = rst.getString(1);
            lblInvoiceDetails.setText(sum);
        }
    }

    private void getSalonItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT COUNT(*) FROM `Salon Item`").executeQuery();
        if (rst.next()) {
            String sum = rst.getString(1);
            lblSalonItems.setText(sum);
        }
    }

    private void getInvoice() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT COUNT(*) FROM Invoice").executeQuery();
        if (rst.next()) {
            String sum = rst.getString(1);
            lblInvoice.setText(sum);
        }
    }

    private void getSupplier() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT COUNT(*) FROM Supplier").executeQuery();
        if (rst.next()) {
            String sum = rst.getString(1);
            lblSupplier.setText(sum);
        }
    }

    private void getStaff() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT COUNT(*) FROM Staff").executeQuery();
        if (rst.next()) {
            String sum = rst.getString(1);
            lblStaff.setText(sum);
        }
    }

    private void getProduct() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT COUNT(*) FROM Product").executeQuery();
        if (rst.next()) {
            String sum = rst.getString(1);
            lblProducts.setText(sum);
        }
    }

    private void getDressingEvent() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT COUNT(*) FROM `Dressing Event`").executeQuery();
        if (rst.next()) {
            String sum = rst.getString(1);
            lblDressingEvent.setText(sum);
        }
    }

    private void getRentItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT COUNT(*) FROM `Rent Item`").executeQuery();
        if (rst.next()) {
            String sum = rst.getString(1);
            lblRentItem.setText(sum);
        }
    }

    public Client getClient() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT COUNT(*) FROM Client").executeQuery();
        if (rst.next()) {
            String sum = rst.getString(1);
            lblClient.setText(sum);
        }
        return null;
    }

    private void getTreatment() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT COUNT(*) FROM Treatment").executeQuery();
        if (rst.next()) {
            String sum = rst.getString(1);
            lblTreatment.setText(sum);
        }
    }

    private void loadUi(String filename) throws IOException {
        URL resource = getClass().getResource("../view/" + filename + ".fxml");
        Parent load = FXMLLoader.load(resource);
        adminContext.getChildren().clear();
        adminContext.getChildren().add(load);
    }

    public void viewDetailOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ViewClient");
    }

    public void salonItemOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("SalonItem");
    }

    public void treatmentOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("Treatment");
    }

    public void rentItemOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("RentItem");
    }

    public void dressingEventOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("DressingEvent");
    }

    public void productOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("Product");
    }

    public void staffOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("Staff");
    }

    public void scheduleOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("Schedule");
    }

    public void incomeOnAction(ActionEvent actionEvent) throws IOException, JRException, SQLException, ClassNotFoundException { loadUi("Income");
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/sqlChart.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);

    }

    public void supplierOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("Supplier");
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) dashboard.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LogInForm.fxml"))));
        window.centerOnScreen();
    }
}
