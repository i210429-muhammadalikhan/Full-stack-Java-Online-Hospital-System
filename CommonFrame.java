package project;

import javax.swing.*;

public class CommonFrame {
    public static void initialize(JFrame frame, String title, int width, int height) {
        frame.setTitle(title);
        frame.setBounds(100, 100, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
    }
}
