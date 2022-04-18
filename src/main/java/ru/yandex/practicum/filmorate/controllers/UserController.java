package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@RestController
public class UserController {
    static Integer userId = 0;
    public static HashMap<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    public HashMap<Integer, User> findAll() {
        log.debug("current number of users: {}", users.size());
        return users;
    }

    @PostMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    public void create(@RequestBody @Valid User user) {
        log.debug("field added: {}", user.getName());
        users.put(user.getId(), user);
    }

    @PutMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    public void update(@RequestBody @Valid User user) {
        log.debug("field updated: {}", user.getName());
        users.put(user.getId(), user);
    }


    public static Integer userIdGen() {
        if (users.size() != 0) {
            userId = users
                    .keySet()
                    .stream()
                    .max(Long::compare)
                    .get();
            ++userId;
            return userId;
        }
        ++userId;
        return userId;
    }


}
