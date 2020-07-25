package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.place.Connection;
import de.travelbuddy.model.place.Place;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/places")
public class PlaceController extends LocationController<Place> {

    @Autowired
    public PlaceController(IGenericRepo<Place> repoPlace, IGenericRepo<Person> repoPerson,
                           IGenericRepo<Expense> repoExpense, IGenericRepo<Connection> repoConnection) {
        super(repoPlace, repoPerson, repoExpense, repoConnection);
        repoPlace.setType(Place.class);
    }
}
