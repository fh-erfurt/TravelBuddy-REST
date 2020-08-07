package de.travelbuddy.controller.v1.api.finance;

import de.travelbuddy.controller.v1.api.BaseController;
import de.travelbuddy.controller.v1.api.exceptions.IdMismatchAPIException;
import de.travelbuddy.controller.v1.api.exceptions.MissingValuesAPIException;
import de.travelbuddy.controller.v1.api.exceptions.PersonNotFoundAPIException;
import de.travelbuddy.controller.v1.api.finance.exceptions.ExpenseNotFoundAPIException;
import de.travelbuddy.controller.v1.api.journey.exceptions.JourneyNotFoundAPIException;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.finance.Money;
import de.travelbuddy.model.journey.Journey;
import de.travelbuddy.storage.repositories.ExpenseRepo;
import de.travelbuddy.storage.repositories.PersonRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/expenses")
public class ExpenseController extends BaseController<Expense> {

    ExpenseRepo repo;
    PersonRepo repoPerson;
    private static final Logger LOG = LoggerFactory.getLogger(ExpenseController.class);

    @Autowired
    public ExpenseController(ExpenseRepo repo, PersonRepo repoPerson) {
        this.repo = repo;
        this.repoPerson = repoPerson;
        this.type = Expense.class;
    }

    private Expense fetchExpense(Long expenseId) {
        LOG.info("Find expense: " + expenseId);

        Optional<Expense> expense = repo.findById(expenseId);

        if (!expense.isPresent())
            throw new ExpenseNotFoundAPIException();

        LOG.info("expense found: " + expenseId);
        return expense.get();
    }

    //<editor-fold desc="CRUD">

    //###################
    //##### CREATE ######
    //###################

    /**
     * Create a new expense
     * @param expense The expense to create
     * @return The saved expense
     */
    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Expense createExpense(@RequestBody Expense expense) {
        LOG.info("Create expense...");
        Expense exp = repo.save(expense);
        LOG.info("Expense saved:" + expense.getId());
        return exp;
    }

    //###################
    //###### READ #######
    //###################

    /**
     * Read an expense
     * @param expenseId The expense to read
     * @return The found expend
     * @throws ExpenseNotFoundAPIException If the expense was not found
     */
    @GetMapping("/{expenseId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Expense getExpense(@PathVariable Long expenseId) throws ExpenseNotFoundAPIException {
        return fetchExpense(expenseId);
    }

    /**
     * Read all existing expenses
     * @return The found expenses
     */
    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Expense> getExpense() throws ExpenseNotFoundAPIException {
        LOG.info("Read all expenses");
        return toListT(repo.findAll());
    }

    //###################
    //##### UPDATE ######
    //###################

    /**
     * Update an existing expense
     * @param expenseId The expense to update
     * @param expense The new expense
     * @return updated expense
     * @throws ExpenseNotFoundAPIException If the expense was not found
     */
    @PutMapping("/{expenseId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Expense updateExpense(@PathVariable Long expenseId, @RequestBody Expense expense) throws ExpenseNotFoundAPIException {
        LOG.info("Update expense: " + expenseId);
        Expense exp1 = fetchExpense(expenseId);

        if (expense.getId() == null)
            throw new MissingValuesAPIException("Missing values: id");

        if (!expense.getId().equals(expenseId))
            throw new IdMismatchAPIException(String.format("Ids %d and %d do not match.", expenseId, expense.getId()));

        copyNonNullProperties(expense, exp1);
        Expense exp = repo.save(exp1);
        LOG.info("Updated expense: " + expenseId);
        return  exp;
    }

    //###################
    //##### DELETE ######
    //###################

    /**
     * Delete an existing expense
     * @param expenseId The expense to delete
     * @throws ExpenseNotFoundAPIException If the expense was not found
     */
    @DeleteMapping("/{expenseId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteExpense(@PathVariable Long expenseId) throws ExpenseNotFoundAPIException {
        LOG.info("Delete expense: " + expenseId);
        repo.delete(fetchExpense(expenseId));
        LOG.info("Expense deleted: " + expenseId);
    }

    //</editor-fold>

    //<editor-fold desc="Involved persons">

    /**
     * Retrieve the persons
     * @param expenseId Id of the Expense
     * @return All persons
     * @throws ExpenseNotFoundAPIException If the expense does not exist
     */
    @GetMapping("/{expenseId}/persons")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Person> getPersons(@PathVariable Long expenseId) throws ExpenseNotFoundAPIException {
        LOG.info("Get all involved persons of expense: " + expenseId);
        return fetchExpense(expenseId).getInvolvedPersons();
    }

    /**
     * Remove a person
     * @param expenseId id of the expense
     * @param personId id of the person
     * @throws ExpenseNotFoundAPIException If the expense does not exist
     */
    @DeleteMapping("/{expenseId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removePerson(@PathVariable Long expenseId, @PathVariable Long personId)
            throws ExpenseNotFoundAPIException {
        LOG.info("Remove person from expense expense: " + expenseId);

        Expense expense = fetchExpense(expenseId);

        try {
            Optional<Person> per = repoPerson.findById(personId);

            if (!per.isPresent())
                throw new PersonNotFoundAPIException();

            expense.removePerson(per.get());
        } catch (IllegalArgumentException e) {
            throw new PersonNotFoundAPIException();
        }

        LOG.info("Person " + personId + " removed from expense " + expenseId);
    }

    /**
     * Add a person
     * @param expenseId Id of the expense
     * @param personId Id of the person
     * @throws ExpenseNotFoundAPIException If the expense does not exist
     */
    @PutMapping("/{expenseId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addPerson(@PathVariable Long expenseId, @PathVariable Long personId)
            throws ExpenseNotFoundAPIException {

        LOG.info("Add person " + personId + " to expense expense: " + expenseId);

        Expense expense = fetchExpense(expenseId);

        try {
            Optional<Person> per = repoPerson.findById(personId);

            if (!per.isPresent())
                throw new PersonNotFoundAPIException();

            expense.addPerson(per.get());
        } catch (IllegalArgumentException e) {
            throw new PersonNotFoundAPIException();
        }

        LOG.info("Person " + personId + " added to expense " + expenseId);
    }

    //</editor-fold>

    /**
     * Calculate the costs of this expense per Person
     * @param expenseId The if of the expense
     * @return Costs per person
     * @throws ExpenseNotFoundAPIException If the expense was not found
     */
    @GetMapping("/{expenseId}/costpp")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getMoneyPp(@PathVariable Long expenseId) throws ExpenseNotFoundAPIException {
        LOG.info("Calculate costs per person of expense: " + expenseId);
        return fetchExpense(expenseId).getMoneyPerPerson();
    }
}
