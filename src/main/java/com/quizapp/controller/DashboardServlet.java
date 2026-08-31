package com.quizapp.controller;
import com.quizapp.model.Quiz;
import com.quizapp.model.User;
import com.quizapp.service.QuizService;
import com.quizapp.model.HistoryItem;
import com.quizapp.service.AttemptService;
import com.quizapp.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private AttemptService attemptService;
    private QuizService quizService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        attemptService = new AttemptService();
        quizService =new QuizService();
        userService=new UserService();
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
            response.sendRedirect(request.getContextPath() +"/admin/dashboard");
            return;
        }

        // STUDENT DASHBOARD
        int userId = (Integer) session.getAttribute("userId");

        // Fetch user from database
        User user = userService.getUserById(userId);

        // GET DASHBOARD DATA
        int totalAttempts = attemptService.getTotalAttemptsByUserId(userId);
        double bestScore = attemptService.getBestScoreByUserId(userId);
        double averageScore = attemptService.getAverageScoreByUserId(userId);

        // GET RECENT QUIZ HISTORY
        List<HistoryItem> history = attemptService.getUserHistory(userId);
        List<Quiz> quizzes = quizService.getAllQuizzes();

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
        request.setAttribute("quizzes", quizzes);
        request.setAttribute("user", user);


        // OPEN STUDENT DASHBOARD JSP
        request.getRequestDispatcher("/WEB-INF/views/user/dashboard.jsp" ).forward(request, response);
    }
}