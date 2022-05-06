package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    public void addLike(int id, int userId) {
        for (User x : InMemoryUserStorage.users.values()) {
            if (x.getId() == userId) {
                InMemoryFilmStorage.films.values()
                        .stream()
                        .filter(film -> film.getId() == id)
                        .forEach(film -> film.setLikes(x.getEmail()));
            }
        }
    }


    public void deleteLike(int id, int userId) {
        for (User x : InMemoryUserStorage.users.values()) {
            if (x.getId() == userId) {
                InMemoryFilmStorage.films.values()
                        .stream()
                        .filter(film -> film.getId() == id)
                        .forEach(film -> film.removeLikes(x.getEmail()));
            }
        }
    }

    public List<Film> topTen(int count) {
        List<Film> unsortedMap = new ArrayList<>(InMemoryFilmStorage.films.values());
        List<Film> topTen = unsortedMap.stream()
                .sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .limit(count).collect(Collectors.toList());
        return topTen;
    }
}
