package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DetailTm;
import model.InvoiceDetail;
import model.InvoiceDetail1;
import util.DataBaseAccessCode;

import java.sql.SQLException;

public class DetailController {
    public TableView<DetailTm> tblInvoice;
    public TableColumn colServiceCode;
    public TableColumn colServiceName;
    public TableColumn colQty;
    public TableColumn colCost;
    public TableColumn colDueDate;
    public Label lblCost;
    public Label lblInvoiceId;

    public void initialize(){
        colServiceCode.setCellValueFactory(new PropertyValueFactory<>("serviceCode"));
        colServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("invoiceDueDate"));
    }

    public void loadAllData(String invoiceId){
        lblInvoiceId.setText(invoiceId);
        double total=0;
        try {
            ObservableList<DetailTm> tmList = FXCollections.observableArrayList();
            for (InvoiceDetail1 tempD :new DataBaseAccessCode().getAllInvoiceDetails(invoiceId)
                 ) {
                total+=tempD.getCost();
                tmList.add(new DetailTm(tempD.getServiceCode(),tempD.getServiceName(),tempD.getQty(),tempD.getCost(),tempD.getInvoiceDueDate()));
            }
            tblInvoice.setItems(tmList);
            lblCost.setText("Rs.  "+(total));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
