package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

// A writer that can write balance data to a file
// source: TellerApp
public class CurrentBalanceWriter {
    private PrintWriter printWriter;

    // EFFECTS: constructs a writer that will write data to file
    public CurrentBalanceWriter(File file) throws FileNotFoundException, UnsupportedEncodingException {
        printWriter = new PrintWriter(file, "UTF-8");
    }

    // MODIFIES: this
    // EFFECTS: writes amount to file
    public void write(Double amount) {
        printWriter.println(amount);
    }

    // MODIFIES: this
    // EFFECTS: close print writer
    // NOTE: you MUST call this method when you are done writing data!
    public void close() {
        printWriter.close();
    }
}
