package persistence;

import model.expenses.ExpenseCategory;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CurrBalanceReaderTest {

    @Test
    void testConstructor() {
        CurrentBalanceReader newBal = new CurrentBalanceReader();
    }

    @Test
    void testParseBalanceFile() {
        try {
        List<Double> currBal = CurrentBalanceReader.readBalance(new File("./data/testCurrBalanceFile.txt"));
        double current = currBal.get(0);
        double assets = currBal.get(1);
        assertEquals(1500.75, current);
        assertEquals(2500, assets);
    } catch (
    IOException e) {
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
