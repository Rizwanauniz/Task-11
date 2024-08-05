package com.example.feedback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/submitFeedback")
public class Feedbackservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String book = request.getParameter("book");
        String feedback = request.getParameter("feedback");

        // Database connection details
        String jdbcUrl = "jdbc:mysql://localhost:3306/FeedbackDB";
        String jdbcUser = "yourDatabaseUser";
        String jdbcPassword = "yourDatabasePassword";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);

            // Prepare the SQL statement
            String sql = "INSERT INTO feedback (name, book, feedback) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, book);
            statement.setString(3, feedback);

            // Execute the statement
            statement.executeUpdate();

            // Send a response to the client
            response.getWriter().println("Feedback saved!");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred: " + e.getMessage());
        } finally {
            // Close the resources
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
