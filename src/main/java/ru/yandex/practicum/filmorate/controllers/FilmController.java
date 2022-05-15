package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/films")
public class FilmController {
    static FilmService filmService;
    static int id = 0;

    FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        filmService.create(film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        filmService.update(film);
        return film;
    }

    @GetMapping(value = "/{id}")
    public Film getFilm(@Valid @PathVariable int id) {
        return filmService.getFilm(id);
    }

    @DeleteMapping
    public void delete(@Valid @RequestBody Film film) {
        filmService.delete(film);
    }

    @GetMapping
    public List<Film> list() {
        return filmService.list();
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping(value = "/popular")
    public List<Film> topTen(@RequestParam(defaultValue = "10", required = false) int count) {
        return filmService.topTen(count);
    }

    public static int idGenerator() {
        id = filmService.getUsersSize() + 1;
        return id;
    }
}
