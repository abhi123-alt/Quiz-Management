package com.quizapp.controller;

import com.quizapp.model.Question;
import com.quizapp.model.Quiz;
import com.quizapp.service.AttemptService;
import com.quizapp.service.OptionService;
import com.quizapp.service.QuestionService;
import com.quizapp.service.QuizService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/quizzes")
public class AdminQuizServlet extends HttpServlet {

    private QuizService quizService;
    private QuestionService questionService;
    private OptionService optionService;
    private AttemptService attemptService;

    @Override
    public void init() throws ServletException {

        try {
            quizService = new QuizService();
            questionService = new QuestionService();
            optionService = new OptionService();
            attemptService = new AttemptService();

        } catch (Exception e) {
            throw new ServletException(
                    "Failed to initialize AdminQuizServlet services",
                    e
            );
        }
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

        response.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "Unknown quiz action"
        );
    }

    // POST
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
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

        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid quiz action"
        );
    }

    // LIST QUIZZES

    private void listQuizzes(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        List<Quiz> quizzes = quizService.getAllQuizzes();

        request.setAttribute("quizzes", quizzes);
        request.getRequestDispatcher("/WEB-INF/views/admin/quizzes.jsp").forward(request, response);
    }

    // SHOW
    private void showAddQuiz(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/admin/add-quiz.jsp").forward(request, response);
    }

    // ADD QUIZ

    private void addQuiz(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String difficulty = request.getParameter("difficulty");
        String timeLimitParameter = request.getParameter("time_limit");
        String createdByParameter = request.getParameter("created_by");

        // VALIDATION
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
            int time_limit =Integer.parseInt(timeLimitParameter.trim());
            int created_by =Integer.parseInt(createdByParameter.trim());
            if (time_limit <= 0) {
                request.setAttribute("error","Time limit must be greater than 0.");
                showAddQuiz(request, response);
                return;
            }

            Quiz quiz = new Quiz();
            quiz.setTitle(title.trim());
            quiz.setDescription(description == null? "" : description.trim());
            quiz.setCategory(category.trim());
            quiz.setDifficulty(difficulty.trim());
            quiz.setTime_limit(time_limit);
            quiz.setCreated_by(created_by);

            // USE SERVICE, NOT DAO
            boolean success = quizService.addQuiz(quiz);
            if (success) {

                // After creating quiz,
                // go directly to add questions
                response.sendRedirect(request.getContextPath()+ "/admin/questions?action=add&quiz_id="+ quiz.getQuiz_id());
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
    private void showEditQuiz(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String quizIdParameter = request.getParameter("quiz_id");
        if (quizIdParameter == null || quizIdParameter.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"quiz_id is required");
            return;
        }

        try {
            int quiz_id =Integer.parseInt(quizIdParameter.trim());
            Quiz quiz = quizService.getQuizById(quiz_id);
            if (quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Quiz not found");
                return;
            }

            request.setAttribute("quiz", quiz);
            request.getRequestDispatcher("/WEB-INF/views/admin/edit-quiz.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id");
        }
    }


    // UPDATE QUIZ
    private void updateQuiz(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String quizIdParameter =request.getParameter("quiz_id");
        String title =request.getParameter("title");
        String description =request.getParameter("description");
        String category =request.getParameter("category");
        String difficulty =request.getParameter("difficulty");
        String timeLimitParameter =request.getParameter("time_limit");

        if (quizIdParameter == null || timeLimitParameter == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz information");
            return;
        }

        try {
            int quiz_id = Integer.parseInt(quizIdParameter.trim());
            int time_limit =Integer.parseInt( timeLimitParameter.trim());

            Quiz quiz = new Quiz();

            quiz.setQuiz_id(quiz_id);
            quiz.setTitle(title);
            quiz.setDescription(description);
            quiz.setCategory(category);
            quiz.setDifficulty(difficulty);
            quiz.setTime_limit(time_limit);

            // USE SERVICE
            boolean success =
                    quizService.updateQuiz(quiz);

            if (success) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/quizzes?action=list"
                );

                return;
            }

            request.setAttribute(
                    "error",
                    "Unable to update quiz."
            );

            request.setAttribute(
                    "quiz",
                    quiz
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/admin/edit-quiz.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid quiz information"
            );
        }
    }

    // =========================================================
    // DELETE QUIZ
    // =========================================================

    private void deleteQuiz(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String quizIdParameter = request.getParameter("quiz_id");
        if (quizIdParameter == null || quizIdParameter.trim().isEmpty()) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"quiz_id is required" );
            return;
        }

        int quiz_id;
        try {
            quiz_id = Integer.parseInt(quizIdParameter.trim());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id");
            return;
        }

        // CHECK QUIZ
        Quiz quiz = quizService.getQuizById(quiz_id);
        if (quiz == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"Quiz not found");
            return;
        }

        try {

            // STEP 1: DELETE ATTEMPTS

            boolean attemptsDeleted =attemptService.deleteAttemptsByQuizId(quiz_id);
            if (!attemptsDeleted) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Could not delete quiz attempts");
                return;
            }

            // STEP 2: GET QUESTIONS
            List<Question> questions =questionService.getQuestionsByQuizId(quiz_id);

            // STEP 3: DELETE OPTIONS
            // STEP 4: DELETE QUESTIONS

            if (questions != null) {
                for (Question question : questions) {
                    int question_id = question.getQuestion_id();

                    // DELETE OPTIONS FIRST
                    optionService.deleteOptionsByQuestionId(question_id);

                    // THEN DELETE QUESTION
                    questionService.deleteQuestion(question_id);
                }
            }

            // STEP 5: DELETE QUIZ
            boolean quizDeleted =quizService.deleteQuiz(quiz_id);
            if (!quizDeleted) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Quiz could not be deleted");
                return;
            }

            // SUCCESS
            response.sendRedirect(request.getContextPath()+ "/admin/quizzes?action=list");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Error while deleting quiz" );
        }
    }
}