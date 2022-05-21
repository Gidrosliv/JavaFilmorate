package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Slf4j
public class FilmDbStorage implements FilmStorage {

    private static final String SQL_GET_ALL_FILMS = "SELECT * FROM Film";
    private static final String SQL_GET_FILMS = "SELECT film_id, name, description, release_date," +
            " duration, mpa  FROM film";
    private static final String SQL_GET_LIKES = "SELECT id FROM likes WHERE film_id = ?";
    private static final String SQL_GET_GENRES = "SELECT genre_id FROM FilmGenre WHERE film_id = ?";

    private static final String SQL_ADD_FILM = "INSERT INTO Film(name, description, release_date, duration, mpa) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_GET_FILM_ID = "SELECT film_id FROM Film WHERE name = ? AND " +
            "description = ? AND release_date = ? AND duration = ? AND mpa = ?";
    private static final String SQL_ADD_GENRE = "INSERT INTO FilmGenre(film_id, genre_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_FILM = "UPDATE Film SET name = ?, description = ?, release_date = ?, " +
            "duration = ?, mpa = ? WHERE film_id = ?";
    private static final String SQL_DELETE_GENRE = "DELETE FROM FilmGenre WHERE film_id = ?";
    private static final String SQL_UPDATE_GENRE = "INSERT INTO FilmGenre(film_id, genre_id) VALUES(?, ?)";
    private static final String SQL_DELETE_FILM = "DELETE FROM Film WHERE film_id = ?";
    private static final String SQL_GET_FILM = "SELECT * FROM Film AS f LEFT JOIN likes AS l ON f.film_id = " +
            "l.film_id WHERE f.film_id = ? GROUP BY f.film_id, l.id";
    private static final String SQL_GET_TOP_FILMS = "SELECT f.film_id, f.name, f.description, " +
            "f.release_date, f.duration, f.mpa, l.id FROM likes AS l RIGHT JOIN Film AS f " +
            "ON f.film_id = l.film_id GROUP BY f.film_id, l.id ORDER BY COUNT(l.id) " +
            "DESC LIMIT ?";


    private final JdbcTemplate jdbcTemplate;
    private final LikesDao likesDao;

    public FilmDbStorage(JdbcTemplate jdbcTemplate, LikesDao likesDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.likesDao = likesDao;
    }

    @Override
    public Film create(Film film) {
        jdbcTemplate.update(SQL_ADD_FILM, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId());
        log.debug("New movie added {}.", film);
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(SQL_GET_FILM_ID, film.getName(),
                film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
        if (filmRows.next()) {
            if (film.getGenres() == null) {
                return new Film(filmRows.getInt("film_id"), film.getName(), film.getDescription(),
                        film.getReleaseDate(), film.getDuration(), film.getMpa(), new HashSet<>(), null);
            } else {
                for (Genre genre : film.getGenres()) {
                    jdbcTemplate.update(SQL_ADD_GENRE, filmRows.getInt("film_id"), genre.getId());
                }
                return new Film(filmRows.getInt("film_id"), film.getName(), film.getDescription(),
                        film.getReleaseDate(), film.getDuration(), film.getMpa(), film.getGenres());
            }
        }
        return film;
    }

    @Override
    public Film update(Film film) {
        jdbcTemplate.update(SQL_UPDATE_FILM, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId());
        log.debug("Movie updated {}.", film);
        if (film.getGenres() == null || film.getGenres().isEmpty()) {
            jdbcTemplate.update(SQL_DELETE_GENRE, film.getId());
            return film;
        }
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(SQL_DELETE_GENRE, film.getId());
        }
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(SQL_UPDATE_GENRE, film.getId(), genre.getId());
        }
        return film;
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        int id = rs.getInt("film_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        Integer duration = rs.getInt("duration");
        int mpa = rs.getInt("mpa");

        Set<Integer> likes = new HashSet<>(jdbcTemplate.query(SQL_GET_LIKES, (rs1, rowNum1) ->
                (rs1.getInt("id")), id));
        Set<Genre> genres = new HashSet<>(jdbcTemplate.query(SQL_GET_GENRES, (rs2, rowNum) ->
                (new Genre(rs2.getInt("genre_id"))), id));

        if (genres.isEmpty()) {
            return new Film(id, name, description, releaseDate, duration, new Mpa(mpa), likes, null);
        }
        return new Film(id, name, description, releaseDate, duration, new Mpa(mpa), likes, genres);
    }

    @Override
    public Film getFilm(Integer id) {
        log.debug("Movie received {}.", id);
        return jdbcTemplate.query(SQL_GET_FILM, (rs, rowNum) -> makeFilm(rs), id).get(0);
    }

    @Override
    public void delete(Film film) {
        jdbcTemplate.update(SQL_DELETE_FILM, film.getId());
        jdbcTemplate.update(SQL_DELETE_GENRE, film.getId());
        log.debug("Removed movie{}.", film.getId());
    }

    @Override
    public Integer addLike(Integer filmId, Integer userId) {
        log.debug("User {} liked movie {}.", userId, filmId);
        return likesDao.addLike(filmId, userId);
    }

    @Override
    public Integer removeLike(Integer filmId, Integer userId) {
        log.debug("The user {} has deleted the liking for the movie {}.", userId, filmId);
        return likesDao.removeLike(filmId, userId);
    }

    @Override
    public Collection<Film> getPopular(Integer count) {
        Collection<Film> films = jdbcTemplate.query(SQL_GET_TOP_FILMS, (rs, rowNum) -> makeFilm(rs),
                count);
        log.debug("{} popular movies requested.", count);
        return films.isEmpty() ? jdbcTemplate.query(SQL_GET_FILMS, (rs, rowNum) -> makeFilm(rs))
                : films;
    }

    @Override
    public List<Film> list() {
        log.debug("{} get All Films.");
        return jdbcTemplate.query(SQL_GET_ALL_FILMS, (rs, rowNum) -> makeFilm(rs));
    }

}
