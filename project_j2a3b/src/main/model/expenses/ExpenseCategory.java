package model.expenses;

import exceptions.InsufficientBalanceException;
import persistence.CategoryReader;
import persistence.Saveable;

import java.io.PrintWriter;

// A class that represents an expense category
public class ExpenseCategory implements Saveable {
    public double budgetAmount;      // amount desired to be spent for the month
    public double spentAmount;       // amount spent in the category

    // EFFECTS: initialises the category with desired budget amount
    public ExpenseCategory(double amount) {
        this.budgetAmount = amount;
        this.spentAmount = 0;
    }

    public ExpenseCategory(double budget, double spent) {
        this.budgetAmount = budget;
        this.spentAmount = spent;
    }

    // MODIFIES: this
    // EFFECTS: returns true and updates spentAmount if amount in budget
    //          otherwise false
    public boolean addTransaction(double amount) throws InsufficientBalanceException {
        if (((this.spentAmount) + amount) >= budgetAmount) {
            throw new InsufficientBalanceException();
        } else {
            this.spentAmount = spentAmount + amount;
            return true;
        }
    }

    public String getName(String nam) {
        return nam;
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(budgetAmount);
        printWriter.print(CategoryReader.DELIMITER);
        printWriter.println(spentAmount);
    }
}
