package interfaces;

import model.DressingEvent;
import model.Staff;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StaffInterFace {
    public boolean saveStaff(Staff s1) throws SQLException, ClassNotFoundException;
    public boolean updateStaff(Staff s) throws SQLException, ClassNotFoundException;
    public boolean deleteStaff(String staffId) throws SQLException, ClassNotFoundException;
    public Staff getStaff(String staffId) throws SQLException, ClassNotFoundException;
    public ArrayList<Staff> getAllStaff() throws SQLException, ClassNotFoundException;
}
