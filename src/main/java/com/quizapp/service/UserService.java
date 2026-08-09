package com.quizapp.service;
import com.quizapp.dao.UserDAO;
import com.quizapp.model.User;
public class UserService {
    private UserDAO userDAO = new UserDAO();
    public boolean register(User user) {
        // Check if email already exist//
        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            return false;
        }
        return userDAO.registerUser(user);
    }
    public User login(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user!=null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}