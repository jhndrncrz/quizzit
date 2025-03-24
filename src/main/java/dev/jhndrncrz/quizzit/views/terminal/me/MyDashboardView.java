package dev.jhndrncrz.quizzit.views.terminal.me;

import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class MyDashboardView {
    private String firstName;

    private String action;

    private boolean isActionValid;

    public MyDashboardView(String firstName) {
        this.firstName = firstName;

        this.reset();
    }

    public void displayView() {
        AppIO.clearScreen();

        while (!isActionValid) {
            AppIO.clearScreen();

            System.out.println("------------");
            System.out.println("MY DASHBOARD");
            System.out.println("------------");
            System.out.println();

            System.out.format("What do you want to do, %s?\n", this.firstName);
            System.out.format("------------------------%s\n", "-".repeat(this.firstName.length()));
            System.out.println("[1] View Profile");
            System.out.println("[2] View Quizzes");
            System.out.println("[3] View Quiz History");
            System.out.println("[4] Logout");

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
