package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

// A writer that can write bill data to a file
// source: TellerApp
public class BillWriter {
    private PrintWriter printWriter;

    // EFFECTS: constructs a writer that will write data to file
    public BillWriter(File file) throws FileNotFoundException, UnsupportedEncodingException {
        printWriter = new PrintWriter(file, "UTF-8");
    }

    // MODIFIES: this
    // EFFECTS: writes name to file
    public void writeName(String name) {
        printWriter.print(name);
        printWriter.print(BillReader.DELIMITER);
    }

    // MODIFIES: this
    // EFFECTS: writes amt to file
    public void writeAmount(Double amt) {
        printWriter.print(amt);
        printWriter.print(BillReader.DELIMITER);
    }

    // MODIFIES: this
    // EFFECTS: writes date to file
    public void writeDate(String date) {
        printWriter.println(date);
    }

    // MODIFIES: this
    // EFFECTS: close print writer
    // NOTE: you MUST call this method when you are done writing data!
    public void close() {
        printWriter.close();
    }
}
