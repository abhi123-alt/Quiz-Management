package com.quizapp.service;

import com.quizapp.dao.QuestionDAO;
import com.quizapp.model.Question;

import java.util.List;

public class QuestionService {
    private final QuestionDAO questionDAO;
    public QuestionService() {
        questionDAO = new QuestionDAO();
    }

    // Get question by ID
    public Question getQuestionById(int questionId) {
        return questionDAO.getQuestionById(questionId);
    }

    // Get all questions of a particular quiz
    public List<Question> getQuestionsByQuizId(int quizId) {
        return questionDAO.getQuestionsByQuizId(quizId);
    }

    // Add a new question
    public int addQuestion(Question question) {
        return questionDAO.addQuestion(question);
    }
    // Update question
    public boolean updateQuestion(Question question) {
        return questionDAO.updateQuestion(question);
    }

    // Delete question
    public boolean deleteQuestion(int questionId) {
        return questionDAO.deleteQuestion(questionId);
    }
}