package interfaces;

import model.DressingEvent;
import model.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductInterFace {
    public boolean saveProduct(Product p1) throws SQLException, ClassNotFoundException;
    public boolean updateProduct(Product p) throws SQLException, ClassNotFoundException;
    public boolean deleteProduct(String productCode) throws SQLException, ClassNotFoundException;
    public Product getProduct(String productCode) throws SQLException, ClassNotFoundException;
    public ArrayList<Product> getAllProducts() throws SQLException, ClassNotFoundException;
}
