package ru.yandex.practicum.filmorate.controllertests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import java.time.LocalDate;
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
        Film film = new Film(null, "Test Description", LocalDate.of(2021, 10, 1), 2222);

        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void testAddFilmValidationExceptionReleaseDateBefore1895() {
        FilmController filmController = new FilmController();
        Film film = new Film("Test Film", "Test Description", LocalDate.of(1800, 1, 1), 2222);

        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void testAddFilmValidationExceptionDurationNegative() {
        FilmController filmController = new FilmController();
        Film film = new Film("Test Film", "Test Description", LocalDate.of(2021, 10, 1), -1);

        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }
}
