package biz.piwowarczyk.untappd.api.scraper;

import biz.piwowarczyk.untappd.api.model.CheckIn;
import biz.piwowarczyk.untappd.api.model.Venue;
import biz.piwowarczyk.untappd.api.scraper.error.ScraperError;
import biz.piwowarczyk.untappd.api.scraper.pageObject.VenuePageObject;
import biz.piwowarczyk.untappd.api.scraper.params.VenueQueryParams;
import biz.piwowarczyk.untappd.api.scraper.util.PageCleanUtil;
import biz.piwowarczyk.untappd.api.model.User;
import biz.piwowarczyk.untappd.api.scraper.util.PageToDateTimeUtil;
import io.vavr.control.Either;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toUnmodifiableList;

@Component
public class VenueScraper extends Scraper<VenueQueryParams, Venue> {

    @Autowired
    private UntappdConfig untappdConfig;

    @Autowired
    private PageCleanUtil pageCleanUtil;

    @Autowired
    private PageToDateTimeUtil pageToDateTimeUtil;

    @Override
    String getTypeUrl() {
        return untappdConfig.getVenuePrefix();
    }

    @Override
    String getUrlQueryParams(VenueQueryParams queryParams) {

        return new StringBuilder()
                .append("anyName")
                .append("/")
                .append(queryParams.id())
                .toString();
    }

    @Override
    Either<Optional<Venue>, ScraperError> processWithEngine(Document document) {

        VenuePageObject venuePageObject = new VenuePageObject(document);

        List<CheckIn> checkInList = venuePageObject.getCheckInLElementList()
                .stream()
                .map(venuePageObject::mapToCheckIn)
                .map(this::cleanCheckIn)
                .collect(toUnmodifiableList());


        return Either.left(
                Optional.of(
                        new Venue(
                                venuePageObject.getTitle(),
                                checkInList)
                )
        );
    }

    private CheckIn cleanCheckIn(CheckIn checkIn) {

        String userId = pageCleanUtil.parseIdFromUrl(checkIn.user().id());
        String beerId = pageCleanUtil.parseIdFromUrl(checkIn.beerId());
        String breweryId = pageCleanUtil.parseIdFromUrl(checkIn.breweryId());

        String dateTime = pageToDateTimeUtil.convert(checkIn.dateTime());

        User user = new User(userId, checkIn.user().name(), checkIn.user().avatar());
        return new CheckIn(checkIn.id(), dateTime, checkIn.rating(), beerId, breweryId, user);
    }
}
