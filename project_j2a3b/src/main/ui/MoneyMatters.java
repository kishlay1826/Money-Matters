package ui;

import exceptions.InsufficientBalanceException;
import model.expenses.Transaction;
import model.expenses.*;
import model.incomes.Assets;
import persistence.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;


// Money Matters: A budgeting application
public class MoneyMatters {
    public static final String BALANCE_FILE = "./data/balances.txt";
    public static final String CURRENT_FILE = "./data/currBalance.txt";
    public static final String BILL_FILE = "./data/bills.txt";
    public static final String ASSET_FILE = "./data/assets.txt";
    public static final String TRANSACTION_FILE = "./data/transactions.txt";
    public LinkedList<Bill> bill;
    public LinkedList<Transaction> transactionLines;
    LinkedList<Assets> asset;
    Transaction transItem;
    double currentBalance;
    ExpenseCategory currentFood = new Food(100);
    ExpenseCategory currentEnt = new Entertainment(100);
    ExpenseCategory currentMisc = new Miscellaneous(100);
    ExpenseCategory currentTrans = new Transportation(100);
    double totalAssets;


    // MODIFIES: this
    // EFFECTS: loads balances from BALANCE_FILE, if that file exists;
    // otherwise initializes categories with default values
    public void loadBalances() {
        try {
            List<ExpenseCategory> balances = CategoryReader.readBalances(new File(BALANCE_FILE));
            List<Double> currBal = CurrentBalanceReader.readBalance(new File(CURRENT_FILE));
            LinkedList<Bill> bills = BillReader.readBills(new File(BILL_FILE));
            LinkedList<Assets> assets = AssetReader.readAsset(new File(ASSET_FILE));
            LinkedList<Transaction> transactions = TransactionReader.readTransaction(new File(TRANSACTION_FILE));
            currentBalance = currBal.get(0);
            totalAssets = currBal.get(1);
            currentFood = balances.get(0);
            currentEnt = balances.get(1);
            currentMisc = balances.get(2);
            currentTrans = balances.get(3);
            bill = bills;
            asset = assets;
            transactionLines = transactions;
        } catch (Exception e) {
            initialise();
        }
    }

    public void saveCurrentBalances() {
        try {
            CurrentBalanceWriter currWriter = new CurrentBalanceWriter(new File(CURRENT_FILE));
            currWriter.write(currentBalance);
            currWriter.write(totalAssets);
            currWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save balances to " + CURRENT_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void saveBills() {
        try {
            BillWriter billWriter = new BillWriter(new File(BILL_FILE));
            for (Bill b : bill) {
                billWriter.writeName(b.getName());
                billWriter.writeAmount(b.getAmount());
                billWriter.writeDate(b.getDate());
            }
            billWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save file to " + BILL_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    // EFFECTS: saves category balances to a file
    public void saveCategoryBalances() {
        try {
            CategoryWriter categoryWriter = new CategoryWriter(new File(BALANCE_FILE));
            categoryWriter.write(currentFood);
            categoryWriter.write(currentEnt);
            categoryWriter.write(currentMisc);
            categoryWriter.write(currentTrans);
            categoryWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save balances to " + BALANCE_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: saves assets to a file
    public void saveAssets() {
        try {
            AssetWriter assetWriter = new AssetWriter(new File(ASSET_FILE));
            for (Assets a : asset) {
                assetWriter.write(a);
            }
            assetWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save assets to " + ASSET_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: saves transactions to a file
    public void saveTransactions() {
        try {
            TransactionWriter transactionWriter = new TransactionWriter(new File(TRANSACTION_FILE));
            for (Transaction t : transactionLines) {
                transactionWriter.writeDate(t.getDate());
                transactionWriter.writeAmount(t.getAmount());
                transactionWriter.writeName(t.getType());
            }
            transactionWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save transactions to " + TRANSACTION_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    public void initialise() {
        bill = new LinkedList<>();
        asset = new LinkedList<>();
        transactionLines = new LinkedList<>();
        currentBalance = 1000;
        totalAssets = 500;
    }

    // MODIFIES: this, type
    // EFFECTS: updates the fields of 'type' and makes a new transaction and
    //          subsequently adding it to a transaction line
    public void addTransactionFinal(ExpenseCategory type, double amt, SimpleDateFormat date, String typeName) throws
            InsufficientBalanceException {
        if (!type.addTransaction(amt) || (amt > currentBalance)) {
            throw new InsufficientBalanceException();
        } else {
            transItem = new Transaction(date, amt, typeName);
            transactionLines.add(transItem);
            currentBalance = currentBalance - amt;
        }
    }


}
