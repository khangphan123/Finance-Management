package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestStock {
    private Stock Google;

    @BeforeEach
    public void setUp() {
        Google = new Stock("Google", "GOOGL", 2751.17, 0.008);
    }

    @Test
    public void testConstructor() {
        String GoogleName = Google.getName();
        String GoogleAbbreviation = Google.getAbbreviation();
        double GoogleGrowthRate = Google.getRate();
        assertEquals(GoogleName, "Google");
        assertTrue(GoogleAbbreviation == "GOOGL");
        assertEquals(GoogleGrowthRate, 0.008);
        assertEquals(Google.getPrice(), 2751.17);
        assertEquals(Google.getRate(), 0.008);
    }

}
