package com.quizapp.dao;

import com.quizapp.model.Option;
import com.quizapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionDAO {

    private final Connection connection;

    public OptionDAO() throws SQLException {
        connection = DBConnection.getConnection();
    }

    // ADD OPTION
    public boolean addOption(Option option) {
        String sql = """
                INSERT INTO options
                (question_id, option_text, is_correct)
                VALUES (?, ?, ?)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, option.getQuestion_id());
            ps.setString(2, option.getOption_text());
            ps.setBoolean(3, option.isIs_correct());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // GET OPTIONS BY QUESTION ID
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
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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


    // GET OPTION BY ID
    public Option getOptionById(int option_id) {
        String sql = """
                SELECT option_id,
                       question_id,
                       option_text,
                       is_correct
                FROM options
                WHERE option_id = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, option_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Option option = new Option();
                    option.setOption_id(rs.getInt("option_id"));
                    option.setQuestion_id(rs.getInt("question_id"));
                    option.setOption_text(rs.getString("option_text"));
                    option.setIs_correct(rs.getBoolean("is_correct"));
                    return option;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // DELETE OPTIONS OF A QUESTION
    public boolean deleteOptionsByQuestionId(int question_id) {
        String sql = """
                DELETE FROM options
                WHERE question_id = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, question_id);
             ps.executeUpdate();
             return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


//    public boolean deleteOptionsByQuizId(int quiz_id) {
//        String sql = """
//        DELETE o
//        FROM options o
//        INNER JOIN questions q
//            ON o.question_id = q.question_id
//        WHERE q.quiz_id = ?
//        """;
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, quiz_id);
//            ps.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}