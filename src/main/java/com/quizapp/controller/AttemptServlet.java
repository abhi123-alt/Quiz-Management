package com.quizapp.controller;

import com.quizapp.model.Attempt;
import com.quizapp.service.AttemptService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/create-attempt")
public class AttemptServlet extends HttpServlet {

    private AttemptService attemptService;
    @Override
    public void init() throws ServletException {
        try {
            attemptService = new AttemptService();
        } catch (Exception e) {
            throw new ServletException( "Failed to initialize AttemptService", e );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Get existing logged-in session
        HttpSession session = request.getSession(false);

        // User is not logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get logged-in user's ID
        int userId = (Integer) session.getAttribute("userId");

        // Get quiz ID from request
        String quiz_id_parameter = request.getParameter("quiz_id");
        if (quiz_id_parameter == null || quiz_id_parameter.trim().isEmpty()) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"quiz_id is required" );
            return;
        }
        int quiz_id;
        try {
            quiz_id = Integer.parseInt(quiz_id_parameter);
        } catch (NumberFormatException e) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id" );
            return;
        }

        // Create new Attempt
        Attempt attempt = new Attempt();

        attempt.setUser_id(userId);
        attempt.setQuiz_id(quiz_id);

        // Initial values
        attempt.setScore(0);
        attempt.setTotal_questions(0);

        // Create attempt in database
        int attemptId =attemptService.createAttempt(attempt);
        if (attemptId > 0) {
            // Save attempt ID for the quiz flow
            session.setAttribute("attemptId",attemptId);
            // Continue to quiz
            response.sendRedirect(request.getContextPath() + "/quizzes?action=play&quiz_id=" + quiz_id);
        } else {
            response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Unable to create quiz attempt." );
        }
    }
}
