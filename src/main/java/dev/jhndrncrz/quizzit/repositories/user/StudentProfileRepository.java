package dev.jhndrncrz.quizzit.repositories.user;

import dev.jhndrncrz.quizzit.models.user.StudentProfile;
import dev.jhndrncrz.quizzit.utilities.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentProfileRepository {
    private final Connection connection;

    public StudentProfileRepository() throws ClassNotFoundException, SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public StudentProfile findByStudentId(Integer studentId) throws SQLException {
        final String queryString = """
                    SELECT
                        *
                    FROM
                        `StudentProfile`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new StudentProfile(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"));
                }
            }
        }

        return null;
    }

    public List<StudentProfile> findAll() throws SQLException {
        List<StudentProfile> studentProfiles = new ArrayList<>();

        final String queryString = """
                    SELECT
                        *
                    FROM
                        `StudentProfile`
                """;

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(queryString)) {
                while (resultSet.next()) {
                    studentProfiles.add(new StudentProfile(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name")));
                }
            }
        }

        return studentProfiles;
    }

    public StudentProfile create(StudentProfile studentProfile) throws SQLException {
        final String queryString = """
                    INSERT INTO
                        `StudentProfile` (`id`, `first_name`, `last_name`)
                    VALUES
                        (?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, studentProfile.getId());
            statement.setString(2, studentProfile.getFirstName());
            statement.setString(3, studentProfile.getLastName());

            statement.executeUpdate();
        }

        return studentProfile;
    }

    public void deleteById(Integer id) throws SQLException {
        final String queryString = """
                    DELETE
                    FROM
                        `StudentProfile`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public StudentProfile updateById(StudentProfile studentProfile) throws SQLException {
        final String queryString = """
                    UPDATE
                        `StudentProfile`
                    SET
                        `first_name` = ?,
                        `last_name` = ?
                    WHERE
                        `id` = ?,
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setString(1, studentProfile.getFirstName());
            statement.setString(2, studentProfile.getLastName());
            statement.setInt(3, studentProfile.getId());

            statement.executeUpdate();
        }

        return studentProfile;
    }
}