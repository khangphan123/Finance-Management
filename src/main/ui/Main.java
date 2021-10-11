package ui;

import model.Customer;
import model.Transaction;

public class Main {
    public static void main(String[] args) {
        Customer khang = new Customer("Khang", 1310, 5000);
        Transaction transaction1 = new Transaction("Buy Mcdonald", 10, "Consumption");
        Transaction transaction2 = new Transaction("Buy textbook", 200, "Investment");
        System.out.println(khang.getBalance());
        khang.makePurchase(transaction1);
        khang.makePurchase(transaction2);
        System.out.println(khang.getBalance());
    }
}
