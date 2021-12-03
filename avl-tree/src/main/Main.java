package main;

import model.AvlTree;
import model.Person;
import utils.FileUtils;
import utils.Input;

import java.util.List;

public class Main {

    AvlTree cpfs;
    AvlTree names;
    AvlTree birthDates;

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


    public void createTrees(List<Person> people) {
        cpfs = new AvlTree();
        names = new AvlTree();
        birthDates = new AvlTree();
        for (Person person : people) {
            
        }
    }

}
