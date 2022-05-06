package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/users")
public class UserController {
    static InMemoryUserStorage inMemoryUserStorage;
    UserService userService;
    static int id = 0;

    @Autowired
    UserController(InMemoryUserStorage inMemoryUserStorage, UserService userService) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.userService = userService;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {

        inMemoryUserStorage.create(user);
        log.info("field with type {} and id={} added.", user.getClass(), user.getId());
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        inMemoryUserStorage.update(user);
        log.info("field  with id={} updated: {}", user.getId(), user.getClass());
        return user;
    }

    @GetMapping(value = "/{id}")
    public User getUser(@Valid @PathVariable int id) {
        if (id < 0) {
            throw new InvalidUserIdException("ID пользователя не может быть отрицательным.");
        }
        return inMemoryUserStorage.getUser(id);
    }

    @DeleteMapping
    public void delete(@Valid @RequestBody User user) {
        inMemoryUserStorage.delete(user);
        log.info("field  with id={} deleted: {}", user.getId(), user.getClass());
    }

    @GetMapping
    public Collection<User> list() {
        log.info("current number of {}:", inMemoryUserStorage.users.size());
        return inMemoryUserStorage.list();
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        inMemoryUserStorage.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        inMemoryUserStorage.deleteFriend(id, friendId);
    }

    @GetMapping(value = "/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        return inMemoryUserStorage.getUser(id).getFriends()
                .stream()
                .map(friens -> inMemoryUserStorage.getUser(friens))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> mutualFriends(@PathVariable int id, @PathVariable int otherId) {
        return inMemoryUserStorage.mutualFriends(id, otherId);
    }

    public static int idGenerator() {
        id = inMemoryUserStorage.users.size() + 1;
        return id;
    }
}
