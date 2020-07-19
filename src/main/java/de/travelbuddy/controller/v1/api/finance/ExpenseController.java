package de.travelbuddy.controller.v1.api.finance;

import de.travelbuddy.controller.v1.api.exceptions.PersonNotFoundAPIException;
import de.travelbuddy.controller.v1.api.finance.exceptions.ExpenseNotFoundAPIException;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.finance.Money;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/expenses")
public class ExpenseController {

    IGenericRepo<Expense> repo = null;
    IGenericRepo<Person> repoPerson = null;

    @Autowired
    public ExpenseController(IGenericRepo<Expense> repo, IGenericRepo<Person> repoPerson) {
        this.repo = repo;
        this.repo.setType(Expense.class);
        this.repoPerson = repoPerson;
        this.repoPerson.setType(Person.class);
    }

    private Expense fetchExpense(Long expenseId) {
        return repo
                .getStream()
                .where(Expense -> Expense.getId().equals(expenseId))
                .findOne()
                .orElseThrow(ExpenseNotFoundAPIException::new);
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
        fetchExpense(expenseId);

        repo.remove(expenseId);
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

        Person person = repoPerson.getStream()
                            .where(p -> p.getId().equals(personId))
                            .findOne()
                            .orElseThrow(PersonNotFoundAPIException::new);

        expense.removePerson(person);
        repo.save(expense);
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

        Person person = repoPerson.getStream()
                .where(p -> p.getId().equals(personId))
                .findOne()
                .orElseThrow(PersonNotFoundAPIException::new);

        expense.addPerson(person);
        repo.save(expense);
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
