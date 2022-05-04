package ru.yandex.practicum.filmorate.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

@Validated
@RestController
@RequestMapping(value = "/films")
public class FilmController extends Controllers<Film> {
    static int id = 0;

    public static int idGenerator() {
        ++id;
        return id;
    }
}
