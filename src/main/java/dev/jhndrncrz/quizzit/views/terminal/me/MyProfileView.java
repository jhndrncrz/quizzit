package dev.jhndrncrz.quizzit.views.terminal.me;

import dev.jhndrncrz.quizzit.models.user.Student;
import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class MyProfileView {
    private String firstName;
    private final Student student;

    private String action;

    private boolean isActionValid;
    private boolean isDisplaying;

    public MyProfileView(String firstName, Student student) {
        this.firstName = firstName;
        this.student = student;

        this.reset();
    }

    public void displayView() {
        while (isDisplaying) {
            while (!isActionValid) {
                AppIO.clearScreen();

                System.out.println("---------");
                System.out.println("MY PROFILE");
                System.out.println("---------");
                System.out.println();

                System.out.println("Profile Details");
                System.out.println("------------");
                System.out.format("Username:       %s\n", this.student.getUsername());
                System.out.format("Student Number: %s\n", this.student.getStudentNumber());
                System.out.format("Full Name:      %s\n", this.student.getProfile().getLastName(), this.student.getProfile().getLastName());
                System.out.println();

                System.out.format("What do you want to do, %s?\n", this.firstName);
                System.out.format("------------------------%s\n", "-".repeat(this.firstName.length()));
                System.out.println("[1] Back");
                
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
            case "1" -> this.isActionValid = true;
            default -> {
                System.out.println("-- Invalid action!");
                return;
            }
        }

        this.action = action;

        switch (action.trim()) {
            case "1" -> {
                this.isDisplaying = false;
                return;
            }
        }

        this.isActionValid = false;
    }

    public void reset() {
        this.action = "";
        this.isActionValid = false;
        this.isDisplaying = true;
    }
}
