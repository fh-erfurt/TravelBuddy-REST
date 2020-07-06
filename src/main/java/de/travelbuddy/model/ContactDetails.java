package de.travelbuddy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class which represents the Contact details
 */

@Entity
@Table(name = "CONTACTDETAILS")
@Getter
@Setter
public class ContactDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String email;
    private String town;
    private String street;
    private int streetNumber;
    private String ZIP;
    private String country;

    // Required for JPA
    public ContactDetails() {};

    /**
     * combines the contact details
     * @param phone phone number, it could a mobile number or a home phone number
     * @param email a simple mail address
     * @param town were a person live or you can find the place (sight)
     * @param street a street
     * @param streetNumber a street number without letters
     * @param ZIP the zip from the town, its more accurate then town only
     * @param country it's important, if the journey takes place in several countries
     */

    public ContactDetails(String phone, String email,String town, String street, int streetNumber, String ZIP, String country) {
        this.phone = phone;
        this.email = email;
        this.town = town;
        this.street = street;
        this.streetNumber = streetNumber;
        this.ZIP = ZIP;
        this.country = country;
    }
}
