package de.travelbuddy;

import java.awt.*;

import java.time.LocalDate;

public class Person {

    private String firstName;
    private String name;
    private LocalDate birthdate;

    public Person(String firstName, String name, LocalDate birthdate) {
        this.firstName = firstName;
        this.name = name;
        this.birthdate = birthdate;
    }

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public LocalDate getBirthdate() {return birthdate;}

    public void setBirthdate(LocalDate birthdate) {this.birthdate = birthdate;}

}
