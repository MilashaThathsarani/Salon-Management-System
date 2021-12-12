package interfaces;

import model.Client;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClientInterFace {
        public boolean saveClient(Client c1) throws SQLException, ClassNotFoundException;
        public boolean updateClient(Client c) throws SQLException, ClassNotFoundException;
        public boolean deleteClient(String clientId) throws SQLException, ClassNotFoundException;
        public Client getClient(String clientId) throws SQLException, ClassNotFoundException;
        public ArrayList<Client> getAllClient() throws SQLException, ClassNotFoundException;

}
