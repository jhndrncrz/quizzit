package dev.jhndrncrz.quizzit.utilities.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.*;

public class DatabaseConnection {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        final Dotenv dotenv = Dotenv.load();
        final String DB_CONNECTION_STRING = dotenv.get("DB_CONNECTION_STRING");
        final String DB_DATABASE_NAME = dotenv.get("DB_DATABASE_NAME");
        final String DB_USERNAME = dotenv.get("DB_USERNAME");
        final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

        // try {
        //     Class.forName("org.mariadb.jdbc.Driver");
        // } catch (ClassNotFoundException e) {
        //     System.err.println("ERROR: Failed to load MariaDB JDBC driver.");
        //     throw new RuntimeException(e);
        // }
        
        try {
            return DriverManager.getConnection(String.format("jdbc:mariadb://localhost:3306/%s?user=%s&password=%s", DB_DATABASE_NAME, DB_USERNAME, DB_PASSWORD));
        } catch (SQLException e) {
            System.err.println("ERROR: Failed to connect to database.");
            throw new RuntimeException(e);
        }
    }
}
