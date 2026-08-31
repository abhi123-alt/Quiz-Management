package com.quizapp.controller;

import com.quizapp.model.Attempt;
import com.quizapp.model.Quiz;
import com.quizapp.service.AttemptService;
import com.quizapp.service.QuizService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.quizapp.model.Question;
import com.quizapp.model.Option;
import com.quizapp.service.QuestionService;
import com.quizapp.service.OptionService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/quizzes")
public class QuizServlet extends HttpServlet {

    private QuizService quizService;
    private QuestionService questionService;
    private OptionService optionService;
    private AttemptService attemptService;

    @Override
    public void init() throws ServletException {
        quizService = new QuizService();
        questionService = new QuestionService();
        try {
            optionService = new OptionService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        attemptService=new AttemptService();
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

        // INTRODUCTION
        if (action.equals("take")) {
            takeQuiz(request, response);
            return;
        }
        // ACTUAL QUIZ
        if (action.equals("play")) {
            playQuiz(request, response);
            return;
        }

        // DELETE QUIZ
        if ("delete".equals(action)) {
            deleteQuiz(request, response);
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

    private void takeQuiz( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quiz_id_parameter = request.getParameter("quiz_id");
        if (quiz_id_parameter == null || quiz_id_parameter.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/quizzes?action=list" );
            return;
        }
        try {
            int quiz_id = Integer.parseInt(quiz_id_parameter);
            // GET QUIZ
            Quiz quiz = quizService.getQuizById(quiz_id);
            if (quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Quiz not found");
                return;
            }

            // GET QUESTIONS
            List<Question> questions = questionService.getQuestionsByQuizId(quiz_id);

            // SEND QUIZ
            request.setAttribute("quiz",quiz);

            // SEND QUESTIONS
            request.setAttribute("questions", questions);

            // GET ATTEMPT ID FROM SESSION
            jakarta.servlet.http.HttpSession session = request.getSession(false);
            if (session != null) {
                Object attemptId = session.getAttribute("attemptId");
                if (attemptId != null) {
                    request.setAttribute("attemptId", attemptId );
                }
            }

            // FORWARD TO QUIZ PAGE
            request.getRequestDispatcher("/WEB-INF/views/user/take-quiz.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id");
        }
    }

    private void playQuiz(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String quiz_id_parameter = request.getParameter("quiz_id");
        // CHECK QUIZ ID
        if (quiz_id_parameter == null || quiz_id_parameter.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/quizzes?action=list" );
            return;
        }
        try {
            int quiz_id = Integer.parseInt(quiz_id_parameter.trim());
            // GET QUIZ
            Quiz quiz = quizService.getQuizById(quiz_id);
            if (quiz == null) {
                response.sendError( HttpServletResponse.SC_NOT_FOUND,"Quiz not found");
                return;
            }
            // GET QUESTIONS
            List<Question> questions = questionService.getQuestionsByQuizId(quiz_id);
            // CHECK QUESTIONS
            if (questions == null || questions.isEmpty()) {
                request.setAttribute("error","This quiz does not have any questions yet.");
                request.setAttribute("quiz", quiz);
                request.getRequestDispatcher("/WEB-INF/views/user/take-quiz.jsp").forward(request, response);
                return;
            }
            /*
             * GET OPTIONS FOR EACH QUESTION
             *
             * Options are stored in a separate table,
             * so we need to load them separately and
             * put them inside the Question object.
             */
            for (Question question : questions) {
                List<Option> options = optionService.getOptionsByQuestionId(question.getQuestion_id());
                question.setOptions(options);
            }

            // SEND QUIZ TO JSP
            request.setAttribute("quiz", quiz);

            // SEND QUESTIONS + OPTIONS TO JSP
            request.setAttribute("questions", questions);

            // OPEN ACTUAL QUIZ PAGE
            request.getRequestDispatcher("/WEB-INF/views/user/quiz.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("submit".equals(action)) {
            submitQuiz(request, response);
            return;
        }
        response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz action");
    }
    private void submitQuiz(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String quiz_id_parameter = request.getParameter("quiz_id");
        if (quiz_id_parameter == null || quiz_id_parameter.trim().isEmpty()) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"quiz_id is required");
            return;
        }

        int quiz_id;
        try {
            quiz_id = Integer.parseInt( quiz_id_parameter.trim());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid quiz_id");
            return;
        }

        // GET LOGGED-IN USER
        jakarta.servlet.http.HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        // GET ATTEMPT ID
        Object attemptObject = session.getAttribute("attemptId");
        if (attemptObject == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Quiz attempt not found");
            return;
        }

        int attemptId = (Integer) attemptObject;

        // GET QUESTIONS
        List<Question> questions = questionService.getQuestionsByQuizId(quiz_id);
        if (questions == null || questions.isEmpty()) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Quiz has no questions");
            return;
        }

        // CALCULATE SCORE
        int correctAnswers = 0;
        for (Question question : questions) {
            String parameterName = "question_" + question.getQuestion_id();
            String selectedOption = request.getParameter(parameterName);
            if (selectedOption == null || selectedOption.trim().isEmpty()) {
                continue;
            }
            int selectedOptionId;
            try {
                selectedOptionId = Integer.parseInt(selectedOption.trim());
            } catch (NumberFormatException e) {
                continue;
            }

            Option selectedOptionObject = optionService.getOptionById( selectedOptionId);
            if (selectedOptionObject != null && selectedOptionObject.isIs_correct()) {
                correctAnswers++;
            }
        }
        int totalQuestions = questions.size();
        // SAVE RESULT
        Attempt attempt = new Attempt();
        attempt.setAttempt_id(attemptId);
        attempt.setUser_id(userId);
        attempt.setQuiz_id(quiz_id);
        attempt.setScore(correctAnswers);
        attempt.setTotal_questions(totalQuestions);

        boolean updated = attemptService.updateAttempt(attempt);
        if (!updated) {
            response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Unable to save quiz result");
            return;
        }

        // CALCULATE WRONG ANSWERS
        int wrongAnswers = totalQuestions - correctAnswers;

        // CALCULATE PERCENTAGE
        double percentage = 0;
        if (totalQuestions > 0) {
            percentage =((double) correctAnswers /totalQuestions) * 100;
        }

        // GET QUIZ FROM DATABASE
        Quiz quiz = quizService.getQuizById(quiz_id);
        if (quiz == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"Quiz not found");
            return;
        }

        // SEND DATA TO JSP
        request.setAttribute("quiz",quiz);
        request.setAttribute("attempt",attempt);
        request.setAttribute("correctAnswers",correctAnswers);
        request.setAttribute("wrongAnswers",wrongAnswers);
        request.setAttribute("totalQuestions",totalQuestions);
        request.setAttribute("percentage",percentage);

        // FORWARD TO RESULT PAGE
        request.getRequestDispatcher("/WEB-INF/views/user/result.jsp").forward(request, response);
    }

    private void deleteQuiz(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String quiz_id_parameter = request.getParameter("quiz_id");
        if (quiz_id_parameter == null || quiz_id_parameter.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"quiz_id is required");
            return;
        }
        int quiz_id;

        try {
            quiz_id = Integer.parseInt(quiz_id_parameter.trim());
        } catch (NumberFormatException e) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz_id");
            return;
        }


        System.out.println("DELETE QUIZ REQUEST");
        System.out.println("Quiz ID = " + quiz_id);

        // Check quiz exists
        Quiz quiz = quizService.getQuizById(quiz_id);
        if (quiz == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"Quiz not found");
            return;
        }

        try {

            // GET ALL QUESTIONS OF THIS QUIZ
            List<Question> questions = questionService.getQuestionsByQuizId(quiz_id);

            System.out.println(
                    "Questions found = " +
                            (questions == null ? 0 : questions.size())
            );


            // DELETE OPTIONS AND QUESTIONS
            if (questions != null) {
                for (Question question : questions) {
                    int question_id = question.getQuestion_id();
                    // DELETE OPTIONS FIRST
                    optionService.deleteOptionsByQuestionId(question_id);
                    // THEN DELETE QUESTION
                    questionService.deleteQuestion(question_id);
                }
            }

            // DELETE QUIZ
            boolean deleted = quizService.deleteQuiz(quiz_id);

            System.out.println(
                    "Quiz deleted = " + deleted
            );

            if (!deleted) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Quiz could not be deleted" );
                return;
            }

            // BACK TO QUIZ LIST
            response.sendRedirect(request.getContextPath() + "/admin/quizzes?action=list");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Error while deleting quiz");
        }
    }
}