package dev.jhndrncrz.quizzit.views.terminal.quiz;

import dev.jhndrncrz.quizzit.models.quiz.Quiz;
import dev.jhndrncrz.quizzit.models.quiz.QuizQuestion;

import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class ReadQuizView {
    private String firstName;
    private final Quiz quiz;

    private String action;
    private int currentQuestionIndex;

    private boolean isActionValid;
    private boolean isDisplaying;

    public ReadQuizView(String firstName, Quiz quiz) {
        this.firstName = firstName;
        this.quiz = quiz;

        this.reset();
    }

    public void displayView() {
        while (isDisplaying) {
            while (!isActionValid) {
                AppIO.clearScreen();

                System.out.println("---------");
                System.out.println("READ QUIZ");
                System.out.println("---------");
                System.out.println();

                System.out.println("Quiz Details");
                System.out.println("------------");
                System.out.format("Title:               %s\n", this.quiz.getTitle());
                System.out.format("Description:         %s\n", this.quiz.getDescription());
                System.out.format("Number of Questions: %d\n", this.quiz.getQuestions().size());
                System.out.format("Created At:          %s\n", this.quiz.getCreatedAt());
                System.out.format("Updated At:          %s\n", this.quiz.getUpdatedAt());
                System.out.println();

                System.out.format("Question #%d: ", this.currentQuestionIndex + 1);
                this.displayQuestion(this.quiz.getQuestions().get(this.currentQuestionIndex));
                System.out.println();

                System.out.format("What do you want to do, %s?\n", this.firstName);
                System.out.format("------------------------%s\n", "-".repeat(this.firstName.length()));
                System.out.println("[1] Previous Question");
                System.out.println("[2] Next Question");
                System.out.println("[3] Take Quiz");
                System.out.println("[4] Back");
                
                System.out.print("Enter your choice: ");
                this.setAction(System.console().readLine());

                System.out.println();
            }
        }

        System.out.println();
        System.out.println();
        System.out.println();
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        switch (action.trim()) {
            case "1", "2", "3", "4" -> this.isActionValid = true;
            default -> {
                System.out.println("-- Invalid action!");
                return;
            }
        }

        this.action = action;

        switch (action.trim()) {
            case "1" -> {
                if (this.currentQuestionIndex > 0) {
                    this.currentQuestionIndex--;
                } else {
                    System.out.println("-- No more questions!");
                }
            }
            case "2" -> {
                if (this.currentQuestionIndex < this.quiz.getQuestions().size() - 1) {
                    this.currentQuestionIndex++;
                } else {
                    System.out.println("-- No more questions!");
                }
            }
            case "3" -> {
                this.isDisplaying = false;
                return;
            }
            case "4" -> {
                this.isDisplaying = false;
                return;
            }
        }

        this.isActionValid = false;
    }

    public void reset() {
        this.currentQuestionIndex = 0;

        this.action = "";
        this.isActionValid = false;
        this.isDisplaying = true;
    }

    private void displayQuestion(QuizQuestion question) {
        System.out.println(question.getQuestion());

        System.out.println("Options");
        for (int i = 0; i < question.getAnswers().size(); i++) {
            System.out.format("    [%s] %s\n", (char) (i + 'A'), question.getAnswers().get(i).getAnswer());
        }
    }
}