package biz.piwowarczyk.untappd.api.scraper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "untappd")
public class UntappdConfig {

    private String url;
    private String beerPrefix;
    private String venuePrefix;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBeerPrefix() {
        return beerPrefix;
    }

    public void setBeerPrefix(String beerPrefix) {
        this.beerPrefix = beerPrefix;
    }

    public String getVenuePrefix() {
        return venuePrefix;
    }

    public void setVenuePrefix(String venuePrefix) {
        this.venuePrefix = venuePrefix;
    }
}
