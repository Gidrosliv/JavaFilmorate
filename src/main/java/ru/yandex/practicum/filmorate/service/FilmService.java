package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.InvalidCountException;
import ru.yandex.practicum.filmorate.exceptions.invalidFilmIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;

    FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        if (film.getId() < 0) {
            throw new invalidFilmIdException("cannot be negative");
        }
        return filmStorage.update(film);
    }

    public Film getFilm(int id) {
        if (id < 0) {
            throw new invalidFilmIdException("cannot be negative");
        }
        return filmStorage.getFilm(id);
    }

    public void delete(Film film) {
        filmStorage.delete(film);
    }

    public List<Film> list() {
        return filmStorage.list();
    }

    public void addLike(int id, int userId) {
        filmStorage.addLike(id, userId);
    }

    public void removeLike(int id, int userId) {
        filmStorage.removeLike(id, userId);
    }

    public Collection<Film> getPopular(int count) {
        return filmStorage.getPopular(count);
    }

}
