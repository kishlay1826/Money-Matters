package persistence;

import model.expenses.Bill;
import model.expenses.ExpenseCategory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// A reader that can read bills from a file
// source: TellerApp
public class BillReader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns a list of bills parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static LinkedList<Bill> readBills(File file) throws IOException {
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
    private static LinkedList<Bill> parseContent(List<String> fileContent) {
        LinkedList<Bill> bills = new LinkedList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            bills.add(parseBalances(lineComponents));
        }

        return bills;
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
    private static Bill parseBalances(List<String> components) {
        String name = components.get(0);
        double amt = Double.parseDouble(components.get(1));
        SimpleDateFormat date = new SimpleDateFormat(components.get(2));
        return new Bill(name, amt, date);

    }

}

