package interfaces;

import model.Client;
import model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserInterface {
    public boolean saveUser(User u1) throws SQLException, ClassNotFoundException;

    public boolean updateClient(User u) throws SQLException, ClassNotFoundException;
    // public boolean deleteClient(String clientId) throws SQLException, ClassNotFoundException;
    // public Client getClient(String clientId) throws SQLException, ClassNotFoundException;
}
