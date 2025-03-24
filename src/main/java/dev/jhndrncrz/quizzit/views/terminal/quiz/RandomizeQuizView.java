package dev.jhndrncrz.quizzit.views.terminal.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import dev.jhndrncrz.quizzit.models.quiz.Quiz;

import dev.jhndrncrz.quizzit.utilities.others.AppIO;

public class RandomizeQuizView {
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
        while (!isActionValid) {
            AppIO.clearScreen();

            System.out.println("--------------");
            System.out.println("RANDOMIZE QUIZ");
            System.out.println("--------------");
            System.out.println();

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
                    break;
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
            System.out.println();

            System.out.println("Select the maximum number of questions to include in the quiz.");
            System.out.print("Enter your choice: ");
            this.setAction(System.console().readLine());

            System.out.println();
        }

        System.out.println();
        System.out.println();
        System.out.println();
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

    public List<Integer> getSelectedQuizIds() {
        return this.selectedQuizIds;
    }

    public void reset() {
        this.action = "";
        this.isActionValid = false;

        this.selectedQuizIds = new ArrayList<>();
    }
}
