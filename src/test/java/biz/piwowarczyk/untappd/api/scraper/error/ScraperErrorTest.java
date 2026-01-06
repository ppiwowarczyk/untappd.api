package biz.piwowarczyk.untappd.api.scraper.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScraperErrorTest {

    @Test
    void recordStoresDetails() {
        ScraperError e = new ScraperError("boom");
        assertEquals("boom", e.details());
    }
}
