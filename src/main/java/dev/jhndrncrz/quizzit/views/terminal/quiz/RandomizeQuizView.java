package dev.jhndrncrz.quizzit.views.terminal.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import dev.jhndrncrz.quizzit.models.quiz.Quiz;

import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class RandomizeQuizView {
    private Quiz quiz;
    private String firstName;
    private List<Quiz> quizzes;
    private List<Integer> selectedQuizIds;

    private String action;

    private boolean isActionValid;

    public RandomizeQuizView(String firstName, List<Quiz> quizzes) {
        this.firstName = firstName;
        this.quizzes = quizzes;

        this.reset();
    }

    public void displayView() {
        while (true) {
            while (!isActionValid) {
                AppIO.clearScreen();

                System.out.println("--------------");
                System.out.println("RANDOMIZE QUIZ");
                System.out.println("--------------");
                System.out.println();

                System.out.format("What do you want to do, %s?\n", this.firstName);
                System.out.format("------------------------%s\n", "-".repeat(this.firstName.length()));
                System.out.println("[1] Update Title");
                System.out.println("[2] Update Description");
                System.out.println("[3] Select Quizzes");
                System.out.println("[4] Select Number of Questions");
                System.out.println("[5] Save");

                System.out.print("Enter your choice: ");
                this.setAction(System.console().readLine());
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
                    handleSelectQuizzes();
                }
                case "4" -> {
                    handleSelectNumberOfQuestions();
                }
                case "5" -> {
                    if (!isActionValid) {
                        System.out.println("-- Cannot save quiz until all fields are valid!");
                        continue;
                    }

                    this.quiz = new Quiz();
                    this.quiz.setTitle("New Random Quiz");
                    this.quiz.setDescription("A new randomized quiz!");

                    this.isActionValid = false;
                    
                    return;
                }
            }
        }
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
        while (true) {
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

    private void handleSelectQuizzes() {
        System.out.println("Your Quizzes");
        System.out.println("------------");

        if (quizzes.isEmpty()) {
            System.out.println("No quizzes found!");
        } else {
            for (ListIterator<Quiz> iterator = quizzes.listIterator(); iterator.hasNext();) {
                System.out.format("[%d] %s\n", iterator.nextIndex() + 1, iterator.next().getTitle());
            }
        }
        System.out.println();

        System.out.println("Enter the quizzes you want included in the randomized quiz.");
        while (true) {
            System.out.format("Enter your choice [%d-%d, Press Enter to skip]: ", 1, this.quizzes.size());
            String input = System.console().readLine();

            if (input.equalsIgnoreCase("")) {
                this.isActionValid = false;
                return;
            }

            if (input == null || input.trim().length() == 0) {
                System.out.println("-- Invalid selection!");
                continue;
            }

            try {
                int selection = Integer.parseInt(input);

                if (selection < 1 || selection > this.quizzes.size()) {
                    System.out.println("-- Invalid selection!");
                    continue;
                }

                if (this.selectedQuizIds.contains(selection)) {
                    System.out.println("-- Quiz already selected!");
                    continue;
                }

                this.selectedQuizIds.add(quizzes.get(selection - 1).getId());
            } catch (NumberFormatException e) {
                System.out.println("-- Invalid selection!");
                continue;
            }
        }
    }

    private void handleSelectNumberOfQuestions() {
        while (true) {
            System.out.print("Enter the maximum number of questions to include in the quiz [Press Enter to skip]: ");

            String input = System.console().readLine();

            if (input.equalsIgnoreCase("")) {
                this.isActionValid = false;
                return;
            }

            if (input == null || input.trim().length() == 0) {
                System.out.println("-- Invalid number of questions!");
                continue;
            }

            try {
                Integer.parseInt(input);
                this.setAction(input);
            } catch (NumberFormatException e) {
                System.out.println("-- Invalid number of questions!");
                continue;
            }
        }
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        try {
            Integer.parseInt(action);

            this.isActionValid = true;
            this.action = action;

        } catch (NumberFormatException e) {
            System.out.println("-- Invalid action!");
        }
    }

    public Quiz getQuiz() {
        return this.quiz;
    }

    public List<Integer> getSelectedQuizIds() {
        return this.selectedQuizIds;
    }

    public void reset() {
        this.action = "";
        this.isActionValid = false;

        this.selectedQuizIds = new ArrayList<>();
    }
}
