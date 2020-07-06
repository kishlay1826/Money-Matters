package ui;

import exceptions.InsufficientBalanceException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import model.expenses.*;
import model.incomes.Assets;
import persistence.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class Main extends Application {

    Stage window;
    TableView<Transaction> table;
    TableView<Bill> billTable;
    ChoiceBox<String> typeInput;
    TextField amountInput;
    DatePicker dateInput;
    Scene dashboard;
    MoneyMatters moneyMatters = new MoneyMatters();
    Label balanceLabel;
    Double balance;
    TableView<Assets> assetTable;
    Label assetLabel;
    Double sumAssets;
    BarChart barChart;
    AlertBox alertBox;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        moneyMatters.loadBalances();
        window = primaryStage;
        window.setTitle("Money Matters");
        List<Double> currBal = CurrentBalanceReader.readBalance(new File(MoneyMatters.CURRENT_FILE));
        balance = currBal.get(0);
        sumAssets = currBal.get(1);
        Label title = new Label("MONEY MATTERS");
        Button transactionButton = getTransactionButton();
        Button incomeButton = getIncomeButton();
        Button billButton = getBillButton(incomeButton);
        balanceLabel = new Label("Current Balance: " + balance);
        assetLabel = new Label("Total Assets: " + sumAssets);
        Button assetButton = new Button("Add/View Assets");
        assetButton.setOnAction(e -> assetScene());
        balanceLabel.setMinWidth(120);
        balanceLabel.setMinHeight(30);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(balanceLabel, assetLabel);
        GridPane gridPane = getGridPane(transactionButton, billButton, incomeButton, assetButton);
        BarChart barChart = barChart();
        BorderPane borderPane = getBorderPane(title, vbox, gridPane);
        dashboard = new Scene(borderPane);
        setWindow(dashboard);
    }

    private Button getIncomeButton() {
        Button incomeButton = new Button("Add balance");
        incomeButton.setMinWidth(25);
        incomeButton.setMinHeight(25);
        incomeButton.setWrapText(true);
        return incomeButton;
    }

    private Stage setWindow(Scene dashboard) {
        window.setScene(dashboard);
        window.setMinWidth(200);
        window.setMinHeight(200);
        window.show();
        return window;
    }

    private Button getTransactionButton() {
        Button transactionButton = new Button("Add/View Transactions");
        transactionButton.setOnAction(e -> transactionScene());
        transactionButton.setMinWidth(25);
        transactionButton.setMinHeight(25);
        return transactionButton;
    }

    private BorderPane getBorderPane(Label title, VBox vbox, GridPane gridPane) {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(title);
        borderPane.setBottom(vbox);
        borderPane.setCenter(gridPane);
        borderPane.setRight(barChart);
        return borderPane;
    }

    private Button getBillButton(Button incomeButton) {
        Button billButton = new Button("Add/Pay Bills");
        billButton.setOnAction(e -> billScene());
        incomeButton.setOnAction(e -> {
            balance = incomeScene();
        });
        billButton.setWrapText(true);
        billButton.setMinWidth(25);
        billButton.setMinHeight(25);
        billButton.setWrapText(true);
        return billButton;
    }

    private GridPane getGridPane(Button transactionButton, Button billButton, Button incomeButton, Button assetButton) {
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(120, 120);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.add(transactionButton, 0, 0);
        gridPane.add(billButton, 1, 0);
        gridPane.add(incomeButton, 2, 0);
        gridPane.add(assetButton, 0, 1);
        return gridPane;
    }


    private BarChart barChart() {
        CategoryAxis x = new CategoryAxis();
        x.setLabel("Spent Amount");
        NumberAxis y = new NumberAxis();
        y.setLabel("Categories");
        barChart = new BarChart(x, y);
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Expenses");
        dataSeries1.getData().add(new XYChart.Data("Food", moneyMatters.currentFood.spentAmount));
        dataSeries1.getData().add(new XYChart.Data("Entertainment", moneyMatters.currentEnt.spentAmount));
        dataSeries1.getData().add(new XYChart.Data("Transportation", moneyMatters.currentTrans.spentAmount));
        dataSeries1.getData().add(new XYChart.Data("Miscellaneous", moneyMatters.currentMisc.spentAmount));
        barChart.getData().add(dataSeries1);
        return barChart;
    }

    private void updateBarChart() {
        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.getData().add(new XYChart.Data("Food", moneyMatters.currentFood.spentAmount));
        dataSeries2.getData().add(new XYChart.Data("Entertainment", moneyMatters.currentEnt.spentAmount));
        dataSeries2.getData().add(new XYChart.Data("Transportation", moneyMatters.currentTrans.spentAmount));
        dataSeries2.getData().add(new XYChart.Data("Miscellaneous", moneyMatters.currentMisc.spentAmount));
        barChart.getData().set(0, dataSeries2);
    }

    private void assetScene() {
        TableColumn<Assets, String> nameColumn = getAssetNameColumn();
        TableColumn<Assets, Double> amountColumn = getAssetPriceColumn();
        TextField nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);
        TextField priceInput = new TextField();
        priceInput.setPromptText("Price");
        Button addButton = getAddAssetButton(nameInput, priceInput);
        Button deleteButton = getDeleteButton(1);
        HBox hbox = getAssetHBox(nameInput, priceInput, addButton, deleteButton);
        assetTable = new TableView<>();
        assetTable.setItems(getAssets());
        assetTable.getColumns().addAll(nameColumn, amountColumn);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(assetTable, hbox);
        Scene scene = new Scene(vbox);
        window.setScene(scene);
        closeTransactionWindow();
    }

    private ObservableList<Assets> getAssets() {
        ObservableList<Assets> assets = FXCollections.observableArrayList();
        try {
            moneyMatters.asset = AssetReader.readAsset(
                    new File(MoneyMatters.ASSET_FILE));
            assets.addAll(moneyMatters.asset);
        } catch (Exception e) {
            moneyMatters.asset = new LinkedList<>();
        }
        return assets;
    }

    private HBox getAssetHBox(TextField nameInput, TextField amtInput, Button addButton, Button deleteButton) {
        HBox hbox = new HBox();
        Button backButton = new Button("Go Back");
        backButton.setOnAction(e -> window.setScene(dashboard));
        backButton.setMinWidth(75);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(nameInput, amtInput, addButton, deleteButton, backButton);
        return hbox;
    }

    private void deleteAssetButtonClicked() {
        ObservableList<Assets> assetsSelected;
        ObservableList<Assets> allAssets;
        allAssets = assetTable.getItems();
        assetsSelected = assetTable.getSelectionModel().getSelectedItems();
        assetsSelected.forEach(allAssets::remove);
        moneyMatters.asset.removeAll(assetsSelected);
        for (Assets a : assetsSelected) {
            moneyMatters.totalAssets = moneyMatters.totalAssets - a.getPrice();
        }
        assetLabel.setText("Total Assets: " + moneyMatters.totalAssets);
        saveData();
    }

    private Button getAddAssetButton(TextField nameInput, TextField amtInput) {
        Button addButton = new Button("Add");
        addButton.setMinWidth(50);
        addButton.setOnAction(e -> addAssetButtonClicked(nameInput, amtInput));
        return addButton;
    }

    private void addAssetButtonClicked(TextField nameInput, TextField amtInput) {
        Assets assets = new Assets("", 0);
        assets.setName(nameInput.getText());
        assets.setPrice(Double.parseDouble(amtInput.getText()));
        moneyMatters.asset.add(assets);
        moneyMatters.totalAssets = moneyMatters.totalAssets + (Double.parseDouble(amtInput.getText()));
        sumAssets = moneyMatters.totalAssets;
        assetTable.getItems().add(assets);
        assetLabel.setText("Total Assets: " + sumAssets);
        nameInput.clear();
        amtInput.clear();
        saveData();
    }

    private TableColumn<Assets, Double> getAssetPriceColumn() {
        TableColumn<Assets, Double> amountColumn = new TableColumn<>("Price");
        amountColumn.setMinWidth(200);
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        return amountColumn;
    }

    private TableColumn<Assets, String> getAssetNameColumn() {
        TableColumn<Assets, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        return nameColumn;
    }

    private double incomeScene() {
        Stage incomeWindow = new Stage();
        incomeWindow.initModality(Modality.APPLICATION_MODAL);
        incomeWindow.setTitle("Add balance");
        incomeWindow.setMinWidth(250);
        Label label = new Label();
        label.setText("Add amount:");
        TextField amountInput = new TextField();
        amountInput.setPromptText("Amount in $");
        amountInput.setMinWidth(40);
        Button submitButton = getSubmitButton(amountInput, incomeWindow);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(amountInput, submitButton);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, hbox);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);
        incomeWindow.setScene(scene);
        incomeWindow.showAndWait();
        return moneyMatters.currentBalance;
    }

    private Button getSubmitButton(TextField amountInput, Stage incomeWindow) {
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            try {
                Double amount = Double.parseDouble(amountInput.getText());
                moneyMatters.currentBalance = balance + Double.parseDouble(amountInput.getText());
                balanceLabel.setText("Current Balance: " + (moneyMatters.currentBalance));
                incomeWindow.close();
                saveData();
            } catch (NumberFormatException ee) {
                alertBox = new AlertBox();
                alertBox.alertBox("Amount is not a number!");
            }
        });
        return submitButton;
    }

    private void billScene() {
        TableColumn<Bill, String> nameColumn = getBillNameColumn();
        TableColumn<Bill, Double> amountColumn = getBillAmountColumn();
        TableColumn<Bill, String> dateColumn = getBillDateColumn();

        TextField nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);

        TextField amtInput = new TextField();
        amtInput.setPromptText("Amount");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Date");

        Button addButton = getAddBillButton(nameInput, amtInput, datePicker);
        Button deleteButton = getDeleteButton(2);
        Button payBillButton = getPayBillButton();

        HBox hbox = getBillHBox(nameInput, amtInput, datePicker, addButton, deleteButton, payBillButton);

        billTable = new TableView<>();
        billTable.setItems(getBills());
        billTable.getColumns().addAll(nameColumn, amountColumn, dateColumn);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(billTable, hbox);

        Scene scene = new Scene(vbox);
        window.setScene(scene);
        closeTransactionWindow();
    }

    private Button getPayBillButton() {
        Button payBillButton = new Button("Pay Bill");
        payBillButton.setMinWidth(50);
        payBillButton.setOnAction(e -> payBillButtonClicked());
        return payBillButton;
    }

    private void payBillButtonClicked() {
        Bill billSelected;
        billSelected = billTable.getSelectionModel().getSelectedItem();
        billTable.getItems().remove(billSelected);
        Transaction transaction = new Transaction();
        transaction.setType(billSelected.getName());
        transaction.setAmount(billSelected.getAmount());
        transaction.setDate(new SimpleDateFormat(billSelected.getDate()));
        moneyMatters.transactionLines.add(transaction);
        moneyMatters.bill.remove(billSelected);
        saveData();
        balance = balance - billSelected.getAmount();
        balanceLabel.setText("Current Balance: " + balance);
    }

    private ObservableList<Bill> getBills() {
        ObservableList<Bill> bills = FXCollections.observableArrayList();
        try {
            moneyMatters.bill = BillReader.readBills(
                    new File(MoneyMatters.BILL_FILE));
            bills.addAll(moneyMatters.bill);
        } catch (Exception e) {
            moneyMatters.bill = new LinkedList<>();
        }
        return bills;
    }

    private HBox getBillHBox(
            TextField nameInput, TextField amtInput, DatePicker dateInput, Button button, Button add, Button delete) {
        HBox hbox = new HBox();
        Button backButton = new Button("Go Back");
        backButton.setOnAction(e -> window.setScene(dashboard));
        backButton.setMinWidth(75);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(nameInput, amtInput, dateInput, add, delete, backButton, button);
        return hbox;
    }

    private TableColumn<Bill, String> getBillDateColumn() {
        TableColumn<Bill, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(200);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        return dateColumn;
    }

    private TableColumn<Bill, Double> getBillAmountColumn() {
        TableColumn<Bill, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setMinWidth(200);
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        return amountColumn;
    }

    private void transactionScene() {
        TableColumn<Transaction, String> typeColumn = getTypeColumn();
        TableColumn<Transaction, Double> amountColumn = getAmountColumn();
        TableColumn<Transaction, String> dateColumn = getDateColumn();

        typeInput = new ChoiceBox<>();
        typeInput.getItems().addAll("Food", "Transportation", "Entertainment", "Miscellaneous");
        typeInput.setMinWidth(100);

        amountInput = new TextField();
        amountInput.setPromptText("Amount");
        amountInput.setMinWidth(40);

        dateInput = new DatePicker();
        dateInput.setPromptText("Date");
        dateInput.setMinWidth(40);

        Button addButton = getAddButton();
        Button deleteButton = getDeleteButton(3);

        HBox hbox = getHBox(typeInput, amountInput, dateInput, addButton, deleteButton);

        table = new TableView<>();
        table.setItems(getTransaction());
        table.getColumns().addAll(typeColumn, amountColumn, dateColumn);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(table, hbox);

        Scene scene = new Scene(vbox);
        window.setScene(scene);
        closeTransactionWindow();
    }

    private void closeTransactionWindow() {
        window.setOnCloseRequest(e -> {
            e.consume();
            saveData();
            window.close();
        });
    }

    private void deleteBillButtonClicked() {
        Bill billSelected;
        billSelected = billTable.getSelectionModel().getSelectedItem();
        billTable.getItems().remove(billSelected);
        moneyMatters.bill.remove(billSelected);
        saveData();
    }

    private Button getAddBillButton(
            TextField nameInput, TextField amtInput, DatePicker dateInput) {
        Button addButton = new Button("Add");
        addButton.setMinWidth(50);
        addButton.setOnAction(e -> addBillButtonClicked(nameInput, amtInput, dateInput));
        return addButton;
    }

    private void addBillButtonClicked(
            TextField nameInput, TextField amtInput, DatePicker dateInput
    ) {
        Bill bill = new Bill("", 0, new SimpleDateFormat());
        bill.setName(nameInput.getText());
        bill.setAmount(Double.parseDouble(amtInput.getText()));
        bill.setDate(new SimpleDateFormat(dateInput.getValue().toString()));
        moneyMatters.bill.add(bill);
        billTable.getItems().add(bill);
        nameInput.clear();
        amtInput.clear();
        saveData();
    }

    private TableColumn<Bill, String> getBillNameColumn() {
        TableColumn<Bill, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        return nameColumn;
    }

    public TableColumn<Transaction, String> getTypeColumn() {
        TableColumn<Transaction, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(200);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        return typeColumn;
    }

    public TableColumn<Transaction, Double> getAmountColumn() {
        TableColumn<Transaction, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setMinWidth(100);
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        return amountColumn;
    }

    public TableColumn<Transaction, String> getDateColumn() {
        TableColumn<Transaction, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(200);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        return dateColumn;
    }

    public HBox getHBox(
            ChoiceBox<String> typeInput, TextField amountInput, DatePicker dateInput, Button addButton, Button db) {
        HBox hbox = new HBox();
        Button backButton = new Button("Go Back");
        backButton.setMinWidth(75);
        backButton.setOnAction(e -> window.setScene(dashboard));
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(typeInput, amountInput, dateInput, addButton, db, backButton);
        return hbox;
    }

    public Button getAddButton() {
        Button addButton = new Button("Add");
        addButton.setMinWidth(50);
        addButton.setOnAction(e -> addButtonClicked());
        return addButton;
    }

    public Button getDeleteButton(int type) {
        Button deleteButton = new Button("Delete");
        deleteButton.setMinWidth(75);
        deleteButton.setOnAction(e -> {
            switch (type) {
                case 1:
                    deleteAssetButtonClicked();
                    break;
                case 2:
                    deleteBillButtonClicked();
                    break;
                case 3:
                    deleteButtonClicked();
                    break;
            }
        });
        return deleteButton;
    }

    //Get all of the products
    public ObservableList<Transaction> getTransaction() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        try {
            moneyMatters.transactionLines = TransactionReader.readTransaction(
                    new File(MoneyMatters.TRANSACTION_FILE));
            transactions.addAll(moneyMatters.transactionLines);
        } catch (Exception e) {
            moneyMatters.transactionLines = new LinkedList<>();
        }
        return transactions;
    }

    public void addButtonClicked() {
        Transaction transaction = new Transaction();
        transaction.setType(typeInput.getValue());
        Double amount = Double.parseDouble(amountInput.getText());
        transaction.setAmount(amount);
        SimpleDateFormat date = new SimpleDateFormat(dateInput.getValue().toString());
        transaction.setDate(date);
        if (updateCategoryBalances(typeInput.getValue(), amount, date)) {
            table.getItems().add(transaction);
            typeInput.setValue("Food");
            amountInput.clear();
            updateBarChart();
            saveData();
        } else {
            table.getItems().remove(transaction);
            moneyMatters.transactionLines.remove(transaction);
        }
    }

    private boolean updateCategoryBalances(String value, Double amount, SimpleDateFormat date) {
        try {
            switch (value) {
                case "Food":
                    moneyMatters.addTransactionFinal(moneyMatters.currentFood, amount, date, "Food");
                    break;
                case "Entertainment":
                    moneyMatters.addTransactionFinal(moneyMatters.currentEnt, amount, date, "Entertainment");
                    break;
                case "Miscellaneous":
                    moneyMatters.addTransactionFinal(moneyMatters.currentMisc, amount, date, "Miscellaneous");
                    break;
                case "Transportation":
                    moneyMatters.addTransactionFinal(moneyMatters.currentTrans, amount, date, "Transportation");
                    break;
            }
        } catch (InsufficientBalanceException e) {
            alertBox = new AlertBox();
            alertBox.alertBox("Insufficient Balance! Please try again!");
            return false;
        }
        return true;
    }

    public void deleteButtonClicked() {
        Transaction transSelected;
        transSelected = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(transSelected);
        moneyMatters.transactionLines.remove(transSelected);
        saveData();
    }

    public void saveData() {
        moneyMatters.saveTransactions();
        moneyMatters.saveBills();
        moneyMatters.saveAssets();
        moneyMatters.saveCurrentBalances();
        moneyMatters.saveCategoryBalances();
    }

}
