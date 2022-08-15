package biz.piwowarczyk.untappd.api.scraper.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class PageToDateTimeUtil {

    public String convert(String pageDateTime) {

        // Wed, 20 Jul 2022 17:58:17 +0000
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.parse(pageDateTime, formatter);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(dateTimeFormatter);
    }
}
