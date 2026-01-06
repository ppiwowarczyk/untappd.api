package biz.piwowarczyk.untappd.api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckInRecordTest {

    @Test
    void checkInRecordAccessors() {
        User user = new User("u1", "n", "a");
        CheckIn ci = new CheckIn("cid", "dt", "5", "beerId", "breweryId", user);
        assertEquals("cid", ci.id());
        assertEquals("dt", ci.dateTime());
        assertEquals("5", ci.rating());
        assertEquals("beerId", ci.beerId());
        assertEquals("breweryId", ci.breweryId());
        assertSame(user, ci.user());
    }
}
