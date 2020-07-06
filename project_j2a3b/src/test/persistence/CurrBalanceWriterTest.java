package persistence;

import model.expenses.ExpenseCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CurrBalanceWriterTest {
    private static final String TEST_FILE = "./data/testCurrent.txt";
    private CurrentBalanceWriter testWriter;
    private double currentBal;
    private double totalAssets;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new CurrentBalanceWriter(new File(TEST_FILE));
        currentBal = 2500.75;
        totalAssets = 7500;
    }

    @Test
    void testWriteCategory() {
        testWriter.write(currentBal);
        testWriter.write(totalAssets);
        testWriter.close();
        try {
            List<Double> currents = CurrentBalanceReader.readBalance(new File(TEST_FILE));
            double newCurrentBal = currents.get(0);
            double totalA = currents.get(1);
            assertEquals(2500.75, newCurrentBal);
            assertEquals(7500, totalA);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
