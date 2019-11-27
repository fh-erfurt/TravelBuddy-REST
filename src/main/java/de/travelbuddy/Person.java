package de.travelbuddy;

import javafx.scene.image.Image;

import java.time.LocalDate;

public class Person {

    private static int IDCounter;

    private int personID;
    private String firstname;
    private String name;
    private LocalDate birthdate;
    private Image picture;

    public Person(int personID, String firstname, String name, LocalDate birthdate, Image picture) {
        IDCounter++;
        this.personID = IDCounter;
        this.firstname = firstname;
        this.name = name;
        this.birthdate = birthdate;
        this.picture = picture;
    }

    public static int getIDCounter() {return IDCounter;}

    public int getPersonID() {return personID;}

    public String getFirstname() {return firstname;}

    public void setFirstname(String firstname) {this.firstname = firstname;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public LocalDate getBirthdate() {return birthdate;}

    public void setBirthdate(LocalDate birthdate) {this.birthdate = birthdate;}

    public Image getPicture() {return picture;}

    public void setPicture(Image picture) {this.picture = picture;}
}
