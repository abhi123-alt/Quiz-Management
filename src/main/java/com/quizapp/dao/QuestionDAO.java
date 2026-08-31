package com.quizapp.dao;

import com.quizapp.model.Option;
import com.quizapp.model.Question;
import com.quizapp.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    // GET ALL QUESTIONS FOR A QUIZ
    public List<Question> getQuestionsByQuizId(int quiz_id) {
        List<Question> questions = new ArrayList<>();
        String sql ="SELECT question_id, quiz_id, question_text " +
                        "FROM questions " +
                        "WHERE quiz_id = ? " +
                        "ORDER BY question_id ASC";
        try (Connection connection =DBConnection.getConnection();
             PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setInt(1, quiz_id);
            try (ResultSet resultSet =statement.executeQuery()) {
                while (resultSet.next()) {
                    Question question =new Question();
                    question.setQuestion_id(resultSet.getInt("question_id"));
                    question.setQuiz_id(resultSet.getInt("quiz_id"));
                    question.setQuestion_text(resultSet.getString("question_text"));
                    questions.add(question);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    // GET QUESTION BY question_id

    public Question getQuestionById(int question_id) {
        Question question = null;
        String sql =
                "SELECT question_id, quiz_id, question_text " +
                        "FROM questions " +
                        "WHERE question_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setInt(1, question_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    question = new Question();
                    question.setQuestion_id(resultSet.getInt("question_id"));
                    question.setQuiz_id(resultSet.getInt("quiz_id"));
                    question.setQuestion_text(resultSet.getString("question_text"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    // ADD QUESTION

    public int addQuestion(Question question) {
        String sql = """
            INSERT INTO questions
            (quiz_id, question_text)
            VALUES (?, ?)
            """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, question.getQuiz_id());
            ps.setString(2,question.getQuestion_text());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return 0;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // UPDATE QUESTION

    public boolean updateQuestion(Question question) {
        String sql =
                "UPDATE questions " +
                        "SET question_text = ? " +
                        "WHERE question_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,question.getQuestion_text());
            statement.setInt(2,question.getQuestion_id());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE QUESTION
    public boolean deleteQuestion(int question_id) {
        String sql = "DELETE FROM questions " +
                        "WHERE question_id = ?";
        try (Connection connection =DBConnection.getConnection();
             PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setInt(1, question_id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Option> getOptionsByQuestionId(int question_id) {
        List<Option> options = new ArrayList<>();
        String sql = """
        SELECT option_id,
               question_id,
               option_text,
               is_correct
        FROM options
        WHERE question_id = ?
        ORDER BY option_id
        """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, question_id);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Option option = new Option();

                    option.setOption_id(rs.getInt("option_id"));
                    option.setQuestion_id(rs.getInt("question_id"));
                    option.setOption_text(rs.getString("option_text"));
                    option.setIs_correct(rs.getBoolean("is_correct"));

                    options.add(option);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return options;
    }

    public boolean deleteQuestionsByQuizId(int quiz_id) {
        String sql = """
        DELETE FROM questions
        WHERE quiz_id = ?
        """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quiz_id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}