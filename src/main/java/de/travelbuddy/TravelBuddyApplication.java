package de.travelbuddy;

import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;
import de.travelbuddy.storage.exceptions.StorageError;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

import java.time.LocalDate;

@SpringBootApplication
public class TravelBuddyApplication {

    public static void main(String[] args)  {
        SpringApplication.run(TravelBuddyApplication.class, args);
    }

}
