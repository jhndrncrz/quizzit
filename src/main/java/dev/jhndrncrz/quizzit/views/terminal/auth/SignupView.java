package dev.jhndrncrz.quizzit.views.terminal.auth;

import java.sql.SQLException;

import dev.jhndrncrz.quizzit.services.AuthService;
import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class SignupView {
    private AuthService authService;

    private String studentNumber;
    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;

    private boolean isStudentNumberValid;
    private boolean isUsernameValid;
    private boolean isPasswordValid;
    private boolean isConfirmPasswordValid;
    private boolean isFirstNameValid;
    private boolean isLastNameValid;

    public SignupView() throws ClassNotFoundException, SQLException {
        this.authService = new AuthService();

        this.reset();
    }

    public void displayView() throws SQLException {
        AppIO.clearScreen();

        System.out.println("------");
        System.out.println("SIGNUP");
        System.out.println("------");
        System.out.println();

        System.out.println("Enter your signup credentials.");
        System.out.println("(Enter \"X\" if you want to login with an existing account instead.)");

        while (!isStudentNumberValid) {
            System.out.print("Student Number: ");
            this.setStudentNumber(System.console().readLine());

            if (this.getStudentNumber().equals("X")) {
                this.reset();
                return;
            }
        }

        while (!isUsernameValid) {
            System.out.print("Username: ");
            this.setUsername(System.console().readLine());
        }

        while (!isPasswordValid) {
            System.out.print("Password: ");
            this.setPassword(new String(System.console().readPassword()));
        }

        while (!isConfirmPasswordValid) {
            System.out.print("Confirm Password: ");
            this.setConfirmPassword(new String(System.console().readPassword()));
        }

        while (!isFirstNameValid) {
            System.out.print("First Name: ");
            this.setFirstName(System.console().readLine());
        }

        while (!isLastNameValid) {
            System.out.print("Last Name: ");
            this.setLastName(System.console().readLine());
        }

        System.out.println();
        System.out.println();
        System.out.println();
    }

    public String getStudentNumber() {
        return this.studentNumber;
    }

    public void setStudentNumber(String studentNumber) throws SQLException {
        if (studentNumber.equals("X")) {
            this.studentNumber = "X";

            return;
        }

        if (studentNumber.length() != 8) {
            System.out.println("-- Student Number must be 8 characters long.");
            return;
        }

        if (!this.authService.isStudentNumberAvailable(studentNumber)) {
            System.out.println("-- Student Number is already taken.");
            return;
        }

        this.studentNumber = studentNumber;
        this.isStudentNumberValid = true;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("-- Username cannot be empty.");
            return;
        }

        if (!this.authService.isUsernameAvailable(username)) {
            System.out.println("-- Username is already taken.");

            return;
        }

        this.username = username;
        this.isUsernameValid = true;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        if (password.length() < 8) {
            System.out.println("-- Password must be at least 8 characters long.");
            return;
        }

        this.password = password;
        this.isPasswordValid = true;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        if (!this.getPassword().equals(confirmPassword)) {
            System.out.println("-- Passwords do not match.");
            return;
        }

        this.confirmPassword = confirmPassword;
        this.isConfirmPasswordValid = true;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            System.out.println("-- First Name cannot be empty.");
            return;
        }

        this.firstName = firstName;
        this.isFirstNameValid = true;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            System.out.println("-- Last Name cannot be empty.");
            return;
        }

        this.lastName = lastName;
        this.isLastNameValid = true;
    }

    public void reset() {
        this.studentNumber = "";
        this.username = "";
        this.password = "";
        this.confirmPassword = "";
        this.firstName = "";
        this.lastName = "";

        this.isStudentNumberValid = false;
        this.isUsernameValid = false;
        this.isPasswordValid = false;
        this.isConfirmPasswordValid = false;
        this.isFirstNameValid = false;
        this.isLastNameValid = false;
    }
}
