package biz.piwowarczyk.untappd.api.model;

public record BeerRating(String totalCheckIns,
                         String monthlyCheckIns,
                         String uniqueCheckIns,
                         String rating) {
}
