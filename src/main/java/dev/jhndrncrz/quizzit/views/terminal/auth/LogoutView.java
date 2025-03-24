package dev.jhndrncrz.quizzit.views.terminal.auth;

import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class LogoutView {
    private boolean isConfirmed;

    private boolean isIsConfirmedValid;

    public LogoutView() {
        this.reset();
    }

    public void displayView() {
        AppIO.clearScreen();

        while (!isIsConfirmedValid) {
            AppIO.clearScreen();

            System.out.println("------");
            System.out.println("LOGOUT");
            System.out.println("------");
            System.out.println();

            System.out.println("Are you sure you want to logout? [Y]es/[N]o");
            
            System.out.print("Enter your choice: ");
            String choice = System.console().readLine();

            if (choice.toLowerCase().startsWith("y")) {
                this.setConfirmed(true);
            } else if (choice.toLowerCase().startsWith("n")) {
                this.setConfirmed(false);
            } else {
                System.out.println("-- Invalid choice. Please try again.");
            }

            System.out.println();
        }

        System.out.println();
        System.out.println();
        System.out.println();
    }

    public boolean isConfirmed() {
        return this.isConfirmed;
    }

    public void setConfirmed(boolean isConfirmed) {
        this.isIsConfirmedValid = true;
        
        this.isConfirmed = isConfirmed;
    }

    public void reset(){
        this.isConfirmed = false;
        this.isIsConfirmedValid = false;
    }
}
 