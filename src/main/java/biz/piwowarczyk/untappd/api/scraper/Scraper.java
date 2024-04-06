package biz.piwowarczyk.untappd.api.scraper;

import biz.piwowarczyk.untappd.api.scraper.error.ScraperError;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public abstract class Scraper<T, V> {

    @Autowired
    private UntappdConfig untappdConfig;

    public Either<ScraperError, Optional<V>> process(T queryParams) {

        String url = new StringBuffer()
                .append(untappdConfig.getUrl())
                .append(getTypeUrl())
                .append(getUrlQueryParams(queryParams))
                .toString();

            Either<Throwable, Document> documents = Try.of(() -> provideStaticDocument(queryParams).isPresent() ? provideStaticDocument(queryParams).get() : Jsoup.connect(url).get())
                    .toEither();

            if (documents.isLeft())
            {
                return switch (documents.getLeft()) {
                    case HttpStatusException httpStatusException ->
                            httpStatusException.getStatusCode() == NOT_FOUND.value() ?
                                    Either.right(Optional.empty())  : Either.left(new ScraperError(documents.getLeft().toString()));
                    default -> Either.left(new ScraperError(documents.getLeft().toString()));
                };
            }
            else
            {
                return processWithEngine(documents.get());
            }
    }

    abstract String getTypeUrl();

    abstract String getUrlQueryParams(T queryParams);

    abstract Either<ScraperError, Optional<V>> processWithEngine(Document document);

    abstract Optional<Document> provideStaticDocument(T queryParams);
}
