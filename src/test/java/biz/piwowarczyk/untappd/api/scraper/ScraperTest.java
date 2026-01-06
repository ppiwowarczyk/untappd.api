package biz.piwowarczyk.untappd.api.scraper;

import io.vavr.control.Either;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ScraperTest {

    static class TestScraper extends Scraper<String, String> {

        private final boolean throwOnProvide;
        private final Optional<Document> toProvide;
        private final Either<biz.piwowarczyk.untappd.api.scraper.error.ScraperError, Optional<String>> engineResult;

        TestScraper(boolean throwOnProvide, Optional<Document> toProvide,
                    Either<biz.piwowarczyk.untappd.api.scraper.error.ScraperError, Optional<String>> engineResult) {
            this.throwOnProvide = throwOnProvide;
            this.toProvide = toProvide;
            this.engineResult = engineResult;
        }

        @Override
        String getTypeUrl() {
            return "/type/";
        }

        @Override
        String getUrlQueryParams(String queryParams) {
            return queryParams;
        }

        @Override
        Either<biz.piwowarczyk.untappd.api.scraper.error.ScraperError, Optional<String>> processWithEngine(Document document) {
            return engineResult;
        }

        @Override
        Optional<Document> provideStaticDocument(String queryParams) {
            if (throwOnProvide) {
                throw new RuntimeException("provide static document failure");
            }
            return toProvide;
        }
    }

    private void injectUntappdConfig(Scraper<?, ?> scraper) throws Exception {
        var mockConfig = Mockito.mock(UntappdConfig.class);
        Mockito.when(mockConfig.getUrl()).thenReturn("http://example.com/");
        // set private field via reflection
        Field f = Scraper.class.getDeclaredField("untappdConfig");
        f.setAccessible(true);
        f.set(scraper, mockConfig);
    }

    @Test
    void process_returnsRightWhenProvideStaticDocumentPresent() throws Exception {
        Document doc = Jsoup.parse("<html></html>", "http://example.com/beer/1");
        var engineResult = Either.right(Optional.of("ok"));
        TestScraper scraper = new TestScraper(false, Optional.of(doc), engineResult);

        injectUntappdConfig(scraper);

        var result = scraper.process("q");
        assertTrue(result.isRight());
        assertTrue(result.get().isPresent());
        assertEquals("ok", result.get().get());
    }

    @Test
    void process_returnsLeftWhenProvideStaticDocumentThrows() throws Exception {
        TestScraper scraper = new TestScraper(true, Optional.empty(), Either.right(Optional.empty()));
        injectUntappdConfig(scraper);

        var result = scraper.process("q");
        assertTrue(result.isLeft());
        assertNotNull(result.getLeft().details());
        assertTrue(result.getLeft().details().contains("provide static document failure"));
    }
}
