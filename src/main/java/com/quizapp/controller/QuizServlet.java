package com.quizapp.controller;

import com.quizapp.model.Quiz;
import com.quizapp.service.QuizService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/quizzes")
public class QuizServlet extends HttpServlet {
    private QuizService quizService;
    @Override
    public void init() {
        quizService = new QuizService();
    }

    // GET
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String action = request.getParameter("action");

        // DEFAULT → LIST
        if (action == null || action.equals("list")) {
            showQuizList(request, response);
            return;
        }


        // VIEW QUIZ
        if (action.equals("view")) {
            viewQuiz(request, response);
            return;
        }

        // TAKE QUIZ

        if (action.equals("take")) {
            takeQuiz(request, response);
            return;
        }


        // UNKNOWN ACTION
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    // SHOW ALL QUIZZES
    private void showQuizList(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        List<Quiz> quizzes =quizService.getAllQuizzes();
        request.setAttribute("quizzes",quizzes);
        request.getRequestDispatcher("/WEB-INF/views/user/quizzes.jsp").forward(request,response);
    }
    // VIEW QUIZ DETAILS

    private void viewQuiz(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String quiz_id_parameter =request.getParameter("quiz_id");
        if (quiz_id_parameter == null || quiz_id_parameter.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath()+ "/quizzes?action=list");
            return;
        }
        try {
            int quiz_id =Integer.parseInt(quiz_id_parameter);
            Quiz quiz =quizService.getQuizById(quiz_id);
            if (quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Quiz not found");
                return;
            }
            request.setAttribute("quiz",quiz);
            request.getRequestDispatcher("/WEB-INF/views/user/take-quiz.jsp").forward(request,response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id");
        }
    }

    // TAKE QUIZ

    private void takeQuiz(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String quiz_id_parameter =request.getParameter("quiz_id");
        if (quiz_id_parameter == null || quiz_id_parameter.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath()+ "/quizzes?action=list");
            return;
        }
        try {
            int quiz_id =Integer.parseInt(quiz_id_parameter);
            Quiz quiz =quizService.getQuizById(quiz_id);
            if (quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Quiz not found");
                return;
            }
            request.setAttribute("quiz",quiz);
            request.getRequestDispatcher("/WEB-INF/views/user/take-quiz.jsp").forward(request,response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id");
        }
    }
}