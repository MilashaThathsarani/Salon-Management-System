package interfaces;

import model.DressingEvent;
import model.RentItem;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RentItemInterFace {
    public boolean saveRentItem(RentItem r1) throws SQLException, ClassNotFoundException;
    public boolean updateRentItem(RentItem r) throws SQLException, ClassNotFoundException;
    public boolean deleteRentItem(String rentItemCode) throws SQLException, ClassNotFoundException;
    public RentItem getRentItem(String rentItemCode) throws SQLException, ClassNotFoundException;
    public ArrayList<RentItem> getAllRentItem() throws SQLException, ClassNotFoundException;
}
