package persistence;

import model.expenses.ExpenseCategory;
import model.expenses.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CategoryWriterTest {
    private static final String TEST_FILE = "./data/testCategory.txt";
    private CategoryWriter testWriter;
    private ExpenseCategory exp;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new CategoryWriter(new File(TEST_FILE));
        exp = new ExpenseCategory(200, 75);
    }

    @Test
    void testWriteCategory() {
        testWriter.write(exp);
        testWriter.close();
        try {
            List<ExpenseCategory> expenses = CategoryReader.readBalances(new File(TEST_FILE));
            ExpenseCategory testExpense = expenses.get(0);
            assertEquals(200, testExpense.budgetAmount);
            assertEquals(75, testExpense.spentAmount);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
