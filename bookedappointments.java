package project;

import javax.swing.*;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class bookedappointments extends JFrame {

    private List<String> appointments;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    bookedappointments window = new bookedappointments();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public bookedappointments() {
        initialize();
        fetchAndDisplayAppointments();
    }

    private void initialize() {
        setTitle("Booked Appointments");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 1));

        JLabel titleLabel = new JLabel("Booked Appointments");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panel.add(titleLabel);
    }

    public void fetchAndDisplayAppointments() {
        appointments = getAllAppointments();

        for (String appointment : appointments) {
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

    private List<String> getAllAppointments() {
        List<String> appointments = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/proj";
        String dbUsername = "root";
        String dbPassword = "frenchtoast";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM appointment";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int appointmentId = resultSet.getInt("AppointmentId");
                    String appointmentTime = resultSet.getString("AppointmentTime");
                    Date appointmentDate = resultSet.getDate("AppointmentDate");
                    String lab = resultSet.getString("labname");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = dateFormat.format(appointmentDate);

                    String appointmentInfo = "ID: " + appointmentId +
                            ", Time: " + appointmentTime +
                            ", Date: " + formattedDate +
                            ", Lab: " + lab;

                    appointments.add(appointmentInfo);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return appointments;
    }
}
