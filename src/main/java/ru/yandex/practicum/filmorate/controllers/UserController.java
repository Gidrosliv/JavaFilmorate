package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/users")
public class UserController extends Controllers<User> {
    static int id = 0;

    public static int idGenerator() {
        ++id;
        return id;
    }
}
