package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer implements Writeable {
    private double balance;
    private String name;
    private List<Transaction> transactions;
    private int accountNumber;
    private double interestRate;
    private List<Stock> stockPortfolios;

    //EFFECTS: Initialize the Customer with name Account number and initial Balance.
    public Customer(String name, int accountNumber, double initialBalance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.interestRate = 0.05;
        this.stockPortfolios = new ArrayList<>();
    }

    public void setBalance(double amount) {
        balance = amount;
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

    public List<Transaction> getTransaction() {
        return this.transactions;
    }

    public List<Stock> getStockPortfolios() {
        return this.stockPortfolios;
    }

    //EFFECT: Return True if there is this transaction in the list of transaction, else return False
    public boolean containTransaction(Transaction transaction) {
        if (transactions.contains(transaction)) {
            return true;
        } else {
            return false;
        }
    }

    //EFFECT: Return true if there is this stock in the portfolios, else return false.
    public boolean containStock(Stock stock) {
        if (stockPortfolios.contains(stock)) {
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
            EventLog.getInstance().logEvent(new Event("Added new transaction " + transaction.getName()));
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
            if (transaction.getPrice() == amount
                    && transaction.getName().equals(name)) {
                EventLog.getInstance().logEvent(new Event("Canceled transaction " + transaction.getName()));
                transactions.remove(transaction);
                this.balance += amount;
                return true;
            }
        }
        return false;
    }

    // REQUIRE: Year > 0.
    // EFFECT: return the money that the user could gain if they invest with the current interest rate
    public double investMoney(int year) {
        double investedMoney = 0;
        investedMoney = this.balance * (Math.pow(1 + (this.interestRate / 1), (1 * year)));
        return investedMoney;
    }

    // REQUIRE: valid stock
    // MODIFIES: this
    // EFFECT: add the Stock into the stock Portfolios if there is enough balance,else return false;
    public boolean buyStock(Stock stock) {
        if (this.balance >= stock.getPrice()) {
            this.balance -= stock.getPrice();
            this.stockPortfolios.add(stock);
            return true;
        } else {
            return false;
        }
    }

    //REQUIRES: Valid stock
    //MODIFIES: this
    //EFFECTS: Remove stock from the stock portfolios and decrease balance by the stock amount
    // if stock is in the stock portfolios and return true. Return false otherwise
    public boolean sellStock(Stock stock) {
        for (Stock someStock : stockPortfolios) {
            if (someStock.equals(stock)) {
                stockPortfolios.remove(someStock);
                this.balance += stock.getPrice();
                return true;
            }
        }
        return false;
    }


    //REQUIRES: valid year
    //EFFECTS: Return the amount of money gained from investing in stock after nth years.
    public double moneyGainedFromPortfolio(int year) {
        double totalAmount = 0;
        double amountInvestAfterYears = 0;
        for (Stock stock: stockPortfolios) {
            totalAmount += stock.getPrice() + stock.getPrice() * stock.getRate();
        }
        amountInvestAfterYears = totalAmount * year;
        return amountInvestAfterYears;
    }


    //EFFECTS:
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("balance", balance);
        json.put("account number", accountNumber);
        json.put("interest rate", interestRate);
        json.put("transactions", transactionsToJson());
        json.put("stocks", stockPortfoliosToJson());
        return json;
    }

    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction transaction: transactions) {
            jsonArray.put(transaction.toJson());
        }

        return jsonArray;
    }

    private JSONArray stockPortfoliosToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Stock stock: stockPortfolios) {
            jsonArray.put(stock.toJson());
        }
        return jsonArray;
    }



}
