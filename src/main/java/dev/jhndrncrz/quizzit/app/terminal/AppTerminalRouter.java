package dev.jhndrncrz.quizzit.app.terminal;

import java.sql.SQLException;

import dev.jhndrncrz.quizzit.controllers.terminal.AuthController;
import dev.jhndrncrz.quizzit.controllers.terminal.MeController;
import dev.jhndrncrz.quizzit.controllers.terminal.QuizController;
import dev.jhndrncrz.quizzit.services.AuthService;
import dev.jhndrncrz.quizzit.services.QuizService;
import dev.jhndrncrz.quizzit.services.StudentService;
import dev.jhndrncrz.quizzit.views.terminal.IndexView;

public class AppTerminalRouter {
    private String currentRoute;

    private final AuthService authService;
    private final StudentService studentService;
    private final QuizService quizService;

    private final AuthController authController;
    private final QuizController quizController;
    private final MeController meController;
    private final IndexView indexView;

    public AppTerminalRouter() throws ClassNotFoundException, SQLException {
        this.currentRoute = "";

        this.authService = new AuthService();
        this.studentService = new StudentService();
        this.quizService = new QuizService();

        this.authController = new AuthController(this.authService, this);
        this.quizController = new QuizController(this.authService, this.quizService, this);
        this.meController = new MeController(this.authService, this.studentService, this.quizService, this);
        this.indexView = new IndexView(this);
    }

    public String getRoute() {
        return this.currentRoute;
    }

    public void setRoute(String route) {
        this.currentRoute = route;
    }

    public void displayRoute() throws SQLException {
        switch (this.currentRoute) {
            case "" -> {
                this.indexView.displayView();
            }
            case "/auth/login" -> {
                this.authController.login();
            }
            case "/auth/logout" -> {
                this.authController.logout();
            }
            case "/auth/signup" -> {
                this.authController.signup();
            }
            case "/me" -> {
                this.meController.getMyDashboard();
            }
            case "/me/profile" -> {
                // this.meController.getMyProfile();
                System.out.println("This feature is not implemented, since this is not required.");
                System.console().readLine();
            }
            case "/me/quizzes" -> {
                this.meController.getMyQuizzes();
            }
            case "/me/quizzes/history" -> {
                this.meController.getMyQuizHistory();
            }
            case "/me/quizzes/select" -> {
                this.quizController.selectQuiz();
            }
            case "/me/quizzes/create" -> {
                this.quizController.createQuiz();
            }
            case "/me/quizzes/read" -> {
                this.quizController.readQuiz();
            }
            case "/me/quizzes/randomize" -> {
                this.quizController.randomizeQuiz();
            }
            case "/me/quizzes/update" -> {
                // this.quizController.updateQuiz();
                System.out.println("This feature is not implemented, since this is not required.");
                System.console().readLine();
            }
            case "/me/quizzes/delete" -> {
                // this.quizController.deleteQuiz();
                System.out.println("This feature is not implemented, since this is not required.");
                System.console().readLine();
            }
            case "/me/quizzes/take" -> {
                this.quizController.takeQuiz();
            }
            default -> {
                System.out.println("This feature is not implemented, since this is not required.");
                System.console().readLine();
            }
        }
    }
}
