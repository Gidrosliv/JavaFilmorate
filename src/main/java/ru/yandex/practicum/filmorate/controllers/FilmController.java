package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@RestController
public class FilmController {
    public static HashMap<Integer, Film> films = new HashMap<>();
    static Integer filmId = 0;

    @GetMapping("/films")
    public HashMap<Integer, Film> findAll() {
        log.debug("current number of films: {}", films.size());
        return films;
    }

    @PostMapping(value = "/films", produces = APPLICATION_JSON_VALUE)
    public void create(@Valid @RequestBody Film film) {
        log.debug("field added: {}", film.getName());
        films.put(film.getId(), film);
    }

    @PutMapping(value = "/films", produces = APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Film film) {
        log.debug("field updated: {}", film.getName());
        films.put(film.getId(), film);
    }

    public static Integer filmIdGenerator() {
        if (films.size() != 0) {
            filmId = films
                    .keySet()
                    .stream()
                    .max(Long::compare)
                    .get();
            ++filmId;
            return filmId;
        }
        ++filmId;
        return filmId;
    }
}
