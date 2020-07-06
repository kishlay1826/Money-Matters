package persistence;

import model.expenses.Bill;
import model.expenses.ExpenseCategory;
import model.expenses.Transaction;
import model.expenses.Transportation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// A class that can read transaction data from a file
// source: TellerApp
public class TransactionReader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns a list of balances parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static LinkedList<Transaction> readTransaction(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of balances parsed from list of strings
    // where each string contains data for one account
    private static LinkedList<Transaction> parseContent(List<String> fileContent) {
        LinkedList<Transaction> accounts = new LinkedList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            accounts.add(parseBalances(lineComponents));
        }

        return accounts;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 4 where element 0 represents the
    // id of the next account to be constructed, element 1 represents
    // the id, elements 2 represents the name and element 3 represents
    // the balance of the account to be constructed
    // EFFECTS: returns an account constructed from components
    private static Transaction parseBalances(List<String> components) {
        SimpleDateFormat date = new SimpleDateFormat(components.get(0));
        double amt = Double.parseDouble(components.get(1));
        String name = components.get(2);
        return new Transaction(date, amt, name);

    }

}
