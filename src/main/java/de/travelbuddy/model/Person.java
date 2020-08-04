package de.travelbuddy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@SequenceGenerator(sequenceName = "seq_gen_expense", name = "seq_gen_base")
public class Person extends BaseModel {

    private String firstName;
    private String name;
    private LocalDate birthdate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ContactDetails contactDetails;
}
