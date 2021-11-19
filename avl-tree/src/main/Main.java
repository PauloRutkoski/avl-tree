package main;

import utils.Input;

public class Main {

	public static void main(String[] args) {
		Input.getInstance().initInput();
		
		Menu menu = new Menu();
		menu.executeMenu();
		
		Input.getInstance().closeInput();
	}

}
