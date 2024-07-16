package project;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class viewyourhistory extends JFrame {

    private String username; 
    private List<String> appointmentHistory;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    viewyourhistory window = new viewyourhistory("sampleUsername"); 
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public viewyourhistory(String username) {
        this.username = username;
        this.appointmentHistory = new ArrayList<>();

        initialize();
        fetchAndDisplayUserHistory();
        writeToFile(); 
    }

    private void initialize() {
        setTitle("View Your History");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("User History for " + username);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);
    }

    public void fetchAndDisplayUserHistory() {
        fetchUserHistory();
        for (String appointment : appointmentHistory) {
            displayAppointmentDetails(appointment);
        }
    }

    private void displayAppointmentDetails(String appointmentInfo) {
        JLabel appointmentLabel = new JLabel();
        appointmentLabel.setText(appointmentInfo);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        JPanel panel = (JPanel) getContentPane().getComponent(0);
        panel.add(appointmentLabel, gbc);

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        revalidate();
        repaint();
    }

    private void fetchUserHistory() {
        appointmentHistory.clear(); 

        String url = "jdbc:mysql://localhost:3306/proj";
        String dbUsername = "root";
        String dbPassword = "frenchtoast";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM appointment WHERE UserId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                int userId = getUserIdByUsername(username);
                preparedStatement.setInt(1, userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int appointmentId = resultSet.getInt("AppointmentId");
                    String appointmentTime = resultSet.getString("AppointmentTime");
                    Date appointmentDate = resultSet.getDate("AppointmentDate");
                    String appointmentStatus = resultSet.getString("AppointmentStatus");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = dateFormat.format(appointmentDate);

                    String appointmentInfo = "ID: " + appointmentId +
                            ", Time: " + appointmentTime +
                            ", Date: " + formattedDate +
                            ", Status: " + appointmentStatus;

                    appointmentHistory.add(appointmentInfo);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int getUserIdByUsername(String username) {
        String url = "jdbc:mysql://localhost:3306/proj";
        String dbUsername = "root";
        String dbPassword = "frenchtoast";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT id FROM register WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    return -1;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private void writeToFile() {
        File file = new File("file1.txt");
        if (file.exists()) {
            file.delete();
        }

        try (FileWriter writer = new FileWriter("file1.txt")) {
            for (String appointment : appointmentHistory) {
                writer.write(appointment + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
