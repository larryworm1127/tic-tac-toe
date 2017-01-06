package org.eom.login_control;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class RegisterFrame implements ActionListener {

	private JFrame frame;
	private JPasswordField passwordField;
	private JButton register;
	private JTextField usernameField;

	public RegisterFrame() {
		// initialize frame
		frameInit();
		
		// initialize buttons
		buttonInit();

		// initialize user input fields
		userInputFieldInit();

		// initialize labels
		labelInit();
	}
	
	public void frameInit() {
		frame = new JFrame("Register");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	public void buttonInit() {
		// register register button
		register = new JButton("Register");
		register.setBounds(115, 196, 85, 25);
		register.addActionListener(this);
		frame.add(register);
	}

	public void userInputFieldInit() {
		// register password field
		passwordField = new JPasswordField();
		passwordField.setBounds(105, 114, 105, 22);
		frame.add(passwordField);

		// register user name field
		usernameField = new JTextField();
		usernameField.setBounds(105, 79, 105, 22);
		frame.add(usernameField);
	}

	public void labelInit() {
		// register user name label
		JLabel labelUsername = new JLabel("Username");
		labelUsername.setBounds(35, 82, 76, 16);
		frame.add(labelUsername);

		// register password label
		JLabel labelPassword = new JLabel("Password");
		labelPassword.setBounds(35, 117, 66, 16);
		frame.add(labelPassword);

		// register label for title
		Font font = new Font("Arial", Font.BOLD, 26);
		JLabel labelTitle = new JLabel("Register");
		labelTitle.setFont(font);
		labelTitle.setBounds(35, 30, 150, 30);
		frame.add(labelTitle);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == register) {
			
		}
	}
}
