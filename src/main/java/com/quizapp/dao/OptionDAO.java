package com.quizapp.dao;

import com.quizapp.model.Option;
import com.quizapp.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionDAO {

    // GET OPTIONS BY question_id
    public List<Option> getOptionsByQuestionId(int question_id) {
        List<Option> options = new ArrayList<>();
        String sql =
                "SELECT option_id, question_id, " +
                        "option_text, is_correct " +
                        "FROM options " +
                        "WHERE question_id = ? " +
                        "ORDER BY option_id ASC";


        try (Connection connection =DBConnection.getConnection();
             PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setInt(1, question_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Option option =new Option();
                    option.setOption_id(resultSet.getInt("option_id"));
                    option.setQuestion_id(resultSet.getInt("question_id"));
                    option.setOption_text(resultSet.getString("option_text"));
                    option.setIs_correct(resultSet.getBoolean("is_correct"));
                    options.add(option);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return options;
    }

    // ADD OPTION
    public boolean addOption(Option option) {
        String sql =
                "INSERT INTO options " +
                        "(question_id, option_text, is_correct) " +
                        "VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1,option.getQuestion_id());
            statement.setString(2,option.getOption_text());
            statement.setBoolean(3,option.isIs_correct());
            int rows =statement.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys =statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        option.setOption_id(keys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE OPTION
    public boolean updateOption(Option option) {
        String sql =
                "UPDATE options SET " +
                        "option_text = ?, " +
                        "is_correct = ? " +
                        "WHERE option_id = ?";
        try (Connection connection =DBConnection.getConnection();
             PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setString(1,option.getOption_text());
            statement.setBoolean(2,option.isIs_correct());
            statement.setInt(3,option.getOption_id());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE OPTION
    public boolean deleteOption(int option_id) {
        String sql =
                "DELETE FROM options " +
                        "WHERE option_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setInt(1, option_id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}