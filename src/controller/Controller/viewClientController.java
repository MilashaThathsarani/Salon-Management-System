package controller.Controller;

import db.DbConnection;
import interfaces.ViewClientInterFace;
import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class viewClientController implements ViewClientInterFace {
    protected static ArrayList<Client> searchClient(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM Client WHERE clientName LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Client> clients= new ArrayList<>();
        while (rst.next()) {
            clients.add(new Client(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return clients;
    }

    @Override
    public ArrayList<Client> getAllClient() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Client");
        ResultSet rst = stm.executeQuery();
        ArrayList<Client> clients = new ArrayList<>();
        while (rst.next()) {
            clients.add(new Client(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)

            ));
        }
        return clients;
    }
}
