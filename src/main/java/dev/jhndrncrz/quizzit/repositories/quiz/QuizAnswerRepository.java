package dev.jhndrncrz.quizzit.repositories.quiz;

import dev.jhndrncrz.quizzit.models.quiz.QuizAnswer;
import dev.jhndrncrz.quizzit.utilities.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizAnswerRepository {
    private final Connection connection;

    public QuizAnswerRepository() throws ClassNotFoundException, SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public QuizAnswer findById(Integer id) throws SQLException {
        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizAnswer`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new QuizAnswer(
                            resultSet.getInt("id"),
                            resultSet.getInt("question_id"),
                            resultSet.getString("answer"),
                            resultSet.getInt("answer_index"),
                            resultSet.getBoolean("is_correct"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at"));
                }
            }
        }

        return null;
    }

    public List<QuizAnswer> findAll() throws SQLException {
        List<QuizAnswer> answers = new ArrayList<>();

        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizAnswer`
                """;

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(queryString)) {
                while (resultSet.next()) {
                    answers.add(new QuizAnswer(
                            resultSet.getInt("id"),
                            resultSet.getInt("question_id"),
                            resultSet.getString("answer"),
                            resultSet.getInt("answer_index"),
                            resultSet.getBoolean("is_correct"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at")));
                }
            }
        }

        return answers;
    }

    public List<QuizAnswer> findAllByQuestionId(Integer questionId) throws SQLException {
        List<QuizAnswer> answers = new ArrayList<>();

        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizAnswer`
                    WHERE
                        `question_id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, questionId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    answers.add(new QuizAnswer(
                            resultSet.getInt("id"),
                            resultSet.getInt("question_id"),
                            resultSet.getString("answer"),
                            resultSet.getInt("answer_index"),
                            resultSet.getBoolean("is_correct"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at")));
                }
            }
        }

        return answers;
    }

    public QuizAnswer create(QuizAnswer answer) throws SQLException {
        final String queryString = """
                    INSERT INTO
                        `QuizAnswer` (`question_id`, `answer`, `answer_index`, `is_correct`)
                    VALUES
                        (?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, answer.getQuestionId());
            statement.setString(2, answer.getAnswer());
            statement.setInt(3, answer.getAnswerIndex());
            statement.setBoolean(4, answer.getIsCorrect());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    answer.setId(resultSet.getInt("id"));
                    answer.setCreatedAt(resultSet.getTimestamp("created_at"));
                    answer.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                }
            }
        }

        return answer;
    }

    public void deleteById(Integer id) throws SQLException {
        final String queryString = """
                    DELETE
                    FROM
                        `QuizAnswer`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public QuizAnswer updateById(QuizAnswer answer) throws SQLException {
        final String queryString = """
                    UPDATE
                        `QuizAnswer`
                    SET
                        `answer` = ?,
                        `answer_index` = ?,
                        `is_correct` = ?
                    WHERE
                        `id` = ?,
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setString(1, answer.getAnswer());
            statement.setInt(2, answer.getAnswerIndex());
            statement.setBoolean(3, answer.getIsCorrect());
            statement.setInt(4, answer.getId());

            statement.executeUpdate();
        }

        return answer;   
    }
}
