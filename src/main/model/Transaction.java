package model;

import org.json.JSONObject;
import persistence.Writeable;

public class Transaction implements Writeable {
    private String name;
    private double price;
    private String type;

    //EFFECTS: Initialize Transaction with name, price, and type;
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        json.put("type", type);
        return json;
    }
}
