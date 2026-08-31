package com.quizapp.dao;

import com.quizapp.model.Attempt;
import com.quizapp.util.DBConnection;
import com.quizapp.model.HistoryItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AttemptDAO {
    private final Connection connection;
    public AttemptDAO() throws SQLException {
        connection = DBConnection.getConnection();
    }

    // 1. CREATE NEW ATTEMPT
    public int createAttempt(Attempt attempt) {
        String sql = """
                INSERT INTO quiz_attempt
                (user_id, quiz_id, score, total_questions, started_at)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, attempt.getUser_id());
            ps.setInt(2, attempt.getQuiz_id());
            ps.setInt(3, attempt.getScore());
            ps.setInt(4, attempt.getTotal_questions());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return -1;
            }
            // Get generated attempt_id
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 2. GET ATTEMPT BY ID
    public Attempt getAttemptById(int attempt_id) {
        String sql = """
                SELECT attempt_id,
                       user_id,
                       quiz_id,
                       score,
                       total_questions,
                       started_at,
                       completed_at
                FROM quiz_attempt
                WHERE attempt_id = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, attempt_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAttempt(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. GET ALL ATTEMPTS OF A USER
    public List<Attempt> getAttemptsByUserId(int user_id) {
        List<Attempt> attempts = new ArrayList<>();
        String sql = """
                SELECT attempt_id,
                       user_id,
                       quiz_id,
                       score,
                       total_questions,
                       started_at,
                       completed_at
                FROM quiz_attempt
                WHERE user_id = ?
                ORDER BY started_at DESC
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    attempts.add(mapResultSetToAttempt(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attempts;
    }

    // 4. GET ATTEMPTS OF A PARTICULAR QUIZ BY USER
    public List<Attempt> getAttemptsByUserAndQuiz(int user_id,int quiz_id) {
        List<Attempt> attempts = new ArrayList<>();
        String sql = """
                SELECT attempt_id,
                       user_id,
                       quiz_id,
                       score,
                       total_questions,
                       started_at,
                       completed_at
                FROM quiz_attempt
                WHERE user_id = ?
                  AND quiz_id = ?
                ORDER BY started_at DESC
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user_id);
            ps.setInt(2, quiz_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    attempts.add(mapResultSetToAttempt(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attempts;
    }

    // 5. COMPLETE ATTEMPT
    public boolean completeAttempt(int attempt_id,int score) {
        String sql = """
                UPDATE quiz_attempt
                SET score = ?,
                    completed_at = CURRENT_TIMESTAMP
                WHERE attempt_id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, score);
            ps.setInt(2, attempt_id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. UPDATE SCORE AND TOTAL QUESTIONS
    public boolean updateScore(int attempt_id,int score,int total_questions) {
        String sql = """
                UPDATE quiz_attempt
                SET score = ?,
                    total_questions = ?,
                    completed_at = CURRENT_TIMESTAMP
                WHERE attempt_id = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, score);
            ps.setInt(2, total_questions);
            ps.setInt(3, attempt_id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE SCORE
    public boolean updateAttempt(Attempt attempt) {
        String sql = """
            UPDATE quiz_attempt
            SET score = ?,
                total_questions = ?
            WHERE attempt_id = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, attempt.getScore());
            ps.setInt(2, attempt.getTotal_questions());
            ps.setInt(3, attempt.getAttempt_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 7. GET TOTAL NUMBER OF ATTEMPTS
    public int getTotalAttemptsByUserId(int user_id) {
        String sql = """
                SELECT COUNT(*)
                FROM quiz_attempt
                WHERE user_id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 8. GET BEST SCORE PERCENTAGE
    public double getBestScoreByUserId(int user_id) {
        String sql = """
                SELECT COALESCE(
                    MAX(
                        CASE
                            WHEN total_questions > 0
                            THEN (score * 100.0 / total_questions)
                            ELSE 0
                        END
                    ),
                    0
                )
                FROM quiz_attempt
                WHERE user_id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 9. GET AVERAGE SCORE PERCENTAGE
    public double getAverageScoreByUserId(int user_id) {
        String sql = """
                SELECT COALESCE(
                  ROUND(
                    AVG(
                        CASE
                            WHEN total_questions > 0
                            THEN (score * 100.0 / total_questions)
                            ELSE 0
                        END
                      ),
                      2
                 ),
                 0
              )
                FROM quiz_attempt
                WHERE user_id = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 10. GET LATEST ATTEMPTS
    public List<Attempt> getRecentAttempts(int user_id,int limit) {
        List<Attempt> attempts = new ArrayList<>();
        /*
         * LIMIT cannot safely be passed as ? in every MySQL/JDBC
         * configuration, so we validate it before putting it
         * into the SQL.
         */
        if (limit <= 0) {
            limit = 5;
        }
        String sql = """
                SELECT attempt_id,
                       user_id,
                       quiz_id,
                       score,
                       total_questions,
                       started_at,
                       completed_at
                FROM quiz_attempt
                WHERE user_id = ?
                ORDER BY started_at DESC
                LIMIT ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user_id);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    attempts.add(mapResultSetToAttempt(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attempts;
    }

    // 11. DELETE ATTEMPT
    public boolean deleteAttempt(int attempt_id) {
        String sql = """
                DELETE FROM quiz_attempt
                WHERE attempt_id = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, attempt_id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 12. HELPER METHOD
    private Attempt mapResultSetToAttempt(ResultSet rs) throws SQLException {
        Attempt attempt = new Attempt();
        attempt.setAttempt_id(rs.getInt("attempt_id"));
        attempt.setUser_id(rs.getInt("user_id"));
        attempt.setQuiz_id(rs.getInt("quiz_id"));
        attempt.setScore(rs.getInt("score"));
        attempt.setTotal_questions(rs.getInt("total_questions"));
        attempt.setStarted_at(rs.getTimestamp("started_at"));
        attempt.setCompleted_at(rs.getTimestamp("completed_at"));
        return attempt;
    }

    //13. GET HISTORY OF A USER BY USERID
    public List<HistoryItem> getHistoryByUserId(int user_id) {
        List<HistoryItem> history = new ArrayList<>();
        String sql = """
            SELECT
                qa.attempt_id,
                qa.user_id,
                qa.quiz_id,
                qa.score,
                qa.total_questions,
                qa.started_at,
                qa.completed_at,
                q.title,
                q.category,
                q.difficulty
            FROM quiz_attempt qa
            INNER JOIN quizzes q ON qa.quiz_id = q.quiz_id
            WHERE qa.user_id = ?
            ORDER BY qa.started_at DESC
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HistoryItem item = new HistoryItem();
                    item.setAttempt_id( rs.getInt("attempt_id"));
                    item.setQuiz_id(rs.getInt("quiz_id"));
                    item.setTitle(rs.getString("title"));
                    item.setCategory(rs.getString("category"));
                    item.setDifficulty(rs.getString("difficulty"));
                    item.setScore(rs.getInt("score"));
                    item.setTotal_questions(rs.getInt("total_questions"));
                    item.setStarted_at(rs.getTimestamp("started_at"));
                    item.setCompleted_at(rs.getTimestamp("completed_at"));
                    history.add(item);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return history;
    }

    public boolean deleteAttemptsByQuizId(int quiz_id) {
        String sql = """
        DELETE FROM quiz_attempt
        WHERE quiz_id = ?
        """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quiz_id);
            int rows = ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}