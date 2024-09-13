package ru.yandex.practicum.filmorate.modeltests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTests {

    @Test
    public void testFilmConstructorWithNameDescriptionReleaseDateDuration() {
        String name = "Test Film";
        String description = "Test Description";
        LocalDate releaseDate = LocalDate.of(2021, 10, 1);
        Duration duration = Duration.ofHours(2);

        Film film = new Film(name, description, releaseDate, duration);

        assertEquals(name, film.getName());
        assertEquals(description, film.getDescription());
        assertEquals(releaseDate, film.getReleaseDate());
        assertEquals(duration, film.getDuration());
    }

    @Test
    public void testFilmConstructorWithIdNameDescriptionReleaseDateDuration() {
        Long id = 1L;
        String name = "Test Film";
        String description = "Test Description";
        LocalDate releaseDate = LocalDate.of(2021, 10, 1);
        Duration duration = Duration.ofHours(2);

        Film film = new Film(name, description, releaseDate, duration);
        film.setId(id);

        assertEquals(id, film.getId());
        assertEquals(name, film.getName());
        assertEquals(description, film.getDescription());
        assertEquals(releaseDate, film.getReleaseDate());
        assertEquals(duration, film.getDuration());
    }
}
