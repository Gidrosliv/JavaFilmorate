package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Repository
@Slf4j
public class LikesDao {
    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_ADD_LIKE = "INSERT INTO likes(id, film_id) VALUES (?, ?)";
    private static final String SQL_REMOVE_LIKE = "DELETE FROM likes WHERE id = ? AND film_id = ?";

    @Autowired
    public LikesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer addLike(Integer filmId, Integer userId) {
        jdbcTemplate.update(SQL_ADD_LIKE, userId, filmId);
        log.debug("User {} liked movie {}", userId, filmId);
        return userId;
    }

    public Integer removeLike(Integer filmId, Integer userId) {
        if(filmId<0 || userId <0){
            throw new ElementNotFoundException("cannot be negative");
        }
        jdbcTemplate.update(SQL_REMOVE_LIKE, userId, filmId);
        log.debug("User {} has deleted his like for movie {}", userId, filmId);
        return userId;
    }

}
