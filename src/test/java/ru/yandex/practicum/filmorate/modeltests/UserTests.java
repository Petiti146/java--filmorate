package ru.yandex.practicum.filmorate.modeltests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {

    @Test
    public void testUserConstructor() {
        User user = new User("test@example.com", "testuser", "Test User", LocalDate.of(1990, 1, 1));

        assertEquals("test@example.com", user.getEmail());
        assertEquals("testuser", user.getLogin());
        assertEquals("Test User", user.getName());
        assertEquals(LocalDate.of(1990, 1, 1), user.getBirthday());
    }
}
