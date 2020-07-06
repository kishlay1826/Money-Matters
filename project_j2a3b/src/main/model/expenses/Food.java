package model.expenses;

// A class that represents a food expense
public class Food extends ExpenseCategory {
    String name = "Food";

    public Food(double amount) {
        super(amount);
    }
}
