package dev.jhndrncrz.quizzit.repositories.quiz;

import dev.jhndrncrz.quizzit.models.quiz.QuizQuestion;
import dev.jhndrncrz.quizzit.models.quiz.QuizAnswer;
import dev.jhndrncrz.quizzit.utilities.database.DatabaseConnection;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizQuestionRepository {
    private final Connection connection;
    private final QuizAnswerRepository quizAnswerRepository;

    public QuizQuestionRepository() throws ClassNotFoundException, SQLException {
        this.connection = DatabaseConnection.getConnection();
        this.quizAnswerRepository = new QuizAnswerRepository();
    }

    public QuizQuestion findById(Integer id) throws SQLException {
        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizQuestion`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new QuizQuestion(
                            resultSet.getInt("id"),
                            resultSet.getInt("quiz_id"),
                            resultSet.getString("question"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at"),
                            this.quizAnswerRepository.findAllByQuestionId(id));
                }
            }
        }

        return null;
    }

    public List<QuizQuestion> findAll() throws SQLException {
        List<QuizQuestion> questions = new ArrayList<>();

        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizQuestion`
                """;

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(queryString)) {
                while (resultSet.next()) {
                    questions.add(new QuizQuestion(
                            resultSet.getInt("id"),
                            resultSet.getInt("quiz_id"),
                            resultSet.getString("question"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at"),
                            this.quizAnswerRepository.findAllByQuestionId(resultSet.getInt("id"))));
                }
            }
        }

        return questions;
    }

    public List<QuizQuestion> findAllByQuizIds(List<Integer> quizIds) throws SQLException {
        List<QuizQuestion> questions = new ArrayList<>();

        final String queryString = String.format("""
                    SELECT
                        *
                    FROM
                        `QuizQuestion`
                    WHERE
                        `quiz_id` IN (%s)
                """, "?, ".repeat(quizIds.size()).substring(0, "?, ".length() * quizIds.size() - 2));

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            for (int i = 0; i < quizIds.size(); i++) {
                statement.setInt(i + 1, quizIds.get(i));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    questions.add(new QuizQuestion(
                            resultSet.getInt("id"),
                            resultSet.getInt("quiz_id"),
                            resultSet.getString("question"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at"),
                            this.quizAnswerRepository.findAllByQuestionId(resultSet.getInt("id"))));
                }
            }
        }

        return questions;
    }

    public List<QuizQuestion> findAllByQuizId(Integer quizId) throws SQLException {
        List<QuizQuestion> questions = new ArrayList<>();

        final String queryString = """
                    SELECT
                        *
                    FROM
                        `QuizQuestion`
                    WHERE 
                        `quiz_id` = ?
                    ORDER BY
                        RAND()
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, quizId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    questions.add(new QuizQuestion(
                        resultSet.getInt("id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getString("question"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at"),
                        this.quizAnswerRepository.findAllByQuestionId(resultSet.getInt("id"))));
                }
            }
        }

        return questions;
    }

    public QuizQuestion create(QuizQuestion question) throws SQLException {
        final String queryString = """
                    INSERT INTO
                        `QuizQuestion` (`quiz_id`, `question`)
                    VALUES
                        (?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, question.getQuizId());
            statement.setString(2, question.getQuestion());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating QuizQuestion failed, no rows affected.");
            }

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    question.setId(resultSet.getInt(1));
                    
                    for (QuizAnswer answer : question.getAnswers()) {
                        answer.setQuestionId(question.getId());
                    }
                }
            }
        }

        for (QuizAnswer answer : question.getAnswers()) {
            this.quizAnswerRepository.create(answer);
        }

        return question;
    }

    public void deleteById(Integer id) throws SQLException {
        final String queryString = """
                    DELETE
                    FROM
                        `QuizQuestion`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public QuizQuestion updateById(QuizQuestion question) throws SQLException {
        final String queryString = """
                    UPDATE
                        `QuizQuestion`
                    SET
                        `question` = ?,
                    WHERE
                        `id` = ?,
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setString(1, question.getQuestion());
            statement.setInt(2, question.getId());

            statement.executeUpdate();
        }

        for (QuizAnswer answer : question.getAnswers()) {
            if (answer.getId() != null) {
                this.quizAnswerRepository.updateById(answer);
            } else {
                this.quizAnswerRepository.create(answer);
            }
        }

        return question;
    }
}
