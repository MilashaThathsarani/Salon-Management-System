package controller.Controller;

import db.DbConnection;
import model.Client;
import model.InvoiceTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class schduleController {
    protected static ArrayList<InvoiceTm> searchClient(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM Invoice WHERE invoiceId LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<InvoiceTm> invoiceTms= new ArrayList<>();
        while (rst.next()) {
            invoiceTms.add(new InvoiceTm(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)

            ));
        }
        return invoiceTms;
    }
}
