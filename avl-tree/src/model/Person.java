package model;

import java.time.LocalDate;

public class Person {
    private String cpf;
    private String rg;
    private String name;
    private LocalDate birthDate;
    private String city;

    public Person() {
    }

    public Person(String cpf, String rg, String name, LocalDate birthDate, String city) {
        this.cpf = cpf;
        this.rg = rg;
        this.name = name;
        this.birthDate = birthDate;
        this.city = city;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
