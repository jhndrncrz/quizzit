package dev.jhndrncrz.quizzit.models.quiz;

import java.sql.Timestamp;
import java.util.List;

public class QuizResult {
    private Integer id;

    private Integer studentId;
    private Integer quizId;

    private Integer score;
    private Boolean isSubmitted;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    private Quiz quiz;

    List<QuizResultAnswer> answers;

    public QuizResult(Integer id, Integer studentId, Integer quizId, Integer score, Boolean isSubmitted, Timestamp createdAt,
            Timestamp updatedAt, Quiz quiz, List<QuizResultAnswer> answers) {
        this.setId(id);
        this.setStudentId(studentId);
        this.setQuizId(quizId);
        this.setScore(score);
        this.setIsSubmitted(isSubmitted);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
        this.setQuiz(quiz);
        this.setAnswers(answers);
    }

    public QuizResult() {
        this.id = null;
        this.studentId = null;
        this.quizId = null;
        this.score = null;
        this.isSubmitted = null;
        this.createdAt = null;
        this.updatedAt = null;
        this.quiz = null;
        this.answers = null;
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

    public Integer getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Integer studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("studentId cannot be null");
        }

        this.studentId = studentId;
    }

    public Integer getQuizId() {
        return this.quizId;
    }

    public void setQuizId(Integer quizId) {
        if (quizId == null) {
            throw new IllegalArgumentException("quizId cannot be null");
        }

        this.quizId = quizId;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        if (score == null) {
            throw new IllegalArgumentException("score cannot be null");
        }

        this.score = score;
    }

    public Boolean getIsSubmitted() {
        return this.isSubmitted;
    }

    public void setIsSubmitted(Boolean isSubmitted) {
        if (isSubmitted == null) {
            throw new IllegalArgumentException("isSubmitted cannot be null");
        }

        this.isSubmitted = isSubmitted;
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

    public Quiz getQuiz() {
        return this.quiz;
    }

    public void setQuiz(Quiz quiz) {
        if (quiz == null) {
            throw new IllegalArgumentException("quiz cannot be null");
        }

        this.quiz = quiz;
    }

    public List<QuizResultAnswer> getAnswers() {
        return this.answers;
    }

    public void setAnswers(List<QuizResultAnswer> answers) {
        if (answers == null) {
            throw new IllegalArgumentException("answers cannot be null");
        }

        this.answers = answers;
    }
}
