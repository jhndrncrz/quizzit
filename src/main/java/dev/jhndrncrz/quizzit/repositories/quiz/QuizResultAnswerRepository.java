package dev.jhndrncrz.quizzit.repositories.quiz;

import dev.jhndrncrz.quizzit.models.quiz.QuizResultAnswer;
import dev.jhndrncrz.quizzit.utilities.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizResultAnswerRepository {
    private final Connection connection;

    public QuizResultAnswerRepository() throws ClassNotFoundException, SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public QuizResultAnswer findById(Integer id) throws SQLException {
        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizResultAnswer`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new QuizResultAnswer(
                            resultSet.getInt("id"),
                            resultSet.getInt("result_id"),
                            resultSet.getInt("question_id"),
                            resultSet.getInt("answer_id"),
                            resultSet.getBoolean("is_correct"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at"));
                }
            }
        }

        return null;
    }

    public List<QuizResultAnswer> findAll() throws SQLException {
        List<QuizResultAnswer> answers = new ArrayList<>();

        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizResultAnswer`
                """;

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(queryString)) {
                while (resultSet.next()) {
                    answers.add(new QuizResultAnswer(
                            resultSet.getInt("id"),
                            resultSet.getInt("result_id"),
                            resultSet.getInt("question_id"),
                            resultSet.getInt("answer_id"),
                            resultSet.getBoolean("is_correct"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at")));
                }
            }
        }

        return answers;
    }

    public List<QuizResultAnswer> findAllByResultId(Integer resultId) throws SQLException {
        List<QuizResultAnswer> answers = new ArrayList<>();

        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizResultAnswer`
                    WHERE
                        `result_id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, resultId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    answers.add(new QuizResultAnswer(
                        resultSet.getInt("id"),
                        resultSet.getInt("result_id"),
                        resultSet.getInt("question_id"),
                        resultSet.getInt("answer_id"),
                        resultSet.getBoolean("is_correct"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at")));
                }
            }
        }

        return answers;
    }

    public QuizResultAnswer create(QuizResultAnswer answer) throws SQLException {
        final String queryString = """
                    INSERT INTO
                        `QuizResultAnswer` (`result_id`, `question_id`, `answer_id`, `is_correct`)
                    VALUES
                        (?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, answer.getResultId());
            statement.setInt(2, answer.getQuestionId());
            statement.setInt(3, answer.getAnswerId());
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
                        `QuizResultAnswer`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public QuizResultAnswer updateById(QuizResultAnswer answer) throws SQLException {
        final String queryString = """
                    UPDATE
                        `QuizResultAnswer`
                    SET
                        `answer_id` = ?,
                        `is_correct` = ?
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, answer.getAnswerId());
            statement.setBoolean(2, answer.getIsCorrect());
            statement.setInt(3, answer.getId());

            statement.executeUpdate();
        }

        return answer;
    }
}
