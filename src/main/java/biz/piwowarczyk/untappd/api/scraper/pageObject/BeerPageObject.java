package biz.piwowarczyk.untappd.api.scraper.pageObject;

import biz.piwowarczyk.untappd.api.model.Beer;
import biz.piwowarczyk.untappd.api.model.BeerRating;
import biz.piwowarczyk.untappd.api.model.Brewery;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BeerPageObject extends PageObject {

    private final Element nameSection;
    private final Element imgSection;
    private final Element details;
    private final Elements statsSections;

    public BeerPageObject(Document document) {
        super(document);

        nameSection = document
                .select("div.content").get(0)
                .select("div.top").get(0)
                .select("div.basic").get(0)
                .select("div.name").get(0);

        imgSection = document
                        .select("div.content").get(0)
                        .select("div.top").get(0)
                        .select("div.basic").get(0)
                        .select("img").get(0);

        details = document
                .select("div.content").get(0)
                .select("div.details").get(0);

        statsSections = document
                .select("div.content").get(0)
                .select("div.top").get(0)
                .select("div.stats").get(0)
                .select("span.count");
    }

    public Beer getBeer() {

        String beerName = nameSection.select("h1").get(0).text();
        String beerStyle = nameSection.select("p").get(1).text();
        String abv = details.select("p.abv").first().text();
        String img = imgSection.attr("src");

        return new Beer(null, beerName, beerStyle, abv, null, null, img);
    }

    public Brewery getBrewery() {

        String breweryId = nameSection.select("a").first().attr("href");
        String breweryName = nameSection.select("a").first().text();

        return new Brewery(breweryId, breweryName);
    }

    public BeerRating getBeerRating() {

        String totalCheckIns = statsSections.get(0).text();
        String uniqueCheckIns = statsSections.get(1).text();
        String monthlyCheckIns = statsSections.get(2).text();
        String rating = details.select("div.caps").first().attr("data-rating");

        return new BeerRating(totalCheckIns, monthlyCheckIns, uniqueCheckIns, rating);
    }
}
