package biz.piwowarczyk.untappd.api.scraper;

import biz.piwowarczyk.untappd.api.model.Beer;
import biz.piwowarczyk.untappd.api.model.Brewery;
import biz.piwowarczyk.untappd.api.scraper.error.ScraperError;
import biz.piwowarczyk.untappd.api.scraper.pageObject.BeerPageObject;
import biz.piwowarczyk.untappd.api.scraper.params.BeerQueryParams;
import biz.piwowarczyk.untappd.api.model.BeerRating;
import biz.piwowarczyk.untappd.api.scraper.util.PageCleanUtil;
import io.vavr.control.Either;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BeerScraper extends Scraper<BeerQueryParams, Beer> {

    @Autowired
    private UntappdConfig untappdConfig;

    @Autowired
    private PageCleanUtil pageCleanUtil;

    @Override
    String getTypeUrl() {
        return untappdConfig.getBeerPrefix();
    }

    @Override
    String getUrlQueryParams(BeerQueryParams queryParams) {

        return new StringBuilder()
                .append("anyName")
                .append("/")
                .append(queryParams.id())
                .toString();
    }

    @Override
    Either<Optional<Beer>, ScraperError> processWithEngine(Document document) {

        BeerPageObject beerPageObject = new BeerPageObject(document);

        String beerIdFromUrl = document.baseUri();
        Beer basicBeer = beerPageObject.getBeer();
        Brewery brewery = beerPageObject.getBrewery();
        BeerRating beerRating = beerPageObject.getBeerRating();

        return Either.left(
                Optional.of(
                        new Beer(
                                pageCleanUtil.parseIdFromUrl(beerIdFromUrl),
                                basicBeer.name(),
                                basicBeer.style(),
                                pageCleanUtil.parseAbv(basicBeer.abv()),
                                brewery,
                                beerRating,
                                basicBeer.img()
                        )
                ));
    }

    @Override
    Optional<Document> provideStaticDocument(BeerQueryParams queryParams) {
        return Optional.empty();
    }
}
