package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Customer;
import model.Stock;
import model.Transaction;
import org.json.*;
// Reads Customer from JSON date stored in file

public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public Customer read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCustomer(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private Customer parseCustomer(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double balance = jsonObject.getDouble("balance");
        int accountNumber = jsonObject.getInt("account number");
        Customer cst = new Customer(name, accountNumber, balance);
        addTransactions(cst, jsonObject);
        addStocks(cst, jsonObject);
        return cst;
    }

    //MODIFIES: cst
    //EFFECTS: parses transaction from JSON objects and
    private void addTransactions(Customer cst, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(cst, nextTransaction);
        }
    }

    //EFFECTS: parses Transaction from JSON Object and make a purchase of the treansaciton
    //. In this case, make Purchase will add Transaction to customer.
    private void addTransaction(Customer cst, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        String type = jsonObject.getString("type");
        Transaction transaction = new Transaction(name, price, type);
        cst.makePurchase(transaction);
    }

    //MODIFIES: cst
    //EFFECTS: parses stocks from JSON objects
    private void addStocks(Customer cst, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("stocks");
        for (Object json : jsonArray) {
            JSONObject nextStock = (JSONObject) json;
            addStock(cst, nextStock);
        }
    }

    //EFFECTS: parses stock from JSON Object and make a purchase of the stock
    //. In this case, buyStock will add stock to customer.
    private void addStock(Customer cst, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        String abbreviation = jsonObject.getString("abbreviation");

        double type = jsonObject.getDouble("rate");
        Stock stock = new Stock(name, abbreviation, price, type);
        cst.buyStock(stock);
    }
}
