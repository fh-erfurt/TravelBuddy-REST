package de.travelbuddy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 *Class which represents a Person with ContactDetails
 */

@Entity
@Table(name = "PERSON")
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String name;
    private LocalDate birthdate;

    @OneToOne(cascade = CascadeType.ALL)
    private ContactDetails contactDetails;

    // Required for JPA
    public Person() {};
}
