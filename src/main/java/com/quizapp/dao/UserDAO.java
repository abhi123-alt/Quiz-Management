package com.quizapp.dao;
import com.quizapp.model.User;
import com.quizapp.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final String INSERT_USER ="INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
    private static final String Get_User_By_Id="select * from users where user_id=?";
    private static final String Update_User="update users set name=?,email=?,password=?,role=? where user_id=?";
    private static final String GET_USER_BY_EMAIL ="select user_id,name,email,password,role from users where email=?";
    // Register User
    public boolean registerUser(User user){
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement= connection.prepareStatement(INSERT_USER);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole() != null ? user.getRole() : "User");
            int rowsInserted= statement.executeUpdate();
            return rowsInserted>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public User getUserByEmail(String email) {
        User user;
        try ( Connection connection = DBConnection.getConnection();
              PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL))
        {
            statement.setString(1, email);
            //statement.setString(2, role);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    // update the user.

}
