package controller.Controller;

import db.DbConnection;
import interfaces.UserInterface;
import model.Client;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class userController implements UserInterface {
    @Override
    public boolean saveUser(User u1) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Users VALUES(?, ?, ?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, u1.getUserName());
        stm.setObject(2, u1.getFullName());
        stm.setObject(3, u1.getEmail());
        stm.setObject(4, u1.getPassword());

        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean updateClient(User u) throws SQLException, ClassNotFoundException {
        return false;

    }


    public ArrayList<User> getAllUser() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM Users");
        ResultSet rst = stm.executeQuery();
        ArrayList<User> users = new ArrayList<>();
        while (rst.next()) {
            users.add(new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            ));
        }
        return users;

       /* public User getUser(String userName, String password) throws SQLException, ClassNotFoundException {
            Connection con = DbConnection.getInstance().getConnection();
            PreparedStatement stm = con.prepareStatement("SELECT * FROM User WHERE supplierName WHERE  userName= ? AND password=?");
            stm.setObject(1, userName);
            stm.setObject(2, password);
            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                return new User(
                      rst.getString(1),
                      rst.getString(2),
                      rst.getString(3),
                      rst.getString(4)
                );
            }
            return null;
        }*/
    }
}

