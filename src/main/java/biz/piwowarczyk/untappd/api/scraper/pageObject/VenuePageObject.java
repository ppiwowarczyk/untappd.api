package biz.piwowarczyk.untappd.api.scraper.pageObject;

import biz.piwowarczyk.untappd.api.model.CheckIn;
import biz.piwowarczyk.untappd.api.model.User;
import biz.piwowarczyk.untappd.api.scraper.jsoupExtension.ElementsWrapper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VenuePageObject extends PageObject {

    public VenuePageObject(Document document) {
        super(document);
    }

    public Elements getCheckInLElementList() {
        return document.select("#main-stream")
                .get(0)
                .select("div.item");
    }

    public CheckIn mapToCheckIn(Element singleElement) {

        String id = singleElement.attr("data-checkin-id");

        String avatarUrl = singleElement
                .select("div.avatar").first()
                .select("img").first()
                .attr("data-original");


        Element topElement = singleElement
                .select("div.checkin")
                .select("div.top").get(0);

        Elements links = topElement
                .select("p.text").first()
                .select("a");

        String userId = getLink(links.get(0)).linkId;
        String userName = getLink(links.get(0)).linkValue;
        String beerId = getLink(links.get(1)).linkId;
        String breweryId = getLink(links.get(2)).linkId;

        String rating = new ElementsWrapper(topElement.select("div.checkin-comment"))
                .getFirst()
                .select("div.rating-serving").getFirst()
                .select("div.caps").getFirst()
                .attr("data-rating");

        String checkInDate = singleElement
                .select("div.checkin")
                .select("div.feedback").get(0)
                .select("div.bottom").get(0)
                .select("a").get(0)
                .text();
                //.attr("data-gregtime");

        User user = new User(userId, userName, avatarUrl);

        return new CheckIn(id, checkInDate, rating, beerId, breweryId, user);
    }

    private LinkObject getLink(Element element) {

        return new LinkObject(
                element.attr("href"),
                element.text()
        );
    }

    private record LinkObject(String linkId, String linkValue) {
    }
}
