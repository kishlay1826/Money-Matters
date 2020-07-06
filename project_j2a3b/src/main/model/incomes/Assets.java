package model.incomes;

import persistence.AssetReader;
import persistence.Saveable;

import java.io.PrintWriter;

// A class that represents an asset
public class Assets implements Saveable {
    String name;
    double price;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // EFFECTS: constructs a new asset with a given name and price
    public Assets(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(name);
        printWriter.print(AssetReader.DELIMITER);
        printWriter.println(price);
    }
}
