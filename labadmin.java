package project;

import javax.swing.*;

import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class labadmin {

    private JFrame frame;
    private JTable feedbackTable;
    private JScrollPane scrollPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                labadmin window = new labadmin();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public labadmin() {
        initialize();
        displayFeedbackData();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        frame.getContentPane().setLayout(flowLayout);

        JLabel headingLabel = new JLabel("Welcome to Review Feedback Page");
        headingLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 20));
        frame.getContentPane().add(headingLabel);

        feedbackTable = new JTable();
        scrollPane = new JScrollPane(feedbackTable);
        frame.getContentPane().add(scrollPane);
    }

    private void displayFeedbackData() {
        String url = "jdbc:mysql://localhost:3306/proj";
        String dbUsername = "root";
        String dbPassword = "frenchtoast";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT review , rating , username , appointmentId  FROM feedbackk";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                ResultSetMetaData metaData = resultSet.getMetaData();

                int columnCount = metaData.getColumnCount();
                DefaultTableModel model = new DefaultTableModel();

                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    model.addColumn(metaData.getColumnName(columnIndex));
                }

                while (resultSet.next()) {
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = resultSet.getObject(i + 1);
                    }
                    model.addRow(rowData);
                }

                feedbackTable.setModel(model);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
