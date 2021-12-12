package controller.Controller;

import db.DbConnection;
import interfaces.ProductInterFace;
import interfaces.SupplierInterFace;
import model.Product;
import model.RentItem;
import model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class supplierController implements SupplierInterFace {

    public String getSupplierIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT supplierId FROM Supplier ORDER BY supplierId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "SU-00" + tempId;
            } else if (tempId <= 99) {
                return "SU-0" + tempId;
            } else {
                return "SU-" + tempId;
            }

        } else {
            return "SU-001";
        }
    }


    protected static ArrayList<Supplier> searchSupplier(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM Supplier WHERE supplierName LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Supplier> suppliers= new ArrayList<>();
        while (rst.next()) {
            suppliers.add(new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getString(7),
                    rst.getString(8)
            ));
        }
        return suppliers;
    }

    @Override
    public boolean saveSupplier(Supplier s1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Supplier VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,s1.getSupplierId());
        stm.setObject(2,s1.getSupplierName());
        stm.setObject(3,s1.getAddress());
        stm.setObject(4,s1.getContact());
        stm.setObject(5,s1.getProductName());
        stm.setObject(6,s1.getProductQty());
        stm.setObject(7,s1.getDate());
        stm.setObject(8,s1.getTime());


        return stm.executeUpdate()>0;
    }

    @Override
    public boolean updateSupplier(Supplier s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Supplier SET supplierName=?,address=?,contact=?, productName=?, productQty=? WHERE supplierId=?");
        stm.setObject(1,s.getSupplierName());
        stm.setObject(2,s.getAddress());
        stm.setObject(3,s.getContact());
        stm.setObject(4,s.getProductName());
        stm.setObject(5,s.getProductQty());
        stm.setObject(6,s.getSupplierId());


        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Supplier WHERE supplierId='" + supplierId + "'").executeUpdate() > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Supplier getSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Supplier WHERE supplierId='" + supplierId + "'").
                executeQuery();
        if (rst.next()) {
            return new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getString(7),
                    rst.getString(8)

            );
        }else{
            return null;
        }
    }

    @Override
    public ArrayList<Supplier> getAllSuppliers() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier");
        ResultSet rst = stm.executeQuery();
        ArrayList<Supplier> suppliers = new ArrayList<>();
        while (rst.next()) {
            suppliers.add(new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getString(7),
                    rst.getString(8)

            ));
        }
        return suppliers;
    }
}

