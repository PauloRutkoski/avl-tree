package utils;

import model.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<Person> importFile(String path) throws Exception{
        BufferedReader csvReader = new BufferedReader(  new InputStreamReader(
                new FileInputStream(path), StandardCharsets.UTF_8));
        List<Person> list = new ArrayList<>();
        String row = "";
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            list.add(new Person(data[0], data[1], data[2], stringToDate(data[3]), data[4]));
        }
        csvReader.close();
        return list;
    }

    private static LocalDate stringToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }
}
