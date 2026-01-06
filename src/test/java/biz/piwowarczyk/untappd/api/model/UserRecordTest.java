package biz.piwowarczyk.untappd.api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRecordTest {

    @Test
    void userRecordAccessors() {
        User u = new User("u1", "name", "avatar");
        assertEquals("u1", u.id());
        assertEquals("name", u.name());
        assertEquals("avatar", u.avatar());
    }
}
