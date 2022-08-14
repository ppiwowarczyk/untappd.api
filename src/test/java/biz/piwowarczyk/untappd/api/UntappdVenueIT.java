package biz.piwowarczyk.untappd.api;

import biz.piwowarczyk.untappd.api.model.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UntappdVenueIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void venueApiShouldReturnVenueStats() {

        // given
        String venueId = "9450338";

        // when
        String uri = new StringBuffer()
                .append("http://localhost:")
                .append(port)
                .append("/venue?id=")
                .append(venueId)
                .toString();


        ResponseEntity<Response> venueResponse = this.restTemplate.getForEntity(uri, Response.class);

        // then
        assertThat(venueResponse).isNotNull();
        assertThat(venueResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(venueResponse.getBody().response()).isNotNull();
        assertThat(venueResponse.getBody().error()).isNull();
    }

    @Test
    void venueApiShouldReturn404() {

        // given
        String venueId = "aaa";

        // when
        String uri = new StringBuffer()
                .append("http://localhost:")
                .append(port)
                .append("/venue?id=")
                .append(venueId)
                .toString();


        ResponseEntity<Response> venueResponse = this.restTemplate.getForEntity(uri, Response.class);

        // then
        assertThat(venueResponse).isNotNull();
        assertThat(venueResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(venueResponse.getBody().response()).isNull();
        assertThat(venueResponse.getBody().error()).isNotNull();
    }
}
