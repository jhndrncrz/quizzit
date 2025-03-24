package dev.jhndrncrz.quizzit.views.terminal.quiz;

import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class SelectQuizView {
    private int startIndex;
    private int endIndex;

    private String action;

    private boolean isActionValid;

    public SelectQuizView(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;

        this.reset();
    }

    public void displayView() {
        while (!isActionValid) {
            AppIO.clearScreen();

            System.out.println("-----------");
            System.out.println("SELECT QUIZ");
            System.out.println("-----------");
            System.out.println();

            System.out.println("Select a quiz to take an action to.");
            System.out.format("Enter your choice [%d-%d]: ", startIndex, endIndex);
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
        if (!action.matches("([1-9])*")
                && (startIndex <= Integer.parseInt(action) && Integer.parseInt(action) <= endIndex)) {
            System.out.println("-- Invalid action!");
            return;
        }

        this.isActionValid = true;
        this.action = action;
    }

    public void reset() {
        this.action = "";
        this.isActionValid = false;
    }
}
