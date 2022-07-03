package biz.piwowarczyk.untappd.api.scraper;

import biz.piwowarczyk.untappd.api.scraper.error.ScraperError;
import io.vavr.control.Either;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Optional;

public abstract class Scraper<T, V> {

    private final String UNTAPPD_URL = "https://untappd.com/";

    public Either<Optional<V>, ScraperError> process(T queryParams) {

        String url = new StringBuffer()
                .append(UNTAPPD_URL)
                .append(getTypeUrl())
                .append(getUrlQueryParams(queryParams))
                .toString();

        try {
            return processWithEngine(Jsoup.connect(url).get());

        } catch (IOException e) {
            return switch (e) {
                case HttpStatusException httpStatusException -> Either.left(Optional.empty());
                default -> Either.right(new ScraperError(e.toString()));
            };
        }
    }

    abstract String getTypeUrl();

    abstract String getUrlQueryParams(T queryParams);

    abstract Either<Optional<V>, ScraperError> processWithEngine(Document document);
}
