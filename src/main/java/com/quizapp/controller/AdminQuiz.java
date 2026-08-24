package com.quizapp.controller;

import com.quizapp.dao.QuizDAO;
import com.quizapp.model.Quiz;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/quizzes")
public class AdminQuiz extends HttpServlet {
    private QuizDAO quizDAO;
    @Override
    public void init() {
        quizDAO = new QuizDAO();
    }

    // GET
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String action = request.getParameter("action");

        // DEFAULT → LIST
        if (action == null || action.equals("list")) {
            listQuizzes(request, response);
            return;
        }

        // ADD PAGE
        if (action.equals("add")) {
            showAddQuiz(request, response);
            return;
        }

        // EDIT PAGE
        if (action.equals("edit")) {
            showEditQuiz(request, response);
            return;
        }

        // DELETE
        if (action.equals("delete")) {
            deleteQuiz(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    // POST
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        // ADD
        if ("add".equals(action)) {
            addQuiz(request, response);
            return;
        }

        // UPDATE
        if ("update".equals(action)) {
            updateQuiz(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    // LIST QUIZZES
    private void listQuizzes(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        List<Quiz> quizzes = quizDAO.getAllQuizzes();
        request.setAttribute("quizzes", quizzes);
        request.getRequestDispatcher("/WEB-INF/views/admin/quizzes.jsp").forward(request, response);
    }

    // SHOW ADD QUIZ PAGE
    private void showAddQuiz(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/add-quiz.jsp").forward(request, response);
    }

    // ADD QUIZ
    private void addQuiz(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String title =request.getParameter("title");
        String description =request.getParameter("description");
        String category =request.getParameter("category");
        String difficulty =request.getParameter("difficulty");
        String timeLimitParameter =request.getParameter("time_limit");
        String createdByParameter =request.getParameter("created_by");
        if (title == null || title.trim().isEmpty()
                || category == null || category.trim().isEmpty()
                || difficulty == null || difficulty.trim().isEmpty()
                || timeLimitParameter == null
                || timeLimitParameter.trim().isEmpty()
                || createdByParameter == null
                || createdByParameter.trim().isEmpty()) {
            request.setAttribute("error","Please fill all required fields.");
            showAddQuiz(request, response);
            return;
        }

        try {
            int time_limit =Integer.parseInt(timeLimitParameter);
            int created_by =Integer.parseInt(createdByParameter);
            Quiz quiz = new Quiz();
            quiz.setTitle(title.trim());
            quiz.setDescription(description == null ? "" : description.trim());
            quiz.setCategory(category.trim());
            quiz.setDifficulty(difficulty.trim());
            quiz.setTime_limit(time_limit);
            quiz.setCreated_by(created_by);
            boolean success = quizDAO.addQuiz(quiz);
            if (success) {
                response.sendRedirect(request.getContextPath()+ "/admin/quizzes?action=list");
                return;
            }
            request.setAttribute("error","Unable to create quiz.");
            showAddQuiz(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error","Time limit and created_by must be valid numbers.");
            showAddQuiz(request, response);
        }
    }

    // SHOW EDIT QUIZ
    private void showEditQuiz(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String quizIdParameter = request.getParameter("quiz_id");
        if (quizIdParameter == null || quizIdParameter.trim().isEmpty()) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"quiz_id is required");
            return;
        }

        try {
            int quiz_id = Integer.parseInt(quizIdParameter);
            Quiz quiz = quizDAO.getQuizById(quiz_id);
            if (quiz == null) {
                response.sendError( HttpServletResponse.SC_NOT_FOUND,"Quiz not found");
                return;
            }
            request.setAttribute("quiz",quiz);
            request.getRequestDispatcher("/WEB-INF/views/admin/edit-quiz.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id");
        }
    }

    // UPDATE QUIZ

    private void updateQuiz(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String quizIdParameter = request.getParameter("quiz_id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String difficulty = request.getParameter("difficulty");
        String timeLimitParameter = request.getParameter("time_limit");
        try {
            int quiz_id = Integer.parseInt(quizIdParameter);
            int time_limit = Integer.parseInt(timeLimitParameter);
            Quiz quiz = new Quiz();
            quiz.setQuiz_id(quiz_id);
            quiz.setTitle(title);
            quiz.setDescription(description);
            quiz.setCategory(category);
            quiz.setDifficulty(difficulty);
            quiz.setTime_limit(time_limit);

            // UPDATE QUIZ
            boolean success = quizDAO.updateQuiz(quiz);
            if (success) {
                response.sendRedirect(request.getContextPath()+ "/admin/quizzes?action=list");
                return;
            }

            // UPDATE FAILED
            request.setAttribute("error","Unable to update quiz.");
            request.setAttribute("quiz",quiz);
            request.getRequestDispatcher("/WEB-INF/views/admin/edit-quiz.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz information");
        }
    }

    // DELETE QUIZ
    private void deleteQuiz(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String quizIdParameter =request.getParameter("quiz_id");
        if (quizIdParameter == null || quizIdParameter.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"quiz_id is required");
            return;
        }
        try {
            int quiz_id =Integer.parseInt(quizIdParameter);
            boolean success =quizDAO.deleteQuiz(quiz_id);
            if (success) {
                response.sendRedirect(request.getContextPath()+ "/admin/quizzes?action=list");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Quiz could not be deleted");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id");
        }
    }
}