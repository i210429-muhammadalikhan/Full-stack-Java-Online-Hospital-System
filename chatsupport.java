package project;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class chatsupport {

    private JFrame frmChatSupport;
    private JTextArea textArea;
    private JButton btnSubmitQuery;
    private JButton btnBack;
    private LoginApp loginAppInstance; 

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                chatsupport window = new chatsupport();
                window.frmChatSupport.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @wbp.parser.entryPoint
     */
    public chatsupport() {
        loginAppInstance = new LoginApp();
        initialize();
    }

    private void initialize() {
        frmChatSupport = new JFrame();
        frmChatSupport.setTitle("AI Assistance");
        frmChatSupport.setBounds(100, 100, 800, 600);
        frmChatSupport.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmChatSupport.getContentPane().setLayout(null);

        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();

                GradientPaint gp = new GradientPaint(0, 0, new Color(169, 169, 169), w, 0, new Color(245, 245, 220));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };

        gradientPanel.setLayout(null);

        textArea = new JTextArea();
        textArea.setBounds(253, 64, 241, 74);
        gradientPanel.add(textArea);

        JLabel lblNewLabel = new JLabel("Welcome to AI Assistance");
        lblNewLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 16));
        lblNewLabel.setBounds(267, 10, 258, 20);
        gradientPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Enter Your Query");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 13));
        lblNewLabel_1.setBounds(107, 92, 110, 20);
        gradientPanel.add(lblNewLabel_1);

        btnSubmitQuery = new JButton("Submit Query");
        btnSubmitQuery.setFont(new Font("Times New Roman", Font.BOLD, 12));
        btnSubmitQuery.setBounds(294, 185, 150, 30);
        btnSubmitQuery.addActionListener(this::onSubmitQuery);

        btnSubmitQuery.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnSubmitQuery.setBackground(new Color(245, 245, 220));
            }

            public void mouseExited(MouseEvent evt) {
                btnSubmitQuery.setBackground(new Color(169, 169, 169));
            }
        });
        gradientPanel.add(btnSubmitQuery);

        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Times New Roman", Font.BOLD, 12));
        btnBack.setBounds(294, 244, 150, 30);
        btnBack.addActionListener(this::onBack);
        // Add hover effect
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnBack.setBackground(new Color(245, 245, 220));
            }

            public void mouseExited(MouseEvent evt) {
                btnBack.setBackground(new Color(169, 169, 169));
            }
        });
        gradientPanel.add(btnBack);

        frmChatSupport.setContentPane(gradientPanel);
    }

    public JFrame getFrmChatSupport() {
        return frmChatSupport;
    }

    private void onSubmitQuery(ActionEvent actionEvent) {
        String userQuery = textArea.getText();
        if (userQuery.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frmChatSupport, "Please enter a query.");
            return;
        }

        String username = loginAppInstance.getUsername1(); 

        if (userQuery.trim().equalsIgnoreCase("Give best-reviewed medical centres")) {
            openWebsiteForMedicalCentres();
        }

        else if (userQuery.trim().equalsIgnoreCase("show me the portfolio of ministry of health")) {
            openWebsiteForMinistryOfHealth();
        }

        else if (userQuery.trim().equalsIgnoreCase("take me to IDC website")) {
            openWebsiteForIDC();
        }

        else if (userQuery.trim().equalsIgnoreCase("take me to ShaukatKhanum Lab website")) {
            openWebsiteForShaukatKhanumLab();
        }

        else if (userQuery.trim().equalsIgnoreCase("take me to Chughtai Lab website")) {
            openWebsiteForChughtaiLab();
        } else {
            JOptionPane.showMessageDialog(frmChatSupport, "Sorry, I can only provide information about the best-reviewed medical centres, the portfolio of the Ministry of Health, take you to the IDC website, take you to the ShaukatKhanum Lab website, or take you to the Chughtai Lab website.");
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
                frmChatSupport.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void openWebsiteForMedicalCentres() {
        String websiteURL = "https://www.google.com/search?q=best+reviewed+medical+testing+centres+in+islamabad&rlz=1C1KNTJ_enPK1072PK1072&oq=best+reviewed+medical+testing+centres+in+islamabad&gs_lcrp=EgZjaHJvbWUyBggAEEUYOTIHCAEQIRigATIHCAIQIRigAdIBCTEwNzIxajBqN6gCALACAA&sourceid=chrome&ie=UTF-8";

        try {
            Desktop.getDesktop().browse(new URI(websiteURL));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frmChatSupport, "Error opening the website for medical centres");
        }
    }

    private void openWebsiteForMinistryOfHealth() {
        String websiteURL = "https://www.nhsrc.gov.pk/index";

        try {
            Desktop.getDesktop().browse(new URI(websiteURL));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frmChatSupport, "Error opening the website for the portfolio of the Ministry of Health");
        }
    }

    private void openWebsiteForIDC() {
        String websiteURL = "https://idc.net.pk/";

        try {
            Desktop.getDesktop().browse(new URI(websiteURL));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frmChatSupport, "Error opening the website for IDC");
        }
    }

    private void openWebsiteForShaukatKhanumLab() {
        String websiteURL = "https://shaukatkhanum.org.pk/";

        try {
            Desktop.getDesktop().browse(new URI(websiteURL));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frmChatSupport, "Error opening the website for ShaukatKhanum Lab");
        }
    }

    private void openWebsiteForChughtaiLab() {
        String websiteURL = "https://chughtailab.com/";

        try {
            Desktop.getDesktop().browse(new URI(websiteURL));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frmChatSupport, "Error opening the website for Chughtai Lab");
        }
    }

    public void setVisible(boolean visible) {
        frmChatSupport.setVisible(visible);
    }
}
