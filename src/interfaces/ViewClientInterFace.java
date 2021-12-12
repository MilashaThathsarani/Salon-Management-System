package interfaces;

import model.Client;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ViewClientInterFace {
    public ArrayList<Client> getAllClient() throws SQLException, ClassNotFoundException;
}
