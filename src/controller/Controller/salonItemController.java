package controller.Controller;

import db.DbConnection;
import interfaces.SalonItemInterFace;
import model.Product;
import model.SalonItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class salonItemController implements SalonItemInterFace {

    public String getSalonItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT itemCode FROM `Salon Item` ORDER BY itemCode DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "I-00" + tempId;
            } else if (tempId <= 99) {
                return "I-0" + tempId;
            } else {
                return "I-" + tempId;
            }

        } else {
            return "I-001";
        }
    }


    protected static ArrayList<SalonItem> searchItem(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM `Salon Item` WHERE description LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<SalonItem> salonItems= new ArrayList<>();
        while (rst.next()) {
            salonItems.add(new SalonItem(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return salonItems;
    }

    @Override
    public boolean saveSalonItem(SalonItem s1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `Salon Item` VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,s1.getItemCode());
        stm.setObject(2,s1.getDescription());
        stm.setObject(3,s1.getUnitPrice());
        stm.setObject(4,s1.getQtyOnHand());
        stm.setObject(5,s1.getDate());
        stm.setObject(6,s1.getTime());


        return stm.executeUpdate()>0;
    }

    @Override
    public boolean updateSalonItem(SalonItem s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Salon Item` SET description=?,unitPrice=?,qtyOnHand=? WHERE itemCode=?");
        stm.setObject(1,s.getDescription());
        stm.setObject(2,s.getUnitPrice());
        stm.setObject(3,s.getQtyOnHand());
        stm.setObject(4,s.getItemCode());


        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteSalonItem(String itemCode) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Salon Item` WHERE itemCode ='" + itemCode + "'").executeUpdate() > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public SalonItem getSalonItem(String itemCode) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM `Salon Item` WHERE itemCode='" + itemCode + "'").
                executeQuery();
        if (rst.next()) {
            return new SalonItem(
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
    public ArrayList<SalonItem> getAllSalonItem() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Salon Item`");
        ResultSet rst = stm.executeQuery();
        ArrayList<SalonItem> salonItems = new ArrayList<>();
        while (rst.next()) {
            salonItems.add(new SalonItem(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6)

            ));
        }
        return salonItems;
    }
}
