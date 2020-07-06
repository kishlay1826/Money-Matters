package persistence;

import model.expenses.ExpenseCategory;
import model.expenses.Food;
import model.expenses.Transaction;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CategoryReaderTest {

    @Test
    void testConstructor() {
        CategoryReader newCat = new CategoryReader();
    }

    @Test
    void testParseCategoryFile() {
        try {
            List<ExpenseCategory> expense = CategoryReader.readBalances(new File("./data/testCategoryFile.txt"));
            ExpenseCategory food = expense.get(0);
            ExpenseCategory entertainment = expense.get(1);
            ExpenseCategory misc = expense.get(2);
            ExpenseCategory trans = expense.get(3);
            assertEquals(250.0, food.budgetAmount);
            assertEquals(125.7, food.spentAmount);
            assertEquals(125, entertainment.budgetAmount);
            assertEquals(40.8, entertainment.spentAmount);
            assertEquals(100, misc.budgetAmount);
            assertEquals(75, misc.spentAmount);
            assertEquals(300, trans.budgetAmount);
            assertEquals(100, trans.spentAmount);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            TransactionReader.readTransaction(new File("./path/does/not/exist/testTransaction.txt"));
        } catch (IOException e) {
            // expected
        }
    }
}
