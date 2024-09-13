package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private Map<Long, Film> films = new HashMap<>();
    private long idCounter = 1;

    @GetMapping
    public Map<Long, Film> getFilms() {
        log.info("Retrieving list of films");
        System.out.println();
        return films;
    }

    @PostMapping
    public Film addFilm(@RequestBody Film newFilm) {
        if (newFilm.getName() == null || newFilm.getName().length() > 200) {
            throw new ValidationException("Название фильма не может быть пустым и содержать больше 200 символов");
        }
        if (newFilm.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза фильма не может быть раньше 1895 года");
        }
        if (newFilm.getDuration().getSeconds() < 0) {
            throw new ValidationException("Продолжительность не может быть отрицательной");
        }

        log.info("Adding new film: {}", newFilm);

        if (films.get(newFilm.getId()) == null) {
            long id = getNextId();
            newFilm.setId(id);
            films.put(id, newFilm);
        }

        films.remove(newFilm.getId());
        films.put(newFilm.getId(), newFilm);

        return newFilm;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film updatedFilm) {
        if (updatedFilm.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }

        log.info("Updating film with id: {}", updatedFilm.getId());

        for (Film film : films.values()) {
            if (film.getId().equals(updatedFilm.getId())) {
                return addFilm(updatedFilm);
            }
        }

        throw new ValidationException("Фильма с таким id не существует");
    }

    private long getNextId() {
        return idCounter++;
    }
}