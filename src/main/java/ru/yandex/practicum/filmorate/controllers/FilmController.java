package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.exceptions.invalidFilmIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/films")
public class FilmController {
    static FilmService filmService;
    static FilmStorage filmStorage;

    @Autowired
    FilmController(FilmService filmService, @Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }

    @PostMapping(value = "/create")
    public Film create(@RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping(value = "/update")
    public Film update(@RequestBody Film film) {
        if (film.getId() < 0) {
            throw new invalidFilmIdException("cannot be negative");
        }
        return filmStorage.update(film);
    }

    @GetMapping(value = "/{id}")
    public Film getFilm(@Valid @PathVariable int id) {
        if (id < 0) {
            throw new invalidFilmIdException("cannot be negative");
        }
        return filmStorage.getFilm(id);
    }

    @DeleteMapping
    public void delete(@RequestBody Film film) {
        filmStorage.delete(film);
    }

    @GetMapping
    public List<Film> list() {
        return filmStorage.list();
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmStorage.addLike(id, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmStorage.removeLike(id, userId);
    }

    @GetMapping(value = "/popular")
    public Collection<Film> getPopular(@RequestParam(defaultValue = "10", required = false) int count) {
        return filmStorage.getPopular(count);
    }


}
