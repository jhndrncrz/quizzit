package dev.jhndrncrz.quizzit.views.terminal.me;

import java.util.List;
import java.util.ListIterator;

import dev.jhndrncrz.quizzit.models.quiz.QuizResult;
import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class MyQuizHistoryView {
    private String firstName;
    private List<QuizResult> results;

    private String action;

    private boolean isActionValid;

    public MyQuizHistoryView(String firstName, List<QuizResult> results) {
        this.firstName = firstName;
        this.results = results;

        this.reset();
    }

    public void displayView() {
        while (!isActionValid) {
            AppIO.clearScreen();

            System.out.println("---------------");
            System.out.println("MY QUIZ HISTORY");
            System.out.println("---------------");
            System.out.println();

            System.out.println("Your Quiz History");
            System.out.println("-----------------");

            if (results.isEmpty()) {
                System.out.println("No results found!");
            } else {
                for (ListIterator<QuizResult> iterator = results.listIterator(); iterator.hasNext();) {
                    QuizResult result = iterator.next();

                    if (result.getIsSubmitted()) {
                        System.out.format("[%d] %s (%d/%d, %.2f%%) - Quiz \"%s\" [%s]\n", iterator.nextIndex() + 1, result.getCreatedAt().toString(), result.getScore(), result.getQuiz().getQuestions().size(), 100.0 * result.getScore() / result.getQuiz().getQuestions().size(), result.getQuiz().getTitle(), "DONE");
                    } else {
                        System.out.format("[%d] %s - Quiz \"%s\" [%s]\n", iterator.nextIndex() + 1, result.getCreatedAt().toString(), result.getQuiz().getTitle(), "PENDING");
                    }
                }
            }
            System.out.println();

            System.out.format("What do you want to do, %s?\n", this.firstName);
            System.out.format("------------------------%s\n", "-".repeat(this.firstName.length()));
            System.out.println("[1] Back");

            System.out.print("Enter your choice: ");
            this.setAction(System.console().readLine());

            System.out.println();
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
            case "1" -> this.isActionValid = true;
            default -> {
                System.out.println("-- Invalid action!");
                return;
            }
        }

        this.action = action;
    }

    public void reset() {
        this.action = "";
        this.isActionValid = false;
    }
}
