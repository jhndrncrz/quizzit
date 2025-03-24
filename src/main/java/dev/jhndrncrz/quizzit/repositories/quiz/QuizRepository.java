package dev.jhndrncrz.quizzit.repositories.quiz;

import dev.jhndrncrz.quizzit.models.quiz.Quiz;
import dev.jhndrncrz.quizzit.models.quiz.QuizQuestion;
import dev.jhndrncrz.quizzit.utilities.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizRepository {
    private final Connection connection;
    private final QuizQuestionRepository quizQuestionRepository;

    public QuizRepository() throws ClassNotFoundException, SQLException {
        this.connection = DatabaseConnection.getConnection();
        this.quizQuestionRepository = new QuizQuestionRepository();
    }

    public Quiz findById(Integer id) throws SQLException {
        final String queryString = """
                    SELECT
                        *
                    FROM
                        `Quiz`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Quiz(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getInt("created_by"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at"),
                            this.quizQuestionRepository.findAllByQuizId(id));
                }
            }
        }

        return null;
    }

    public List<Quiz> findAll() throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();

        final String queryString = """
                    SELECT
                        *
                    FROM
                        `Quiz`
                """;

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(queryString)) {
                while (resultSet.next()) {
                    quizzes.add(new Quiz(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("created_by"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at"),
                        this.quizQuestionRepository.findAllByQuizId(resultSet.getInt("id"))));
                }
            }
        }

        return quizzes;
    }

    public List<Quiz> findAllByStudentId(Integer studentId) throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();

        final String queryString = """
                    SELECT
                        *
                    FROM
                        `Quiz`
                    WHERE
                        `created_by` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    quizzes.add(new Quiz(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("created_by"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at"),
                        this.quizQuestionRepository.findAllByQuizId(resultSet.getInt("id"))));
                }
            }
        }

        return quizzes;
    }

    public Quiz create(Quiz quiz) throws SQLException {
        final String queryString = """
                    INSERT INTO
                        `Quiz` (`title`, `description`, `created_by`)
                    VALUES
                        (?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, quiz.getTitle());
            statement.setString(2, quiz.getDescription());
            statement.setInt(3, quiz.getCreatedBy());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating student failed, no rows affected.");
            }

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    quiz.setId(resultSet.getInt(1));

                    for (QuizQuestion question : quiz.getQuestions()) {
                        question.setQuizId(quiz.getId());
                    }
                }
            }
        }

        for (QuizQuestion question : quiz.getQuestions()) {
            this.quizQuestionRepository.create(question);
        }

        return quiz;
    }

    public void deleteById(Integer id) throws SQLException {
        final String queryString = """
                    DELETE
                    FROM
                        `Quiz`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public Quiz updateById(Quiz quiz) throws SQLException {
        final String queryString = """
                    UPDATE
                        `Quiz`
                    SET
                        `title` = ?,
                        `description` = ?
                    WHERE
                        `id` = ?,
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setString(1, quiz.getTitle());
            statement.setString(2, quiz.getDescription());
            statement.setInt(3, quiz.getId());

            statement.executeUpdate();
        }

        for (QuizQuestion question : quiz.getQuestions()) {
            if (question.getId() != null) {
                this.quizQuestionRepository.updateById(question);
            } else {
                this.quizQuestionRepository.create(question);
            }
        }

        return quiz;
    }
}