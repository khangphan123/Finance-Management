package ui;

import model.Customer;
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
    private List<Customer> customerList;
    private List<Transaction> availableTransaction;
    private Scanner input;

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

    private void processCommand(String command) {
        if (command.equals("d")) {
            doDeposit();
        } else if (command.equals("w")) {
            doWithdrawal();

        } else if (command.equals("m")) {
            doMakePurchase();
        } else if (command.equals("c")) {
            doCancelPurchase();
//        } else if (command.equals("bs")) {
//            doBuyStock();
//        } else if (command.equals("st")) {
//            doSellStock();

        } else {
            System.out.println("Selection not valid...");
        }
    }


    private void init() {
        khang = new Customer("Khang", 1310, 5000);
        john = new Customer("John", 123, 1000);
        transaction1 = new Transaction("car", 5000, "Consumption");
        transaction2 = new Transaction("shoes", 100, "Consumption");
        transaction3 = new Transaction("rocket", 99999999, "Consumption");
        transaction4 = new Transaction("car ticket", 100, "Fine");
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

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> deposit");
        System.out.println("\tw -> withdraw");
        System.out.println("\tm -> make purchase");
        System.out.println("\tc -> cancel purchase");
        System.out.println("\tbs -> buy stock");
        System.out.println("\tst -> sell stock");

    }

    private void doDeposit() {
        Customer customerAccount = logInAccount();
        System.out.print("How much do you want to deposit");
        double amount = input.nextDouble();

        if (amount >= 0) {
            customerAccount.deposit(amount);
        } else {
            System.out.println("Invalid amount");
        }

        System.out.println("Account balance: " + customerAccount.getBalance());
    }

    //     MODIFIES: this
//     EFFECTS: conducts a withdraw transaction
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

    }

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

    private void doCancelPurchase() {
        Customer customerAccount = logInAccount();
        String itemName = "";
        double itemPrice = 0;
        String itemType = "";
        System.out.println("Please provide the information of the item that you want to cancel: ");
        itemName = input.next();
        itemName = itemName.toLowerCase();
        itemPrice = input.nextDouble();
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
}
