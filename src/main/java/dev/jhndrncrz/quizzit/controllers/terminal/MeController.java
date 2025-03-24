package dev.jhndrncrz.quizzit.controllers.terminal;

import dev.jhndrncrz.quizzit.app.terminal.AppTerminalRouter;

import dev.jhndrncrz.quizzit.models.user.Student;
import dev.jhndrncrz.quizzit.services.QuizService;
import dev.jhndrncrz.quizzit.services.StudentService;
import dev.jhndrncrz.quizzit.services.AuthService;

import dev.jhndrncrz.quizzit.views.terminal.me.MyDashboardView;
import dev.jhndrncrz.quizzit.views.terminal.me.MyProfileView;
import dev.jhndrncrz.quizzit.views.terminal.me.MyQuizzesView;
import dev.jhndrncrz.quizzit.views.terminal.me.MyQuizHistoryView;

import java.sql.SQLException;

public class MeController {
    private final AppTerminalRouter router;

    private final AuthService authService;
    private final StudentService studentService;
    private final QuizService quizService;

    private MyDashboardView myDashboardView;
    private MyProfileView myProfileView;
    private MyQuizzesView myQuizzesView;
    private MyQuizHistoryView myQuizHistoryView;

    public MeController(AuthService authService, StudentService studentService, QuizService quizService,
            AppTerminalRouter router)
            throws SQLException {
        this.router = router;

        this.authService = authService;
        this.studentService = studentService;
        this.quizService = quizService;
    }

    public void getMyDashboard() throws SQLException {
        if (!this.authService.isAuthenticated()) {
            return;
        }

        Student authenticatedStudent = authService.getAuthenticatedStudent();

        this.myDashboardView = new MyDashboardView(authenticatedStudent.getProfile().getFirstName());
        myDashboardView.displayView();

        switch (myDashboardView.getAction()) {
            case "1" -> {
                myDashboardView.reset();

                this.router.setRoute("/me/profile");
                this.router.displayRoute();
            }
            case "2" -> {
                myDashboardView.reset();

                this.router.setRoute("/me/quizzes");
                this.router.displayRoute();
            }
            case "3" -> {
                myDashboardView.reset();

                this.router.setRoute("/me/quizzes/history");
                this.router.displayRoute();
            }
            case "4" -> {
                myDashboardView.reset();

                this.router.setRoute("/auth/logout");
                this.router.displayRoute();
            }
            default -> {
                return;
            }
        }

        myDashboardView.reset();
    }

    // public void getMyProfile() throws UnsupportedOperationException {
    // if (!this.authService.isAuthenticated()) {

    // }

    // myProfileView.displayView();
    // }

    public void getMyQuizzes() throws SQLException {
        if (!this.authService.isAuthenticated()) {
            return;
        }

        Student authenticatedStudent = authService.getAuthenticatedStudent();

        this.myQuizzesView = new MyQuizzesView(authenticatedStudent.getProfile().getFirstName(),
                quizService.findAllByStudentId(authenticatedStudent.getId()));
        myQuizzesView.displayView();

        switch (myQuizzesView.getAction()) {
            case "1" -> {
                myQuizzesView.reset();

                this.router.setRoute("/me/quizzes/select");
                this.router.displayRoute();

                this.router.setRoute("/me/quizzes/read");
                this.router.displayRoute();
            }
            case "2" -> {
                myQuizzesView.reset();

                this.router.setRoute("/me/quizzes/create");
                this.router.displayRoute();
            }
            case "3" -> {
                myQuizzesView.reset();

                this.router.setRoute("/me/quizzes/randomize");
                this.router.displayRoute();
            }
            case "4" -> {
                myQuizzesView.reset();

                this.router.setRoute("/me");
                this.router.displayRoute();
            }
            default -> {
                return;
            }
        }

        myQuizzesView.reset();
    }

    public void getMyQuizHistory() throws SQLException {
        if (!this.authService.isAuthenticated()) {
            return;
        }

        Student authenticatedStudent = authService.getAuthenticatedStudent();

        this.myQuizHistoryView = new MyQuizHistoryView(authenticatedStudent.getProfile().getFirstName(),
                quizService.findAllQuizResultsByStudentId(authenticatedStudent.getId()));
        this.myQuizHistoryView.displayView();

        switch (this.myQuizHistoryView.getAction()) {
            case "1" -> {
                this.myQuizHistoryView.reset();

                this.router.setRoute("/me");
                this.router.displayRoute();
            }
            default -> {
                return;
            }
        }

        this.myQuizHistoryView.reset();
    }
}
