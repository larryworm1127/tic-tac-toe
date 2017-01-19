package org.eom.login_control;

import org.eom.main.MainControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginFrame implements ActionListener {

    private JFrame frame;
    private JPasswordField passwordField;
    private JButton login, register;
    private JTextField usernameField;

    public LoginFrame() {
        // initialize frame
        frameInit();

        // initialize buttons
        buttonInit();

        // initialize user input fields
        userInputFieldInit();

        // initialize labels
        labelInit();

        frame.setVisible(true);

    }

    private void frameInit() {
        // initialize frame
        frame = new JFrame("Login");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
    }

    private void buttonInit() {
        // register login button
        login = new JButton("Login");
        login.setBounds(115, 158, 85, 25);
        login.addActionListener(this);
        frame.add(login);

        // register register button
        register = new JButton("Register");
        register.setBounds(115, 196, 85, 25);
        register.addActionListener(this);
        frame.add(register);
    }

    private void userInputFieldInit() {
        // register password field
        passwordField = new JPasswordField();
        passwordField.setBounds(105, 114, 105, 22);
        frame.add(passwordField);

        // register user name field
        usernameField = new JTextField();
        usernameField.setBounds(105, 79, 105, 22);
        frame.add(usernameField);
    }

    private void labelInit() {
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
        JLabel labelTitle = new JLabel("TTT Login");
        labelTitle.setFont(font);
        labelTitle.setBounds(35, 30, 150, 30);
        frame.add(labelTitle);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == login) {
            loginAction();
        } else if (event.getSource() == register) {
            MainControl.register();
        }
    }

    private void loginAction() {
        String inputUsername = usernameField.getText();
        String inputPassword = new String(passwordField.getPassword());
        LoginLogic obj = new LoginLogic(inputUsername, inputPassword);

        try {
            if (obj.login()) {
                frame.dispose();
                MainControl.TTTGame();
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect Username or Password");
                usernameField.setText("");
                passwordField.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
