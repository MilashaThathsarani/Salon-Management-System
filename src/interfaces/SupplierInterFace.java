package interfaces;

import model.Product;
import model.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierInterFace {
    public boolean saveSupplier(Supplier s1) throws SQLException, ClassNotFoundException;
    public boolean updateSupplier(Supplier s) throws SQLException, ClassNotFoundException;
    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException;
    public Supplier getSupplier(String supplierId) throws SQLException, ClassNotFoundException;
    public ArrayList<Supplier> getAllSuppliers() throws SQLException, ClassNotFoundException;
}
