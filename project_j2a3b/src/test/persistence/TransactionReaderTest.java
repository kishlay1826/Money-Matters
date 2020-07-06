package persistence;

import model.expenses.Transaction;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//source: TellerApp
public class TransactionReaderTest {

    @Test
    void testConstructor() {
        TransactionReader newTrans = new TransactionReader();
    }

    @Test
    void testParseTransactionFile() {
        try {
            LinkedList<Transaction> transactions = TransactionReader.readTransaction(new File("./data/testTransactionFile.txt"));
            Transaction t1 = transactions.get(0);
            assertEquals("2020-03-04", t1.getDate());
            assertEquals(30.0, t1.getAmount());
            assertEquals("Entertainment", t1.getType());
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
