package biz.piwowarczyk.untappd.api.model;

public record Beer(
        String id,
        String name,
        String style,
        String abv,
        Brewery brewery,
        BeerRating beerRaiting
        ) {
}
