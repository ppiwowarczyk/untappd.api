package biz.piwowarczyk.untappd.api.scraper.util;

import org.springframework.stereotype.Component;

@Component
public class PageCleanUtil {

    public String parseIdFromUrl(String beerIdFromUrl) {
        if (beerIdFromUrl.lastIndexOf('/') == (beerIdFromUrl.length() - 1)) {
            beerIdFromUrl = beerIdFromUrl.substring(0, beerIdFromUrl.length() - 1);
        }
        return beerIdFromUrl.substring(beerIdFromUrl.lastIndexOf('/')  + 1 );
    }

    public String parseAbv(String abv) {
        return abv.replace("% ABV", "");
    }
}
