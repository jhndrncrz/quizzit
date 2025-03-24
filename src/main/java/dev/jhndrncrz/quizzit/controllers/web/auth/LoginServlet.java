package dev.jhndrncrz.quizzit.controllers.web.auth;

import dev.jhndrncrz.quizzit.repositories.user.StudentRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final StudentRepository studentRepository;

    public LoginServlet() throws ClassNotFoundException, SQLException {
        this.studentRepository = new StudentRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            if (studentRepository.authenticateUser(username, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", username);
                
                response.sendRedirect("dashboard.jsp");
            } else {
                response.sendRedirect("login.jsp?error=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}