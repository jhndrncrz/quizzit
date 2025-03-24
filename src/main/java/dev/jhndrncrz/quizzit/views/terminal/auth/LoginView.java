package dev.jhndrncrz.quizzit.views.terminal.auth;

import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class LoginView {
    private String username;
    private String password;

    private boolean isUsernameValid;
    private boolean isPasswordValid;

    public LoginView() {
        this.reset();
    }

    public void displayView() {
        while (!isUsernameValid) {
            AppIO.clearScreen();

            System.out.println("-----");
            System.out.println("LOGIN");
            System.out.println("-----");
            System.out.println();

            System.out.println("Enter your login credentials.");
            System.out.println("(Enter \"X\" if you want to signup for an account instead.)");

            System.out.print("Username: ");
            this.setUsername(System.console().readLine());

            if (this.getUsername().equals("X")) {
                return;
            }
        }

        while (!isPasswordValid) {
            System.out.print("Password: ");
            this.setPassword(new String(System.console().readPassword()));
        }

        System.out.println();
        System.out.println();
        System.out.println();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        if (username.equals("X")) {
            this.username = "X";

            return;
        }

        if (username == null || username.trim().isEmpty()) {
            System.out.println("-- Username cannot be empty.");
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

    public void reset() {
        this.username = "";
        this.password = "";
        this.isUsernameValid = false;
        this.isPasswordValid = false;
    }
}
