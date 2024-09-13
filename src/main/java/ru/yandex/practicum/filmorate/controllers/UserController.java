package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private long idCounter = 1;

    @GetMapping
    public Map<Long, User> getUsers() {
        log.info("Retrieving list of users");
        return users;
    }

    @PostMapping
    public User addUser(@RequestBody User newUser) {
        if (newUser.getEmail() == null || !newUser.getEmail().contains("@")) {
            throw new ValidationException("Имейл пустой, либо в нем отсутствует знак <@>");
        }
        if (newUser.getLogin() == null || newUser.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен быть пустым и не должен содержать пробелы");
        }
        if (newUser.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Ваша дата рождения указана не коректно");
        }

        log.info("Adding new user: {}", newUser);

        if (users.get(newUser.getId()) != null) {
            users.remove(newUser.getId());
            users.put(newUser.getId(), newUser);
            return newUser;
        }

        for (User user : users.values()) {
            if (user.getEmail().equals(newUser.getEmail())) {
                throw new ValidationException("Этот имейл уже используется");
            }
        }

        long id = getNextId();
        newUser.setId(id);
        users.put(id, newUser);

        return newUser;
    }

    @PutMapping
    public User updateUser(@RequestBody User updatedUser) {
        if (updatedUser.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }

        log.info("Updating user with id: {}", updatedUser.getId());

        for (User user : users.values()) {
            if (!user.getId().equals(updatedUser.getId()) && user.getEmail() != null && user.getEmail().equals(updatedUser.getEmail())) {
                throw new ValidationException("Этот имейл уже используется");
            }
            if (user.getId().equals(updatedUser.getId())) {
                return addUser(updatedUser);
            }
        }

        throw new ValidationException("Поста с таким id не существует");
    }

    private long getNextId() {
        return idCounter++;
    }
}