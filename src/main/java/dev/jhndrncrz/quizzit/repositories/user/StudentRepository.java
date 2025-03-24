package dev.jhndrncrz.quizzit.repositories.user;

import dev.jhndrncrz.quizzit.models.user.Student;
import dev.jhndrncrz.quizzit.utilities.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private final Connection connection;
    private final StudentProfileRepository studentProfileRepository;

    public StudentRepository() throws ClassNotFoundException, SQLException {
        this.connection = DatabaseConnection.getConnection();
        this.studentProfileRepository = new StudentProfileRepository();
    }

    public Student findById(Integer id) throws SQLException {
        final String queryString = """
                    SELECT
                        *
                    FROM
                        `Student`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Student(
                            resultSet.getInt("id"),
                            resultSet.getString("student_number"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            this.studentProfileRepository.findByStudentId(resultSet.getInt("id")),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at"));
                }
            }
        }

        return null;
    }

    public Student findByUsername(String username) throws SQLException {
        final String queryString = """
                    SELECT
                        *
                    FROM
                        `Student`
                    WHERE
                        `username` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Student(
                            resultSet.getInt("id"),
                            resultSet.getString("student_number"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            this.studentProfileRepository.findByStudentId(resultSet.getInt("id")),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at"));
                }
            }
        }

        return null;
    }

    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();

        final String queryString = """
                    SELECT
                        *
                    FROM
                        `Student`
                """;

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(queryString)) {
                while (resultSet.next()) {
                    students.add(new Student(
                            resultSet.getInt("id"),
                            resultSet.getString("student_number"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            this.studentProfileRepository.findByStudentId(resultSet.getInt("id")),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("updated_at")));
                }
            }
        }

        return students;
    }

    public Student create(Student student) throws SQLException {
        final String queryString = """
                    INSERT INTO
                        `Student` (
                            `student_number`,
                            `username`,
                            `password`
                        )
                    VALUES
                        (?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getStudentNumber());
            statement.setString(2, student.getUsername());
            statement.setString(3, student.getPassword());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating student failed, no rows affected.");
            }

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    student.setId(resultSet.getInt(1));
                    student.getProfile().setId(resultSet.getInt(1));
                }
            }
        }

        System.out.println(student.getProfile().getId());

        this.studentProfileRepository.create(student.getProfile());

        return student;
    }

    public void deleteById(Integer id) throws SQLException {
        final String queryString = """
                    DELETE
                    FROM
                        `Student`
                    WHERE
                        `id` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public Student updateById(Student student) throws SQLException {
        final String queryString = """
                    UPDATE
                        `Student`
                    SET
                        `student_number` = ?,
                        `username` = ?,
                        `password` = ?
                    WHERE
                        `id` = ?,
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setString(1, student.getStudentNumber());
            statement.setString(2, student.getUsername());
            statement.setString(3, student.getPassword());
            statement.setInt(4, student.getId());

            statement.executeUpdate();
        }

        this.studentProfileRepository.updateById(student.getProfile());

        return student;
    }

    public boolean authenticateUser(String username, String password) throws SQLException {
        final String queryString = """
                    SELECT
                        *
                    FROM
                        `Student`
                    WHERE
                        `username` = ? AND `password` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean isUsernameAvailable(String username) throws SQLException {
        final String queryString = """
                    SELECT
                        *
                    FROM
                        `Student`
                    WHERE
                        `username` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                return !resultSet.next();
            }
        }
    }

    public boolean isStudentNumberAvailable(String studentNumber) throws SQLException {
        final String queryString = """
                    SELECT
                        *
                    FROM
                        `Student`
                    WHERE
                        `student_number` = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setString(1, studentNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                return !resultSet.next();
            }
        }
    }
}
