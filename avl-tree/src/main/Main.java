package main;

import model.AvlTree;
import model.Person;
import utils.FileUtils;
import utils.Input;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            List<Person> people = FileUtils.importFile("../data.csv");
            Input.getInstance().initInput();

            Menu menu = new Menu();
            menu.executeMenu(people);

            Input.getInstance().closeInput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
