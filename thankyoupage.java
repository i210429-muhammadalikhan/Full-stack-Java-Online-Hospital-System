package project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Font;

public class thankyoupage {

	private JFrame frame;
	/**
	 * @wbp.nonvisual location=70,394
	 */
	private final JList list = new JList();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					thankyoupage window = new thankyoupage();
					window.setVisible(true); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public thankyoupage() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ThankYou for using MEDManager");
		
		lblNewLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
		lblNewLabel.setBounds(58, 59, 301, 105);
		frame.getContentPane().add(lblNewLabel);
	}
	
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
}
