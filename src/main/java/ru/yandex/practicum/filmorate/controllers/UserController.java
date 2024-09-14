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
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private long idCounter = 1;

    @GetMapping
    public List<User> getUsers() {
        return users.values().stream().toList();
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

        for (User user : users.values()) {
            if (user.getEmail().equals(newUser.getEmail())) {
                throw new ValidationException("Этот имейл уже используется");
            }
        }
        if (newUser.getName() == null) {
            newUser.setName(newUser.getLogin());
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
        if (users.get(updatedUser.getId()) == null) {
            throw new ValidationException("Пользователся с таким в id не существует");
        }
        if (updatedUser.getEmail() == null || !updatedUser.getEmail().contains("@")) {
            throw new ValidationException("Имейл пустой, либо в нем отсутствует знак <@>");
        }
        if (updatedUser.getLogin() == null || updatedUser.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен быть пустым и не должен содержать пробелы");
        }
        if (updatedUser.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Ваша дата рождения указана не коректно");
        }

        log.info("Updating user with id: {}", updatedUser.getId());

        for (User user : users.values()) {
            if (!user.getId().equals(updatedUser.getId()) && user.getEmail() != null && user.getEmail().equals(updatedUser.getEmail())) {
                throw new ValidationException("Этот имейл уже используется");
            }
        }
        users.remove(updatedUser.getId());
        users.put(updatedUser.getId(), updatedUser);
        return updatedUser;
    }

    private long getNextId() {
        return idCounter++;
    }
}
