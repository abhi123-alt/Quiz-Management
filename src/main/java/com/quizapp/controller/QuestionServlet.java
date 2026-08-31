package com.quizapp.controller;

import com.quizapp.model.Question;
import com.quizapp.service.QuestionService;
import com.quizapp.model.Option;
import com.quizapp.service.OptionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/questions")
public class QuestionServlet extends HttpServlet {
    private QuestionService questionService;
    private OptionService optionService;
    @Override
    public void init() throws ServletException {
        try {
            questionService = new QuestionService();
            optionService = new OptionService();
        } catch (Exception e) {
            throw new ServletException("Failed to initialize QuestionService or OptionService", e);
        }
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
            request.getRequestDispatcher( "/WEB-INF/views/admin/question.jsp" ).forward(request, response);

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
        String quiz_id_parameter = request.getParameter("quiz_id");
        String question_text = request.getParameter("question_text");
        String option_1 = request.getParameter("option_1");
        String option_2 = request.getParameter("option_2");
        String option_3 = request.getParameter("option_3");
        String option_4 = request.getParameter("option_4");
        String correct_option = request.getParameter("correct_option");
        // CHECK QUIZ ID
        if (quiz_id_parameter == null || quiz_id_parameter.trim().isEmpty()) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST, "quiz_id is required");
            return;
        }

        int quiz_id;
        try {
            quiz_id = Integer.parseInt( quiz_id_parameter.trim());
        } catch (NumberFormatException e) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id");
            return;
        }
        // VALIDATE QUESTION
        if (question_text == null || question_text.trim().isEmpty()) {
            request.setAttribute("error","Question text is required.");
            request.setAttribute("quiz_id", quiz_id);
            request.getRequestDispatcher("/WEB-INF/views/admin/add-question.jsp" ).forward(request, response);
            return;
        }

        // VALIDATE OPTIONS
        if (option_1 == null || option_1.trim().isEmpty()
                || option_2 == null || option_2.trim().isEmpty()
                || option_3 == null || option_3.trim().isEmpty()
                || option_4 == null || option_4.trim().isEmpty()) {
            request.setAttribute("error","All four options are required.");
            request.setAttribute("quiz_id",quiz_id);
            request.getRequestDispatcher("/WEB-INF/views/admin/add-question.jsp").forward(request, response);
            return;
        }

        // VALIDATE CORRECT ANSWER
        if (correct_option == null || correct_option.trim().isEmpty()) {
            request.setAttribute("error","Please select the correct answer.");
            request.setAttribute("quiz_id", quiz_id );
            request.getRequestDispatcher("/WEB-INF/views/admin/add-question.jsp").forward(request, response);
            return;
        }
        int correct_option_number;
        try {
            correct_option_number = Integer.parseInt( correct_option.trim() );
        } catch (NumberFormatException e) {
            request.setAttribute("error","Invalid correct option.");
            request.setAttribute("quiz_id",quiz_id);
            request.getRequestDispatcher("/WEB-INF/views/admin/add-question.jsp").forward(request, response);
            return;
        }

        // CREATE QUESTION
        Question question = new Question();
        question.setQuiz_id(quiz_id);
        question.setQuestion_text( question_text.trim());
        // SAVE QUESTION AND GET GENERATED QUESTION ID
        int question_id = questionService.addQuestion(question);
        if (question_id <= 0) {
            request.setAttribute("error","Unable to add question.");
            request.setAttribute("quiz_id",quiz_id);
            request.getRequestDispatcher("/WEB-INF/views/admin/add-question.jsp").forward(request, response);
            return;
        }

        // OPTION 1
        Option option1 = new Option();
        option1.setQuestion_id(question_id);
        option1.setOption_text(option_1.trim());
        option1.setIs_correct( correct_option_number == 1);
        boolean option1Success = optionService.addOption(option1);

        // OPTION 2
        Option option2 = new Option();
        option2.setQuestion_id(question_id);
        option2.setOption_text(option_2.trim());
        option2.setIs_correct(correct_option_number == 2);
        boolean option2Success =optionService.addOption(option2);

        // OPTION 3
        Option option3 = new Option();
        option3.setQuestion_id(question_id);
        option3.setOption_text(option_3.trim());
        option3.setIs_correct(correct_option_number == 3);
        boolean option3Success =optionService.addOption(option3);

        // OPTION 4
        Option option4 = new Option();
        option4.setQuestion_id(question_id);
        option4.setOption_text(option_4.trim());
        option4.setIs_correct(correct_option_number == 4);
        boolean option4Success = optionService.addOption(option4);

        // CHECK WHETHER ALL OPTIONS WERE SAVED
        if (!option1Success ||
                !option2Success ||
                !option3Success ||
                !option4Success) {
            request.setAttribute("error","Question was created, but one or more options could not be saved." );
            request.setAttribute("quiz_id",quiz_id);
            request.getRequestDispatcher("/WEB-INF/views/admin/add-question.jsp").forward(request, response);
            return;
        }

        // ADD ANOTHER QUESTION
        response.sendRedirect(request.getContextPath()+ "/admin/questions?action=add&quiz_id="+ quiz_id);
    }

    // SHOW EDIT QUESTION
    private void showEditQuestion( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String question_id_parameter = request.getParameter("question_id");
        // CHECK QUESTION ID
        if (question_id_parameter == null || question_id_parameter.trim().isEmpty()) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"question_id is required");
            return;
        }
        int question_id;
        try {
            question_id = Integer.parseInt(question_id_parameter.trim());
        } catch (NumberFormatException e) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST, "Invalid question_id" );
            return;
        }
        // GET QUESTION
        Question question = questionService.getQuestionById(question_id);
        if (question == null) {
            response.sendError( HttpServletResponse.SC_NOT_FOUND,"Question not found");
            return;
        }

        // GET ALL OPTIONS FOR THIS QUESTION
        List<Option> options = optionService.getOptionsByQuestionId(question_id);
        // SEND DATA TO JSP
        request.setAttribute("question", question);
        request.setAttribute("options",options);
        // OPEN EDIT PAGE
        request.getRequestDispatcher("/WEB-INF/views/admin/edit-question.jsp").forward(request, response);
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
    private void deleteQuestion(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String questionIdParameter = request.getParameter("question_id");
        String quizIdParameter = request.getParameter("quiz_id");

        if (questionIdParameter == null || questionIdParameter.trim().isEmpty()
                || quizIdParameter == null || quizIdParameter.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid question information");
            return;
        }

        int question_id;
        int quiz_id;

        try {
            question_id = Integer.parseInt(questionIdParameter.trim());
            quiz_id = Integer.parseInt(quizIdParameter.trim());

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid question information"
            );
            return;
        }

        boolean deleted = questionService.deleteQuestion(question_id);
        if (!deleted) {
            request.setAttribute("error", "Unable to delete question.");
            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/questions?action=list&quiz_id="
                            + quiz_id
            );
            return;
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/questions?action=list&quiz_id="
                        + quiz_id
        );
    }
}