package ru.yandex.practicum.filmorate.controllerstests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTests {

    private FilmController filmController;

    @BeforeEach
    public void setUp() {
        filmController = new FilmController();
    }

    @Test
    public void testAddFilmValidationExceptionNameEmpty() {
        FilmController filmController = new FilmController();
        Film film = new Film(null, "Test Description", LocalDate.of(2021, 10, 1), Duration.ofHours(2));

        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void testAddFilmValidationExceptionReleaseDateBefore1895() {
        FilmController filmController = new FilmController();
        Film film = new Film("Test Film", "Test Description", LocalDate.of(1800, 1, 1), Duration.ofHours(2));

        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void testAddFilmValidationExceptionDurationNegative() {
        FilmController filmController = new FilmController();
        Film film = new Film("Test Film", "Test Description", LocalDate.of(2021, 10, 1), Duration.ofSeconds(-1));

        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void testUpdateFilm() {
        Film newFilm = new Film("Test Film", "Test Description", LocalDate.of(2021, 10, 1), Duration.ofHours(2));
        filmController.addFilm(newFilm);

        Film updatedFilm = new Film("Test Film2", "Updated Film", LocalDate.of(2021, 10, 1), Duration.ofHours(2));
        updatedFilm.setId(1L);
        Film updatedUserResult = filmController.updateFilm(updatedFilm);

        assertEquals(updatedFilm, updatedUserResult);
        assertNotEquals("Test Description", updatedUserResult.getDescription());
        assertEquals("Test Film2", updatedUserResult.getName());
    }
    // Add more test cases for addFilm method and updateUser method as needed
}
