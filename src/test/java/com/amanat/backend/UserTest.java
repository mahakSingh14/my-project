package com.amanat.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void testUserCreation() {
        User user = new User();
        user.setName("Mahak");
        user.setEmail("mahak@gmail.com");

        assertEquals("Mahak", user.getName());
        assertEquals("mahak@gmail.com", user.getEmail());
    }
}
