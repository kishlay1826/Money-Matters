package model.expenses;

// A class that represents transportation expense
public class Transportation extends ExpenseCategory {
    String name = "Transportation";

    public Transportation(double amount) {
        super(amount);
    }
}
