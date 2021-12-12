package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DetailTm;
import model.InvoiceDetail1;
import util.DataBaseAccessCode;

import java.sql.SQLException;

public class ManageDetailController {
    public TableView tblMnageDetail;
    public TableColumn colServiceCode;
    public TableColumn colServiceName;
    public TableColumn colQty;
    public TableColumn colDate;
    public TableColumn colTime;
    public Label lblInvoiceId;
    public Label lblCost;

    public void initialize(){
        colDate.setStyle("-fx-alignment:CENTER;");
        colTime.setStyle("-fx-alignment:CENTER;");

        colServiceCode.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colServiceName.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colQty.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTime.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");

        colServiceCode.setCellValueFactory(new PropertyValueFactory<>("serviceCode"));
        colServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("invoiceDueDate"));
    }

    public void loadAllData(String invoiceId) {
        lblInvoiceId.setText(invoiceId);
        double total=0;
        try {
            ObservableList<DetailTm> tmList = FXCollections.observableArrayList();
            for (InvoiceDetail1 tempD :new DataBaseAccessCode().getAllInvoiceDetails(invoiceId)
            ) {
                total+=tempD.getCost();
                tmList.add(new DetailTm(tempD.getServiceCode(),tempD.getServiceName(),tempD.getQty(),tempD.getCost(),tempD.getInvoiceDueDate()));
            }
            tblMnageDetail.setItems(tmList);
            lblCost.setText("Rs.  "+(total));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
