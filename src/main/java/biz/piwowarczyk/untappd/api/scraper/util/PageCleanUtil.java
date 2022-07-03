package biz.piwowarczyk.untappd.api.scraper.util;

import org.springframework.stereotype.Component;

@Component
public class PageCleanUtil {

    public String parseIdFromUrl(String beerIdFromUrl) {
        return beerIdFromUrl.substring(beerIdFromUrl. lastIndexOf('/')).replace("/", "");
    }

    public String parseAbv(String abv) {
        return abv.replace("% ABV", "");
    }
}
