package persistence;

import model.expenses.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TransactionWriterTest {
    private static final String TEST_FILE = "./data/testTransaction.txt";
    private TransactionWriter testWriter;
    private Transaction t2;
    private SimpleDateFormat date = new SimpleDateFormat("2020-03-23");

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new TransactionWriter(new File(TEST_FILE));
        t2 = new Transaction(date, 60.0, "Food");
    }

    @Test
    void testWriteTransaction() {
        testWriter.writeDate(t2.getDate());
        testWriter.writeAmount(t2.getAmount());
        testWriter.writeName(t2.getType());
        testWriter.close();
        try {
            LinkedList<Transaction> transactions = TransactionReader.readTransaction(new File(TEST_FILE));
            Transaction testTrans = transactions.get(0);
            assertEquals("Food", testTrans.getType());
            assertEquals("2020-03-23", testTrans.getDate());
            assertEquals(60.0, testTrans.getAmount());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
