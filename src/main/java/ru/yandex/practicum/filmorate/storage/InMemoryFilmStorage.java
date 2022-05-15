package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.invalidFilmIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    public static final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Film create(Film film) {
        films.put(film.getId(), film);
        log.info("field with type {} and id={} added.", film.getClass(), film.getId());
        return films.get(film.getId());
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
        return films.get(film.getId());
    }

    @Override
    public void delete(Film film) {
        films.remove(film.getId());
        log.info("field  with id={} deleted: {}", film.getId(), film.getClass());
    }

    public Film getFilm(int id) {
        if (id < 0) {
            throw new invalidFilmIdException("The user ID cannot be negative.");
        }
        log.info("field  with id={}", id);
        return films.get(id);
    }

    @Override
    public List<Film> list() {
        List<Film> list = new ArrayList<>();
        for (Film x : films.values()) {
            list.add(x);
        }
        log.info("number of films {}:", films.size());
        return list;
    }
}
