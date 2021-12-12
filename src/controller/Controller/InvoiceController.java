package controller.Controller;

import db.DbConnection;
import model.Invoice;
import model.InvoiceDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceController {

    public String getInvoiceIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT invoiceId FROM Invoice ORDER BY invoiceId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "I-00"+tempId;
            }else if(tempId<=99){
                return "I-0"+tempId;
            }else{
                return "I-"+tempId;
            }

        }else{
            return "I-001";
        }
    }

    public boolean placeInvoice(Invoice invoice) throws SQLException, ClassNotFoundException {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.
                    prepareStatement("INSERT INTO Invoice VALUES(?,?,?,?)");

            stm.setObject(1, invoice.getInvoiceId());
            stm.setObject(2, invoice.getcId());
            stm.setObject(3,invoice.getInvoiceDate());
            stm.setObject(4,invoice.getTime());

            if (stm.executeUpdate() > 0) {
                if (saveInvoiceDetail(invoice.getInvoiceId(), invoice.getInvoices())) {
                    con.commit();
                    return true;
                }else  {
                    con.rollback();
                }
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally{
            con.setAutoCommit(true);        }
        return false;
    }

    private boolean saveInvoiceDetail(String inCode, ArrayList<InvoiceDetail> invoices) throws SQLException, ClassNotFoundException {
        for(InvoiceDetail temp : invoices
        ) {
            PreparedStatement stm = DbConnection.getInstance().
                    getConnection().
                    prepareStatement("INSERT INTO `Invoice Detail` VALUES(?,?,?,?,?,?,?,?,?)");
            stm.setObject(1,inCode);
            stm.setObject(2,temp.getServiceId());
            stm.setObject(3,temp.getServiceId());
            stm.setObject(4,temp.getServiceId());
            stm.setObject(5,temp.getServiceId());
            stm.setObject(6, temp.getServiceName());
            stm.setObject(7, temp.getInvoiceQty());
            stm.setObject(8,temp.getCost());
            stm.setObject(9,temp.getInvoiceDueDate());

            if (stm.executeUpdate() > 0) {

            } else {
                return false;
            }
        }
        return true;

    }

}
