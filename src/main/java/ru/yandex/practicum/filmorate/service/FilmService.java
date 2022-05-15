package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InvalidCountException;
import ru.yandex.practicum.filmorate.exceptions.invalidFilmIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    InMemoryFilmStorage inMemoryFilmStorage;
    InMemoryUserStorage inMemoryUserStorage;

    FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public Film create(Film film) {
        return inMemoryFilmStorage.create(film);
    }

    public Film update(Film film) {
        return inMemoryFilmStorage.update(film);
    }

    public void delete(Film film) {
        inMemoryFilmStorage.delete(film);
    }

    public Film getFilm(int id) {
        return inMemoryFilmStorage.getFilm(id);
    }

    public List<Film> list() {
        return inMemoryFilmStorage.list();
    }

    public void addLike(int id, int userId) {
        if (id <= 0) {
            throw new invalidFilmIdException("The user ID cannot be negative.");
        }
        if (userId <= 0) {
            throw new invalidFilmIdException("The user ID cannot be negative.");
        }
        for (User x : inMemoryUserStorage.users.values()) {
            if (x.getId() == userId) {
                inMemoryFilmStorage.films.values()
                        .stream()
                        .filter(film -> film.getId() == id)
                        .forEach(film -> film.setLikes(x.getEmail()));
            }
        }
        log.info("field  with id={} liked by: {}", id, userId);
    }


    public void deleteLike(int id, int userId) {
        if (id <= 0) {
            throw new invalidFilmIdException("The user ID cannot be negative.");
        }
        if (userId <= 0) {
            throw new invalidFilmIdException("The user ID cannot be negative.");
        }
        for (User x : inMemoryUserStorage.users.values()) {
            if (x.getId() == userId) {
                inMemoryFilmStorage.films.values()
                        .stream()
                        .filter(film -> film.getId() == id)
                        .forEach(film -> film.removeLikes(x.getEmail()));
            }
        }
        log.info("field  with id={} unliked by: {}", id, userId);
    }

    public List<Film> topTen(int count) {
        if (count <= 0) {
            throw new InvalidCountException("The count cannot be negative.");
        }
        List<Film> unsortedMap = new ArrayList<>(inMemoryFilmStorage.films.values());
        List<Film> topTen = unsortedMap.stream()
                .sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .limit(count).collect(Collectors.toList());
        log.info("field count={} best films", count);
        return topTen;
    }

    public int getUsersSize() {
        log.info("got a list of sizes");
        return inMemoryFilmStorage.films.size();
    }
}
