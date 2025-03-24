package dev.jhndrncrz.quizzit.models.quiz;

import java.sql.Timestamp;

public class QuizResultAnswer {
    private Integer id;

    private Integer resultId;
    private Integer questionId;
    private Integer answerId;

    private Boolean isCorrect;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    public QuizResultAnswer(Integer id, Integer resultId, Integer questionId, Integer answerId, Boolean isCorrect,
            Timestamp createdAt, Timestamp updatedAt) {
        this.setId(id);
        this.setResultId(resultId);
        this.setQuestionId(questionId);
        this.setAnswerId(answerId);
        this.setIsCorrect(isCorrect);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
    }

    public QuizResultAnswer() {
        this.id = null;
        this.resultId = null;
        this.questionId = null;
        this.answerId = null;
        this.isCorrect = null;
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

    public Integer getResultId() {
        return this.resultId;
    }

    public void setResultId(Integer resultId) {
        if (resultId == null) {
            throw new IllegalArgumentException("resultId cannot be null");
        }

        this.resultId = resultId;
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

    public Integer getAnswerId() {
        return this.answerId;
    }

    public void setAnswerId(Integer answerId) {
        if (answerId == null) {
            throw new IllegalArgumentException("answerId cannot be null");
        }

        this.answerId = answerId;
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
    }
}

