package dev.jhndrncrz.quizzit.services;

import java.sql.SQLException;
import java.util.List;

import dev.jhndrncrz.quizzit.models.user.Student;
import dev.jhndrncrz.quizzit.repositories.user.StudentRepository;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService() throws ClassNotFoundException, SQLException {
        this.studentRepository = new StudentRepository();
    }

    public Student findById(Integer id) throws SQLException {
        return this.studentRepository.findById(id);
    }
    
    public Student findByUsername(String username) throws SQLException {
        return this.studentRepository.findByUsername(username);
    }

    public List<Student> findAll() throws SQLException {
        return this.studentRepository.findAll();
    }

    public Student create(Student student) throws SQLException {
        return this.studentRepository.create(student);
    }

    public void deleteById(Integer id) throws SQLException {
        this.studentRepository.deleteById(id);
    }

    public Student updateById(Student student) throws SQLException {
        return this.studentRepository.updateById(student);
    }
}
