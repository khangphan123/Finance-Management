package persistence;

import model.Customer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/notfoundfile.json");
        try {
            Customer cst = reader.read();
            fail("Exception IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderCustomerEmptyTransactionOneStock() {
        JsonReader reader = new JsonReader("./data/testReaderCustomerEmptyTransaction.json");
        try {
            Customer cst = reader.read();
            assertEquals(cst.getTransaction().size(), 0);
            assertEquals(cst.getStockPortfolios().size(), 1);
        } catch (IOException e) {
            fail("Couldn't read from this file");
        }
    }

    @Test
    void testReaderCustomerManyTransactionEmptyStock() {
        JsonReader reader = new JsonReader("./data/testReaderCustomerManyTransactionEmptyStock.json");
        try {
            Customer cst = reader.read();
            assertEquals(cst.getStockPortfolios().size(), 0);
            assertEquals(cst.getTransaction().size(), 2);

        } catch (IOException e) {
            fail("Couldn't read from this file");
        }
    }
}
