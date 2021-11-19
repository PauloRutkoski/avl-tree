package main;

import model.Person;
import utils.FileUtils;
import utils.Input;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            List<Person> people = FileUtils.importFile("../data.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }

		Input.getInstance().initInput();

		Menu menu = new Menu();
		menu.executeMenu();

		Input.getInstance().closeInput();
    }

}
