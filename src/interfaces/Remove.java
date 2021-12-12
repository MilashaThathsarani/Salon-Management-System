package interfaces;

import model.Client;
import model.Invoice;
import model.InvoiceDB;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Remove {
    public ArrayList<InvoiceDB> getAllInvoice() throws SQLException, ClassNotFoundException;
}
