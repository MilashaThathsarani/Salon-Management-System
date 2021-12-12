package controller.Controller;

import db.DbConnection;
import interfaces.DressingEventInterFace;
import model.Client;
import model.DressingEvent;
import model.RentItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dressingEventController implements DressingEventInterFace {

    public String getDressingEventItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT dressingEventCode FROM `Dressing Event` ORDER BY dressingEventCode DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "D-00" + tempId;
            } else if (tempId <= 99) {
                return "D-0" + tempId;
            } else {
                return "D-" + tempId;
            }

        } else {
            return "D-001";
        }
    }

    protected static ArrayList<DressingEvent> searchEvents(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM `Dressing Event` WHERE description LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<DressingEvent> events= new ArrayList<>();
        while (rst.next()) {
            events.add(new DressingEvent(
                    rst.getString("dressingEventCode"),
                    rst.getString("description"),
                    rst.getDouble("price"),
                    rst.getString("date"),
                    rst.getString("time")
            ));
        }
        return events;
    }

    @Override
    public boolean saveEvent(DressingEvent d1) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO `Dressing Event` VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, d1.getDressingEventCode());
        stm.setObject(2, d1.getDescription());
        stm.setObject(3, d1.getPrice());
        stm.setObject(4, d1.getDate());
        stm.setObject(5, d1.getTime());


        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean updateEvent(DressingEvent d) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Dressing Event` SET description=?,price=? WHERE dressingEventCode=?");
        stm.setObject(1, d.getDescription());
        stm.setObject(2, d.getPrice());
        stm.setObject(3, d.getDressingEventCode());

        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteEvent(String dressingEventCode) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Dressing Event` WHERE dressingEventCode='" + dressingEventCode + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DressingEvent getDressingEvent(String dressingEventCode) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM `Dressing Event` WHERE dressingEventCode='" + dressingEventCode + "'").
                executeQuery();
        if (rst.next()) {
            return new DressingEvent(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4),
                    rst.getString(5)

            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<DressingEvent> getAllEvents() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Dressing Event`");
        ResultSet rst = stm.executeQuery();
        ArrayList<DressingEvent> events = new ArrayList<>();
        while (rst.next()) {
            events.add(new DressingEvent(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4),
                    rst.getString(5)

            ));
        }
        return events;
    }

    public ArrayList<String> getDressingEventIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM `Dressing Event` " +
                        "").executeQuery();
        ArrayList<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

}
