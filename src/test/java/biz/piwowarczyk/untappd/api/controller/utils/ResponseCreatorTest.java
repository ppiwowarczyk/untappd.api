package biz.piwowarczyk.untappd.api.controller.utils;

import biz.piwowarczyk.untappd.api.model.Response;
import biz.piwowarczyk.untappd.api.scraper.error.ScraperError;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ResponseCreatorTest {

    private final ResponseCreator<String> responseCreator = new ResponseCreator<>();

    @Test
    public void shouldReturnInternalServerErrorResponseWhenErrorOccurs() {

        // given
        Either<Optional<String>, ScraperError> result = Either.right(new ScraperError("test error"));

        // when
        ResponseEntity<Response<String>> responseEntity = responseCreator.apply(result);

        // then
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertNull(responseEntity.getBody().response());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getBody().error().httpStatus());
        Assertions.assertEquals("test error", responseEntity.getBody().error().description());
    }

    @Test
    public void shouldReturnNotFoundResponseWhenResultIsEmpty() {

        // given
        Either<Optional<String>, ScraperError> result = Either.left(Optional.empty());

        // when
        ResponseEntity<Response<String>> responseEntity = responseCreator.apply(result);

        // then
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertNull(responseEntity.getBody().response());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getBody().error().httpStatus());
        Assertions.assertNull(responseEntity.getBody().error().description());
    }

    @Test
    public void shouldReturnOkResponseWithResult() {

        // given
        Either<Optional<String>, ScraperError> result = Either.left(Optional.of("test result"));

        // when
        ResponseEntity<Response<String>> responseEntity = responseCreator.apply(result);

        // then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("test result", responseEntity.getBody().response());
        Assertions.assertNull(responseEntity.getBody().error());
    }
}