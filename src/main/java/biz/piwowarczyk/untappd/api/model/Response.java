package biz.piwowarczyk.untappd.api.model;

public record Response<T>(T response, Error error) {
}
