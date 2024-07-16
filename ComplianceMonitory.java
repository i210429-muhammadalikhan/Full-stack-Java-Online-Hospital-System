package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ComplianceMonitory {

    public static void main(String[] args) {
        ComplianceMonitory complianceMonitor = new ComplianceMonitory();
        complianceMonitor.runChecks();
    }

    public void runChecks() {
        checkDatabaseConnection();

        checkTablesExist();

        checkUserRolesInLoginTable();

        checkNoDuplicateUsernames();

    }

    private void checkDatabaseConnection() {
        System.out.println("Checking Database Connection...");

        String url = "jdbc:mysql://localhost:3306/proj";
        String username = "root";
        String password = "frenchtoast";

        StringBuilder result = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            result.append("Database connection established successfully\n");
        } catch (SQLException e) {
            result.append("Error connecting to the database\n");
            e.printStackTrace();
        }

        displayResults("Connection Check", result.toString());
    }

    private void checkTablesExist() {
        System.out.println("Checking Required Tables...");

        String url = "jdbc:mysql://localhost:3306/proj";
        String username = "root";
        String password = "frenchtoast";

        StringBuilder result = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            String checkSql = "SHOW TABLES LIKE 'register'";
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkSql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    result.append("Table 'register' exists\n");
                } else {
                    result.append("Table 'register' does not exist\n");
                }
            }

            checkSql = "SHOW TABLES LIKE 'login'";
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkSql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    result.append("Table 'login' exists\n");
                } else {
                    result.append("Table 'login' does not exist\n");
                }
            }
        } catch (SQLException e) {
            result.append("Error checking table existence\n");
            e.printStackTrace();
        }

        displayResults("Table Existence Check", result.toString());
    }

    private void checkUserRolesInLoginTable() {
        System.out.println("Checking User Roles in Login Table...");

        String url = "jdbc:mysql://localhost:3306/proj";
        String username = "root";
        String password = "frenchtoast";

        StringBuilder result = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String checkSql = "SELECT DISTINCT role1 FROM login";
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkSql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String role = resultSet.getString("role1");
                    if ("DefaultRole".equalsIgnoreCase(role)) {
                        role = "User";
                    }
                    result.append("Role found in login table: ").append(role).append("\n");
                }
            }
        } catch (SQLException e) {
            result.append("Error checking user roles in login table\n");
            e.printStackTrace();
        }

        displayResults("User Role Check", result.toString());
    }

    private void checkNoDuplicateUsernames() {
        System.out.println("Checking for Duplicate Usernames...");

        String url = "jdbc:mysql://localhost:3306/proj";
        String username = "root";
        String password = "frenchtoast";

        StringBuilder result = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String checkSql = "SELECT username, COUNT(*) as count FROM register GROUP BY username HAVING count > 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkSql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String duplicateUsername = resultSet.getString("username");
                    result.append("Duplicate username found: ").append(duplicateUsername).append("\n");
                }

                if (!resultSet.next()) {
                    result.append("No duplicate usernames found\n");
                }
            }
        } catch (SQLException e) {
            result.append("Error checking for duplicate usernames\n");
            e.printStackTrace();
        }

        displayResults("Duplicate Username Check", result.toString());
    }

    private void displayResults(String title, String results) {
        JTextArea textArea = new JTextArea(results);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, "Welcome to Compliance Monitory Checks - " + title, JOptionPane.INFORMATION_MESSAGE);
    }
}
