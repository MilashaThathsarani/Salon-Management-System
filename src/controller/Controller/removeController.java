package controller.Controller;

import db.DbConnection;
import interfaces.Remove;
import model.Invoice;
import model.InvoiceDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class removeController implements Remove {
    @Override
    public ArrayList<InvoiceDB> getAllInvoice() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Invoice");
        ResultSet rst = stm.executeQuery();
        ArrayList<InvoiceDB> invoiceDBS = new ArrayList<>();
        while (rst.next()) {
            invoiceDBS.add(new InvoiceDB(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)

            ));
        }
        return invoiceDBS;
    }
   /* public ArrayList<Invoice> getAllInvoice() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Invoice");
        ResultSet rst = stm.executeQuery();
        ArrayList<Invoice> invoices = new ArrayList<>();
        while (rst.next()) {
            invoices.add(new Invoice(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)

            ));
        }
        return invoices;
    }*/
}
