package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/users")
public class UserController {
    static UserService userService;
    static UserStorage userStorage;

    @Autowired
    public UserController(UserService userService, @Qualifier("userDbStorage") UserStorage userStorage) {
        this.userService = userService;
        this.userStorage= userStorage;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userStorage.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (user.getId() < 0) {
            throw new InvalidUserIdException("must be greatest then 0");
        }
        return userStorage.update(user);
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable @Valid int id) {
        if(id<0){
            throw new InvalidUserIdException("id cannot be negative");
        }
        return userStorage.getUser(id);
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers().values();
    }

    @DeleteMapping
    public void delete(@Valid @RequestBody int id) {
        userStorage.delete(id);
    }


    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        if(id <0 || friendId <0) {
            throw new InvalidUserIdException("cannot be negative");
        }
        userStorage.addToFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public Integer removeFromFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        return userStorage.removeFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable Integer id) {
        return userStorage.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getMutualFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        return userStorage.getMutualFriends(id, otherId);
    }

}
