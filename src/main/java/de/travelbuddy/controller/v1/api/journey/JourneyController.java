package de.travelbuddy.controller.v1.api.journey;

import de.travelbuddy.model.Person;
import de.travelbuddy.storage.repositories.GenericRepo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/journey")
public class JourneyController {


    @GetMapping("/{journeyId}/locations")
    @ResponseStatus(code = HttpStatus.OK)
    public List<String> getJourneys(@PathVariable long journeyId) throws JourneyNotFoundAPIException {

        GenericRepo<Person> s = new GenericRepo<>();
        s.setType(Person.class);

        List<Person> persons = s
                .getStream()
                .where(p -> p.getId().equals(1L))
                .toList();

        persons.get(0).setName("Hans");
        s.save(persons.get(0));


        if (journeyId == 42)
            return Arrays.asList("Magrathea", "Beteigeuze 5", "Beteigeuze 7", "Erde");
        else
            throw new JourneyNotFoundAPIException(String.format("Journey '%d' does not exist.", journeyId));
    }
}
