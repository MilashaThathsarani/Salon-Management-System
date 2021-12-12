package interfaces;

import model.Client;
import model.DressingEvent;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DressingEventInterFace {
    public boolean saveEvent(DressingEvent d1) throws SQLException, ClassNotFoundException;
    public boolean updateEvent(DressingEvent d) throws SQLException, ClassNotFoundException;
    public boolean deleteEvent(String dressingEventCode) throws SQLException, ClassNotFoundException;
    public DressingEvent getDressingEvent(String dressingEventCode) throws SQLException, ClassNotFoundException;
    public ArrayList<DressingEvent> getAllEvents() throws SQLException, ClassNotFoundException;

}
