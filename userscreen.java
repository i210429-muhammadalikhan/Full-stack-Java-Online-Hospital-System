package project;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class userscreen extends JFrame implements UserInterface {

    private JButton btnNewButton;
    private JButton btnMedicalTests;
    private JButton btnBack;
    private JButton btnNewButton_4;
    private JButton btnNewButton_2;

    private String username;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                userscreen window = new userscreen("sampleUsername");
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public userscreen(String username) {
        this.username = username;
        initialize();
    }

    private void initialize() {
        setTitle("User Screen");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a gradient background
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(169, 169, 169), w, 0, new Color(255, 235, 205));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };

        gradientPanel.setLayout(null);

        btnNewButton = createButton("Book Appointment");
        btnNewButton.setBounds(140, 42, 160, 21);
        btnNewButton.addActionListener(this::onBookAppointment);
        gradientPanel.add(btnNewButton);

        btnMedicalTests = createButton("Search Medical Tests");
        btnMedicalTests.setBounds(140, 73, 160, 21);
        btnMedicalTests.addActionListener(this::onMedicalTests);
        gradientPanel.add(btnMedicalTests);

        btnBack = createButton("Back");
        btnBack.setBounds(0, 10, 87, 21);
        btnBack.addActionListener(this::onBack);
        gradientPanel.add(btnBack);

        btnNewButton_4 = createButton("Give Feedback");
        btnNewButton_4.setBounds(140, 104, 160, 21);
        btnNewButton_4.addActionListener(this::onGiveFeedback);
        gradientPanel.add(btnNewButton_4);

        btnNewButton_2 = createButton("Chat Support");
        btnNewButton_2.setBounds(140, 135, 160, 21);
        btnNewButton_2.addActionListener(this::onChatSupport);
        gradientPanel.add(btnNewButton_2);

        setContentPane(gradientPanel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.LIGHT_GRAY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(UIManager.getColor("control"));
            }
        });
        return button;
    }

    private void onBookAppointment(ActionEvent e) {
        navigateToAppointmentPage();
    }

    private void onMedicalTests(ActionEvent e) {
        navigateToMedicalTests();
    }

    private void onGiveFeedback(ActionEvent e) {
        navigateToFeedback();
    }

    private void onChatSupport(ActionEvent e) {
        navigateToChatSupport();
    }

    private void onBack(ActionEvent e) {
        navigateToLogin();
    }

    private void navigateToAppointmentPage() {
        EventQueue.invokeLater(() -> {
            try {
                AppointmentPage appointmentPage = new AppointmentPage(username);
                appointmentPage.setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void navigateToMedicalTests() {
        EventQueue.invokeLater(() -> {
            try {
                medicaltests medicalTests = new medicaltests(username);
                medicalTests.setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void navigateToFeedback() {
        EventQueue.invokeLater(() -> {
            try {
                feedback feedbackPage = new feedback();
                feedbackPage.setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void navigateToChatSupport() {
        EventQueue.invokeLater(() -> {
            try {
                chatsupport chatSupportPage = new chatsupport();
                chatSupportPage.setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void navigateToLogin() {
        EventQueue.invokeLater(() -> {
            try {
                LoginApp window = new LoginApp();
                window.setVisible(true);
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
