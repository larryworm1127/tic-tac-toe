package org.eom.main;

import org.eom.login_control.LoginFrame;


public class MainControl {

	public static final String USERNAME_PATH = "";
	public static final String PASSWORD_PATH = "";
		
	public MainControl() {
		login();
	}
	
	public static void login() {
		new LoginFrame();
	}
	
	public static void register() {
	}
	
	public static void TTTGame() {
	}
	
	public static void main(String[] args) {
		new MainControl();
	}
}