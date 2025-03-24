package dev.jhndrncrz.quizzit.models.user;

import java.sql.Timestamp;

public class Student {
    private Integer id;

    private String studentNumber;
    private String username;
    private String password;
    private StudentProfile profile; 

    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Student(Integer id, String studentNumber, String username, String password, StudentProfile profile, Timestamp createdAt, Timestamp updatedAt) {
        this.setId(id);
        this.setStudentNumber(studentNumber);
        this.setUsername(username);
        this.setPassword(password);
        this.setProfile(profile);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
    }

    public Student() {
        this.id = null;
        this.studentNumber = null;
        this.username = null;
        this.password = null;
        this.profile = null;
        this.createdAt = null;
        this.updatedAt = null;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        this.id = id;
    }

    public String getStudentNumber() {
        return this.studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        if (studentNumber == null || studentNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("studentNumber cannot be null or empty");
        }

        this.studentNumber = studentNumber.trim();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("username cannot be null or empty");
        }

        this.username = username.trim();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("password cannot be null or empty");
        }

        this.password = password.trim();
    }

    public StudentProfile getProfile() {
        return this.profile;
    }

    public void setProfile(StudentProfile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("profile cannot be null");
        }

        this.profile = profile;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("createdAt cannot be null");
        }

        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        if (updatedAt == null) {
            throw new IllegalArgumentException("updatedAt cannot be null");
        }

        if (updatedAt.before(this.createdAt)) {
            throw new IllegalArgumentException("updatedAt cannot be before createdAt");
        }

        this.updatedAt = updatedAt;
    }
}
