package com.quizapp.controller;

import com.quizapp.model.Question;
import com.quizapp.service.QuestionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/questions")
public class QuestionServlet extends HttpServlet {
    private QuestionService questionService;
    @Override
    public void init() throws ServletException {
        questionService = new QuestionService();
    }

    // GET
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        // LIST QUESTIONS
        if (action == null || action.equals("list")) {
            showQuestionList(request, response);
            return;
        }

        // ADD QUESTION PAGE
        if (action.equals("add")) {
            showAddQuestion(request, response);
            return;
        }

        // EDIT QUESTION PAGE
        if (action.equals("edit")) {
            showEditQuestion(request, response);
            return;
        }

        // DELETE QUESTION
        if (action.equals("delete")) {
            deleteQuestion(request, response);
            return;
        }

        response.sendError( HttpServletResponse.SC_NOT_FOUND, "Unknown question action");
    }


    // POST
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addQuestion(request, response);
            return;
        }

        if ("update".equals(action)) {
            updateQuestion(request, response);
            return;
        }

        response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Invalid question action");
    }


    // LIST QUESTIONS
    private void showQuestionList(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String quiz_id_parameter =request.getParameter("quiz_id");
        if (quiz_id_parameter == null || quiz_id_parameter.trim().isEmpty()) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"quiz_id is required");
            return;
        }

        try {
            int quiz_id = Integer.parseInt(quiz_id_parameter);
            List<Question> questions = questionService.getQuestionsByQuizId(quiz_id);
            request.setAttribute("questions",questions);
            request.setAttribute("quiz_id",quiz_id);
            request.getRequestDispatcher( "/WEB-INF/views/admin/questions.jsp" ).forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id");
        }
    }

    // SHOW ADD QUESTION
    private void showAddQuestion(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String quiz_id_parameter = request.getParameter("quiz_id");
        if (quiz_id_parameter == null || quiz_id_parameter.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"quiz_id is required");
            return;
        }
        try {
            int quiz_id = Integer.parseInt(quiz_id_parameter);
            request.setAttribute("quiz_id",quiz_id);
            request.getRequestDispatcher("/WEB-INF/views/admin/add-question.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz_id");
        }
    }

    // ADD QUESTION

    private void addQuestion( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quiz_id_parameter =request.getParameter("quiz_id");
        String question_text = request.getParameter("question_text");
        if (quiz_id_parameter == null || quiz_id_parameter.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"quiz_id is required");
            return;
        }
        try {
            int quiz_id = Integer.parseInt(quiz_id_parameter);
            // VALIDATE QUESTION
            if (question_text == null || question_text.trim().isEmpty()) {
                request.setAttribute("error","Question text is required.");
                request.setAttribute("quiz_id",quiz_id);
                request.getRequestDispatcher("/WEB-INF/views/admin/add-question.jsp").forward(request, response);
                return;
            }

            // CREATE QUESTION
            Question question = new Question();
            question.setQuiz_id(quiz_id);
            question.setQuestion_text(question_text.trim());

            // SAVE QUESTION
            boolean success = questionService.addQuestion(question);
            if (success) {
                response.sendRedirect( request.getContextPath()+ "/admin/questions?action=list&quiz_id="+ quiz_id);
                return;
            }

            request.setAttribute("error","Unable to add question.");
            request.setAttribute( "quiz_id", quiz_id);
            request.getRequestDispatcher("/WEB-INF/views/admin/add-question.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id");
        }
    }

    // SHOW EDIT QUESTION
    private void showEditQuestion( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String question_id_parameter = request.getParameter("question_id");
        if (question_id_parameter == null || question_id_parameter.trim().isEmpty()) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"question_id is required");
            return;
        }


        try {
            int question_id = Integer.parseInt(question_id_parameter);
            Question question = questionService.getQuestionById( question_id);
            if (question == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Question not found");
                return;
            }
            request.setAttribute("question",question);
            request.getRequestDispatcher("/WEB-INF/views/admin/edit-question.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Invalid question_id");
        }
    }

    // UPDATE QUESTION
    private void updateQuestion(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String question_id_parameter = request.getParameter("question_id");
        String quiz_id_parameter = request.getParameter("quiz_id");
        String question_text = request.getParameter("question_text");
        try {
            int question_id = Integer.parseInt(question_id_parameter);
            int quiz_id = Integer.parseInt(quiz_id_parameter);
            Question question = new Question();
            question.setQuestion_id(question_id );
            question.setQuiz_id(quiz_id);
            question.setQuestion_text(question_text);

            // VALIDATE
            if (question_text == null || question_text.trim().isEmpty()) {
                request.setAttribute("question",question);
                request.setAttribute("error","Question text is required.");
                request.getRequestDispatcher("/WEB-INF/views/admin/edit-question.jsp").forward(request, response);
                return;
            }

            question.setQuestion_text( question_text.trim());

            // UPDATE

            boolean success = questionService.updateQuestion(question);
            if (success) {
                response.sendRedirect(request.getContextPath()+ "/admin/questions?action=list&quiz_id="+ quiz_id);
                return;
            }


            request.setAttribute("question", question);
            request.setAttribute( "error", "Unable to update question.");
            request.getRequestDispatcher("/WEB-INF/views/admin/edit-question.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Invalid question information");
        }
    }

    // DELETE QUESTION
    private void deleteQuestion(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String question_id_parameter = request.getParameter("question_id");
        String quiz_id_parameter = request.getParameter("quiz_id");
        try {
            int question_id =Integer.parseInt( question_id_parameter);
            int quiz_id = Integer.parseInt( quiz_id_parameter);
            boolean success = questionService.deleteQuestion(question_id);
            if (!success) {
                request.setAttribute("error","Unable to delete question.");
            }
            response.sendRedirect(request.getContextPath()+ "/admin/questions?action=list&quiz_id="+ quiz_id );
        } catch (NumberFormatException e) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Invalid question information");
        }
    }
}