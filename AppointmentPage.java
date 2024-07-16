package project;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import com.toedter.calendar.JDateChooser;

public class AppointmentPage extends JFrame implements UserInterface {

    private JComboBox<String> appointmentTimeComboBox;
    private JDateChooser appointmentDateChooser;
    private JComboBox<String> labComboBox;

    private JButton btnSubmit;
    private JButton btnViewBookedAppointments;
    private JButton btnViewYourHistory;
    private JButton btnBack;

    private String username;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AppointmentPage window = new AppointmentPage("sampleUsername");
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AppointmentPage(String username) {
        this.username = username;
        initialize();
    }

    private void initialize() {
        JPanel gradientPanel = new GradientPanel();
        setContentPane(gradientPanel);

        CommonFrame.initialize(this, "Appointment", 900, 500);

        Timer colorTransitionTimer = new Timer();
        Color[] currentColor = {new Color(169, 169, 169)};

        colorTransitionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                int r = currentColor[0].getRed();
                int g = currentColor[0].getGreen();
                int b = currentColor[0].getBlue();

                r += 1;
                g += 1;
                b += 1;

                r = Math.min(245, Math.max(169, r));
                g = Math.min(245, Math.max(169, g));
                b = Math.min(245, Math.max(169, b));

                currentColor[0] = new Color(r, g, b);

                gradientPanel.setBackground(currentColor[0]);
                repaint();

                if (r >= 245 && g >= 245 && b >= 245) {
                    colorTransitionTimer.cancel();
                }
            }
        }, 0, 10);

        addComponentsToFrame();

        setVisible(true);
    }

    private void addComponentsToFrame() {
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Appointment Time");
        lblNewLabel.setBounds(47, 58, 104, 13);
        getContentPane().add(lblNewLabel);

        String[] timeSlots = {"8:00 - 8:30 AM", "8:30 - 9:00 AM", "9:00 - 9:30 AM", "9:30 - 10:00 AM", "10:00 - 10:30 AM", "10:30 - 11:00 AM", "11:00 - 11:30 AM","11:30 - 12:00 PM"};
        appointmentTimeComboBox = new JComboBox<>(timeSlots);
        appointmentTimeComboBox.setBounds(161, 54, 135, 21);
        getContentPane().add(appointmentTimeComboBox);

        JLabel lblNewLabel_1 = new JLabel("Appointment Date");
        lblNewLabel_1.setBounds(47, 95, 104, 13);
        getContentPane().add(lblNewLabel_1);

        appointmentDateChooser = new JDateChooser();
        appointmentDateChooser.setBounds(161, 91, 135, 21);
        getContentPane().add(appointmentDateChooser);

        JLabel lblNewLabel_2 = new JLabel("Preferred Lab");
        lblNewLabel_2.setBounds(47, 128, 104, 13);
        getContentPane().add(lblNewLabel_2);

        String[] labOptions = {"IDC Lab", "Shaukat Khanum Lab", "Chughtai Lab", "Agha Khan Lab", "City Lab", "Al-Shifa Lab", "Maroof Intl Lab", "PIMS Lab", "DHQ Lab", "CMH Lab"};
        labComboBox = new JComboBox<>(labOptions);
        labComboBox.setBounds(161, 124, 135, 21);
        getContentPane().add(labComboBox);

        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(154, 167, 142, 21);
        btnSubmit.addActionListener(this::onSubmit);
        // Add hover effect
        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSubmit.setBackground(new Color(255, 223, 186)); 
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSubmit.setBackground(UIManager.getColor("control"));
            }
        });
        getContentPane().add(btnSubmit);

        btnViewBookedAppointments = new JButton("View Bookings");
        btnViewBookedAppointments.setBounds(154, 199, 142, 21);
        btnViewBookedAppointments.addActionListener(this::onViewBookedAppointments);
        // Add hover effect
        btnViewBookedAppointments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewBookedAppointments.setBackground(new Color(255, 223, 186)); 
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnViewBookedAppointments.setBackground(UIManager.getColor("control"));
            }
        });
        getContentPane().add(btnViewBookedAppointments);

        btnViewYourHistory = new JButton("View Your History");
        btnViewYourHistory.setBounds(154, 230, 142, 21);
        btnViewYourHistory.addActionListener(this::onViewYourHistory);
        // Add hover effect
        btnViewYourHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewYourHistory.setBackground(new Color(255, 223, 186)); 
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnViewYourHistory.setBackground(UIManager.getColor("control"));
            }
        });
        getContentPane().add(btnViewYourHistory);

        JLabel lblNewLabel_3 = new JLabel("Welcome to Appointment Page");
        lblNewLabel_3.setFont(new Font("Lucida Calligraphy", Font.BOLD, 16));
        lblNewLabel_3.setBounds(100, 10, 319, 13);
        getContentPane().add(lblNewLabel_3);

        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 12));
        btnBack.setBounds(0, 7, 85, 21);
        btnBack.addActionListener(this::onBack);
        // Add hover effect
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBack.setBackground(new Color(255, 223, 186));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBack.setBackground(UIManager.getColor("control"));
            }
        });
        getContentPane().add(btnBack);
    }

    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            GradientPaint gradientPaint = new GradientPaint(0, 0, new Color(169, 169, 169), width, height, new Color(255, 223, 186));
            g2d.setPaint(gradientPaint);
            g2d.fillRect(0, 0, width, height);
        }
    }

    private void onSubmit(ActionEvent e) {
        String selectedTime = (String) appointmentTimeComboBox.getSelectedItem();
        Date selectedDate = new Date(appointmentDateChooser.getDate().getTime());
        String selectedLab = (String) labComboBox.getSelectedItem();

        int appointmentId = submitAppointment(username, selectedTime, selectedDate, selectedLab);

        if (appointmentId != -1) {
            JOptionPane.showMessageDialog(this, "Appointment submitted successfully!");
            navigateToPaymentPage(appointmentId);
        }
    }

    private boolean isAppointmentAlreadyBooked(int userId, String time, Date date, String lab) {
        String url = "jdbc:mysql://localhost:3306/proj";
        String dbUsername = "root";
        String dbPassword = "frenchtoast";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM appointment WHERE UserId = ? AND AppointmentTime = ? AND AppointmentDate = ? AND labname = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, time);
                preparedStatement.setDate(3, new java.sql.Date(date.getTime()));
                preparedStatement.setString(4, lab);

                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public int getUserIdByUsername(String username) {
        int userId = -1;

        String url = "jdbc:mysql://localhost:3306/proj";
        String dbUsername = "root";
        String dbPassword = "frenchtoast";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT id FROM register WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    userId = resultSet.getInt("id");
                } else {
                    System.out.println("User not found in the register table for username: " + username);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userId;
    }

    private int submitAppointment(String username, String time, Date date, String selectedLab) {
        String url = "jdbc:mysql://localhost:3306/proj";
        String dbUsername = "root";
        String dbPassword = "frenchtoast";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            int userId = getUserIdByUsername(username);

            if (userId != -1) {
                if (isAppointmentAlreadyBooked(userId, time, date, selectedLab)) {
                    JOptionPane.showMessageDialog(this, "Appointment with the same details already booked.");
                    return -1;
                }

                String sqlAppointment = "INSERT INTO appointment (UserId, AppointmentTime, AppointmentDate, AppointmentStatus, labname) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatementAppointment = connection.prepareStatement(sqlAppointment, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    preparedStatementAppointment.setInt(1, userId);
                    preparedStatementAppointment.setString(2, time);
                    preparedStatementAppointment.setDate(3, new java.sql.Date(date.getTime()));
                    preparedStatementAppointment.setString(4, "Pending");
                    preparedStatementAppointment.setString(5, selectedLab);

                    int affectedRows = preparedStatementAppointment.executeUpdate();

                    if (affectedRows > 0) {
                        ResultSet generatedKeys = preparedStatementAppointment.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            return generatedKeys.getInt(1);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    private void onViewBookedAppointments(ActionEvent e) {
        navigateToBookedAppointments();
    }

    private void onViewYourHistory(ActionEvent e) {
        navigateToAppointmentHistory();
    }

    private void onBack(ActionEvent e) {
        navigateToUserScreen();
    }

    private void navigateToPaymentPage(int appointmentId) {
        EventQueue.invokeLater(() -> {
            try {
                PaymentPage paymentPage = new PaymentPage();
                paymentPage.setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void navigateToBookedAppointments() {
        EventQueue.invokeLater(() -> {
            try {
                bookedappointments bookedAppointments = new bookedappointments();
                bookedAppointments.setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void navigateToAppointmentHistory() {
        EventQueue.invokeLater(() -> {
            try {
                viewyourhistory appointmentHistory = new viewyourhistory(username);
                appointmentHistory.setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void navigateToUserScreen() {
        EventQueue.invokeLater(() -> {
            try {
                userscreen userScreen = new userscreen(username);
                userScreen.setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
}
