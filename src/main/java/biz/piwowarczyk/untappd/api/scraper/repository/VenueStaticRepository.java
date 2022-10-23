package biz.piwowarczyk.untappd.api.scraper.repository;

import biz.piwowarczyk.untappd.api.scraper.params.VenueQueryParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

@Component
public class VenueStaticRepository implements Function<VenueQueryParams, Optional<Document>> {

    @Override
    public Optional<Document> apply(VenueQueryParams venueQueryParams) {

        if (venueQueryParams.id() == "9450338") {
            File input = new File("C:/Dev/untappd.api/src/main/resources/venue.html");

            try {
                Document doc = Jsoup.parse(input, "UTF-8", "https://untappd.com/");
                return Optional.of(doc);
            } catch (IOException e) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }
}
