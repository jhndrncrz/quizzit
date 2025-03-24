package dev.jhndrncrz.quizzit.views.terminal.quiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import dev.jhndrncrz.quizzit.models.quiz.Quiz;
import dev.jhndrncrz.quizzit.models.quiz.QuizQuestion;
import dev.jhndrncrz.quizzit.models.quiz.QuizAnswer;

import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class CreateQuizView {
    private final String firstName;
    private Quiz quiz;

    private String action;

    private boolean isActionValid;

    public CreateQuizView(String firstName) {
        this.firstName = firstName;

        this.reset();
    }

    public void displayView() {
        while (true) {
            while (!isActionValid) {
                AppIO.clearScreen();

                System.out.println("-----------");
                System.out.println("CREATE QUIZ");
                System.out.println("-----------");
                System.out.println();

                System.out.format("What do you want to do, %s?\n", this.firstName);
                System.out.format("------------------------%s\n", "-".repeat(this.firstName.length()));
                System.out.println("[1] Update Title");
                System.out.println("[2] Update Description");
                System.out.println("[3] Create Question");
                System.out.println("[4] Delete Question");
                System.out.println("[5] Save");

                System.out.print("Enter your choice: ");
                this.setAction(System.console().readLine());

                System.out.println();
            }

            System.out.println();
            System.out.println();
            System.out.println();

            switch (this.action.trim()) {
                case "1" -> {
                    handleUpdateTitle();
                }
                case "2" -> {
                    handleUpdateDescription();
                }
                case "3" -> {
                    handleCreateQuestion();
                }
                case "4" -> {
                    handleDeleteQuestion();
                }
                case "5" -> {
                    return;
                }
            }
        }
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        switch (action.trim()) {
            case "1", "2", "3", "4", "5" -> this.isActionValid = true;
            default -> {
                System.out.println("-- Invalid action!");
                return;
            }
        }

        this.action = action;
    }

    public void reset() {
        this.quiz = new Quiz();

        quiz.setTitle("New Quiz");
        quiz.setDescription(String.format("A new quiz created by %s", this.firstName));
        quiz.setQuestions(new ArrayList<>());

        this.action = "";
        this.isActionValid = false;
    }

    private void displayQuestion(QuizQuestion question) {
        System.out.println(question.getQuestion());
    }

    private void handleUpdateTitle() {
        boolean isTitleValid = false;

        while (!isTitleValid) {
            System.out.print("Enter a new title [Press Enter to skip]: ");
            String input = System.console().readLine();

            if (input.equalsIgnoreCase("")) {
                this.isActionValid = false;
                return;
            }

            if (input == null || input.trim().length() == 0) {
                System.out.println("-- Invalid title!");
                continue;
            }

            this.quiz.setTitle(input);
        }
    }

    private void handleUpdateDescription() {
        boolean isDescriptionValid = false;

        while (!isDescriptionValid) {
            System.out.print("Enter a new description [Press Enter to skip]: ");
        String input = System.console().readLine();

        if (input.equalsIgnoreCase("")) {
            this.isActionValid = false;
            return;
        }

        if (input == null || input.trim().length() == 0) {
            System.out.println("-- Invalid description!");
            continue;
        }

        this.quiz.setDescription(input);
        }
    }

    private void handleCreateQuestion() {
        QuizQuestion question = new QuizQuestion();

        boolean isQuestionValid = false;

        while (!isQuestionValid) {
            System.out.print("Enter a question [Press Enter to skip]: ");
            String input = System.console().readLine();

            if (input.equalsIgnoreCase("")) {
                this.isActionValid = false;
                return;
            }

            if (input == null || input.length() == 0) {
                System.out.println("-- Invalid question!");
                continue;
            }

            isQuestionValid = true;
            question.setQuestion(input);
        }

        List<QuizAnswer> answers = new ArrayList<>();
        int answerIndex = 0;

        while (true) {
            System.out.print("Enter the answer [Press Enter to skip]: ");
            String input = System.console().readLine();

            if (input.equalsIgnoreCase("")) {
                break;
            }

            if (input == null || input.trim().length() == 0) {
                System.out.println("-- Invalid answer!");
                continue;
            }

            System.out.format("Is \"%s\" a correct answer? [Y]es/[N]o\n", input);

            System.out.print("Enter your choice: ");
            String inputIsCorrect = System.console().readLine();

            if (inputIsCorrect == null || inputIsCorrect.trim().length() == 0
                    || !(inputIsCorrect.substring(0, 1).equalsIgnoreCase("y")
                            || inputIsCorrect.substring(0, 1).equalsIgnoreCase("n"))) {
                System.out.println("-- Invalid response!");
                continue;
            }

            ++answerIndex;
            answers.add(new QuizAnswer(-1, -1, input, answerIndex, inputIsCorrect.substring(0, 1).equalsIgnoreCase("y"),
                    new Timestamp((new Date()).getTime()), new Timestamp((new Date()).getTime())));
        }

        question.setAnswers(answers);
        this.getQuiz().getQuestions().add(question);
    }

    private void handleDeleteQuestion() {
        boolean isSelectionValid = false;

        System.out.println("Questions");
        for (int i = 0; i < this.quiz.getQuestions().size(); i++) {
            System.out.format("[%d] ", i + 1);
            this.displayQuestion(this.quiz.getQuestions().get(i));
        }

        while (!isSelectionValid) {
            System.out.println("Select a question to delete.");
            System.out.format("Enter your choice [%d-%d]: ", 1, this.quiz.getQuestions().size());
            String input = System.console().readLine();

            if (input == null || input.trim().length() == 0) {
                System.out.println("-- Invalid selection!");
                continue;
            }

            int selection = Integer.parseInt(input);

            if (selection < 1 || selection > this.quiz.getQuestions().size()) {
                System.out.println("-- Invalid selection!");
                continue;
            }

            isSelectionValid = true;
            this.quiz.getQuestions().remove(selection - 1);
        }
    }

    public Quiz getQuiz() {
        return this.quiz;
    }
}
