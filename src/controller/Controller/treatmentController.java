package controller.Controller;

import db.DbConnection;
import interfaces.TreatmentInterFace;
import model.DressingEvent;
import model.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class treatmentController implements TreatmentInterFace {

    public String getTreatmentsIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT treatmentCode FROM Treatment ORDER BY treatmentCode DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "T-00" + tempId;
            } else if (tempId <= 99) {
                return "T-0" + tempId;
            } else {
                return "T-" + tempId;
            }

        } else {
            return "T-001";
        }
    }

    protected static ArrayList<Treatment> searchTreatment(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM Treatment WHERE description LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Treatment> treatments= new ArrayList<>();
        while (rst.next()) {
            treatments.add(new Treatment(
                    rst.getString("treatmentCode"),
                    rst.getString("description"),
                    rst.getString("price"),
                    rst.getString("date"),
                    rst.getString("time")
            ));
        }
        return treatments;
    }

    @Override
    public boolean saveTreatment(Treatment t1) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Treatment VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, t1.getTreatmentCode());
        stm.setObject(2, t1.getDescription());
        stm.setObject(3, t1.getPrice());
        stm.setObject(4, t1.getDate());
        stm.setObject(5, t1.getTime());


        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean updateTreatment(Treatment t) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Treatment SET description=?,price=? WHERE treatmentCode=?");
        stm.setObject(1, t.getDescription());
        stm.setObject(2, t.getPrice());
        stm.setObject(3, t.getTreatmentCode());

        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteTreatment(String treatmentCode) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Treatment WHERE treatmentCode='" + treatmentCode + "'").executeUpdate() > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Treatment getTreatment(String treatmentCode) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Treatment WHERE treatmentCode='" + treatmentCode + "'").
                executeQuery();
        if (rst.next()) {
            return new Treatment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }else{
            return null;
        }
    }


    @Override
    public ArrayList<Treatment> getAllTreatment() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Treatment");
        ResultSet rst = stm.executeQuery();
        ArrayList<Treatment> treatments = new ArrayList<>();
        while (rst.next()) {
            treatments.add(new Treatment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)

            ));
        }
        return treatments;
    }

    public ArrayList<String> getTreatmentIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Treatment " +
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
