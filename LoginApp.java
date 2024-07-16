package project;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class LoginApp extends JFrame {

    private JTextField textField;
    private JPasswordField passwordField;
    private String userRole;
    private String username;

    public LoginApp() {
        initialize();
    }

    private void initialize() {
        setTitle("Login");
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
                GradientPaint gp = new GradientPaint(0, 0, new Color(169, 169, 169), w, 0, new Color(255, 240, 245));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };

        gradientPanel.setLayout(null);

        JLabel headingLabel = new JLabel("Welcome to Login Page");
        headingLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 20));
        headingLabel.setBounds(60, 20, 300, 30);
        gradientPanel.add(headingLabel);

        JLabel lblNewLabel = new JLabel("Username");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
        lblNewLabel.setBounds(60, 66, 95, 22);
        gradientPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Password");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
        lblNewLabel_1.setBounds(60, 134, 67, 15);
        gradientPanel.add(lblNewLabel_1);

        textField = new JTextField();
        textField.setBounds(164, 69, 158, 19);
        gradientPanel.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(164, 133, 158, 19);
        gradientPanel.add(passwordField);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        btnNewButton.setBounds(190, 200, 85, 21);
        gradientPanel.add(btnNewButton);

        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnNewButton.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNewButton.setBackground(UIManager.getColor("control"));
            }
        });

        btnNewButton.addActionListener(e -> {
            String enteredUsername = getUsername1();
            String enteredPassword = getPassword();

            if (checkUserCredentials(enteredUsername, enteredPassword)) {
                JOptionPane.showMessageDialog(this, "Login Successful");

                setUsername(enteredUsername);

                openUserScreen();

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        });

        setContentPane(gradientPanel);
    }

    private boolean checkUserCredentials(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/proj";
        String dbUsername = "root";
        String dbPassword = "frenchtoast";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM register WHERE username = ? AND passwordd = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getUsername1() {
        return textField.getText();
    }

    private String getPassword() {
        return new String(passwordField.getPassword());
    }

    private void openUserScreen() {
        EventQueue.invokeLater(() -> {
            try {
                if ("System Administrator".equalsIgnoreCase(userRole)) {
                    ComplianceMonitory complianceMonitory = new ComplianceMonitory();
                    complianceMonitory.runChecks();
                } else if ("Lab Administrator".equalsIgnoreCase(userRole)) {
                    labadmin labAdminPage = new labadmin();
                    labAdminPage.setVisible(true);
                } else {
                    userscreen userScreen = new userscreen(getUsername1());
                    userScreen.setVisible(true);
                }
                dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setUserRole(String role) {
        this.userRole = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LoginApp loginApp = new LoginApp();
            loginApp.setVisible(true);
        });
    }
}
