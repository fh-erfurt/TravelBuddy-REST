package de.travelbuddy.storage.repositorys;

import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.storage.core.DataController;
import de.travelbuddy.storage.core.JpaGenericDao;
import de.travelbuddy.storage.exceptions.StorageError;
import org.mariadb.jdbc.internal.logging.LoggerFactory;

import java.util.logging.Logger;

public class ExpenseRepo {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(ExpenseRepo.class);

    public void saveExpense(Expense expense) throws StorageError {

        JpaGenericDao<Expense,Long> expenseDao = DataController.getInstance().getExpenseDao();

        if(expense.getId() == null)
            expenseDao.create(expense);
        else
            expenseDao.update(expense);

    }

    public Expense readExpense(Long Id) throws StorageError {
        JpaGenericDao<Expense,Long> expenseDao = DataController.getInstance().getExpenseDao();
        return expenseDao.findById(Id);
    }

}
