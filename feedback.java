package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class feedback extends JFrame implements UserInterface {

    private JRadioButton[] ratingButtons;
    private JTextArea textArea;

    private JButton btnSubmitQuery;
    private JButton btnBack;

    private LoginApp loginAppInstance;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                feedback window = new feedback();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public feedback() {
        loginAppInstance = new LoginApp();
        initialize();
    }

    private void initialize() {
        setTitle("Feedback");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(169, 169, 169), w, 0, new Color(255, 240, 245)); // Adjusted shade
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };

        gradientPanel.setLayout(null);

        JLabel headingLabel = new JLabel("Welcome to Feedback Page");
        headingLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 18));
        headingLabel.setBounds(200, 10, 400, 30);
        gradientPanel.add(headingLabel);

        textArea = new JTextArea();
        textArea.setBounds(177, 52, 241, 74);
        gradientPanel.add(textArea);

        JLabel lblNewLabel = new JLabel("Review");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNewLabel.setBounds(78, 68, 77, 29);
        gradientPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Rating");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNewLabel_1.setBounds(78, 138, 45, 13);
        gradientPanel.add(lblNewLabel_1);

        ratingButtons = new JRadioButton[5];
        String[] ratingLabels = {" Excellent", "   Good", "  Moderate", "      Bad", "     Poor"};

        for (int i = 0; i < ratingButtons.length; i++) {
            ratingButtons[i] = new JRadioButton(String.valueOf(i + 1));
            ratingButtons[i].setBounds(183 + i * 45, 136, 31, 21);
            gradientPanel.add(ratingButtons[i]);

            // Add labels next to the radio buttons
            JLabel label = new JLabel(ratingLabels[i]);
            label.setFont(new Font("Times New Roman", Font.PLAIN, 10));
            label.setBounds(170 + i * 45, 160, 50, 20);
            gradientPanel.add(label);
        }

        btnSubmitQuery = new JButton("Submit Query");
        btnSubmitQuery.setFont(new Font("Times New Roman", Font.BOLD, 12));
        btnSubmitQuery.setBounds(212, 183, 150, 30);
        btnSubmitQuery.addActionListener(this::onSubmitQuery);
        gradientPanel.add(btnSubmitQuery);

        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Times New Roman", Font.BOLD, 12));
        btnBack.setBounds(212, 235, 150, 30);
        btnBack.addActionListener(this::onBack);
        gradientPanel.add(btnBack);

        setContentPane(gradientPanel);
    }

    private void onSubmitQuery(ActionEvent actionEvent) {
        String review = textArea.getText();
        int rating = getSelectedRating();

        if (insertFeedbackIntoDatabase(review, rating, loginAppInstance.getUsername1(), getLatestAppointmentId())) {
            System.out.println("Feedback submitted successfully!");
        }
    }

    private void onBack(ActionEvent actionEvent) {
        openUserscreen();
    }

    private void openUserscreen() {
        EventQueue.invokeLater(() -> {
            try {
                String username = loginAppInstance.getUsername1();
                userscreen userScreen = new userscreen(username);
                userScreen.setVisible(true);
                dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private int getSelectedRating() {
        for (int i = 0; i < ratingButtons.length; i++) {
            if (ratingButtons[i].isSelected()) {
                return i + 1;
            }
        }
        return 0;
    }

    private boolean insertFeedbackIntoDatabase(String review, int rating, String username, int appointmentId) {
        String url = "jdbc:mysql://localhost:3306/proj";
        String dbUsername = "root";
        String dbPassword = "frenchtoast";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO feedbackk (review, rating, username, appointmentId) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, review);
                preparedStatement.setInt(2, rating);
                preparedStatement.setString(3, username);
                preparedStatement.setInt(4, appointmentId);

                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private int getLatestAppointmentId() {
        return 123;
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
}
