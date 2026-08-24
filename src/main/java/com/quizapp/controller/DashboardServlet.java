package com.quizapp.controller;

import com.quizapp.model.Attempt;
import com.quizapp.model.HistoryItem;
import com.quizapp.service.AttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private AttemptService attemptService;

    @Override
    public void init() throws ServletException {
        attemptService = new AttemptService();
    }

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        // Get existing session
        HttpSession session = request.getSession(false);

        // User is not logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login" );
            return;
        }

        // CHECK USER ROLE
        String role = (String) session.getAttribute("role");

        // ADMIN DASHBOARD
        if ("Admin".equalsIgnoreCase(role)) {
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
            return;
        }


        // STUDENT DASHBOARD
        int userId = (Integer) session.getAttribute("userId");

        // GET DASHBOARD DATA
        int totalAttempts = attemptService.getTotalAttemptsByUserId(userId);
        double bestScore = attemptService.getBestScoreByUserId(userId);
        double averageScore = attemptService.getAverageScoreByUserId(userId);

        // GET RECENT QUIZ HISTORY
        List<HistoryItem> history = attemptService.getUserHistory(userId);

        // Keep only recent 5 attempts for dashboard
        List<HistoryItem> recentHistory;
        if (history.size() > 5) {
            recentHistory = history.subList(0, 5);
        } else {
            recentHistory = history;
        }

        // SEND DATA TO JSP
        request.setAttribute("totalAttempts",totalAttempts );
        request.setAttribute("bestScore", bestScore );
        request.setAttribute("averageScore", averageScore);
        request.setAttribute("recentHistory",recentHistory);

        // OPEN STUDENT DASHBOARD JSP
        request.getRequestDispatcher("/WEB-INF/views/user/dashboard.jsp" ).forward(request, response);
    }
}