package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.model.place.Accommodation;
import de.travelbuddy.storage.repositories.AccommodationRepo;
import de.travelbuddy.storage.repositories.ExpenseRepo;
import de.travelbuddy.storage.repositories.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/accommodations")
public class AccommodationController extends LocationController<Accommodation> {


    @Autowired
    public AccommodationController(AccommodationRepo repoAccommodation, PersonRepo repoPerson,
                                   ExpenseRepo repoExpense) {
        super(repoPerson, repoExpense);
        this.repoLocation = repoAccommodation;
        this.type = Accommodation.class;
    }
}
