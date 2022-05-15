package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/users")
public class UserController {
    static UserService userService;
    static int id = 0;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        userService.create(user);
        return userService.getUser(user.getId());
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping
    public void delete(@Valid @RequestBody User user) {
        userService.delete(user);
    }

    @GetMapping(value = "/{id}")
    public User getUser(@Valid @PathVariable int id) {
        return userService.getUser(id);
    }

    @GetMapping
    public Collection<User> list() {
        return userService.list();
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
    }

    @GetMapping(value = "/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        return userService.getUser(id).getFriends()
                .stream()
                .map(friens -> userService.getUser(friens))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> mutualFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.mutualFriends(id, otherId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriend(id, friendId);
    }

    public static int idGenerator() {
        id = userService.getUsersSize() + 1;
        return id;
    }
}
