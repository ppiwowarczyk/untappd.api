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
public class UntappdBeerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void beerApiShouldReturnBeerStats() {

        // given
        String beerId = "1499569";

        // when
        String uri = new StringBuffer()
                .append("http://localhost:")
                .append(port)
                .append("/beer?id=")
                .append(beerId)
                .toString();


        ResponseEntity<Response> venueResponse = this.restTemplate.getForEntity(uri, Response.class);

        // then
        assertThat(venueResponse).isNotNull();
        assertThat(venueResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(venueResponse.getBody().response()).isNotNull();
        assertThat(venueResponse.getBody().error()).isNull();
    }

    @Test
    void beerApiShouldReturn404() {

        // given
        String beerId = "aaa";

        // when
        String uri = new StringBuffer()
                .append("http://localhost:")
                .append(port)
                .append("/beer?id=")
                .append(beerId)
                .toString();


        ResponseEntity<Response> venueResponse = this.restTemplate.getForEntity(uri, Response.class);

        // then
        assertThat(venueResponse).isNotNull();
        assertThat(venueResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(venueResponse.getBody().response()).isNull();
        assertThat(venueResponse.getBody().error()).isNotNull();
    }
}
