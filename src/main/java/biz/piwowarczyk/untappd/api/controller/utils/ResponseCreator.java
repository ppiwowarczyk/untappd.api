package biz.piwowarczyk.untappd.api.controller.utils;

import biz.piwowarczyk.untappd.api.model.Error;
import biz.piwowarczyk.untappd.api.model.Response;
import biz.piwowarczyk.untappd.api.scraper.error.ScraperError;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class ResponseCreator<T> implements Function<Either<Optional<T>, ScraperError>, ResponseEntity<Response<T>>> {
    @Override
    public ResponseEntity<Response<T>> apply(Either<Optional<T>, ScraperError> result) {

        ResponseEntity<Response<T>> returnValue;

        if (result.isRight()) {
            returnValue =  new ResponseEntity<>(new Response<T>(null, new Error(HttpStatus.INTERNAL_SERVER_ERROR, result.get().details())), HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (result.getLeft().isEmpty()) {
            returnValue =  new ResponseEntity<>(new Response<T>(null, new Error(HttpStatus.NOT_FOUND, null)), HttpStatus.NOT_FOUND);
        } else {
            returnValue =  new ResponseEntity<>(new Response<T>(result.getLeft().get(), null), HttpStatus.OK);
        }

        return returnValue;

    }
}
