package interfaces;

import model.Client;
import model.Treatment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TreatmentInterFace {
    public boolean saveTreatment(Treatment t1) throws SQLException, ClassNotFoundException;
    public boolean updateTreatment(Treatment t) throws SQLException, ClassNotFoundException;
    public boolean deleteTreatment(String treatmentCode) throws SQLException, ClassNotFoundException;
    public Treatment getTreatment(String treatmentCode) throws SQLException, ClassNotFoundException;
    public ArrayList<Treatment> getAllTreatment() throws SQLException, ClassNotFoundException;

}

