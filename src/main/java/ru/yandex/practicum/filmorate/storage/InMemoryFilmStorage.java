package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.InvalidCountException;
import ru.yandex.practicum.filmorate.exceptions.invalidFilmIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    public static final Map<Integer, Film> films = new HashMap<>();
    FilmService filmService;

    InMemoryFilmStorage(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public Film create(Film film) {
        films.put(film.getId(), film);
        log.info("field with type {} and id={} added.", film.getClass(), film.getId());
        return film;
    }

    @Override
    public Film update(Film film) {
        for (Film x : films.values()) {
            if (x.getId() == film.getId()) {
                x.setName(film.getName());
                x.setDescription(film.getDescription());
                x.setReleaseDate(film.getReleaseDate());
                x.setDuration(film.getDuration());
            }
        }
        log.info("field  with id={} updated: {}", film.getId(), film.getClass());
        return film;
    }

    @Override
    public Film delete(Film film) {
        films.remove(film.getId());
        log.info("field  with id={} deleted: {}", film.getId(), film.getClass());
        return film;
    }

    public Film getFilm(int id) {
        if (id < 0) {
            throw new invalidFilmIdException("The user ID cannot be negative.");
        }
        log.info("field  with id={}", id);
        return films.get(id);
    }

    public void addLike(int id, int userId) {
        if (id <= 0) {
            throw new invalidFilmIdException("The user ID cannot be negative.");
        }
        if (userId <= 0) {
            throw new invalidFilmIdException("The user ID cannot be negative.");
        }
        log.info("field  with id={} liked by: {}", id, userId);
        filmService.addLike(id, userId);
    }

    public void deleteLike(int id, int userId) {
        if (id <= 0) {
            throw new invalidFilmIdException("The user ID cannot be negative.");
        }
        if (userId <= 0) {
            throw new invalidFilmIdException("The user ID cannot be negative.");
        }
        log.info("field  with id={} unliked by: {}", id, userId);
        filmService.deleteLike(id, userId);
    }

    public List<Film> topTen(int count) {
        if (count <= 0) {
            throw new InvalidCountException("The count cannot be negative.");
        }
        log.info("field count={} best films", count);
        return filmService.topTen(count);
    }


    @Override
    public List<Film> list() {
        List<Film> fucking = new ArrayList<>();
        for (Film x : films.values()) {
            fucking.add(x);
        }
        log.info("number of films {}:", films.size());
        return fucking;
    }
}
