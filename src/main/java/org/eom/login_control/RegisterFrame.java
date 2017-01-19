package org.eom.login_control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class RegisterFrame implements ActionListener {

    private JFrame frame;
    private JPasswordField passwordField;
    private JButton register;
    private JTextField usernameField;
    private JPasswordField confirmField;

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

    private void frameInit() {
        frame = new JFrame("Register");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void buttonInit() {
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

        // register confirm password field
        confirmField = new JPasswordField();
        confirmField.setBounds(105, 149, 105, 22);
        frame.add(confirmField);
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

        // register confirm label
        JLabel labelConfirm = new JLabel("Confirm");
        labelConfirm.setBounds(35, 152, 76, 16);
        frame.add(labelConfirm);

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
            try {
                registerAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerAction() throws IOException {
        String inputUsername = usernameField.getText();
        String inputPassword = new String(passwordField.getPassword());
        String inputConfirm = new String(confirmField.getPassword());
        RegisterLogic obj = new RegisterLogic(inputUsername, inputPassword, inputConfirm);
        int command = obj.register();

        if (command == 1) {
            JOptionPane.showMessageDialog(frame, "Register Success");
            frame.dispose();
        } else if (command == 0) {
            JOptionPane.showMessageDialog(frame, "Password doesn't match the confirm password");
            usernameField.setText("");
            passwordField.setText("");
            confirmField.setText("");
        } else if (command == 2) {
            JOptionPane.showMessageDialog(frame, "Username already exist.");
            usernameField.setText("");
            passwordField.setText("");
            confirmField.setText("");
        } else if (command == 3) {
            JOptionPane.showMessageDialog(frame, "Username or password not entered.");
            usernameField.setText("");
            passwordField.setText("");
            confirmField.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a password and username within 15 characters");
            usernameField.setText("");
            passwordField.setText("");
            confirmField.setText("");
        }
    }
}
