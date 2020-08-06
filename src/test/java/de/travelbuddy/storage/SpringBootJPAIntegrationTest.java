package de.travelbuddy.storage;

import de.travelbuddy.model.place.Coordinates;
import de.travelbuddy.storage.repositories.IGenericRepo;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpringBootJPAIntegrationTest {

    private IGenericRepo<Coordinates> genericRepo = null;

    @Autowired
    EntityManager em;

    @Autowired
    public SpringBootJPAIntegrationTest(IGenericRepo<Coordinates> genericRepo)
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
        Coordinates coord = genericRepo.save(InstanceHelper.clearId(InstanceHelper.createCoordinate()));
        Optional<Coordinates> coord2 = genericRepo.findById(coord.getId());

        //Then
        Assertions.assertNotNull(coord);
        Assertions.assertTrue(coord2.isPresent());
        assertEquals(coord.getLatitude(), coord2.get().getLatitude());
        assertEquals(coord.getLongitude(), coord2.get().getLongitude());
    }
}