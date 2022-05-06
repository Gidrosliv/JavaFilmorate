package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/films")
public class FilmController {
    static InMemoryFilmStorage inMemoryFilmStorage;
    static int id = 0;

    FilmController(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        inMemoryFilmStorage.create(film);
        log.info("field with type {} and id={} added.", film.getClass(), film.getId());
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        inMemoryFilmStorage.update(film);
        log.info("field  with id={} updated: {}", film.getId(), film.getClass());
        return film;
    }

    @GetMapping(value = "/{id}")
    public Film getFilm(@Valid @PathVariable int id) {
        return  inMemoryFilmStorage.getFilm(id);
//        log.info("field  with id={} deleted: {}", film.getId(), film.getClass());
//        return film;
    }

    @DeleteMapping
    public Film delete(@Valid @RequestBody Film film) {
        inMemoryFilmStorage.delete(film);
        log.info("field  with id={} deleted: {}", film.getId(), film.getClass());
        return film;
    }

    @GetMapping
    public List<Film> list() {
        log.info("current number of {}:", inMemoryFilmStorage.films.size());
        return inMemoryFilmStorage.list();
    }
//todo
    @PutMapping(value = "/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        inMemoryFilmStorage.addLike(id, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        inMemoryFilmStorage.deleteLike(id, userId);
    }

    @GetMapping(value = "/popular")
    public List<Film> topTen(@RequestParam(defaultValue = "10", required = false) int count) {
        return inMemoryFilmStorage.topTen(count);
    }

    public static int idGenerator() {
        id = inMemoryFilmStorage.films.size() + 1;
        return id;
    }
}
