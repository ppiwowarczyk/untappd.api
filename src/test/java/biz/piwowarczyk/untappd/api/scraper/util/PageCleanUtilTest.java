package biz.piwowarczyk.untappd.api.scraper.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageCleanUtilTest {

    private final PageCleanUtil util = new PageCleanUtil();

    @Test
    void parseIdFromUrl_trimsAndReturnsId() {
        String url = "https://untappd.com/beer/12345/";
        String id = util.parseIdFromUrl(url);
        assertEquals("12345", id);
    }

    @Test
    void parseIdFromUrl_whenNoTrailingSlash_returnsLastSegment() {
        String url = "https://untappd.com/beer/abc-xyz";
        String id = util.parseIdFromUrl(url);
        assertEquals("abc-xyz", id);
    }

    @Test
    void parseAbv_removesPercentAbvSuffix() {
        String abv = "5% ABV";
        assertEquals("5", util.parseAbv(abv));
    }

    @Test
    void parseAbv_handlesEmptyString() {
        String abv = "";
        assertEquals("", util.parseAbv(abv));
    }
}
