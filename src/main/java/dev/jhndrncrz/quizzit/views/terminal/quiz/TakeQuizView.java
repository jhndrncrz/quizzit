package dev.jhndrncrz.quizzit.views.terminal.quiz;

import java.util.ArrayList;

import dev.jhndrncrz.quizzit.models.quiz.Quiz;
import dev.jhndrncrz.quizzit.models.quiz.QuizQuestion;
import dev.jhndrncrz.quizzit.models.quiz.QuizAnswer;
import dev.jhndrncrz.quizzit.models.quiz.QuizResult;
import dev.jhndrncrz.quizzit.models.quiz.QuizResultAnswer;
import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class TakeQuizView {
    private boolean isResumable;

    private String firstName;
    private final Quiz quiz;
    private final QuizResult result;

    private String action;
    private QuizQuestion currentQuestion;
    private int currentQuestionIndex;

    private boolean isActionValid;
    private boolean isDisplaying;

    public TakeQuizView(String firstName, Quiz quiz, QuizResult previousResult) {
        this.firstName = firstName;

        this.quiz = quiz;
        this.currentQuestionIndex = 0;
        this.currentQuestion = this.quiz.getQuestions().get(this.currentQuestionIndex);

        if (previousResult == null) {
            this.result = new QuizResult();
            this.result.setAnswers(new ArrayList<>());
            this.isResumable = false;
        } else {
            this.result = previousResult;
            this.isResumable = true;
        }

        this.reset();
    }

    public void displayView() {
        if (this.isResumable) {
            while (true) {
                AppIO.clearScreen();

                System.out.println("An existing quiz attempt is currently recorded.");
                System.out.println("Do you want to continue? [Y]es/[N]o");

                System.out.print("Enter your choice: ");
                String input = System.console().readLine();

                if (!(input.substring(0, 1).equalsIgnoreCase("y")
                        || input.substring(0, 1).equalsIgnoreCase("n"))) {
                    System.out.println("-- Invalid response!");
                    continue;
                }

                if (input.substring(0, 1).equalsIgnoreCase("y")) {
                    break;
                } else {
                    return;
                }
            }
        }

        while (isDisplaying) {
            while (!isActionValid) {
                AppIO.clearScreen();

                this.currentQuestion = this.quiz.getQuestions().get(this.currentQuestionIndex);

                System.out.println("---------");
                System.out.println("TAKE QUIZ");
                System.out.println("---------");
                System.out.println();

                System.out.println("Quiz Details");
                System.out.println("------------");
                System.out.format("Title:               %s\n", this.quiz.getTitle());
                System.out.format("Description:         %s\n", this.quiz.getDescription());
                System.out.format("Number of Questions: %d\n", this.quiz.getQuestions().size());
                System.out.format("Created At:          %s\n", this.quiz.getCreatedAt());
                System.out.format("Updated At:          %s\n", this.quiz.getUpdatedAt());

                System.out.println();
                if (this.result.getId() != null) {
                    System.out.println("(This is a continued quiz.)");
                }
                System.out.println();

                System.out.println(
                        result.getAnswers().size() + " / " + this.quiz.getQuestions().size() + " questions answered.");
                System.out.format("Question #%d: ", this.currentQuestionIndex + 1);
                this.displayQuestion(this.currentQuestion);

                if (this.result.getAnswers().stream()
                        .anyMatch((answer) -> answer.getQuestionId() == this.currentQuestion.getId())) {
                    System.out.println("(You already answered this question.)");
                }
                if (this.result.getAnswers().size() == this.quiz.getQuestions().size()) {
                    System.out.println("(You already answered all questions. You may now submit the quiz.)");
                }
                System.out.println();

                System.out.format("What do you want to do, %s?\n", this.firstName);
                System.out.format("------------------------%s\n", "-".repeat(this.firstName.length()));
                System.out.format("[%s-%s] Choose Answer\n", (char) (0 + 'A'),
                        (char) (this.currentQuestion.getAnswers().size() - 1 + 'A'));
                System.out.println("[PREV] Previous Question");
                System.out.println("[NEXT] Next Question");
                System.out.println("[SAVE] Save");
                System.out.println("[SUBMIT] Submit");

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
            case String s when s.length() == 1 && (0 <= s.toUpperCase().charAt(0) - 'A'
                    && s.toUpperCase().charAt(0) - 'A' <= this.currentQuestion.getAnswers().size()) ->
                this.isActionValid = true;
            case "PREV", "NEXT", "SAVE", "SUBMIT" -> this.isActionValid = true;
            default -> {
                System.out.println("-- Invalid action!");
                return;
            }
        }

        this.action = action;

        switch (action.trim()) {
            case String s when 0 <= s.toUpperCase().charAt(0) - 'A'
                    && s.toUpperCase().charAt(0) - 'A' <= this.currentQuestion.getAnswers().size() -> {
                QuizResultAnswer resultAnswer;

                if (this.result.getAnswers().stream()
                        .anyMatch((answer) -> answer.getQuestionId() == this.currentQuestion.getId())) {
                    resultAnswer = result.getAnswers().stream()
                            .filter((answer) -> answer.getQuestionId() == this.currentQuestion.getId()).findFirst()
                            .get();
                } else {
                    resultAnswer = new QuizResultAnswer();
                }

                QuizAnswer answer = this.currentQuestion.getAnswers().get(s.toUpperCase().charAt(0) - 'A');

                if (result.getId() != null) {
                    resultAnswer.setResultId(result.getId());
                }
                resultAnswer.setQuestionId(this.currentQuestion.getId());
                resultAnswer.setAnswerId(answer.getId());
                resultAnswer.setIsCorrect(answer.getIsCorrect());

                if (!this.result.getAnswers().stream()
                        .anyMatch((a) -> a.getQuestionId() == this.currentQuestion.getId())) {
                    result.getAnswers().add(resultAnswer);
                }

                this.handleNextQuestion();
            }
            case "PREV" -> {
                this.handlePreviousQuestion();
            }
            case "NEXT" -> {
                this.handleNextQuestion();
            }
            case "SAVE" -> {
                int score = 0;

                for (QuizResultAnswer answer : result.getAnswers()) {
                    if (answer.getIsCorrect()) {
                        ++score;
                    }
                }

                result.setScore(score);

                this.isDisplaying = false;
                return;
            }
            case "SUBMIT" -> {
                int score = 0;

                for (QuizResultAnswer answer : result.getAnswers()) {
                    if (answer.getIsCorrect()) {
                        ++score;
                    }
                }

                result.setScore(score);
                result.setIsSubmitted(true);

                System.out.println();

                System.out.format("Your score is: (%d/%d, %.2f%%).\n", score, this.quiz.getQuestions().size(),
                        100.0 * score / this.quiz.getQuestions().size());

                System.out.print("Press Enter to continue...");
                System.console().readLine();

                this.isDisplaying = false;
                return;
            }
            default -> {
                System.out.println("-- Invalid action!");
                return;
            }
        }

        this.isActionValid = false;
    }

    public QuizResult getResult() {
        return this.result;
    }

    public void reset() {
        this.currentQuestionIndex = 0;
        this.isResumable = false;

        this.action = "";
        this.isActionValid = false;
        this.isDisplaying = true;
    }

    private void displayQuestion(QuizQuestion question) {
        System.out.println(question.getQuestion());

        System.out.println("Options");
        for (int i = 0; i < question.getAnswers().size(); i++) {
            final int index = i;

            System.out.format("    [%s] %s ", (char) (i + 'A'), question.getAnswers().get(i).getAnswer());

            if (result.getAnswers().stream()
                .anyMatch((answer) -> answer.getQuestionId() == this.currentQuestion.getId()
                        && answer.getAnswerId() == question.getAnswers().get(index).getId())) {
                            System.out.println(" (Your Answer)");
                        } else {
                            System.out.println();
                        }
        }
    }

    private void handlePreviousQuestion() {
        if (this.currentQuestionIndex > 0) {
            this.currentQuestionIndex--;
        } else {
            System.out.println("-- No more questions!");
        }
    }

    private void handleNextQuestion() {
        if (this.currentQuestionIndex < this.quiz.getQuestions().size() - 1) {
            this.currentQuestionIndex++;
        } else {
            System.out.println("-- No more questions!");
        }
    }
}
