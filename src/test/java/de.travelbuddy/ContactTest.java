package de.travelbuddy;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class ContactTest {
    @Test
    public void should_create_contact() {

        String phone = "036167000";
        String email = "rektorat@fh-erfurt.de";

        Contact contact = new Contact(phone, email);

        assertEquals(contact.getPhone(), phone);
        assertEquals(contact.getEmail(), email);
    }
}