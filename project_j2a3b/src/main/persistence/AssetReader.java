package persistence;

import model.expenses.ExpenseCategory;
import model.incomes.Assets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// A class that reads asset date from a file
// source: TellerApp
public class AssetReader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns a list of balances parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static LinkedList<Assets> readAsset(File file) throws IOException {
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
    private static LinkedList<Assets> parseContent(List<String> fileContent) {
        LinkedList<Assets> assets = new LinkedList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            assets.add(parseBalances(lineComponents));
        }

        return assets;
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
    private static Assets parseBalances(List<String> components) {
        String name = components.get(0);
        double amount = Double.parseDouble(components.get(1));
        return new Assets(name, amount);

    }

}
