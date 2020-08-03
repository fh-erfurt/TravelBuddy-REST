package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.place.Accommodation;
import de.travelbuddy.model.place.Connection;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/accommodations")
public class AccommodationController extends LocationController<Accommodation> {


    @Autowired
    public AccommodationController(IGenericRepo<Accommodation> repoAccommodation, IGenericRepo<Person> repoPerson,
                                   IGenericRepo<Expense> repoExpense, IGenericRepo<Connection> repoConnection) {
        super(repoAccommodation, repoPerson, repoExpense, repoConnection);

        repoLocation.setType(Accommodation.class);
    }
}
