package de.travelbuddy.storage;

import de.travelbuddy.model.place.Coordinates;
import de.travelbuddy.storage.repositories.GenericRepo;
import de.travelbuddy.utilities.InstanceHelper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpringBootJPAIntegrationTest {

    private GenericRepo<Coordinates> genericRepo = null;

    @Autowired
    public SpringBootJPAIntegrationTest(GenericRepo<Coordinates> genericRepo)
    {
        this.genericRepo = genericRepo;
        this.genericRepo.setType(Coordinates.class);
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
        Coordinates coord = genericRepo.save(InstanceHelper.createCoordinate());
        Coordinates coord2 = genericRepo.read(coord.getId());

        //Then
        Assertions.assertNotNull(coord);
        Assertions.assertNotNull(coord2);
        assertEquals(coord.getLatitude(), coord2.getLatitude());
        assertEquals(coord.getLongitude(), coord2.getLongitude());
    }
}