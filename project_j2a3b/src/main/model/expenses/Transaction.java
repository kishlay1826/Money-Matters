package model.expenses;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

// Represents a transaction line
public class Transaction {

    private SimpleDateFormat date;
    private double amount;
    String type;

    // EFFECTS: constructs a transaction line for the given type and date
    public Transaction(SimpleDateFormat date, double amount, String type) {
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

    // EFFECTS: constructs a transaction line for the given type and date
    public Transaction() {
        this.date = new SimpleDateFormat();
        this.amount = 0;
        this.type = "";
    }

    // EFFECTS: returns the type name of transaction
    public String getType() {
        return type;
    }

    // EFFECTS: returns the date of transaction
    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat = date;
        return dateFormat.toLocalizedPattern();
    }

    // EFFECTS: returns the date of transaction
    public double getAmount() {
        return amount;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }
}
