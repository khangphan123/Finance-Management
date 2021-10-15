package ui;

import model.Customer;
import model.Stock;
import model.Transaction;

import static org.omg.CORBA.ORB.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class FinanceApp {
    private Customer khang;
    private Customer john;
    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Transaction transaction4;
    private Stock google;
    private Stock facebook;
    private Stock amazon;
    private Stock tesla;
    private Stock apple;
    private List<Customer> customerList;
    private List<Transaction> availableTransaction;
    private List<Stock> stockList;
    private Scanner input;

    // Note: The code in runFinance(), processCommand(), displayMenu is taken
    // from the
    // EFFECTS: run the finance application
    public FinanceApp() {
        runFinance();
    }

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

        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECT: Initialize the necessary objects needed for the program to function
    // properly
    @SuppressWarnings("methodlength")
    private void init() {
        khang = new Customer("Khang", 1310, 5000);
        john = new Customer("John", 123, 1000);
        transaction1 = new Transaction("car", 5000, "consumption");
        transaction2 = new Transaction("shoes", 100, "consumption");
        transaction3 = new Transaction("rocket", 99999999, "consumption");
        transaction4 = new Transaction("car ticket", 100, "fine");
        tesla = new Stock("Tesla", "TSLA", 810.47, 0.02);
        amazon = new Stock("Amazon", "AMZN", 3282.88, 0.018);
        apple = new Stock("Apple", "AAPL", 40.51, -0.01);
        google = new Stock("Google", "GOOGL", 2751.17, 0.008);
        facebook = new Stock("Facebook", "FB", 324.72, 0.002);
        stockList = new ArrayList<>();
        stockList.add(tesla);
        stockList.add(amazon);
        stockList.add(apple);
        stockList.add(google);
        stockList.add(facebook);
        availableTransaction = new ArrayList<>();
        availableTransaction.add(transaction1);
        availableTransaction.add(transaction2);
        availableTransaction.add(transaction3);
        availableTransaction.add(transaction4);
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

    }

    //MODIFIES: this
    //EFFECTS: Prompt the user on how much they to deposit and add that money
    //to the account balance
    private void doDeposit() {
        Customer customerAccount = logInAccount();
        System.out.print("How much do you want to deposit: ");
        double amount = input.nextDouble();

        if (amount >= 0) {
            customerAccount.deposit(amount);
        } else {
            System.out.println("Invalid amount");
        }

        System.out.println("Account balance: " + customerAccount.getBalance());
        printTransactions(customerAccount);
    }

    //     MODIFIES: this
//     EFFECTS: Prompt user on how much they want to withdraw money and then
    // subtract that money from the money account.
    private void doWithdrawal() {
        Customer customerAccount = logInAccount();
        System.out.print("How much do you want to withdraw: ");
        double amount = input.nextDouble();

        if (amount < 0) {
            System.out.println("Invalid amount");
        } else if (customerAccount.getBalance() < amount) {
            System.out.println("Not enough money");
        } else {
            customerAccount.withDraw(amount);
        }

        System.out.println("Account balance: " + customerAccount.getBalance());
        printTransactions(customerAccount);

    }

    // MODIFIES: this
    // EFFECTS: Prompt the users for item they want to purchase and then if found
    // the item in the list of available then subtract the item's price from the account
    private void doMakePurchase() {
        Customer customerAccount = logInAccount();
        String itemName = "";
        System.out.println("What do you want to purchase: ");
        itemName = input.next();
        for (Transaction transaction : availableTransaction) {
            if (itemName.equals(transaction.getName())) {
                if (transaction.getPrice() > customerAccount.getBalance()) {
                    System.out.println("Not enough money to purchase this item");
                } else {
                    customerAccount.makePurchase(transaction);
                }

            }
        }


        System.out.println("Account balance: " + customerAccount.getBalance());
        printTransactions(customerAccount);

    }

    //EFFECTS: Prompt users for the specific item with specific price and specific type
    // and then cancel that purchase and then add that item's price to the customer's
    //balance.
    private void doCancelPurchase() {
        Customer customerAccount = logInAccount();
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
        boolean status = customerAccount.cancelTransaction(itemName, itemPrice, itemType);
        if (status) {
            System.out.println("Cancel successfully");
            System.out.println("Account balance: " + customerAccount.getBalance());
        } else {
            System.out.println("Item does not exists");
        }
        printTransactions(customerAccount);
    }

    //EFFECT: Prompt the user for the stock they want to buy and then proceed to buy it.
    private void doBuyStock() {
        Customer customerAccount = logInAccount();
        String stockName = "";
        System.out.println("What stock do you want to buy: ");
        stockName = input.next();
        for (Stock stock : stockList) {
            if (stockName.equals(stock.getName())) {
                if (stock.getPrice() > customerAccount.getBalance()) {
                    System.out.println("Not enough money to purchase this stock");
                } else {
                    customerAccount.buyStock(stock);
                }

            }

        }
        System.out.println("Account balance: " + customerAccount.getBalance());
        printStockPortfolios(customerAccount);
    }

    private void doSellStock() {
        Customer customerAccount = logInAccount();
        String stockName = "";
        Stock stockToSell = null;
        System.out.println("what stock do you want to sell: ");
        stockName = input.next();
        for (Stock stock : stockList) {
            if (stockName.equals(stock.getName())) {
                stockToSell = stock;
            }
        }
        boolean status = customerAccount.sellStock(stockToSell);
        if (status) {
            System.out.println("Stock sold successfully");
        } else {
            System.out.println("Stock does not exists");
        }

        System.out.println("Account balance: " + customerAccount.getBalance());
        printStockPortfolios(customerAccount);

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

    public void printStockPortfolios(Customer customer) {
        if (customer.getStockPortfolios().size() == 0) {
            System.out.println("Empty stock portfolios");
        }

        for (Stock stock : customer.getStockPortfolios()) {
            System.out.println("Stock: " + stock.getName() + "(" + stock.getAbbreviation()
                    + ") " + "Price: " + stock.getPrice());
        }
    }
}
