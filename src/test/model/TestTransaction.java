package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransaction {
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction("Buy a pair of shoes", 100, "money out");
    }

    @Test
    public void testConstructor() {
        assertEquals(transaction.getName(), "Buy a pair of shoes");
        assertTrue(transaction.getPrice() == 100);
        assertFalse(transaction.getType() == "money in");
    }

}
