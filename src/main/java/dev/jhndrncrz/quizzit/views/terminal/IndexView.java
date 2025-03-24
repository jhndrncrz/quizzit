package dev.jhndrncrz.quizzit.views.terminal;

import java.sql.SQLException;

import dev.jhndrncrz.quizzit.app.terminal.AppTerminalRouter;
import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class IndexView {
    private final AppTerminalRouter router;

    private final String title = "### Welcome to Quizzit! ###";

    private String action;

    private boolean isActionValid;

    public IndexView(AppTerminalRouter router) {
        this.router = router;

        this.reset();
    }

    public void displayView() throws SQLException {
        while (!isActionValid) {
            AppIO.clearScreen();

            System.out.println("-".repeat(this.title.length()));
            System.out.println(this.title);
            System.out.println("-".repeat(this.title.length()));
            System.out.println();

            System.out.println("What do you want to do?");
            System.out.println("-----------------------");
            System.out.println("[1] Login");
            System.out.println("[2] Signup");

            System.out.print("Enter your choice: ");
            this.setAction(System.console().readLine());
        }

        System.out.println();
        System.out.println();
        System.out.println();
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) throws SQLException {
        System.out.println();

        switch (action.trim()) {
            case "1" -> {
                this.router.setRoute("/auth/login");
                this.router.displayRoute();
                this.isActionValid = true;
            }
            case "2" -> {
                this.router.setRoute("/auth/signup");
                this.router.displayRoute();
                this.isActionValid = true;
            }
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
