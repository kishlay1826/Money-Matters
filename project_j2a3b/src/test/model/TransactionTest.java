package model;

import model.expenses.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {
    ExpenseCategory exp;
    Food food;
    Miscellaneous misc;
    Transportation trans;
    Entertainment ent;
    Transaction tran;
    String stringDate =  "2020-02-18";
    SimpleDateFormat date = new SimpleDateFormat(stringDate);

    @BeforeEach
    void runBefore() {
        exp = new ExpenseCategory(200);
        food = new Food(200);
        misc = new Miscellaneous(200);
        trans = new Transportation(200);
        ent = new Entertainment(200);
        tran = new Transaction(date, 100, "Food");
    }

    @Test
    void testConstructor() {
        Transaction tran2 = new Transaction(date, 150, "Misc");
    }

    @Test
    void testGetters() {
        assertEquals(stringDate, tran.getDate());
        assertEquals(100, tran.getAmount());
        assertEquals("Food", tran.getType());
    }

    @Test
    void testSetters() {
        Transaction transaction = new Transaction();
        transaction.setType("Food");
        transaction.setAmount(5);
        transaction.setDate(new SimpleDateFormat("23/09/2020"));
        assertEquals("Food", transaction.getType());
        assertEquals(5, transaction.getAmount());
        assertEquals("23/09/2020", transaction.getDate());
    }
}
