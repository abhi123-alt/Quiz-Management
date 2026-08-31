package com.quizapp.dao;

import com.quizapp.model.Quiz;
import com.quizapp.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    // GET ALL QUIZZES
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql =
                "SELECT quiz_id, title, description, category, " +
                        "difficulty, time_limit, created_by, created_at " +
                        "FROM quizzes " +
                        "ORDER BY created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Quiz quiz = new Quiz();

                quiz.setQuiz_id(resultSet.getInt("quiz_id"));
                quiz.setTitle(resultSet.getString("title"));
                quiz.setDescription(resultSet.getString("description"));
                quiz.setCategory(resultSet.getString("category"));
                quiz.setDifficulty(resultSet.getString("difficulty"));
                quiz.setTime_limit(resultSet.getInt("time_limit"));
                quiz.setCreated_by(resultSet.getInt("created_by"));
                quiz.setCreated_at(resultSet.getTimestamp("created_at"));

                quizzes.add(quiz);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quizzes;
    }

    // GET QUIZ BY quiz_id
    public Quiz getQuizById(int quiz_id) {
        Quiz quiz = null;
        String sql =
                "SELECT quiz_id, title, description, category, " +
                        "difficulty, time_limit, created_by, created_at " +
                        "FROM quizzes " +
                        "WHERE quiz_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, quiz_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    quiz = new Quiz();
                    quiz.setQuiz_id(resultSet.getInt("quiz_id"));
                    quiz.setTitle(resultSet.getString("title"));
                    quiz.setDescription(resultSet.getString("description"));
                    quiz.setCategory(resultSet.getString("category"));
                    quiz.setDifficulty(resultSet.getString("difficulty"));
                    quiz.setTime_limit(resultSet.getInt("time_limit"));
                    quiz.setCreated_by(resultSet.getInt("created_by"));
                    quiz.setCreated_at(resultSet.getTimestamp("created_at"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    // GET QUIZZES CREATED BY A USER
    public List<Quiz> getQuizzesByCreatedBy(int created_by) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql =
                "SELECT quiz_id, title, description, category, " +
                        "difficulty, time_limit, created_by, created_at " +
                        "FROM quizzes " +
                        "WHERE created_by = ? " +
                        "ORDER BY created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, created_by);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setQuiz_id(resultSet.getInt("quiz_id"));
                    quiz.setTitle(resultSet.getString("title"));
                    quiz.setDescription(resultSet.getString("description"));
                    quiz.setCategory(resultSet.getString("category"));
                    quiz.setDifficulty(resultSet.getString("difficulty"));
                    quiz.setTime_limit(resultSet.getInt("time_limit"));
                    quiz.setCreated_by(resultSet.getInt("created_by"));
                    quiz.setCreated_at(resultSet.getTimestamp("created_at"));

                    quizzes.add(quiz);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quizzes;
    }

    // ADD QUIZ
    public boolean addQuiz(Quiz quiz) {

        String sql =
                "INSERT INTO quizzes " +
                        "(title, description, category, difficulty, time_limit, created_by) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setString(1, quiz.getTitle());
            statement.setString(2, quiz.getDescription());
            statement.setString(3, quiz.getCategory());
            statement.setString(4, quiz.getDifficulty());
            statement.setInt(5, quiz.getTime_limit());
            statement.setInt(6, quiz.getCreated_by());
            int rows = statement.executeUpdate();

            if (rows > 0) {
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        int quiz_id = keys.getInt(1);
                        quiz.setQuiz_id(quiz_id);
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    // DELETE QUIZ
//    public boolean deleteQuiz(int quiz_id) {
//        String sql ="DELETE FROM quizzes WHERE quiz_id = ?";
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, quiz_id);
//            return statement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }

    public boolean deleteQuiz(int quiz_id) {

        String sql = """
        DELETE FROM quizzes
        WHERE quiz_id = ?
        """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quiz_id);
            int rows = ps.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    // update Quiz
    public boolean updateQuiz(Quiz quiz) {
        String sql =
                "UPDATE quizzes SET " +
                        "title = ?, " +
                        "description = ?, " +
                        "category = ?, " +
                        "difficulty = ?, " +
                        "time_limit = ? " +
                        "WHERE quiz_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setString(1, quiz.getTitle());
            statement.setString(2, quiz.getDescription());
            statement.setString(3, quiz.getCategory());
            statement.setString(4, quiz.getDifficulty());
            statement.setInt(5, quiz.getTime_limit());
            statement.setInt(6, quiz.getQuiz_id());
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}