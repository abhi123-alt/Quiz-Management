package com.quizapp.controller;

import com.quizapp.dao.UserDAO;
import com.quizapp.model.User;
import com.quizapp.service.AdminDashboardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashBoardServlet extends HttpServlet {
    private AdminDashboardService dashboardService;
    private UserDAO userDAO;
    @Override
    public void init() throws ServletException {
        dashboardService = new AdminDashboardService();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        // Fetch the logged-in user from database
        User user = userDAO.getUserById(userId);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"User not found");
            return;
        }
        int totalQuizzes = dashboardService.getTotalQuizzes();
        int totalQuestions = dashboardService.getTotalQuestions();
        int totalAttempts = dashboardService.getTotalAttempts();
        int totalUsers = dashboardService.getTotalUsers();

        request.setAttribute("totalQuizzes", totalQuizzes);
        request.setAttribute("totalQuestions", totalQuestions);
        request.setAttribute("totalAttempts", totalAttempts);
        request.setAttribute("totalUsers", totalUsers);

        request.setAttribute("user",user);

        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
    }

}