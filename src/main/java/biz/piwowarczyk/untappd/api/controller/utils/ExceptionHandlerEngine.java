package biz.piwowarczyk.untappd.api.controller.utils;

import biz.piwowarczyk.untappd.api.model.Error;
import biz.piwowarczyk.untappd.api.model.Response;
import biz.piwowarczyk.untappd.api.model.Venue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlerEngine {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Venue>> globalExceptionHandler(Exception ex, WebRequest request) {
        Error message = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        Response<Venue> objectResponse = new Response<>(null, message);
        return new ResponseEntity<>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
