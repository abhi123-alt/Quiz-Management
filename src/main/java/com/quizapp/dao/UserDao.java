package com.quizapp.dao;
import com.quizapp.model.User;
import com.quizapp.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    private static final String INSERT_USER="insert into users (name,email,password,role) values(?,?,?,?)";
    private static final String Get_User_By_Id="select * from users where user_id=?";
    private static final String Update_User="update users set name=?,email=?,password=?,role=? where user_id=?";

    // Register User
    public boolean registerUser(User user){
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement= connection.prepareStatement(INSERT_USER);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            int rowsInserted= statement.executeUpdate();
            connection.close();
            return rowsInserted>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // get user by email.
    public User Get_User_By_Id(int id){
        
    }
    // update the user.

}
