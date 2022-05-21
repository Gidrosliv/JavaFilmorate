package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Repository
@Slf4j
public class MpaDao {
    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_GET_MPA_BY_ID = "SELECT id FROM Rate WHERE id = ?";
    private static final String SQL_GET_ALL_MPA = "SELECT id FROM Rate";

    @Autowired
    public MpaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Mpa getMpaById(Integer id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SQL_GET_MPA_BY_ID, id);
        if (rowSet.next()) {
            log.debug("Rating {} requested.", id);
            return new Mpa(rowSet.getInt("id"));
        }
        return new Mpa(0);
    }

    public Collection<Mpa> getAllMpa() {
        log.debug("All ratings requested.");
        return jdbcTemplate.query(SQL_GET_ALL_MPA, (rs, rowNum) -> new Mpa(rs.getInt("id")));
    }
}
