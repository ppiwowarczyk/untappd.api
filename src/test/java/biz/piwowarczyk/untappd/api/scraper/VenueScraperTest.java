package biz.piwowarczyk.untappd.api.scraper;

import biz.piwowarczyk.untappd.api.scraper.params.VenueQueryParams;
import biz.piwowarczyk.untappd.api.scraper.repository.VenueStaticRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VenueScraperTest {

    @Test
    void getUrlQueryParams_buildsWithId() throws Exception {
        VenueScraper scraper = new VenueScraper();

        var mockConfig = Mockito.mock(UntappdConfig.class);
        Mockito.when(mockConfig.getVenuePrefix()).thenReturn("/venue/");
        Mockito.when(mockConfig.getUrl()).thenReturn("http://example.com/");
        Field f = VenueScraper.class.getDeclaredField("untappdConfig");
        f.setAccessible(true);
        f.set(scraper, mockConfig);

        String params = scraper.getUrlQueryParams(new VenueQueryParams("v1"));
        assertTrue(params.contains("v1"));
    }

    @Test
    void provideStaticDocument_delegatesToRepository() throws Exception {
        VenueScraper scraper = new VenueScraper();

        var repo = mock(VenueStaticRepository.class);
        when(repo.apply(any())).thenReturn(Optional.of(org.jsoup.Jsoup.parse("<html></html>")));
        Field r = VenueScraper.class.getDeclaredField("venueStaticRepository");
        r.setAccessible(true);
        r.set(scraper, repo);

        Optional<?> provided = scraper.provideStaticDocument(new VenueQueryParams("v1"));
        assertTrue(provided.isPresent());
        verify(repo, times(1)).apply(any());
    }
}
