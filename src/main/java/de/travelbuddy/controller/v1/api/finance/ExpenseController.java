package de.travelbuddy.controller.v1.api.finance;

import de.travelbuddy.controller.v1.api.BaseController;
import de.travelbuddy.controller.v1.api.exceptions.IdMismatchAPIException;
import de.travelbuddy.controller.v1.api.exceptions.MissingValuesAPIException;
import de.travelbuddy.controller.v1.api.finance.exceptions.ExpenseNotFoundAPIException;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.finance.Money;
import de.travelbuddy.storage.repositories.ExpenseRepo;
import de.travelbuddy.storage.repositories.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/expenses")
public class ExpenseController extends BaseController<Expense> {

    ExpenseRepo repo = null;
    PersonRepo repoPerson = null;

    @Autowired
    public ExpenseController(ExpenseRepo repo, PersonRepo repoPerson) {
        this.repo = repo;
        this.repoPerson = repoPerson;
        this.type = Expense.class;
    }

    private Expense fetchExpense(Long expenseId) {
        Optional<Expense> expense = repo.findById(expenseId);

        if (!expense.isPresent())
            throw new ExpenseNotFoundAPIException();

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
        return repo.save(expense);
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

    //###################
    //##### UPDATE ######
    //###################

    /**
     * Update an existing expense
     * @param expenseId The expense to update
     * @param expense The new expense
     * @return
     * @throws ExpenseNotFoundAPIException If the expense was not found
     */
    @PutMapping("/{expenseId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Expense updateExpense(@PathVariable Long expenseId, @RequestBody Expense expense) throws ExpenseNotFoundAPIException {
        //Check if exist
        fetchExpense(expenseId);

        if (expense.getId() == null)
            throw new MissingValuesAPIException("Missing values: id");

        if (!expense.getId().equals(expenseId))
            throw new IdMismatchAPIException(String.format("Ids %d and %d do not match.", expenseId, expense.getId()));

        return repo.save(expense);
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
        //Check if exist
        repo.delete(fetchExpense(expenseId));
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
        //Check if exist
        Expense expense = fetchExpense(expenseId);


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
        //Check if exist
        Expense expense = fetchExpense(expenseId);

        return;
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
        return fetchExpense(expenseId).getMoneyPerPerson();
    }
}
