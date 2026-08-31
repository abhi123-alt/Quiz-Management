package com.quizapp.service;
import com.quizapp.dao.UserDAO;
import com.quizapp.model.User;
public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public boolean register(User user) {

        // Check if email already exist//
        if (userDAO.getUserByEmail(user.getEmail(), user.getRole()) != null) {
            return false;
        }
        return userDAO.registerUser(user);
    }
    public User login(String email, String password,String role) {
        User user = userDAO.getUserByEmail(email,role);
        if (user!=null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    public boolean updateProfile(int user_id,String name, String email, String password) {
        return userDAO.updateProfile(user_id, name,email,password);
    }
    public User getUserById(int user_id) {
        return userDAO.getUserById(user_id);
    }
}