package project;

import java.awt.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class medicaltests {

    private JFrame frame;
    private JComboBox<String> medicalTestsComboBox;
    private String username;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginApp loginApp = new LoginApp();
                loginApp.setVisible(true);

                String username = loginApp.getUsername1();

                medicaltests window = new medicaltests(username);
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public medicaltests(String username) {
        this.username = username;
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

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

        JLabel lblNewLabel = new JLabel("Search Medical Tests");
        lblNewLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD | Font.ITALIC, 12));
        lblNewLabel.setBounds(154, 30, 144, 29);
        gradientPanel.add(lblNewLabel);

        String[] medicalTests = {
                "Blood Count", "Blood Typing", "Bone Marrow Aspiration", "Enzyme Analysis",
                "Glucose Tolerance Test", "Dengue and Malaria", "Kidney Function Test",
                "Liver Function Test", "Pregnancy Test", "Ultrasound", "Fluid Analysis",
                "Angiography", "Tomography", "Electro Cardio Graphy (ECG)", "Ballisto Cardio Graphy (BCG)",
                "Semen Analysis", "Biopsy Test", "Skin Test", "X-RAY", "MRI", "Endoscopy",
                "Autopsy", "Perfusion Scan", "Lung Ventilation Test", "Uroscopy", "Thyroid Test",
                "Iodine Test"
        };

        medicalTestsComboBox = new JComboBox<>(medicalTests);
        medicalTestsComboBox.setBounds(90, 80, 250, 20);
        gradientPanel.add(medicalTestsComboBox);

        JButton proceedButton = new JButton("Proceed");
        proceedButton.addActionListener(e -> {
            String selectedTest = (String) medicalTestsComboBox.getSelectedItem();
            openAppointmentPage(selectedTest);
        });
        proceedButton.setBounds(177, 130, 89, 23);
        gradientPanel.add(proceedButton);

        proceedButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                proceedButton.setBackground(Color.GRAY);
            }

            public void mouseExited(MouseEvent e) {
                proceedButton.setBackground(UIManager.getColor("control"));
            }
        });

        JButton btnNewButton = new JButton("Back");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnNewButton.setBounds(181, 194, 85, 21);
        btnNewButton.addActionListener(e -> openUserscreen());
        gradientPanel.add(btnNewButton);

        btnNewButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnNewButton.setBackground(Color.GRAY);
            }

            public void mouseExited(MouseEvent e) {
                btnNewButton.setBackground(UIManager.getColor("control"));
            }
        });

        frame.setContentPane(gradientPanel);
    }

    private void openAppointmentPage(String selectedTest) {
        EventQueue.invokeLater(() -> {
            try {
                AppointmentPage appointmentPage = new AppointmentPage(username);
                appointmentPage.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void openUserscreen() {
        EventQueue.invokeLater(() -> {
            try {
                userscreen userScreen = new userscreen(username);
                userScreen.setVisible(true);
                frame.dispose(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
