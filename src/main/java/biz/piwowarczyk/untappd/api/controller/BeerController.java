package biz.piwowarczyk.untappd.api.controller;

import biz.piwowarczyk.untappd.api.controller.utils.ResponseCreator;
import biz.piwowarczyk.untappd.api.model.Beer;
import biz.piwowarczyk.untappd.api.model.Response;
import biz.piwowarczyk.untappd.api.scraper.BeerScraper;
import biz.piwowarczyk.untappd.api.scraper.params.BeerQueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeerController {

    @Autowired
    private BeerScraper beerScraper;

    @Autowired
    private ResponseCreator<Beer> responseCreator;

    @RequestMapping("/beer")
    @ExceptionHandler
    public ResponseEntity<Response<Beer>> beer(@RequestParam(value = "id") String beerId) {

        BeerQueryParams beerQueryParams = new BeerQueryParams(beerId);
        var result = beerScraper.process(beerQueryParams);
        return null;
        //return responseCreator.apply(result);
    }
}
