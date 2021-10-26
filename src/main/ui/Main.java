package ui;

import model.Customer;
import model.Transaction;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new FinanceApp();
        } catch (FileNotFoundException e) {
            System.out.println("application not found");
        }
    }
}
