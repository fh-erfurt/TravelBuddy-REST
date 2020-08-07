package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.model.place.Sight;
import de.travelbuddy.storage.repositories.ExpenseRepo;
import de.travelbuddy.storage.repositories.PersonRepo;
import de.travelbuddy.storage.repositories.SightRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sights")
public class SightController extends LocationController<Sight> {

    private static final Logger LOG = LoggerFactory.getLogger(SightController.class);

    @Autowired
    public SightController(SightRepo repoSight, PersonRepo repoPerson,
                           ExpenseRepo repoExpense) {
        super(repoPerson,repoExpense);
        this.repoLocation = repoSight;
        this.type = Sight.class;
    }
}
