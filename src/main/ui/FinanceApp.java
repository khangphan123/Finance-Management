package ui;

import model.Customer;
import model.Stock;
import model.Transaction;

import static org.omg.CORBA.ORB.init;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


// EFFECTS: construct Finance app
public class FinanceApp {
    private static final String JSON_STORE = "./data/Finance.json";
    private Customer khang;
    private Customer john;
    private List<Customer> customerList;
    private List<Transaction> availableTransaction;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private List<Stock> stockList;
    private Scanner input;

    // Note: The code in runFinance(), processCommand(), displayMenu is taken
    // from the file in the course
    // EFFECTS: run the finance application
    public FinanceApp() throws FileNotFoundException {
        khang = new Customer("Khang", 1310, 5000);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runFinance();

    }

    //EFFECTS: run the app and prompt users for command.
    private void runFinance() {
        boolean running = true;
        String command = null;

        init();
        while (running == true) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("e")) {
                running = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Goodbye!");
    }

    // REQUIRES: command
    // EFFECT: process the command from users and proceed to perfom the necessary
    // function.

    private void processCommand(String command) {
        if (command.equals("d")) {
            doDeposit();
        } else if (command.equals("w")) {
            doWithdrawal();
        } else if (command.equals("m")) {
            doMakePurchase();
        } else if (command.equals("c")) {
            doCancelPurchase();
        } else if (command.equals("bs")) {
            doBuyStock();
        } else if (command.equals("st")) {
            doSellStock();
        } else if (command.equals("si")) {
            gainFromPortfolios();
        } else if (command.equals("pp")) {
            printProfile();
        } else if (command.equals("sf")) {
            saveCustomer();
        } else if (command.equals("l")) {
            loadCustomer();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECT: Initialize the necessary objects needed for the program to function
    // properly

    private void init() {
        john = new Customer("John", 123, 1000);
        stockList = new ArrayList<>();
        customerList = new ArrayList<>();
        customerList.add(khang);
        customerList.add(john);

        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //EFFECT: Display a menu so that users can chooses which option they want to use
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> deposit");
        System.out.println("\tw -> withdraw");
        System.out.println("\tm -> make purchase");
        System.out.println("\tc -> cancel purchase");
        System.out.println("\tbs -> buy stock");
        System.out.println("\tst -> sell stock");
        System.out.println("\tpp -> print profile");
        System.out.println("\tsi -> see investment");
        System.out.println("\tsf -> save customer to file");
        System.out.println("\tl -> load customer from file");
        System.out.println("\te -> exit");

    }

    //MODIFIES: this
    //EFFECTS: Prompt the user to sign up for an account
    private void doSignup() {
        String name = "";
        int accountNumber = 0;
        double initialBalance = 0;
        System.out.println("What is your name: ");
        name = input.next();
        System.out.println("What is your account number: ");
        accountNumber = input.nextInt();
        System.out.println(("What is your initial Balance: "));
        initialBalance = input.nextDouble();
        Customer newCustomer = new Customer(name, accountNumber, initialBalance);
        customerList.add(newCustomer);
        System.out.println("Sign up successfully");

    }

    //MODIFIES: this
    //EFFECTS: Prompt the user on how much they to deposit and add that money
    //to the account balance
    private void doDeposit() {
//        Customer customerAccount = logInAccount();
        System.out.print("How much do you want to deposit: ");
        double amount = input.nextDouble();

        if (amount >= 0) {
            khang.deposit(amount);
        } else {
            System.out.println("Invalid amount");
        }

        System.out.println("Account balance: " + khang.getBalance());
        printTransactions(khang);
    }

    //     MODIFIES: this
//     EFFECTS: Prompt user on how much they want to withdraw money and then
    // subtract that money from the money account.
    private void doWithdrawal() {
//        Customer customerAccount = logInAccount();
        System.out.print("How much do you want to withdraw: ");
        double amount = input.nextDouble();

        if (amount < 0) {
            System.out.println("Invalid amount");
        } else if (khang.getBalance() < amount) {
            System.out.println("Not enough money");
        } else {
            khang.withDraw(amount);
        }

        System.out.println("Account balance: " + khang.getBalance());
        printTransactions(khang);

    }

    // MODIFIES: this
    // EFFECTS: Prompt the users for item they want to purchase and then if found
    // the item in the list of available then subtract the item's price from the account
    private void doMakePurchase() {
//        Customer customerAccount = logInAccount();
        String itemName = "";
        double itemPrice = 0;
        String itemType = "";
        System.out.println("What do you want to purchase: ");
        itemName = input.next();
        itemName = itemName.toLowerCase();
        System.out.println("What is the price of the item: ");
        itemPrice = input.nextDouble();
        System.out.println("What is the type of the item: ");
        itemType = input.next();
        itemType = itemType.toLowerCase();
        Transaction newPurchase = new Transaction(itemName, itemPrice, itemType);
        if (khang.getBalance() < itemPrice) {
            System.out.println("Not enough money to purchase this item");
        } else {
            khang.makePurchase(newPurchase);
        }
//        for (Transaction transaction : availableTransaction) {
//            if (itemName.equals(transaction.getName())) {
//                if (transaction.getPrice() > customerAccount.getBalance()) {
//                    System.out.println("Not enough money to purchase this item");
//                } else {
//                    customerAccount.makePurchase(transaction);
//                }
//
//            }
//        }


        System.out.println("Account balance: " + khang.getBalance());
        printTransactions(khang);

    }

    //EFFECTS: Prompt users for the specific item with specific price and specific type
    // and then cancel that purchase and then add that item's price to the customer's
    //balance.
    private void doCancelPurchase() {
//        Customer customerAccount = logInAccount();
        String itemName = "";
        double itemPrice = 0;
        String itemType = "";
        System.out.println("Please provide the information of the item that you want to cancel: ");
        System.out.print("Item name: ");
        itemName = input.next();
        itemName = itemName.toLowerCase();
        System.out.print("Item price: ");
        itemPrice = input.nextDouble();
        System.out.print("Item type: ");
        itemType = input.next();
        itemType = itemType.toLowerCase();
        boolean status = khang.cancelTransaction(itemName, itemPrice, itemType);
        if (status) {
            System.out.println("Cancel successfully");
            System.out.println("Account balance: " + khang.getBalance());
        } else {
            System.out.println("Item does not exists");
        }
        printTransactions(khang);
    }

    //MODIFIES: this
    //EFFECT: Prompt the user for the stock they want to buy and then proceed to buy it.
    private void doBuyStock() {
//        Customer customerAccount = logInAccount();
        String stockName = "";
        String stockAbbreviation = "";
        double price = 0;
        double rate = 0;
        System.out.println("What stock do you want to buy: ");
        stockName = input.next();
        stockName = stockName.toLowerCase();
        System.out.println("What is the abbreviation of the stock: ");
        stockAbbreviation = input.next();
        System.out.println("What is the price of the stock: ");
        price = input.nextDouble();
        System.out.println("what is the current rate of the stock: ");
        rate = input.nextDouble();
        Stock newStock = new Stock(stockName, stockAbbreviation, price, rate);
        if (price > khang.getBalance()) {
            System.out.println("Don't have enough money");
        } else {
            khang.buyStock(newStock);
            stockList.add(newStock);
        }
//        for (Stock stock : stockList) {
//            if (stockName.equals(stock.getName())) {
//                if (stock.getPrice() > customerAccount.getBalance()) {
//                    System.out.println("Not enough money to purchase this stock");
//                } else {
//                    customerAccount.buyStock(stock);
//                }
//
//            }
//
//        }
        System.out.println("Account balance: " + khang.getBalance());
        printStockPortfolios(khang);
    }

    //MODIFIES: this
    //EFFECTS: Prompt users for the stock they want to sell and if the stock exists in
    // the list of stock then remove it from the list and add the stock price to the account's
    // balance
    private void doSellStock() {
//        Customer customerAccount = logInAccount();
        String stockName = "";
        Stock stockToSell = null;
        System.out.println("what stock do you want to sell: ");
        stockName = input.next();
        for (Stock stock : stockList) {
            if (stockName.equals(stock.getName())) {
                stockToSell = stock;
            }
        }
        boolean status = khang.sellStock(stockToSell);
        if (status) {
            System.out.println("Stock sold successfully");
        } else {
            System.out.println("Stock does not exists");
        }

        System.out.println("Account balance: " + khang.getBalance());
        printStockPortfolios(khang);

    }

    //EFFECTS: Prompt users for the number of years they want to invest with their current stock portfolios, then
    // calculate the gain from those stocks.
    private void gainFromPortfolios() {
//        Customer customerAccount = logInAccount();
        int year = 0;
        System.out.println("How many years do you want to invest: ");
        year = input.nextInt();
        System.out.println("Total money gained after " + year + " years will be " + khang.moneyGainedFromPortfolio(year));

    }


    //EFFECT: Prompt user for their account number and if the account number matches
    // with an existent account that has been initialized then proceed.
    private Customer logInAccount() {
        int customerId = 0;  // force entry into loop
        ArrayList<Integer> listOfAccount = new ArrayList<>();
        for (Customer customer : customerList) {
            listOfAccount.add((customer.getAccountNumber()));
        }
        while (!listOfAccount.contains(customerId)) {
            System.out.println("Please enter your Account number: ");
            customerId = input.nextInt();

        }
        for (Customer customer : customerList) {
            if (customerId == customer.getAccountNumber()) {
                return customer;
            }
        }
        return null;
    }


    // EFFECT: PRINT OUT ALL THE TRANSACTION WITH ITS CORRESPONDING PRICE ON TO THE SCREEN.
    public void printTransactions(Customer customer) {
        if (customer.getTransaction().size() == 0) {
            System.out.println("There are no transaction");
        }
        for (Transaction transaction : customer.getTransaction()) {
            System.out.println("Transaction: " + transaction.getName() + " Amount: " + transaction.getPrice()
                    + " Type: " + transaction.getType());
        }

    }

    // EFFECTS: Print out the stock with its corresponding prices and abbreviation.
    public void printStockPortfolios(Customer customer) {
        if (customer.getStockPortfolios().size() == 0) {
            System.out.println("Empty stock portfolios");
        }

        for (Stock stock : customer.getStockPortfolios()) {
            System.out.println("Stock: " + stock.getName() + "(" + stock.getAbbreviation()
                    + ") " + "Price: " + stock.getPrice());
        }
    }

    public void printProfile() {
//        Customer customerAccount = logInAccount();
        System.out.println("Account balance: " + khang.getBalance());
        printStockPortfolios(khang);
        printTransactions(khang);
    }

    private void saveCustomer() {
        try {
            jsonWriter.open();
            jsonWriter.write(khang);
            jsonWriter.close();
            System.out.println("Saved " + khang.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads customer from file
    private void loadCustomer() {
        try {
            khang = jsonReader.read();
            System.out.println("Loaded " + khang.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
