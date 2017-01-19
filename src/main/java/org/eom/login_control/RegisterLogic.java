package org.eom.login_control;

import org.eom.main.FileEditor;
import org.eom.main.FileInput;
import org.eom.main.MainControl;

import java.io.IOException;

public class RegisterLogic {

    private String username, password, confirm;

    public RegisterLogic(String inputUsername, String inputPassword, String inputConfirm) {
        username = inputUsername;
        password = inputPassword;
        confirm = inputConfirm;
    }

    public int register() throws IOException {
        // initialize variables
        FileEditor usernameOut = new FileEditor(MainControl.USERNAME_PATH);
        FileEditor passwordOut = new FileEditor(MainControl.PASSWORD_PATH);

        // check for correct registration password and username
        if (checkValidInput() == 1) {
            usernameOut.write(username);
            passwordOut.write(password);
            usernameOut.closeFile();
            passwordOut.closeFile();
            return 1;
        } else if (checkValidInput() == 0) {
            return 0;
        } else if (checkValidInput() == 2) {
            return 2;
        } else if (checkValidInput() == 3) {
            return 3;
        }

        return -1;
    }

    private int checkValidInput() throws IOException {
        int result;
        if (username.length() <= 15 && password.length() <= 15) {
            if (username.length() > 0 && password.length() > 0) {
                if (password.equals(confirm)) {
                    result = 1;
                } else {
                    return 0;
                }
            } else {
                return 3;
            }
        } else {
            return -1;
        }

        FileInput usernameFile = new FileInput(MainControl.USERNAME_PATH);
        String lineName = usernameFile.readLine();
        while (lineName != null) {
            if (username.equals(lineName)) {
                usernameFile.closeFile();
                return 2;
            } else {
                lineName = usernameFile.readLine();
            }
        }

        return result;
    }
}