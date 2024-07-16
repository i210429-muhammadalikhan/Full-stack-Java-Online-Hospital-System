package project;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationApp extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField contactInfoField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private Timer blinkTimer;
    private Color currentColor;

    public RegistrationApp() {
        setTitle("Registration Page");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        gradientPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Register New User", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(8, 2));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        nameField = new JTextField();
        emailField = new JTextField();
        contactInfoField = new JTextField();

        roleComboBox = new JComboBox<>();
        roleComboBox.addItem("User");
        roleComboBox.addItem("System Administrator");
        roleComboBox.addItem("Lab Administrator");
        roleComboBox.setSelectedItem("User");

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        styleButton(registerButton, Color.BLACK);
        centerButton(registerButton);

        blinkTimer = new Timer(1000, e -> blinkButton());
        currentColor = new Color(255, 235, 205);
        blinkTimer.start();

        centerPanel.add(new JLabel());
        centerPanel.add(new JLabel());
        centerPanel.add(createLabel("Username:"));
        centerPanel.add(styleTextField(usernameField));
        centerPanel.add(createLabel("Password:"));
        centerPanel.add(stylePasswordField(passwordField));
        centerPanel.add(createLabel("Name:"));
        centerPanel.add(styleTextField(nameField));
        centerPanel.add(createLabel("Email:"));
        centerPanel.add(styleTextField(emailField));
        centerPanel.add(createLabel("Contact Info:"));
        centerPanel.add(styleTextField(contactInfoField));
        centerPanel.add(createLabel("Role:"));
        centerPanel.add(roleComboBox);
        centerPanel.add(new JLabel());
        centerPanel.add(registerButton);

        gradientPanel.add(centerPanel, BorderLayout.CENTER);

        add(gradientPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD  | Font.ITALIC, 22));
        label.setForeground(Color.BLACK);
        return label;
    }

    private JTextField styleTextField(JTextField textField) {
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textField.setBackground(new Color(220, 220, 220));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return textField;
    }

    private JPasswordField stylePasswordField(JPasswordField passwordField) {
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        passwordField.setBackground(new Color(220, 220, 220));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return passwordField;
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Times New Roman", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Dimension size = new Dimension(180, 30);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
        button.setSize(size);
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    private void centerButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentY(Component.CENTER_ALIGNMENT);
    }

    private void blinkButton() {
        currentColor = (currentColor.equals(new Color(255, 235, 205))) ? Color.BLACK : new Color(255, 235, 205);
        registerButton.setBackground(currentColor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Register")) {
            register();
        }
    }

    private void register() {
        String url = "jdbc:mysql://localhost:3306/proj";
        String username = "root";
        String password = "frenchtoast";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO register (username, passwordd, namee, email, contactinfo, role) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, getUsername());
                preparedStatement.setString(2, getPassword());
                preparedStatement.setString(3, getName());
                preparedStatement.setString(4, getEmail());
                preparedStatement.setString(5, getContactInfo());
                preparedStatement.setString(6, getRole());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                    openWelcomePage();
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void openWelcomePage() {
        EventQueue.invokeLater(() -> {
            try {
                WelcomePage welcomePage = new WelcomePage();
                welcomePage.setVisible(true);
                this.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public void setUsername(String username) {
        usernameField.setText(username);
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void setPassword(String password) {
        passwordField.setText(password);
    }

    public String getName() {
        return nameField.getText();
    }

    public void setName(String name) {
        nameField.setText(name);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public void setEmail(String email) {
        emailField.setText(email);
    }

    public String getContactInfo() {
        return contactInfoField.getText();
    }

    public void setContactInfo(String contactInfo) {
        contactInfoField.setText(contactInfo);
    }

    public String getRole() {
        return roleComboBox.getSelectedItem().toString();
    }

    public void setRole(String role) {
        roleComboBox.setSelectedItem(role);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrationApp registrationApp = new RegistrationApp();
            registrationApp.setVisible(true);
        });
    }
}
