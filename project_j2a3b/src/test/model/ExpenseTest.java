package model;

import com.sun.org.apache.xerces.internal.impl.dv.dtd.ENTITYDatatypeValidator;
import exceptions.InsufficientBalanceException;
import model.expenses.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {
    ExpenseCategory expense;
    Food food;
    Entertainment ent;
    Miscellaneous misc;
    Transportation trans;
    Bill b;
    String stringDate =  "2020-02-18";
    SimpleDateFormat date = new SimpleDateFormat(stringDate);

    @BeforeEach
    void runBefore() {
        expense = new ExpenseCategory(100);
        food = new Food(100);
        misc = new Miscellaneous(100);
        trans = new Transportation(100);
        ent = new Entertainment(100);
        b = new Bill("Phone", 25, date);
    }

    @Test
    void testConstructor() {
        Bill bill2 = new Bill("Electricity", 30, date);
        ExpenseCategory exp2 = new ExpenseCategory(200);
        Food food2 = new Food(200);
        Miscellaneous misc2 = new Miscellaneous(200);
        Transportation trans2 = new Transportation(200);
        Entertainment ent2 = new Entertainment(200);
    }

    @Test
    void testAddTransactionLessAmount() {
        try {
            assertTrue(expense.addTransaction(10));
            assertTrue(food.addTransaction(10));
            assertTrue(misc.addTransaction(10));
            assertTrue(trans.addTransaction(10));
            assertTrue(ent.addTransaction(10));
            assertEquals(10, expense.spentAmount);
            assertEquals(10, food.spentAmount);
            assertEquals(10, misc.spentAmount);
            assertEquals(10, trans.spentAmount);
            assertEquals(10, ent.spentAmount);
        } catch (InsufficientBalanceException e) {
            fail();
        }
    }

    @Test
    void testAddTransactionMoreAmount() {
        try {
            assertFalse(expense.addTransaction(500));
            assertEquals(0, expense.spentAmount);
            assertEquals(0, food.spentAmount);
            fail();
        } catch (InsufficientBalanceException e) {
            //
        }
    }

    @Test
    void testGetters() {
        assertEquals("Phone", b.getName());
        assertEquals(stringDate, b.getDate());
        assertEquals(25, b.getAmount());
    }

    @Test
    void testSetters() {
        b.setName("Insurance");
        b.setAmount(37.50);
        b.setDate(new SimpleDateFormat("23/10/2020"));
        assertEquals("Insurance", b.getName());
        assertEquals(37.50, b.getAmount());
        assertEquals("23/10/2020", b.getDate());
    }

}
