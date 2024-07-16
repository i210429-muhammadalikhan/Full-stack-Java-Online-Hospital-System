package project;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentPage {

    private JFrame frmPaymentPage;
    private JTextField textFieldCardNumber;
    private JPasswordField passwordFieldPIN;
    private JComboBox<String> paymentMethodComboBox;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PaymentPage window = new PaymentPage();
                    window.frmPaymentPage.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PaymentPage() {
        initialize();
    }

    private void initialize() {
        frmPaymentPage = new JFrame();
        frmPaymentPage.setTitle("Payment Page");
        frmPaymentPage.setSize(800, 500);
        frmPaymentPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmPaymentPage.setLocationRelativeTo(null);

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

        JLabel lblWelcomeMessage = new JLabel("Welcome to Payment Page");
        lblWelcomeMessage.setFont(new Font("Lucida Calligraphy", Font.BOLD | Font.ITALIC, 18));
        lblWelcomeMessage.setBounds(90, 10, 400, 30);
        gradientPanel.add(lblWelcomeMessage);

        JLabel emptyLine1 = new JLabel();
        emptyLine1.setBounds(90, 40, 400, 10);
        gradientPanel.add(emptyLine1);

        JLabel emptyLine2 = new JLabel();
        emptyLine2.setBounds(90, 60, 400, 10);
        gradientPanel.add(emptyLine2);

        JLabel lblPaymentMethod = new JLabel("Payment Method");
        lblPaymentMethod.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblPaymentMethod.setBounds(40, 90, 114, 13);
        gradientPanel.add(lblPaymentMethod);

        String[] paymentMethods = {"MasterCard", "Visa", "EasyPaisa", "SadaPay", "JazzCash", "NayaPay", "PayPal", "Inter Bank Transfer"};
        paymentMethodComboBox = new JComboBox<>(paymentMethods);
        paymentMethodComboBox.setBounds(186, 87, 150, 21);
        gradientPanel.add(paymentMethodComboBox);

        JLabel lblCardNumber = new JLabel("Card Number");
        lblCardNumber.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblCardNumber.setBounds(40, 124, 114, 13);
        gradientPanel.add(lblCardNumber);

        textFieldCardNumber = new JTextField();
        textFieldCardNumber.setBounds(186, 121, 150, 19);
        gradientPanel.add(textFieldCardNumber);
        textFieldCardNumber.setColumns(10);

        JLabel lblPIN = new JLabel("PIN");
        lblPIN.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblPIN.setBounds(40, 165, 114, 13);
        gradientPanel.add(lblPIN);

        passwordFieldPIN = new JPasswordField();
        passwordFieldPIN.setBounds(186, 162, 150, 19);
        gradientPanel.add(passwordFieldPIN);

        JButton btnPay = new JButton("Pay");
        btnPay.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnPay.setBounds(149, 225, 85, 21);
        gradientPanel.add(btnPay);

        btnPay.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnPay.setBackground(Color.GRAY);
            }

            public void mouseExited(MouseEvent e) {
                btnPay.setBackground(UIManager.getColor("control"));
            }
        });

        btnPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });

        frmPaymentPage.setContentPane(gradientPanel);
    }

    private void processPayment() {
        String selectedPaymentMethod = (String) paymentMethodComboBox.getSelectedItem();
        String cardNumber = textFieldCardNumber.getText();
        String pin = String.valueOf(passwordFieldPIN.getPassword());

        if (validateInputs(selectedPaymentMethod, cardNumber, pin)) {
            if (insertPaymentIntoDatabase(selectedPaymentMethod, cardNumber, pin)) {
                JOptionPane.showMessageDialog(frmPaymentPage, "Payment successful!");

                navigateToThankYouPage();
            } else {
                JOptionPane.showMessageDialog(frmPaymentPage, "Payment failed. Please try again.");
            }
        }
    }

    private void navigateToThankYouPage() {
        EventQueue.invokeLater(() -> {
            try {
                thankyoupage thankYouPage = new thankyoupage();
                thankYouPage.setVisible(true);

                frmPaymentPage.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private boolean validateInputs(String paymentMethod, String cardNumber, String pin) {
        return true;
    }

    private boolean insertPaymentIntoDatabase(String paymentMethod, String cardNumber, String pin) {
        String url = "jdbc:mysql://localhost:3306/proj";
        String dbUsername = "root";
        String dbPassword = "frenchtoast";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO payment (PaymentDate, PaymentMethod, PaymentStatus, PaymentAmount, CreditCardPIN, CreditCardPassword) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Get the current date and time
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String paymentDate = dateFormat.format(new Date());

                preparedStatement.setString(1, paymentDate);
                preparedStatement.setString(2, paymentMethod);
                preparedStatement.setString(3, "Success"); 
                preparedStatement.setBigDecimal(4, new BigDecimal("100.00")); 
                preparedStatement.setString(5, pin);
                preparedStatement.setString(6, cardNumber);

                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void setVisible(boolean visible) {
        frmPaymentPage.setVisible(visible);
    }
}
