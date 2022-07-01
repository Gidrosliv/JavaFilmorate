package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    void delete(Film film);

    Film getFilm(Integer id);

    Integer addLike(Integer filmId, Integer userId);

    Integer removeLike(Integer filmId, Integer userId);

    Collection<Film> getPopular(Integer count);

    List<Film> list();

}
