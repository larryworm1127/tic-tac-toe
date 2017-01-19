package org.eom.login_control;

import org.eom.main.FileInput;
import org.eom.main.MainControl;

import java.io.IOException;

public class LoginLogic {

    private String username, password;

    public LoginLogic(String inputUsername, String inputPassword) {
        username = inputUsername;
        password = inputPassword;
    }

    public boolean login() throws IOException {
        // initialize variables
        FileInput usernameFile = new FileInput(MainControl.USERNAME_PATH);
        FileInput passwordFile = new FileInput(MainControl.PASSWORD_PATH);

        // check for correct passwords
        String lineName = usernameFile.readLine();
        String linePassword = passwordFile.readLine();
        while (lineName != null && linePassword != null) {
            if (username.equals(lineName) && password.equals(linePassword)) {
                usernameFile.closeFile();
                passwordFile.closeFile();
                return true;
            } else {
                lineName = usernameFile.readLine();
                linePassword = passwordFile.readLine();
            }
        }

        return false;
    }

}
