package interfaces;

import model.RentItem;
import model.SalonItem;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SalonItemInterFace {
    public boolean saveSalonItem(SalonItem s1) throws SQLException, ClassNotFoundException;
    public boolean updateSalonItem(SalonItem s) throws SQLException, ClassNotFoundException;
    public boolean deleteSalonItem(String salonItemCode) throws SQLException, ClassNotFoundException;
    public SalonItem getSalonItem(String salonItemCode) throws SQLException, ClassNotFoundException;
    public ArrayList<SalonItem> getAllSalonItem() throws SQLException, ClassNotFoundException;
}
