package persistence;

import model.expenses.Transaction;
import model.incomes.Assets;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AssetReaderTest {

    @Test
    void testConstructor() {
        AssetReader newAsset = new AssetReader();
    }

    @Test
    void testParseAssetFile() {
        try {
            LinkedList<Assets> assets = AssetReader.readAsset(new File("./data/testAssetFile.txt"));
            Assets assets1 = assets.get(0);
            Assets assets2 = assets.get(1);
            assertEquals("Jewellery", assets1.getName());
            assertEquals("Car", assets2.getName());
            assertEquals(4000, assets1.getPrice());
            assertEquals(3000, assets2.getPrice());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            TransactionReader.readTransaction(new File("./path/does/not/exist/testTransaction.txt"));
        } catch (IOException e) {
            // expected
        }
    }
}
