package utils;

import java.util.Scanner;

public class Input {
	private static Input instance;
	private Scanner scanner;

	private Input() {
	}

	public static synchronized Input getInstance() {
		if (instance == null) {
			instance = new Input();
		}
		return instance;
	}

	public void initInput() {
		scanner = new Scanner(System.in);
	}

	public String nextLine() {
		return scanner.nextLine();
	}
	
	public void closeInput() {
		scanner.close();
	}
}
