package de.travelbuddy;

import java.awt.*;

import java.time.LocalDate;

public class Person {

    private static int IDCounter;

    private int personID;
    private String firstName;
    private String name;
    private LocalDate birthdate;
    private Image picture;

    public Person(String firstName, String name, LocalDate birthdate, Image picture) {
        IDCounter++;
        this.personID = IDCounter;
        this.firstName = firstName;
        this.name = name;
        this.birthdate = birthdate;
        this.picture = picture;
    }

    public static int getIDCounter() {return IDCounter;}

    public int getPersonID() {return personID;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public LocalDate getBirthdate() {return birthdate;}

    public void setBirthdate(LocalDate birthdate) {this.birthdate = birthdate;}

    public Image getPicture() {return picture;}

    public void setPicture(Image picture) {this.picture = picture;}
}
