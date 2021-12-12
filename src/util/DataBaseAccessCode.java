package util;

import db.DbConnection;
import model.Invoice;
import model.Invoice1;
import model.InvoiceDetail1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseAccessCode {

    public ArrayList<Invoice1> getAllInvoices() throws SQLException, ClassNotFoundException {
        ArrayList<Invoice1> list = new ArrayList();
        ResultSet resultSet = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT c.clientId, c.clientName, i.invoiceId, i.invoiceDate, i.time FROM  Client c JOIN Invoice i ON i.cId=c.clientId")
                .executeQuery();
        while (resultSet.next()){
            list.add(
                    new Invoice1(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5))
            );
        }
        return  list;
    }

    public ArrayList<InvoiceDetail1> getAllInvoiceDetails(String invoiceId) throws SQLException, ClassNotFoundException {
        ArrayList<InvoiceDetail1> details = new ArrayList<>();
        ResultSet resultSet = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT tCode,serviceName,invoiceQty,cost,invoiceDueDate FROM `Invoice Detail` WHERE inCode='" + invoiceId + "'").executeQuery();

        while (resultSet.next()){
            details.add(
                    new InvoiceDetail1(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getDouble(4),
                            resultSet.getString(5)
                    )

            );
        }
        return details;
    }
}
