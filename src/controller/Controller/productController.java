package controller.Controller;

import db.DbConnection;
import interfaces.ProductInterFace;
import model.Client;
import model.Product;
import model.RentItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class productController implements ProductInterFace {

    public String getProductsIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT productCode FROM Product ORDER BY productCode DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "P-00" + tempId;
            } else if (tempId <= 99) {
                return "P-0" + tempId;
            } else {
                return "P-" + tempId;
            }

        } else {
            return "P-001";
        }
    }
    protected static ArrayList<Product> searchProduct(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM Product WHERE description LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Product> products= new ArrayList<>();
        while (rst.next()) {
            products.add(new Product(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return products;
    }

    @Override
    public boolean saveProduct(Product p1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Product VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,p1.getProductCode());
        stm.setObject(2,p1.getDescription());
        stm.setObject(3,p1.getPrice());
        stm.setObject(4,p1.getQtyOnHand());
        stm.setObject(5,p1.getDate());
        stm.setObject(6,p1.getTime());


        return stm.executeUpdate()>0;
    }

    @Override
    public boolean updateProduct(Product p) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Product SET description=?,price=?,qtyOnHand=? WHERE productCode=?");
        stm.setObject(1,p.getDescription());
        stm.setObject(2,p.getPrice());
        stm.setObject(3,p.getQtyOnHand());
        stm.setObject(4,p.getProductCode());


        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteProduct(String productCode) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Product WHERE productCode='" + productCode + "'").executeUpdate() > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Product getProduct(String productCode) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Product WHERE productCode='" +productCode + "'").
                executeQuery();
        if (rst.next()) {
            return new Product(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6)

            );
        }else{
            return null;
        }
    }

    @Override
    public ArrayList<Product> getAllProducts() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Product");
        ResultSet rst = stm.executeQuery();
        ArrayList<Product> products = new ArrayList<>();
        while (rst.next()) {
            products.add(new Product(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6)

            ));
        }
        return products;
    }

    public ArrayList<String> getProductIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Product   " +
                        "").executeQuery();
        ArrayList<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
}
