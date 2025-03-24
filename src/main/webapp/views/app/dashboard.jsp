<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<html>
<head><title>Dashboard</title></head>
<body>
    <%
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>
    <h2>Welcome, <%= session.getAttribute("user") %>!</h2>
    
    <a href="logout">Logout</a>
</body>
</html>