package ru.yandex.practicum.filmorate.modeltests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTests {

    @Test
    public void testFilmConstructorWithNameDescriptionReleaseDateDuration() {
        String name = "Test Film";
        String description = "Test Description";
        LocalDate releaseDate = LocalDate.of(2021, 10, 1);

        Film film = new Film(name, description, releaseDate, 2000);

        assertEquals(name, film.getName());
        assertEquals(description, film.getDescription());
        assertEquals(releaseDate, film.getReleaseDate());
        assertEquals(2000, film.getDuration());
    }

    @Test
    public void testFilmConstructorWithIdNameDescriptionReleaseDateDuration() {
        Long id = 1L;
        String name = "Test Film";
        String description = "Test Description";
        LocalDate releaseDate = LocalDate.of(2021, 10, 1);
        int duration = 2222;

        Film film = new Film(name, description, releaseDate, duration);
        film.setId(id);

        assertEquals(id, film.getId());
        assertEquals(name, film.getName());
        assertEquals(description, film.getDescription());
        assertEquals(releaseDate, film.getReleaseDate());
        assertEquals(duration, film.getDuration());
    }
}
