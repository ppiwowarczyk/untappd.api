package biz.piwowarczyk.untappd.api.scraper.params;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VenueQueryParamsTest {

    @Test
    void recordExposesId() {
        VenueQueryParams p = new VenueQueryParams("123");
        assertEquals("123", p.id());
    }
}
