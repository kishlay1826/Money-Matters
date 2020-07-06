package persistence;

import model.expenses.Transaction;
import model.incomes.Assets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AssetWriterTest {

    private static final String TEST_FILE = "./data/testAsset.txt";
    private AssetWriter testWriter;
    private Assets a1;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new AssetWriter(new File(TEST_FILE));
        a1 = new Assets("Car", 3000);
    }

    @Test
    void testWriteAsset() {
        testWriter.write(a1);
        testWriter.close();
        try {
            LinkedList<Assets> assets = AssetReader.readAsset(new File(TEST_FILE));
            Assets newAsset = assets.get(0);
            assertEquals("Car", newAsset.getName());
            assertEquals(3000, newAsset.getPrice());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
