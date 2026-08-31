package com.quizapp.service;

import com.quizapp.dao.QuizDAO;
import com.quizapp.model.Quiz;

import java.util.List;

public class QuizService {

    private final QuizDAO quizDAO;

    public QuizService() {
        quizDAO = new QuizDAO();
    }

    public List<Quiz> getAllQuizzes() {
        return quizDAO.getAllQuizzes();
    }

    public Quiz getQuizById(int quiz_id) {
        return quizDAO.getQuizById(quiz_id);
    }

    public List<Quiz> getQuizzesByCreatedBy(int created_by) {
        return quizDAO.getQuizzesByCreatedBy(created_by);
    }

    public boolean addQuiz(Quiz quiz) {

        if (quiz == null) {
            return false;
        }

        if (quiz.getTitle() == null ||
                quiz.getTitle().trim().isEmpty()) {
            return false;
        }

        if (quiz.getTime_limit() <= 0) {
            return false;
        }

        return quizDAO.addQuiz(quiz);
    }

    public boolean updateQuiz(Quiz quiz) {
        if (quiz == null) {
            return false;
        }

        if (quiz.getTitle() == null ||
                quiz.getTitle().trim().isEmpty()) {
            return false;
        }

        if (quiz.getTime_limit() <= 0) {
            return false;
        }

        return quizDAO.updateQuiz(quiz);
    }

    public boolean deleteQuiz(int quiz_id) {
        return quizDAO.deleteQuiz(quiz_id);
    }
}