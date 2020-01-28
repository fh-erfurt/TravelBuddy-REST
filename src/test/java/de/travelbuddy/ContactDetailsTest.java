package de.travelbuddy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactDetailsTest {

    @Test
    public void correctly_instantiate_ContactDetailsFHEf(){

        String town = "Erfurt";
        String street = "Altonaer Straße";
        int streetNumber =  25;
        String ZIP = "99085";
        String country = "Deutschland";
        String phone = "036167000";
        String email = "rektorat@fh-erfurt.de";

        ContactDetails contact = new ContactDetails(phone, email, town,
                street, streetNumber, ZIP, country );

        assertEquals(contact.getTown(), town);
        assertEquals(contact.getStreet(), street);
        assertEquals(contact.getStreetNumber(), streetNumber);
        assertEquals(contact.getZIP(), ZIP);
        assertEquals(contact.getCountry(), country);
        assertEquals(contact.getPhone(), phone);
        assertEquals(contact.getEmail(), email);
    }

    @Test
    public void correctly_instantiate_ContactDetailsTheater(){

        String town = "Gotha";
        String street = "Ekhofpl.";
        int streetNumber =  3;
        String ZIP = "99867";
        String country = "Deutschland";
        String phone = "+493621510430";
        String email = "info@gth-theater.de";

        ContactDetails contact = new ContactDetails(phone, email, town,
                street, streetNumber, ZIP, country );

        assertEquals(contact.getTown(), town);
        assertEquals(contact.getStreet(), street);
        assertEquals(contact.getStreetNumber(), streetNumber);
        assertEquals(contact.getZIP(), ZIP);
        assertEquals(contact.getCountry(), country);
        assertEquals(contact.getPhone(), phone);
        assertEquals(contact.getEmail(), email);
    }

    @Test
    public void correctly_instantiate_ContactDetailsRegierung(){

        String town = "Bonn";
        String street = "Adenaueralee";
        int streetNumber =  139;
        String ZIP = "53113";
        String country = "Deutschland";
        String phone = "0228 560";
        String email = "bundes@kanzleramt.de";

        ContactDetails contact = new ContactDetails(phone, email, town,
                street, streetNumber, ZIP, country );

        assertEquals(contact.getTown(), town);
        assertEquals(contact.getStreet(), street);
        assertEquals(contact.getStreetNumber(), streetNumber);
        assertEquals(contact.getZIP(), ZIP);
        assertEquals(contact.getCountry(), country);
        assertEquals(contact.getPhone(), phone);
        assertEquals(contact.getEmail(), email);
    }

    @Test
    public void correctly_instantiate_ContactDetailsIbm(){

        String town = "Düsseldorf";
        String street = "Karl-Arnold-Platz";
        int streetNumber =  1;
        String ZIP = "40474";
        String country = "Deutschland";
        String phone = "+49211 4760";
        String email = "contact@ibm.com";

        ContactDetails contact = new ContactDetails(phone, email, town,
                street, streetNumber, ZIP, country );

        assertEquals(contact.getTown(), town);
        assertEquals(contact.getStreet(), street);
        assertEquals(contact.getStreetNumber(), streetNumber);
        assertEquals(contact.getZIP(), ZIP);
        assertEquals(contact.getCountry(), country);
        assertEquals(contact.getPhone(), phone);
        assertEquals(contact.getEmail(), email);
    }

    @Test
    public void correctly_instantiate_ContactDetailsFerrero(){

        String town = "Frankfurt";
        String street = "Hainer Weg";
        int streetNumber =  120;
        String ZIP = "60599";
        String country = "Deutschland";
        String phone = "069 / 68 05 - 0";
        String email = "consumerservice@ferrero.com";

        ContactDetails contact = new ContactDetails(phone, email, town,
                street, streetNumber, ZIP, country );

        assertEquals(contact.getTown(), town);
        assertEquals(contact.getStreet(), street);
        assertEquals(contact.getStreetNumber(), streetNumber);
        assertEquals(contact.getZIP(), ZIP);
        assertEquals(contact.getCountry(), country);
        assertEquals(contact.getPhone(), phone);
        assertEquals(contact.getEmail(), email);
    }

    @Test
    public void correctly_instantiate_ContactDetailsHortusBotanicus(){

        String town = "Leiden";
        String street = "Rapenburg";
        int streetNumber =  73;
        String ZIP = "2311";
        String country = "Niederlande";
        String phone = "071 5275144";
        String email = "hortus@hortus.leidenuniv.nl";

        ContactDetails contact = new ContactDetails(phone, email, town,
                street, streetNumber, ZIP, country );

        assertEquals(contact.getTown(), town);
        assertEquals(contact.getStreet(), street);
        assertEquals(contact.getStreetNumber(), streetNumber);
        assertEquals(contact.getZIP(), ZIP);
        assertEquals(contact.getCountry(), country);
        assertEquals(contact.getPhone(), phone);
        assertEquals(contact.getEmail(), email);
    }
}
