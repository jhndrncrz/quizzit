package dev.jhndrncrz.quizzit.views.terminal.me;

import java.util.List;
import java.util.ListIterator;

import dev.jhndrncrz.quizzit.models.quiz.Quiz;

import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class MyQuizzesView {
    private String firstName;
    private List<Quiz> quizzes;

    private String action;

    private boolean isActionValid;

    public MyQuizzesView(String firstName, List<Quiz> quizzes) {
        this.firstName = firstName;
        this.quizzes = quizzes;

        this.reset();
    }

    public void displayView() {
        while (!isActionValid) {
            AppIO.clearScreen();

            System.out.println("----------");
            System.out.println("MY QUIZZES");
            System.out.println("----------");
            System.out.println();

            System.out.println("Your Quizzes");
            System.out.println("------------");

            if (quizzes.isEmpty()) {
                System.out.println("No quizzes found!");
            } else {
                for (ListIterator<Quiz> iterator = quizzes.listIterator(); iterator.hasNext();) {
                    System.out.format("[%d] %s\n", iterator.nextIndex() + 1, iterator.next().getTitle());
                }
            }
            System.out.println();

            System.out.format("What do you want to do, %s?\n", this.firstName);
            System.out.format("------------------------%s\n", "-".repeat(this.firstName.length()));
            System.out.println("[1] Select Quiz");
            System.out.println("[2] Create Quiz");
            System.out.println("[3] Create Random Quiz");
            System.out.println("[4] Back");

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
            case "1", "2", "3", "4" -> this.isActionValid = true;
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
