package biz.piwowarczyk.untappd.api;

import biz.piwowarczyk.untappd.api.controller.VenueController;
import biz.piwowarczyk.untappd.api.model.Response;
import biz.piwowarczyk.untappd.api.model.Venue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UntappdApplicationTests {

    @Autowired
    private VenueController venueController;

    @Test
    void testVenueControllerShouldReturnVenueStats() {

        // given
        String venueId = "4955627";

        // when
        ResponseEntity<Response<Venue>> response = venueController.venue(venueId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().response()).isNotNull();
        assertThat(response.getBody().error()).isNull();
    }
}
