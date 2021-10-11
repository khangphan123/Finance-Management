package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer {
    private double balance;
    private String name;
    private List<Transaction> transactions;
    private int accountNumber;

    public Customer(String name, int accountNumber, double initialBalance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public double getBalance() {
        return this.balance;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    //EFFECT: Return True if there is this transaction in the list of transaction, else return False
    public boolean containTransaction(Transaction transaction) {
        if (transactions.contains(transaction)) {
            return true;
        } else {
            return false;
        }
    }

    //REQUIRES: Money must be > 0
    //MODIFIES: this
    //EFFECT: balance will update money, create a transaction object and add it to the list of transaction.
    public void deposit(double amount) {
        this.balance += amount;
        Transaction depositTransaction = new Transaction("deposit", amount, "deposit");
        transactions.add(depositTransaction);
    }

    //REQUIRES: amount must be > 0
    //MODIFIES: this
    //EFFECT: balance will decrease by the amount if balance > amount, else return false
    public boolean withDraw(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            Transaction depositTransaction = new Transaction("Withdraw", amount, "withdraw");
            transactions.add(depositTransaction);
            return true;
        } else {
            return false;
        }
    }

    //REQUIRE: transaction must be valid transaction
    //MODIFIES: this
    //EFFECT: add this transaction to the list of transactions if the account's fee is suffice, else return false
    public boolean makePurchase(Transaction transaction) {
        if (transaction.getPrice() <= this.balance) {
            this.balance -= transaction.getPrice();
            this.transactions.add(transaction);
            return true;
        } else {
            return false;
        }
    }

    //REQUIRE:transaction must be valid transaction, amount and type must also be valid
    //MODIFIES: this
    //EFFECT: Remove transaction from the list of transaction if the transaction price and type match
    // with the amount and type in the parameter. Add to current balance the amount that just
    // got refund. Else return False
    public boolean cancelTransaction(String name, double amount, String type) {
        for (Transaction transaction : transactions) {
            if (transaction.getPrice() == amount && transaction.getType().equals(type)
                    && transaction.getName().equals(name)) {
                transactions.remove(transaction);
                this.balance += amount;
                return true;
            }
        }
        return false;
    }
    //EFFECT: Print out the profile
//    public void printProfile() {
//        System.out.println("Name: " + this.name + "\n" + "Balance: ");
//
//    }


    // EFFECT: PRINT OUT ALL THE TRANSACTION WITH ITS CORRESPONDING PRICE ON TO THE SCREEN.
//    public void printTransactions() {
//        for (Transaction transaction: transactions) {
//            System.out.println("Transaction: " + transaction.getName() + " Amount: " + transaction.getPrice()
//            + " Type: " + transaction.getType());
//        }
//
//    }

}
