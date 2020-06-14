package de.travelbuddy.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 *Class which represents a Person with ContactDetails
 */

@Entity
@Table(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String name;
    private LocalDate birthdate;
    private ContactDetails contactDetails;

    // Required for JPA
    public Person() {};

    public Person(String firstName, String name, LocalDate birthdate, ContactDetails contactDetails) {
        this.firstName = firstName;
        this.name = name;
        this.birthdate = birthdate;
        this.contactDetails = contactDetails;
    }

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public LocalDate getBirthdate() {return birthdate;}

    public void setBirthdate(LocalDate birthdate) {this.birthdate = birthdate;}

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
