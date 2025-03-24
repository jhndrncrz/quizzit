package dev.jhndrncrz.quizzit.services;

import java.sql.SQLException;
import java.util.List;

import dev.jhndrncrz.quizzit.models.quiz.Quiz;
import dev.jhndrncrz.quizzit.models.quiz.QuizQuestion;
import dev.jhndrncrz.quizzit.models.quiz.QuizResult;
import dev.jhndrncrz.quizzit.repositories.quiz.QuizRepository;
import dev.jhndrncrz.quizzit.repositories.quiz.QuizQuestionRepository;
import dev.jhndrncrz.quizzit.repositories.quiz.QuizResultRepository;

public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizResultRepository quizResultRepository;

    public QuizService() throws ClassNotFoundException, SQLException {
        this.quizRepository = new QuizRepository();
        this.quizQuestionRepository = new QuizQuestionRepository();
        this.quizResultRepository = new QuizResultRepository();
    }

    public Quiz findById(Integer id) throws SQLException {
        return this.quizRepository.findById(id);
    }

    public List<Quiz> findAll() throws SQLException {
        return this.quizRepository.findAll();
    }

    public List<Quiz> findAllByStudentId(Integer studentId) throws SQLException {
        return this.quizRepository.findAllByStudentId(studentId);
    }

    public Quiz create(Quiz quiz) throws SQLException {
        return this.quizRepository.create(quiz);
    }

    public void deleteById(Integer id) throws SQLException {
        this.quizRepository.deleteById(id);
    }

    public Quiz updateById(Quiz quiz) throws SQLException {
        return this.quizRepository.updateById(quiz);
    }

    public Quiz randomizeQuiz(Quiz quiz, Integer limit, List<Integer> quizIds) throws SQLException {
        List<QuizQuestion> questions = this.quizQuestionRepository.findAllByQuizIds(quizIds);
        
        for (int i = questions.size() - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            QuizQuestion temp = questions.get(i);
            questions.set(i, questions.get(j));
            questions.set(j, temp);
        }
        
        quiz.setQuestions(questions.subList(0, questions.size() < limit ? questions.size() : limit));

        return this.create(quiz);
    }

    public QuizResult takeQuizResult(Integer quizId, Integer studentId) throws SQLException {
        QuizResult result = this.quizResultRepository.findPendingByQuizIdAndStudentId(quizId, studentId);

        if (result == null) {
            // return new QuizResult();
            return null;
        } else {
            return result;
        }
    }

    public void recordQuizResult(QuizResult result) throws SQLException {
        if (result.getId() != null && this.quizResultRepository.findById(result.getId()) != null) {
            this.quizResultRepository.updateById(result);
        } else {
            this.quizResultRepository.create(result);
        }
    }

    public List<QuizResult> findAllQuizResultsByStudentId(Integer studentId) throws SQLException {
        return this.quizResultRepository.findAllByStudentId(studentId);
    }
}
