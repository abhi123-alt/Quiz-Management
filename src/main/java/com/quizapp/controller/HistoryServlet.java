package com.quizapp.controller;

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

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    private AttemptService attemptService;

    @Override
    public void init() {
        attemptService = new AttemptService();
    }

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // User must be logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int userId = (Integer) session.getAttribute("userId");
        // Get history from database
        List<HistoryItem> history = attemptService.getUserHistory(userId);
        // Send history to JSP
        request.setAttribute("history",history);
        request.getRequestDispatcher("/WEB-INF/views/user/history.jsp").forward(request, response);
    }
}