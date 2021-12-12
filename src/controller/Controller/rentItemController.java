package controller.Controller;

import db.DbConnection;
import interfaces.RentItemInterFace;
import model.DressingEvent;
import model.RentItem;
import model.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class rentItemController implements RentItemInterFace {

    public String getRentItemsIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT rentItemCode FROM `Rent Item` ORDER BY rentItemCode DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "R-00" + tempId;
            } else if (tempId <= 99) {
                return "R-0" + tempId;
            } else {
                return "R-" + tempId;
            }

        } else {
            return "R-001";
        }
    }

    protected static ArrayList<RentItem> searchRentItem(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM `Rent Item` WHERE description LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<RentItem> rentItems= new ArrayList<>();
        while (rst.next()) {
            rentItems.add(new RentItem(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return rentItems;
    }

    @Override
    public boolean saveRentItem(RentItem r1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `Rent Item` VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,r1.getRentItemCode());
        stm.setObject(2,r1.getDescription());
        stm.setObject(3,r1.getPrice());
        stm.setObject(4,r1.getQtyOnHand());
        stm.setObject(5,r1.getDate());
        stm.setObject(6,r1.getTime());


        return stm.executeUpdate()>0;
    }

    @Override
    public boolean updateRentItem(RentItem r) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Rent Item` SET description=?,price=?,qtyOnHand=? WHERE rentItemCode=?");
        stm.setObject(1, r.getDescription());
        stm.setObject(2, r.getPrice());
        stm.setObject(3,r.getQtyOnHand());
        stm.setObject(4, r.getRentItemCode());


        return stm.executeUpdate() > 0;
    }


    @Override
    public boolean deleteRentItem(String rentItemCode) throws SQLException, ClassNotFoundException {
            if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Rent Item` WHERE rentItemCode='" + rentItemCode + "'").executeUpdate() > 0){
                return true;
            }else{
                return false;
            }
    }

    @Override
    public RentItem getRentItem(String rentItemCode) throws SQLException, ClassNotFoundException {
            ResultSet rst = DbConnection.getInstance().getConnection().
                    prepareStatement("SELECT * FROM `Rent Item` WHERE rentItemCode='" + rentItemCode + "'").
                    executeQuery();
            if (rst.next()) {
                return new RentItem(
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
    public ArrayList<RentItem> getAllRentItem() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Rent Item`");
        ResultSet rst = stm.executeQuery();
        ArrayList<RentItem> rentItems = new ArrayList<>();
        while (rst.next()) {
            rentItems.add(new RentItem(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6)

            ));
        }
        return rentItems;
      }

    public ArrayList<String> getRentItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM `Rent Item` " +
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

