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
public class ContactDetails extends BaseModel{

    private String phone;
    private String email;
    private String town;
    private String street;
    private int streetNumber;
    private String ZIP;
    private String country;

    // Required for JPA
    public ContactDetails() {};
}
