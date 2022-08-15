package biz.piwowarczyk.untappd.api.model;

public record CheckIn(String id, String dateTime, String rating, String beerId, String breweryId, User user) {
}

