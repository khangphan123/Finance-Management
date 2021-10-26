package persistence;

import model.Stock;
import model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
//Note: I replicate this code from "JsonSerializationDemo" that is provided.

public class JsonTest {
    protected void checkTransaction(String name, double price, String type, Transaction transaction) {
        assertEquals(name, transaction.getName());
        assertEquals(price, transaction.getPrice());
        assertEquals(type, transaction.getType());
    }

    protected void checkStock(String name, String abbreviation, double price, double currentRate, Stock stock) {
        assertEquals(name, stock.getName());
        assertEquals(abbreviation, stock.getAbbreviation());
        assertEquals(price, stock.getPrice());
        assertEquals(currentRate, stock.getRate());
        ;
    }
}
