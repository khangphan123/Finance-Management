package model;

import persistence.Writeable;
import org.json.JSONObject;

public class Stock implements Writeable {
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("abbreviation", abbreviation);
        json.put("price", price);
        json.put("rate", rate);
        return json;
    }
}
