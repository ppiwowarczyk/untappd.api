package biz.piwowarczyk.untappd.api.controller.utils;

import biz.piwowarczyk.untappd.api.model.Response;
import biz.piwowarczyk.untappd.api.model.Venue;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExceptionHandlerEngineTest {

    @Test
    void globalExceptionHandler_returnsInternalServerErrorAndResponseBody() {
        ExceptionHandlerEngine engine = new ExceptionHandlerEngine();
        Exception ex = new RuntimeException("fail message");
        WebRequest webRequest = mock(WebRequest.class);

        ResponseEntity<Response<Venue>> response = engine.globalExceptionHandler(ex, webRequest);

        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().error());
        assertTrue(response.getBody().error().message().contains("fail message"));
    }
}
