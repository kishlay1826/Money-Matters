package model.expenses;

import persistence.BillReader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

// A class that represents a bill
public class Bill extends ExpenseCategory {
    String name;
    double amount;
    SimpleDateFormat date;

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }

    // EFFECTS: constructs a new bill with given name, amount, and due date
    public Bill(String name, double amount, SimpleDateFormat date) {
        super(amount);
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    // EFFECTS: returns name of bill to superclass
    public String getName() {
        return super.getName(name);
    }

    public double getAmount() {
        return amount;
    }

    // EFFECTS: returns date of bill as string to superclass
    public String getDate() {
        return date.toLocalizedPattern();
    }

}
