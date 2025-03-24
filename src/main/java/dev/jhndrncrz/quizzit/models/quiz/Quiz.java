package dev.jhndrncrz.quizzit.models.quiz;

import java.util.List;
import java.sql.Timestamp;

public class Quiz {
    private Integer id;

    private String title;
    private String description;

    private Integer createdBy;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    private List<QuizQuestion> questions;

    public Quiz(Integer id, String title, String description, Integer createdBy, Timestamp createdAt,
            Timestamp updatedAt, List<QuizQuestion> questions) {
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setCreatedBy(createdBy);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
        this.setQuestions(questions);
    }

    public Quiz() {
        this.id = null;
        this.title = null;
        this.description = null;
        this.createdBy = null;
        this.createdAt = null;
        this.updatedAt = null;
        this.questions = null;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("title cannot be null or empty");
        }

        this.title = title.trim();
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("description cannot be null or empty");
        }

        this.description = description.trim();
    }

    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        if (createdBy == null) {
            throw new IllegalArgumentException("createdBy cannot be null");
        }

        this.createdBy = createdBy;
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

    public List<QuizQuestion> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<QuizQuestion> questions) {
        if (questions == null) {
            throw new IllegalArgumentException("questions cannot be null");
        }

        this.questions = questions;
    }
}
