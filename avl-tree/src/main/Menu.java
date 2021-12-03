package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import enums.Operation;
import model.AvlTree;
import model.Person;
import utils.Input;
import utils.Printer;

import javax.swing.text.DateFormatter;

public class Menu {
    //AvlTree tree;

    AvlTree<String> cpfs;
    AvlTree<String> names;
    AvlTree<ChronoLocalDate> dates;
    List<Person> people;

    public void executeMenu(List<Person> people) {
        this.people = people;
        initTrees();

        boolean execute = true;
        while (execute) {
            this.showMenu();
            Operation option = this.askOption();
            if (option == Operation.CLOSE) {
                execute = false;
                System.out.println("Ate Logo !!!");
            } else if (option == Operation.FIND_DATE) {
                String value1 = this.askValue(option);
                String value2 = this.askValue(option);
                if (value1 != null && value2 != null) {
                    executeOperation(option, value1, value2);
                }
            } else {
                String value = null;
                value = this.askValue(option);
                if (value != null) {
                    executeOperation(option, value, null);
                }
            }
        }
    }

    private void initTrees() {
        cpfs = new AvlTree<>();
        names = new AvlTree<>();
        dates = new AvlTree<>();
        int i = 0;
        for (Person person : people) {
            cpfs.insert(person.getCpf(), i);
            names.insert(person.getName(), i);
            dates.insert(person.getBirthDate(), i);
            i++;
        }
    }

    private void showMenu() {
        System.out.println("||=============================|| MENU ||============================||");
        Printer.printMenuMessage("C - Buscar por CPF");
        Printer.printMenuMessage("N - Buscar por Nome");
        Printer.printMenuMessage("D - Buscar por Período de Nascimento");
        Printer.printMenuMessage("S - Sair");
        System.out.println("||===================================================================||");
    }

    private Operation askOption() {
        boolean ask = true;
        Operation value = null;

        while (ask) {
            System.out.println("Digite a opcao desejada: ");
            value = Operation.fromString(Input.getInstance().nextLine());
            if (value != null) {
                ask = false;
            } else {
                System.out.println("Valor Invalido!");
            }
        }
        return value;
    }

    private String askValue(Operation option) {
        boolean ask = true;
        String value = null;
        while (ask) {
            System.out.println(askValueMessage(option));
            value = Input.getInstance().nextLine();
            if (isBack(value) || value != null) {
                ask = false;
            }
        }
        return value;
    }

    private boolean isBlank(String value) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean isBack(String value) {
        if (!isBlank(value) && value.toLowerCase().equals("v")) {
            return true;
        }
        return false;
    }

    private String askValueMessage(Operation option) {
        switch (option) {
            case FIND_CPF:
                return "Qual CPF deseja consultar? (V - Voltar)";
            case FIND_NAME:
                return "Qual nome deseja buscar? (V - Voltar)";
            case FIND_DATE:
                return "Qual o período de busca (dd-MM-yyyy)? (V - Voltar)";
            default:
                return "";
        }
    }

    private void executeOperation(Operation op, String key1, String key2) {
        switch (op) {
            case FIND_CPF:
                cpfs.findUnique(key1);
                showResult(cpfs.getSearch());
                break;
            case FIND_NAME:
                names.findLike(key1);
                showResult(names.getSearch());
                break;
            case FIND_DATE:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                dates.findByPeriod(LocalDate.parse(key1, formatter), LocalDate.parse(key2, formatter));
                showResult(dates.getSearch());
                break;
            default:
                break;
        }
    }

    private void showResult(List<Integer> positions) {
        List<Person> search = new ArrayList<>();
        for (int index : positions) {
            search.add(people.get(index));
        }
        printList(search);
    }

    private void printList(List<Person> list) {
        StringBuilder builder = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Person it : list) {
            builder.append("|| CPF: ").append(it.getCpf()).append(" | ");
            builder.append("RG: ").append(it.getRg()).append(" | ");
            builder.append("Nome: ").append(it.getName()).append(" | ");
            builder.append("Data de Nascimento: ").append(it.getBirthDate().format(formatter)).append(" | ");
            builder.append("Cidade: ").append(it.getCity()).append(";");
            builder.append("\n");
        }
        System.out.println("||=========================|| Resultados ||==========================||");
        if(list.isEmpty()){
            System.out.println("|| Nenhum dado foi encontrado!");
        }else {
            System.out.print(builder.toString());
        }
        System.out.println("||===================================================================||");
    }
}
