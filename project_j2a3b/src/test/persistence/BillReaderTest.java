package persistence;

import model.expenses.Bill;
import model.incomes.Assets;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BillReaderTest {

    @Test
    void testConstructor() {
        BillReader newBill = new BillReader();
    }

    @Test
    void testParseBillFile() {
        try {
            LinkedList<Bill> bills = BillReader.readBills(new File("./data/testBillFile.txt"));
            Bill bill1 = bills.get(0);
            Bill bill2 = bills.get(1);
            assertEquals("Insurance", bill1.getName());
            assertEquals("Internet", bill2.getName());
            assertEquals(37.50, bill1.getAmount());
            assertEquals(45, bill2.getAmount());
            assertEquals("2020-03-26", bill1.getDate());
            assertEquals("2020-03-05", bill2.getDate());
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
