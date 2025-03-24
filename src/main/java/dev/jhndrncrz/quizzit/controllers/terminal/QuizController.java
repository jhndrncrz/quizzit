package dev.jhndrncrz.quizzit.controllers.terminal;

import java.sql.SQLException;

import dev.jhndrncrz.quizzit.app.terminal.AppTerminalRouter;

import dev.jhndrncrz.quizzit.models.quiz.Quiz;
import dev.jhndrncrz.quizzit.models.quiz.QuizResult;
import dev.jhndrncrz.quizzit.models.user.Student;
import dev.jhndrncrz.quizzit.services.QuizService;
import dev.jhndrncrz.quizzit.services.AuthService;

import dev.jhndrncrz.quizzit.views.terminal.quiz.SelectQuizView;
import dev.jhndrncrz.quizzit.views.terminal.quiz.CreateQuizView;
import dev.jhndrncrz.quizzit.views.terminal.quiz.ReadQuizView;
import dev.jhndrncrz.quizzit.views.terminal.quiz.TakeQuizView;
import dev.jhndrncrz.quizzit.views.terminal.quiz.RandomizeQuizView;

public class QuizController {
    private final AppTerminalRouter router;

    private final AuthService authService;
    private final QuizService quizService;

    private SelectQuizView selectQuizView;
    private CreateQuizView createQuizView;
    private ReadQuizView readQuizView;
    private TakeQuizView takeQuizView;
    private RandomizeQuizView randomizeQuizView;

    public QuizController(AuthService authService, QuizService quizService, AppTerminalRouter router)
            throws SQLException {
        this.router = router;

        this.authService = authService;
        this.quizService = quizService;
    }

    public void selectQuiz() throws SQLException {
        if (!this.authService.isAuthenticated()) {
            return;
        }

        Student authenticatedStudent = authService.getAuthenticatedStudent();

        this.selectQuizView = new SelectQuizView(1,
                quizService.findAllByStudentId(authenticatedStudent.getId()).size());

        this.selectQuizView.displayView();
    }

    public void createQuiz() throws SQLException {
        if (!this.authService.isAuthenticated()) {
            return;
        }

        Student authenticatedStudent = authService.getAuthenticatedStudent();

        this.createQuizView = new CreateQuizView(authenticatedStudent.getProfile().getFirstName());

        this.createQuizView.displayView();

        Quiz quiz = createQuizView.getQuiz();

        quiz.setCreatedBy(authenticatedStudent.getId());
        this.quizService.create(quiz);

        this.router.setRoute("/me");
        this.router.displayRoute();

        this.createQuizView.reset();
    }

    public void readQuiz() throws SQLException {
        if (!this.authService.isAuthenticated()) {
            return;
        }

        if (this.selectQuizView.getAction() == null) {
            return;
        }

        Student authenticatedStudent = authService.getAuthenticatedStudent();

        this.readQuizView = new ReadQuizView(authenticatedStudent.getProfile().getFirstName(),
                quizService.findById(Integer.parseInt(selectQuizView.getAction())));

        this.readQuizView.displayView();

        switch (this.readQuizView.getAction()) {
            case "3" -> {
                this.router.setRoute("/me/quizzes/take");
                this.router.displayRoute();
            }
            case "4" -> {
                this.router.setRoute("/me");
                this.router.displayRoute();
            }
        }

        this.selectQuizView.reset();
        this.readQuizView.reset();
    }

    public void takeQuiz() throws SQLException {
        if (!this.authService.isAuthenticated()) {
            return;
        }

        if (this.selectQuizView.getAction() == null) {
            return;
        }

        Student authenticatedStudent = authService.getAuthenticatedStudent();
        Integer quizId = Integer.parseInt(selectQuizView.getAction());

        QuizResult previousResult = quizService.takeQuizResult(quizId, authenticatedStudent.getId());

        this.takeQuizView = new TakeQuizView(authenticatedStudent.getProfile().getFirstName(),
                quizService.findById(quizId), previousResult);

        this.takeQuizView.displayView();

        switch (this.takeQuizView.getAction()) {
            case "SAVE" -> {
                QuizResult result = this.takeQuizView.getResult();

                result.setStudentId(authenticatedStudent.getId());
                result.setQuizId(quizId);

                quizService.recordQuizResult(result);
            }
            case "SUBMIT" -> {
                QuizResult result = this.takeQuizView.getResult();

                result.setStudentId(authenticatedStudent.getId());
                result.setQuizId(quizId);
                result.setIsSubmitted(true);

                quizService.recordQuizResult(result);
            }
        }

        this.router.setRoute("/me");
        this.router.displayRoute();

        this.selectQuizView.reset();
        this.takeQuizView.reset();
    }

    public void randomizeQuiz() throws SQLException {
        if (!this.authService.isAuthenticated()) {
            return;
        }

        Student authenticatedStudent = authService.getAuthenticatedStudent();

        this.randomizeQuizView = new RandomizeQuizView(authenticatedStudent.getProfile().getFirstName(),
                quizService.findAllByStudentId(authenticatedStudent.getId()));

        this.randomizeQuizView.displayView();

        Quiz quiz = new Quiz();

        quiz.setTitle("New Random Quiz");
        quiz.setDescription("A new random quiz!");
        quiz.setCreatedBy(authenticatedStudent.getId());

        for (var el : this.randomizeQuizView.getSelectedQuizIds()) {
            System.out.format("%s, ", el);
        }
        System.console().readLine();

        try {
            this.quizService.randomizeQuiz(quiz, Integer.parseInt(this.randomizeQuizView.getAction()), this.randomizeQuizView.getSelectedQuizIds());
        } catch (NumberFormatException e) {
            return;
        }

        this.router.setRoute("/me");
        this.router.displayRoute();

        this.randomizeQuizView.reset();
    }
}
