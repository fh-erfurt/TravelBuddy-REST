package de.travelbuddy.storage;

import de.travelbuddy.model.Person;
import de.travelbuddy.storage.repositories.PersonRepo;
import de.travelbuddy.utilities.InstanceHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;

@Component
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpringBootJPAIntegrationTest {

    private PersonRepo genericRepo = null;

    @Autowired
    EntityManager em;

    @Autowired
    public SpringBootJPAIntegrationTest(PersonRepo genericRepo)
    {
        this.genericRepo = genericRepo;
    }

    @BeforeAll
    public void givenGenericRepo_shouldNotBeNull() {
        Assertions.assertNotNull(genericRepo);
    }

    @Test
    public void givenGenericRepo_shouldSaveAndReadEntity() {
        //Create a new Coordinate and store it in the database
        //Then retrieve it again

        //When
        Person coord = genericRepo.save(InstanceHelper.clearId(InstanceHelper.createPerson()));
        Optional<Person> coord2 = genericRepo.findById(coord.getId());

        //Then
        Assertions.assertNotNull(coord);
        Assertions.assertTrue(coord2.isPresent());
        Assertions.assertEquals(coord.getId(), coord2.get().getId());
        Assertions.assertEquals(coord.getContactDetails(), coord2.get().getContactDetails());
    }
}