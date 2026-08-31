package com.quizapp.service;

import com.quizapp.dao.AdminDashboardDAO;

public class AdminDashboardService {
    private final AdminDashboardDAO dashboardDAO;

    public AdminDashboardService() {
        dashboardDAO = new AdminDashboardDAO();
    }

    public int getTotalQuizzes() {
        return dashboardDAO.getTotalQuizzes();
    }

    public int getTotalQuestions() {
        return dashboardDAO.getTotalQuestions();
    }

    public int getTotalAttempts() {
        return dashboardDAO.getTotalAttempts();
    }

    public int getTotalUsers() {
        return dashboardDAO.getTotalUsers();
    }
}