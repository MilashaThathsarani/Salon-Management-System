package controller.Controller;

import db.DbConnection;
import interfaces.StaffInterFace;
import model.DressingEvent;
import model.Product;
import model.RentItem;
import model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class staffController implements StaffInterFace {

    public String getStaffIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT staffId FROM Staff ORDER BY staffId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "S-00" + tempId;
            } else if (tempId <= 99) {
                return "S-0" + tempId;
            } else {
                return "S-" + tempId;
            }

        } else {
            return "S-001";
        }
    }
    protected static ArrayList<Staff> searchStaff(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM Staff WHERE staffName LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Staff> staff= new ArrayList<>();
        while (rst.next()) {
            staff.add(new Staff(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDouble(7),
                    rst.getString(8),
                    rst.getString(9)
            ));
        }
        return staff;
    }

    @Override
    public boolean saveStaff(Staff s1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Staff VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,s1.getStaffId());
        stm.setObject(2,s1.getStaffName());
        stm.setObject(3,s1.getAddress());
        stm.setObject(4,s1.getContact());
        stm.setObject(5,s1.getEducation());
        stm.setObject(6,s1.getPosition());
        stm.setObject(7,s1.getSalary());
        stm.setObject(8,s1.getDate());
        stm.setObject(9,s1.getTime());


        return stm.executeUpdate()>0;
    }

    @Override
    public boolean updateStaff(Staff s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Staff SET staffName=?,address=?,contact=? ,education=? ,position=?,salary=? WHERE staffId=?");
        stm.setObject(1,s.getStaffName());
        stm.setObject(2,s.getAddress());
        stm.setObject(3,s.getContact());
        stm.setObject(4,s.getEducation());
        stm.setObject(5,s.getPosition());
        stm.setObject(6,s.getSalary());
        stm.setObject(7,s.getStaffId());


        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteStaff(String staffId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Staff WHERE staffId='" + staffId + "'").executeUpdate() > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Staff getStaff(String staffId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Staff WHERE staffId='" + staffId + "'").
                executeQuery();
        if (rst.next()) {
            return new Staff(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDouble(7),
                    rst.getString(8),
                    rst.getString(9)

            );
        }else{
            return null;
        }
    }

    @Override
    public ArrayList<Staff> getAllStaff() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Staff");
        ResultSet rst = stm.executeQuery();
        ArrayList<Staff> staff = new ArrayList<>();
        while (rst.next()) {
            staff.add(new Staff(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDouble(7),
                    rst.getString(8),
                    rst.getString(9)

            ));
        }
        return staff;
    }
}
