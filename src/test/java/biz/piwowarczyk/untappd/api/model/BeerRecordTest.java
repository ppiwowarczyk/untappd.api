package biz.piwowarczyk.untappd.api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeerRecordTest {

    @Test
    void beerRecordAccessors() {
        Brewery brewery = new Brewery("bId", "bName", "bCity", "bCountry", "");
        BeerRating rating = new BeerRating("score", "count");
        Beer beer = new Beer("id", "name", "style", "5% ABV", brewery, rating, "img");
        assertEquals("id", beer.id());
        assertEquals("name", beer.name());
        assertEquals("style", beer.style());
        assertEquals("5% ABV", beer.abv());
        assertSame(brewery, beer.brewery());
        assertSame(rating, beer.beerRaiting());
        assertEquals("img", beer.img());
    }
}
