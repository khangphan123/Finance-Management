package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCustomer {
    private Customer Khang;
    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Transaction transaction4;
    private Stock Tesla;
    private Stock Amazon;
    private Stock Apple;
    private Stock Google;
    private Stock Facebook;

    @BeforeEach
    public void setUp() {
        Khang = new Customer("Khang", 12345, 100000);
        transaction1 = new Transaction("Buy a car", 50000, "Consumption");
        transaction2 = new Transaction("Buy a pair of shoes", 100, "Consumption");
        transaction3 = new Transaction("Buy a rocket", 99999999, "Consumption");
        transaction4 = new Transaction("Paying for car ticket", 100, "Fine");
        Tesla = new Stock("Tesla", "TSLA", 810.47, 0.02);
        Amazon = new Stock("Amazon", "AMZN", 3282.88, 0.018);
        Apple = new Stock("Apple", "AAPL", 40.51, -0.01);
        Google = new Stock("Google", "GOOGL", 2751.17, 0.008);
        Facebook = new Stock("Facebook", "FB", 324.72, 0.002);

    }


    @Test
    public void testConstructor() {
        assertEquals(Khang.getName(), "Khang");
        assertFalse(Khang.getAccountNumber() == 321331232);
        assertTrue(Khang.getBalance() == 100000);
        assertFalse(Khang.getTransaction() == null);
    }

    @Test
    public void testDeposit() {
        Khang.deposit(5000);
        assertEquals(Khang.getBalance(), 105000);
        Khang.deposit(1);
        assertFalse(Khang.getBalance() == 200000);
    }

    @Test
    public void testWithdraw() {
        Khang.withDraw(10000);
        assertEquals(Khang.getBalance(), 90000);
        Khang.withDraw(200);
        assertFalse(Khang.getBalance() == 500000);
        assertFalse(Khang.withDraw(200010));
    }

    @Test
    public void testMakePurchase() {
        Khang.makePurchase(transaction1);
        assertEquals(Khang.getBalance(), 50000);
        assertFalse(Khang.makePurchase(transaction3));
    }

    @Test
    public void testContainTransaction() {
        Khang.makePurchase(transaction1);
        Khang.makePurchase(transaction2);
        Khang.makePurchase(transaction4);
        assertTrue(Khang.containTransaction(transaction1));
        assertFalse(Khang.containTransaction(transaction3));
    }

    @Test
    public void testCancelTransaction() {
        Khang.makePurchase(transaction1);
        Khang.makePurchase(transaction2);
        Khang.makePurchase(transaction4);
        Khang.cancelTransaction("Buy a car", 50000, "Consumption");
        assertFalse(Khang.containTransaction(transaction1));
        assertEquals(Khang.getBalance(), 99800);
        assertFalse(Khang.cancelTransaction("Buy a candy", 1, "Consumption"));


    }

    @Test
    public void testInvestMoney() {
        assertEquals(Khang.investMoney(1), 105000);
    }

    @Test
    public void testContainStock() {
        Khang.buyStock(Tesla);
        assertTrue(Khang.containStock(Tesla));
        Khang.buyStock(Facebook);
        assertTrue(Khang.containStock(Facebook));
    }

    @Test
    public void testBuyStock() {
        assertTrue(Khang.buyStock(Tesla));
        Khang.buyStock(Tesla);
        Khang.buyStock(Google);
        assertEquals(Khang.getBalance(), 95627.89);
        assertTrue(Khang.containStock(Tesla));
        assertTrue(Khang.containStock(Google));
        assertFalse(Khang.containStock(Facebook));
        Khang.withDraw(95000);
        assertFalse(Khang.buyStock(Amazon));
    }

    @Test
    public void testSellStock() {
        Khang.buyStock(Tesla);
        Khang.buyStock(Google);
        assertTrue(Khang.sellStock(Tesla));
        assertFalse(Khang.sellStock(Facebook));
        Khang.sellStock(Tesla);
        Khang.sellStock(Google);
        assertEquals(Khang.getBalance(), 100000);

    }
}