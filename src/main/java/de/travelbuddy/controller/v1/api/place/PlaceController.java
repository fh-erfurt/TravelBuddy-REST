package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.model.place.Place;
import de.travelbuddy.storage.repositories.ExpenseRepo;
import de.travelbuddy.storage.repositories.PersonRepo;
import de.travelbuddy.storage.repositories.PlaceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/places")
public class PlaceController extends LocationController<Place> {

    private static final Logger LOG = LoggerFactory.getLogger(PlaceController.class);

    @Autowired
    public PlaceController(PlaceRepo repoPlace, PersonRepo repoPerson,
                           ExpenseRepo repoExpense) {
        super(repoPerson, repoExpense);
        this.repoLocation = repoPlace;
        this.type = Place.class;
    }
}
