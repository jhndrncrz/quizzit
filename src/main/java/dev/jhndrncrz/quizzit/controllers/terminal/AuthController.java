package dev.jhndrncrz.quizzit.controllers.terminal;

import dev.jhndrncrz.quizzit.app.terminal.AppTerminalRouter;

import dev.jhndrncrz.quizzit.models.user.Student;
import dev.jhndrncrz.quizzit.models.user.StudentProfile;
import dev.jhndrncrz.quizzit.services.AuthService;

import dev.jhndrncrz.quizzit.views.terminal.auth.LoginView;
import dev.jhndrncrz.quizzit.views.terminal.auth.LogoutView;
import dev.jhndrncrz.quizzit.views.terminal.auth.SignupView;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class AuthController {
    private final AppTerminalRouter router;

    private final AuthService authService;

    private final LoginView loginView;
    private final LogoutView logoutView;
    private final SignupView signupView;

    public AuthController(AuthService authService, AppTerminalRouter router)
            throws ClassNotFoundException, SQLException {
        this.router = router;

        this.authService = authService;

        this.loginView = new LoginView();
        this.logoutView = new LogoutView();
        this.signupView = new SignupView();
    }

    public void login() throws SQLException {
        this.loginView.displayView();

        if (this.loginView.getUsername().equals("X")) {
            this.loginView.reset();

            this.router.setRoute("/auth/signup");
            this.router.displayRoute();

            return;
        }

        this.authService.login(loginView.getUsername(), loginView.getPassword());

        if (!this.authService.isAuthenticated()) {
            this.loginView.reset();

            System.out.println("-- Invalid credentials!");
            System.out.print("Press Enter to continue...");
            System.console().readLine();

            this.router.setRoute("/auth/login");
            this.router.displayRoute();

            return;
        }

        this.loginView.reset();

        this.router.setRoute("/me");
        this.router.displayRoute();

        this.loginView.reset();
    }

    public void signup() throws SQLException {
        this.signupView.displayView();

        if (this.signupView.getStudentNumber().equals("X")) {
            this.signupView.reset();

            this.router.setRoute("/auth/login");
            this.router.displayRoute();

            return;
        }

        this.authService.signup(new Student(-1, this.signupView.getStudentNumber(), signupView.getUsername(),
                this.signupView.getPassword(),
                new StudentProfile(-1, this.signupView.getFirstName(), this.signupView.getLastName()),
                new Timestamp((new Date()).getTime()), new Timestamp(new Date().getTime())));

        this.signupView.reset();

        this.router.setRoute("/auth/login");
        this.router.displayRoute();

        this.signupView.reset();
    }

    public void logout() throws SQLException {
        this.logoutView.displayView();

        if (!this.logoutView.isConfirmed()) {
            this.logoutView.reset();

            this.router.setRoute("/me");
            this.router.displayRoute();
        } else {
            this.authService.logout();

            this.logoutView.reset();

            this.router.setRoute("");
            this.router.displayRoute();
        }

        this.logoutView.reset();
    }
}
