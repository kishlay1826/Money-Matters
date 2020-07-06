package persistence;

import model.expenses.ExpenseCategory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

// A reader that can read current balance and total asset from a file
// source: TellerApp
public class CurrentBalanceReader {

    // EFFECTS: returns a list of balances parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<Double> readBalance(File file) throws IOException {
        List<String> fileContent = readFile(file);
        LinkedList<Double> currBal = new LinkedList<>();
        for (String content : fileContent) {
            currBal.add(Double.parseDouble(content));
        }
        return currBal;
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }
}
