package persistence;

import model.Customer;
import model.Stock;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
//Note: I replicate this code from "JsonSerializationDemo" that is provided.

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Customer cst = new Customer("Jess", 123, 5000);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {

        }
    }

    @Test
    void testWriterCustomerEmptyTransactionOneStock() {
        try {
            Stock tesla = new Stock("tesla", "TSLA", 500, 0.05);
            Customer cst = new Customer("Khang", 1310, 5300);
            cst.buyStock(tesla);
            JsonWriter writer = new JsonWriter("./data/testWriterCustomerEmptyTransactionOneStock");
            writer.open();
            writer.write(cst);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterCustomerEmptyTransactionOneStock");
            cst = reader.read();
            assertEquals("Khang", cst.getName());
            assertEquals(1310, cst.getAccountNumber());
            assertEquals(4800, cst.getBalance());
            assertEquals(cst.getStockPortfolios().size(), 1);
            assertEquals(cst.getTransaction().size(), 0);
        } catch (IOException e) {
            fail("Exception expected");
        }
    }


    @Test
    void testWriterCustomerManyTransactionEmptyStock() {
        try {
            Transaction transaction1 = new Transaction("nike shoes", 200, "consumption");
            Customer cst = new Customer("Khang", 1310, 4500);
            cst.makePurchase(transaction1);
            cst.deposit(500);
            JsonWriter writer = new JsonWriter("./data/testWriterCustomerManyTransactionEmptyStock");
            writer.open();
            writer.write(cst);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterCustomerManyTransactionEmptyStock");
            cst = reader.read();
            assertEquals("Khang", cst.getName());
            assertEquals(1310, cst.getAccountNumber());
            assertEquals(4800, cst.getBalance());
            assertEquals(cst.getStockPortfolios().size(), 0);
            assertEquals(cst.getTransaction().size(), 2);
        } catch (IOException e) {
            fail("Exception expected");
        }
    }
}
