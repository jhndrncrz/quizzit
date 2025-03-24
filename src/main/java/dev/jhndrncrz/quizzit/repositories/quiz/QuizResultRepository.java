package dev.jhndrncrz.quizzit.repositories.quiz;

import dev.jhndrncrz.quizzit.models.quiz.QuizResult;
import dev.jhndrncrz.quizzit.models.quiz.QuizResultAnswer;
import dev.jhndrncrz.quizzit.utilities.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizResultRepository {
    private final Connection connection;
    private final QuizRepository quizRepository;
    private final QuizResultAnswerRepository quizResultAnswerRepository;

    public QuizResultRepository() throws ClassNotFoundException, SQLException {
        this.connection = DatabaseConnection.getConnection();
        this.quizRepository = new QuizRepository();
        this.quizResultAnswerRepository = new QuizResultAnswerRepository();
    }

    public QuizResult findById(Integer id) throws SQLException {
        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizResult`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new QuizResult(
                            resultSet.getInt("id"),
                            resultSet.getInt("student_id"),
                            resultSet.getInt("quiz_id"),
                            resultSet.getInt("score"),
                            resultSet.getBoolean("is_submitted"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at"),
                            this.quizRepository.findById(resultSet.getInt("quiz_id")),
                            this.quizResultAnswerRepository.findAllByResultId(id));
                }
            }
        }

        return null;
    }

    public QuizResult findPendingByQuizIdAndStudentId(Integer quizId, Integer studentId) throws SQLException {
        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizResult`
                    WHERE
                        `quiz_id` = ? AND `student_id` = ? AND `is_submitted` = 0
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, quizId);
            statement.setInt(2, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new QuizResult(
                            resultSet.getInt("id"),
                            resultSet.getInt("student_id"),
                            resultSet.getInt("quiz_id"),
                            resultSet.getInt("score"),
                            resultSet.getBoolean("is_submitted"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at"),
                            this.quizRepository.findById(resultSet.getInt("quiz_id")),
                            this.quizResultAnswerRepository.findAllByResultId(resultSet.getInt("id")));
                }
            }
        }

        return null;
    }

    public List<QuizResult> findAll() throws SQLException {
        List<QuizResult> results = new ArrayList<>();

        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizResult`
                """;

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(queryString)) {
                while (resultSet.next()) {
                    results.add(new QuizResult(
                            resultSet.getInt("id"),
                            resultSet.getInt("student_id"),
                            resultSet.getInt("quiz_id"),
                            resultSet.getInt("score"),
                            resultSet.getBoolean("is_submitted"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at"),
                            this.quizRepository.findById(resultSet.getInt("quiz_id")),
                            this.quizResultAnswerRepository.findAllByResultId(resultSet.getInt("id"))));
                }
            }
        }

        return results;
    }

    public List<QuizResult> findAllByStudentId(Integer studentId) throws SQLException {
        List<QuizResult> results = new ArrayList<>();

        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizResult`
                    WHERE
                        `student_id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(new QuizResult(
                            resultSet.getInt("id"),
                            resultSet.getInt("student_id"),
                            resultSet.getInt("quiz_id"),
                            resultSet.getInt("score"),
                            resultSet.getBoolean("is_submitted"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at"),
                            this.quizRepository.findById(resultSet.getInt("quiz_id")),
                            this.quizResultAnswerRepository.findAllByResultId(resultSet.getInt("id"))));
                }
            }
        }

        return results;
    }

    public QuizResult create(QuizResult result) throws SQLException {
        final String queryString = """
                    INSERT INTO
                        `QuizResult` (`student_id`, `quiz_id`, `score`, `is_submitted`)
                    VALUES
                        (?, ?, ?, ?)
                """;

        

        try (PreparedStatement statement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, result.getStudentId());
            statement.setInt(2, result.getQuizId());
            statement.setInt(3, result.getScore());
            statement.setBoolean(4, result.getIsSubmitted());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating QuizResult failed, no rows affected.");
            }

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    result.setId(resultSet.getInt(1));
                    
                    for (QuizResultAnswer answer : result.getAnswers()) {
                        answer.setResultId(result.getId());
                    }
                }
            }

        }

        for (QuizResultAnswer answer : result.getAnswers()) {
            this.quizResultAnswerRepository.create(answer);
        }

        return result;
    }

    public void deleteById(Integer id) throws SQLException {
        final String queryString = """
                    DELETE
                    FROM
                        `QuizResult`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public QuizResult updateById(QuizResult result) throws SQLException {
        final String queryString = """
                    UPDATE
                        `QuizResult`
                    SET
                        `score` = ?,
                        `is_submitted` = ?
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, result.getScore());
            statement.setBoolean(2, result.getIsSubmitted());
            statement.setInt(3, result.getId());

            statement.executeUpdate();
        }

        for (QuizResultAnswer answer : result.getAnswers()) {
            if (answer.getId() != null) {
                this.quizResultAnswerRepository.updateById(answer);
            } else {
                this.quizResultAnswerRepository.create(answer);
            }
        }

        return result;
    }
}
