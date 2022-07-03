package biz.piwowarczyk.untappd.api.model;

public record CheckIn(String id, String rating, String beerId, String breweryId, User user) {
}

