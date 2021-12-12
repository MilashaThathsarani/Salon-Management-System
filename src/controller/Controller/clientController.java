package controller.Controller;

import db.DbConnection;
import interfaces.ClientInterFace;
import model.Client;
import model.RentItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class clientController implements ClientInterFace {

   public String getClientsIds() throws SQLException, ClassNotFoundException {
       ResultSet rst = DbConnection.getInstance()
               .getConnection().prepareStatement(
                       "SELECT clientId FROM Client ORDER BY clientId DESC LIMIT 1"
               ).executeQuery();
       if (rst.next()) {
           int tempId = Integer.
                   parseInt(rst.getString(1).split("-")[1]);
           tempId = tempId + 1;
           if (tempId <= 9) {
               return "C-00" + tempId;
           } else if (tempId <= 99) {
               return "C-0" + tempId;
           } else {
               return "C-" + tempId;
           }

       } else {
           return "C-001";
       }
   }


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
    public boolean saveClient(Client c1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Client VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,c1.getClientId());
        stm.setObject(2,c1.getClientName());
        stm.setObject(3,c1.getClientAddress());
        stm.setObject(4,c1.getClientContact());
        stm.setObject(5,c1.getServiceType());
        stm.setObject(6,c1.getClientDate());
        stm.setObject(7,c1.getTime());


        return stm.executeUpdate()>0;
    }

    @Override
    public boolean updateClient(Client c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Client SET clientName=?,clientAddress=?,clientContact=?, serviceType=? WHERE clientId=?");
        stm.setObject(1,c.getClientName());
        stm.setObject(2,c.getClientAddress());
        stm.setObject(3,c.getClientContact());
        stm.setObject(4,c.getServiceType());
        stm.setObject(5,c.getClientId());

        return stm.executeUpdate() > 0;

    }

    @Override
    public boolean deleteClient(String clientId) throws SQLException, ClassNotFoundException {
            if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Client WHERE clientId='" + clientId + "'").executeUpdate() > 0){
                return true;
            }else{
                return false;
            }
    }

    @Override
    public Client getClient(String clientId) throws SQLException, ClassNotFoundException {
            ResultSet rst = DbConnection.getInstance().getConnection().
                    prepareStatement("SELECT * FROM Client WHERE clientId='" + clientId + "'").
                    executeQuery();
            if (rst.next()) {
                return new Client(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7)

                );
            } else {
                return null;
            }
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


    public ArrayList<String> getClientIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Client").executeQuery();
        ArrayList<String> clientIds = new ArrayList<>();
        while (rst.next()) {
           clientIds.add(
                    rst.getString(1)
            );}
        return clientIds;

    }
}
