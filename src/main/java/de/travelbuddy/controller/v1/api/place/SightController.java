package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.place.Connection;
import de.travelbuddy.model.place.Sight;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sights")
public class SightController extends LocationController<Sight> {

    @Autowired
    public SightController(IGenericRepo<Sight> repoSight, IGenericRepo<Person> repoPerson,
                           IGenericRepo<Expense> repoExpense, IGenericRepo<Connection> repoConnection) {
        super(repoSight, repoPerson,repoExpense,repoConnection);
        this.type = Sight.class;
    }
}
