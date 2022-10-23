package biz.piwowarczyk.untappd.api.scraper;

import biz.piwowarczyk.untappd.api.scraper.error.ScraperError;
import io.vavr.control.Either;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public abstract class Scraper<T, V> {

    @Autowired
    private UntappdConfig untappdConfig;

    public Either<Optional<V>, ScraperError> process(T queryParams) {

        String url = new StringBuffer()
                .append(untappdConfig.getUrl())
                .append(getTypeUrl())
                .append(getUrlQueryParams(queryParams))
                .toString();

        try {

            Document document = provideStaticDocument(queryParams).isPresent() ? provideStaticDocument(queryParams).get() : Jsoup.connect(url).get();
            return processWithEngine(document);

        } catch (IOException e) {
            return switch (e) {
                case HttpStatusException httpStatusException ->
                        httpStatusException.getStatusCode() == NOT_FOUND.value() ?
                                Either.left(Optional.empty())  : Either.right(new ScraperError(e.toString()));
                default -> Either.right(new ScraperError(e.toString()));
            };
        }
    }

    abstract String getTypeUrl();

    abstract String getUrlQueryParams(T queryParams);

    abstract Either<Optional<V>, ScraperError> processWithEngine(Document document);

    abstract Optional<Document> provideStaticDocument(T queryParams);
}
