package dev.jhndrncrz.quizzit.models.quiz;

import java.util.List;
import java.sql.Timestamp;

public class QuizQuestion {
    private Integer id;

    private Integer quizId;

    private String question;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    private List<QuizAnswer> answers;

    public QuizQuestion(Integer id, Integer quizId, String question, Timestamp createdAt, Timestamp updatedAt, List<QuizAnswer> answers) {
        this.setId(id);
        this.setQuizId(quizId);
        this.setQuestion(question);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
        this.setAnswers(answers);
    }

    public QuizQuestion() {}

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        this.id = id;
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

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("question cannot be null or empty");
        }

        this.question = question.trim();
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

    public List<QuizAnswer> getAnswers() {
        return this.answers;
    }

    public void setAnswers(List<QuizAnswer> answers) {
        if (answers == null) {
            throw new IllegalArgumentException("answers cannot be null");
        }

        this.answers = answers;
    }

    // public void displayQuestion() {
    //     System.out.println("Question: " + this.question);
    //     System.out.println("Answers:");
    //     for (int i = 0; i < this.answers.size(); i++) {
    //         this.answers.get(i).displayAnswer();
    //     }
    // }
}
