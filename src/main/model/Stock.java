package model;

public class Stock {
    private String name;
    private String abbreviation;
    private double price;
    private double rate;

    public Stock(String name, String abbreviation, double price, double currentRate) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.price = price;
        this.rate = currentRate;
    }

    public String getName() {
        return this.name;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public double getPrice() {
        return this.price;
    }

    public double getRate() {
        return this.rate;
    }

}
