package project;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomePage extends JFrame implements ActionListener {

    private JButton loginButton;
    private JButton registerButton;
    private JButton createAccountButton;
    private JButton backButton;
    private Timer blinkTimer;
    private Color currentColor;

    public WelcomePage() {
        setTitle("Welcome to MEDManager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(169, 169, 169), 0, h, new Color(255, 223, 186));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false); 

        JLabel titleLabel = new JLabel("Welcome to MEDManager", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 40));
        titleLabel.setForeground(new Color(105, 105, 105));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setOpaque(false);
        panel.add(titlePanel, BorderLayout.NORTH);

        JTextArea descriptionTextArea = new JTextArea(
                "Online clinical testing system MEDManager allows patients to book appointments for various medical tests and choose their preferred labs as well! "
                        + "MEDManager aims to streamline the process of accessing healthcare services, offering patients a convenient and efficient way to schedule tests while also providing labs with a modernized approach to managing appointments and test results."
        );

        Font textAreaFont = new Font("Times New Roman", Font.PLAIN, 18);
        descriptionTextArea.setFont(textAreaFont);

        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setBackground(new Color(0, 0, 0, 0)); 
        descriptionTextArea.setForeground(Color.BLACK);

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        descriptionScrollPane.setOpaque(false);
        descriptionScrollPane.getViewport().setOpaque(false);
        panel.add(descriptionScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        loginButton = new JButton("Login to Proceed");
        loginButton.addActionListener(this);
        styleCircularButton(loginButton, Color.BLACK);

        registerButton = new JButton("Register New User");
        registerButton.addActionListener(this);
        styleCircularButton(registerButton, Color.BLACK);

        createAccountButton = new JButton("Create New Account");
        createAccountButton.addActionListener(this);
        styleCircularButton(createAccountButton, Color.BLACK);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        styleCircularButton(backButton, Color.BLACK);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(createAccountButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        blinkTimer = new Timer();
        blinkTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                blinkButtons();
            }
        }, 0, 1000);
        currentColor = new Color(169, 169, 169); 

        gradientPanel.add(panel);
        setContentPane(gradientPanel);
    }

    private void styleCircularButton(JButton button, Color color) {
        button.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Dimension size = new Dimension(180, 50);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
        button.setSize(size);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    private void blinkButtons() {
        currentColor = (currentColor.equals(new Color(169, 169, 169))) ? new Color(255, 223, 186) : new Color(169, 169, 169);
        loginButton.setBackground(currentColor);
        registerButton.setBackground(currentColor);
        createAccountButton.setBackground(currentColor);
        backButton.setBackground(currentColor);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomePage welcomePage = new WelcomePage();
            welcomePage.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            openSelectRolePage();
        } else if (e.getSource() == registerButton || e.getSource() == createAccountButton) {
            openRegistrationPage();
        } else if (e.getSource() == backButton) {
            openPreviousScreen();
        }
    }

    private void openSelectRolePage() {
        SelectRolePage selectRolePage = new SelectRolePage();
        selectRolePage.setVisible(true);
        dispose();
    }

    private void openRegistrationPage() {
        RegistrationApp registrationApp = new RegistrationApp();
        registrationApp.setVisible(true);
        dispose();
    }

    private void openPreviousScreen() {
        WelcomePage welcomePage = new WelcomePage();
        welcomePage.setVisible(true);
        dispose();
    }

    static class SelectRolePage extends JFrame implements ActionListener {
        private JButton userButton;
        private JButton systemAdminButton;
        private JButton labAdminButton;
        private JButton backButton;

        public SelectRolePage() {
            setTitle("Select User Role");
            setSize(400, 250);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 1));

            JLabel selectRoleLabel = new JLabel("Select User Role", SwingConstants.CENTER);

            userButton = new JButton("User");
            userButton.addActionListener(this);
            styleButton(userButton);

            systemAdminButton = new JButton("System Administrator");
            systemAdminButton.addActionListener(this);
            styleButton(systemAdminButton);

            labAdminButton = new JButton("Lab Administrator");
            labAdminButton.addActionListener(this);
            styleButton(labAdminButton);

            backButton = new JButton("Back");
            backButton.addActionListener(this);
            styleButton(backButton);

            panel.add(selectRoleLabel);
            panel.add(userButton);
            panel.add(systemAdminButton);
            panel.add(labAdminButton);
            panel.add(backButton);

            add(panel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == userButton) {
                openLoginPage("User");
            } else if (e.getSource() == systemAdminButton) {
                openLoginPage("System Administrator");
            } else if (e.getSource() == labAdminButton) {
                openLoginPage("Lab Administrator");
            } else if (e.getSource() == backButton) {
                openPreviousScreen();
            }
        }

        private void openLoginPage(String role) {
            LoginApp loginApp = new LoginApp();
            loginApp.setUserRole(role);
            loginApp.setVisible(true);
            dispose();
        }

        private void openPreviousScreen() {
            WelcomePage welcomePage = new WelcomePage();
            welcomePage.setVisible(true);
            dispose();
        }

        private void styleButton(JButton button) {
            button.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            button.setBackground(new Color(169, 169, 169));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
}
