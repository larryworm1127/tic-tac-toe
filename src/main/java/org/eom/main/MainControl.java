package org.eom.main;

import org.eom.login_control.LoginFrame;
import org.eom.login_control.RegisterFrame;
import org.eom.ttt_control.CellState;
import org.eom.ttt_control.GameGUI;


public class MainControl {

    public static final String USERNAME_PATH = "assets/login/username.txt";
    public static final String PASSWORD_PATH = "assets/login/password.txt";

    private MainControl() {
        login();
    }

    public static void login() {
        new LoginFrame();
    }

    public static void register() {
        new RegisterFrame();
    }

    public static void TTTGame() {
        new GameGUI(3, CellState.COMPUTER);
    }

    public static void main(String[] args) {
        new MainControl();
    }
}
