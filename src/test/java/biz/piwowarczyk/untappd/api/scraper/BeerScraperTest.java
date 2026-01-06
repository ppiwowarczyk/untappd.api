package biz.piwowarczyk.untappd.api.scraper;

import biz.piwowarczyk.untappd.api.scraper.params.BeerQueryParams;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BeerScraperTest {

    private void injectUntappdConfig(BeerScraper scraper) throws Exception {
        var mockConfig = Mockito.mock(UntappdConfig.class);
        Mockito.when(mockConfig.getBeerPrefix()).thenReturn("/beer/");
        Mockito.when(mockConfig.getUrl()).thenReturn("http://example.com/");
        Field f = BeerScraper.class.getDeclaredField("untappdConfig");
        f.setAccessible(true);
        f.set(scraper, mockConfig);
    }

    @Test
    void getUrlQueryParams_buildsWithId() throws Exception {
        BeerScraper scraper = new BeerScraper();
        injectUntappdConfig(scraper);

        String params = scraper.getUrlQueryParams(new BeerQueryParams("123"));
        assertTrue(params.contains("123"));
    }

    @Test
    void provideStaticDocument_returnsEmpty() {
        BeerScraper scraper = new BeerScraper();
        Optional<?> provided = scraper.provideStaticDocument(new BeerQueryParams("1"));
        assertEquals(Optional.empty(), provided);
    }
}
