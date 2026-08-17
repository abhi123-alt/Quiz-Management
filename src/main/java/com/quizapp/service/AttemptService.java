package com.quizapp.service;

import com.quizapp.dao.AttemptDAO;
import com.quizapp.model.Attempt;
import com.quizapp.model.HistoryItem;

import java.sql.SQLException;
import java.util.List;
public class AttemptService {
    private final AttemptDAO attemptDAO;
    public AttemptService() {
        try {
            attemptDAO = new AttemptDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize AttemptDAO", e);
        }
    }
    // CREATE ATTEMPT
    public int createAttempt(Attempt attempt) {
        return attemptDAO.createAttempt(attempt);
    }
    // GET ATTEMPT BY ID
    public Attempt getAttemptById(int attempt_id) {
        return attemptDAO.getAttemptById(attempt_id);
    }
    // GET ALL ATTEMPTS OF USER
    public List<Attempt> getAttemptsByUserId(int user_id) {
        return attemptDAO.getAttemptsByUserId(user_id);
    }
    // GET ATTEMPTS OF PARTICULAR QUIZ
    public List<Attempt> getAttemptsByUserAndQuiz(int user_id,int quiz_id) {
        return attemptDAO.getAttemptsByUserAndQuiz(user_id,quiz_id);
    }
    // COMPLETE ATTEMPT
    public boolean completeAttempt(int attempt_id,int score) {
        return attemptDAO.completeAttempt(attempt_id,score);
    }
    // UPDATE SCORE
    public boolean updateScore(int attempt_id,int score,int total_questions) {
        return attemptDAO.updateScore(attempt_id,score,total_questions);
    }
    // TOTAL ATTEMPTS
    public int getTotalAttemptsByUserId(int user_id) {
        return attemptDAO.getTotalAttemptsByUserId(user_id);
    }
    // BEST SCORE
    public double getBestScoreByUserId(int user_id) {
        return attemptDAO.getBestScoreByUserId(user_id);
    }
    // AVERAGE SCORE
    public double getAverageScoreByUserId(int user_id) {
        return attemptDAO.getAverageScoreByUserId(user_id);
    }
    // RECENT ATTEMPTS
    public List<Attempt> getRecentAttempts(int user_id,int limit) {
        return attemptDAO.getRecentAttempts(user_id,limit);
    }
    // HISTORY
    public List<HistoryItem> getUserHistory(int user_id) {
        return attemptDAO.getHistoryByUserId(user_id);
    }
    // DELETE ATTEMPT
    public boolean deleteAttempt(int attempt_id) {
        return attemptDAO.deleteAttempt(attempt_id);
    }
}