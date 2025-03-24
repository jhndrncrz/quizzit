package dev.jhndrncrz.quizzit.services;

import dev.jhndrncrz.quizzit.models.user.Student;
import dev.jhndrncrz.quizzit.repositories.user.StudentRepository;
import dev.jhndrncrz.quizzit.utilities.logging.AppLog;

import java.sql.SQLException;

public class AuthService {
    private final AppLog appLog;
    private final StudentRepository studentRepository;

    private boolean isAuthenticated;
    private Student authenticatedStudent;

    public AuthService() throws ClassNotFoundException, SQLException {
        this.appLog = AppLog.getInstance();
        this.studentRepository = new StudentRepository();
        
        this.isAuthenticated = false;
        this.authenticatedStudent = null;
    }

    public void login(String username, String password) throws SQLException {
        if (this.studentRepository.authenticateUser(username, password)) {
            this.isAuthenticated = true;
            this.authenticatedStudent = this.studentRepository.findByUsername(username);

            appLog.addLog("Login successful!", AppLog.Type.SUCCESS);
        } else {
            appLog.addLog("Login failed!", AppLog.Type.ERROR);
        }
    }

    public void logout() {
        this.isAuthenticated = false;
        this.authenticatedStudent = null;

        appLog.addLog("Logout successful!", AppLog.Type.SUCCESS);
    }

    public void signup(Student student) throws SQLException {
        this.studentRepository.create(student);

        appLog.addLog("Signup successful!", AppLog.Type.SUCCESS);
    }

    public boolean isAuthenticated() {
        if (!this.isAuthenticated) {
            appLog.addLog("User is not authenticated!", AppLog.Type.WARNING);
        }

        return this.isAuthenticated;
    }

    public Student getAuthenticatedStudent() {
        if (!this.isAuthenticated) {
            appLog.addLog("User is not authenticated!", AppLog.Type.WARNING);
        }

        return this.authenticatedStudent;
    }

    public boolean isUsernameAvailable(String username) throws SQLException {
        return this.studentRepository.isUsernameAvailable(username);
    }

    public boolean isStudentNumberAvailable(String studentNumber) throws SQLException {
        return this.studentRepository.isStudentNumberAvailable(studentNumber);
    }
}