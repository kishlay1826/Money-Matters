package persistence;

import model.expenses.Bill;
import model.incomes.Assets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BillWriterTest {
    private static final String TEST_FILE = "./data/testBill.txt";
    private BillWriter testWriter;
    private Bill bill;
    private SimpleDateFormat date = new SimpleDateFormat("2020-03-13");

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new BillWriter(new File(TEST_FILE));
    }

    @Test
    void testWriteAsset() {
        testWriter.writeName("Internet");
        testWriter.writeAmount(45.50);
        testWriter.writeDate("2020-03-13");
        testWriter.close();
        try {
            LinkedList<Bill> bills = BillReader.readBills(new File(TEST_FILE));
            Bill newBill = bills.get(0);
            assertEquals("Internet", newBill.getName());
            assertEquals(45.50, newBill.getAmount());
            assertEquals("2020-03-13", newBill.getDate());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
