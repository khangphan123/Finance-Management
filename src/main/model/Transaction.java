package model;

public class Transaction {
    private String name;
    private double price;
    private String type;


    public Transaction(String name, double price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }


    public double getPrice() {
        return this.price;
    }

    public String getType() {
        return this.type;
    }
}
