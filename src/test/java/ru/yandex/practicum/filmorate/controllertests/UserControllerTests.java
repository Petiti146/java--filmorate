package ru.yandex.practicum.filmorate.controllertests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTests {

    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController();
    }

    @Test
    public void testAddUser() {
        User newUser = new User("test@example.com", "testuser", "Test User", LocalDate.of(1990, 1, 1));
        User addedUser = userController.addUser(newUser);

        assertEquals(newUser, addedUser);
        List<User> users = userController.getUsers();
        assertEquals(1, users.size());
    }

    @Test
    public void testAddUserValidation() {
        User invalidUser = new User(null, "testuser", "Test User", LocalDate.of(1990, 1, 1));

        assertThrows(ValidationException.class, () -> userController.addUser(invalidUser));
    }

    @Test
    public void testUpdateUser() {
        User newUser = new User("test@example.com", "testUser", "Test User", LocalDate.of(1990, 1, 1));
        userController.addUser(newUser);

        User updatedUser = new User("test2@example.com", "updatedUser", "Updated User", LocalDate.of(1990, 1, 1));
        updatedUser.setId(1L);
        User updatedUserResult = userController.updateUser(updatedUser);

        assertEquals(updatedUser, updatedUserResult);
        assertNotEquals("testuser", updatedUserResult.getLogin());
        assertEquals("Updated User", updatedUserResult.getName());
    }

    @Test
    public void testUpdateUserValidation() {
        User invalidUser = new User(null, "testuser", "Test User", LocalDate.of(1990, 1, 1));

        assertThrows(ValidationException.class, () -> userController.updateUser(invalidUser));
    }
}
