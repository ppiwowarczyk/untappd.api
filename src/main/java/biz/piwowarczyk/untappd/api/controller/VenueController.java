package biz.piwowarczyk.untappd.api.controller;

import biz.piwowarczyk.untappd.api.model.Venue;
import biz.piwowarczyk.untappd.api.controller.utils.ResponseCreator;
import biz.piwowarczyk.untappd.api.model.Response;
import biz.piwowarczyk.untappd.api.scraper.VenueScraper;
import biz.piwowarczyk.untappd.api.scraper.params.VenueQueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VenueController {

    @Autowired
    private VenueScraper venueScraper;

    @Autowired
    private ResponseCreator<Venue> responseCreator;

    @RequestMapping("/venue")
    public ResponseEntity<Response<Venue>> venue(@RequestParam(value = "id") String venueId) {
        VenueQueryParams venueQueryParams = new VenueQueryParams(venueId);
        var result = venueScraper.process(venueQueryParams);
        return responseCreator.apply(result);
    }
}
