package biz.piwowarczyk.untappd.api;

import biz.piwowarczyk.untappd.api.model.Response;
import biz.piwowarczyk.untappd.api.scraper.UntappdConfig;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {WireMockInitializer.class})
public class UntappdBeerIT {

    @Autowired
    private UntappdConfig untappdConfig;
    @Autowired
    private WireMockServer wireMockServer;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private String beerId = "1499569";
    private String uri;
    private String wireMockedUrl;

    @BeforeEach
    public void setup() {

        uri = new StringBuffer()
                .append("http://localhost:")
                .append(port)
                .append("/beer?id=")
                .append(beerId)
                .toString();

        wireMockedUrl = new StringBuffer()
                .append("/")
                .append(untappdConfig.getBeerPrefix())
                .append("anyName/")
                .append(beerId)
                .toString();
    }


    @Test
    void beerApiShouldReturnBeerStats() {

        // given
        wireMockServer.stubFor(
                WireMock.get(wireMockedUrl)
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_XHTML_XML_VALUE)
                                .withBodyFile("beer.html")
                        ));

        // when
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
        wireMockServer.stubFor(
                WireMock.get(wireMockedUrl)
                        .willReturn(aResponse()
                                .withStatus(404)
                        ));

        // when
        ResponseEntity<Response> venueResponse = this.restTemplate.getForEntity(uri, Response.class);

        // then
        assertThat(venueResponse).isNotNull();
        assertThat(venueResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(venueResponse.getBody().response()).isNull();
        assertThat(venueResponse.getBody().error()).isNotNull();
    }

    @Test
    void beerApiShouldReturn500() {

        // given
        wireMockServer.stubFor(
                WireMock.get(wireMockedUrl)
                        .willReturn(aResponse()
                                .withStatus(500)
                        ));

        // when
        ResponseEntity<Response> venueResponse = this.restTemplate.getForEntity(uri, Response.class);

        // then
        assertThat(venueResponse).isNotNull();
        assertThat(venueResponse.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(venueResponse.getBody().response()).isNull();
        assertThat(venueResponse.getBody().error()).isNotNull();
    }
}
