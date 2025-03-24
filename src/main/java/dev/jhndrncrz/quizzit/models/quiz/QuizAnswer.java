package dev.jhndrncrz.quizzit.models.quiz;

import java.sql.Timestamp;

public class QuizAnswer {
    private Integer id;
    
    private Integer questionId;

    private String answer;
    private Integer answerIndex;

    private Boolean isCorrect;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    public QuizAnswer(Integer id, Integer questionId, String answer, Integer answerIndex, Boolean isCorrect, Timestamp createdAt, Timestamp updatedAt) {
        this.setId(id);
        this.setQuestionId(questionId);
        this.setAnswer(answer);
        this.setAnswerIndex(answerIndex);
        this.setIsCorrect(isCorrect);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
    }

    public QuizAnswer() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        this.id = id;
    }

    public Integer getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Integer questionId) {
        if (questionId == null) {
            throw new IllegalArgumentException("questionId cannot be null");
        }

        this.questionId = questionId;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        if (answer == null || answer.trim().isEmpty()) {
            throw new IllegalArgumentException("Answer text cannot be null or empty");
        }

        this.answer = answer.trim();
    }

    public Integer getAnswerIndex() {
        return this.answerIndex;
    }

    public void setAnswerIndex(Integer answerIndex) {
        if (answerIndex == null) {
            throw new IllegalArgumentException("answerIndex cannot be null");
        }

        this.answerIndex = answerIndex;
    }

    public Boolean getIsCorrect() {
        return this.isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        if (isCorrect == null) {
            throw new IllegalArgumentException("isCorrect cannot be null");
        }

        this.isCorrect = isCorrect;
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

    public void displayAnswer() {
        System.out.println((char) (this.getAnswerIndex() + 'A') + ". " + this.answer);
    }
}


