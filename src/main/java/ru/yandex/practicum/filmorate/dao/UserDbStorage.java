package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Repository
public class UserDbStorage implements UserStorage {
    static int id = 0;
    private final JdbcTemplate jdbcTemplate;
    private final FriendDao friendDao;

    private static final String SQL_ADD_USERS = "INSERT INTO Person (email, login, name, birthday) VALUES (?, ?, ?, ?)";
    private static final String SQL_GET_USER_ID = "SELECT id FROM Person WHERE email = ? and login = ? and " +
            "name = ? and birthday = ?";
    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM Person WHERE id = ?";
    private static final String SQL_GET_UPDATED_USER = "UPDATE Person SET email = ?, login = ?, name = ?, " +
            "birthday = ? WHERE id = ?";
    private static final String SQL_GET_ALL_USERS = "SELECT * FROM Person";
    private static final String SQL_GET_FRIENDS = "SELECT friend_id FROM Friends WHERE id = ?";
    private static final String SQL_GET_FRIEND_STATUS = "SELECT * FROM Friends WHERE id = ?";
    private static final String SQL_GET_LIKED_FILMS = "SELECT film_id FROM likes WHERE id = ?";

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate,  FriendDao friendDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.friendDao = friendDao;
    }

    @Override
    public User create(User user) {
        jdbcTemplate.update(SQL_ADD_USERS, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        log.debug("Added new user {}.", user);
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(SQL_GET_USER_ID, user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday());
        if (userRows.next()) {
            return new User(userRows.getInt("id"), user.getLogin(),
                    user.getName(), user.getEmail(), user.getBirthday());
        }
        return user;
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update(SQL_GET_UPDATED_USER, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(),
                user.getId());
        log.debug("Updated user {}.", user.getId());
        return user;
    }


    @Override
    public void delete(int id) {
        String sqlQuery = ("DELETE FROM Person WHERE ID = ?");
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public Collection<User> list() {
        return null;
    }

    @Override
    public User getUser(int id) {
        return jdbcTemplate.query(SQL_GET_USER_BY_ID, (rs, rowNum) -> makeUser(rs), id).get(0);
    }

    @Override
    public Map<Integer, User> getAllUsers() {
        Map<Integer, User> userMap = new HashMap<>();
        List<User> userList = jdbcTemplate.query(SQL_GET_ALL_USERS, (rs, rowNum) -> makeUser(rs));
        for (User u : userList) {
            userMap.put(u.getId(), u);
        }
        log.debug("A list of all users has been requested.");
        return (HashMap<Integer, User>) userMap;
    }

    @Override
    public User addFriend(int id, int friendId) {
        return friendDao.addToFriends(id, friendId);
    }

    @Override
    public Integer removeFromFriends(Integer id, Integer friendId) {
        return friendDao.removeFromFriends(id, friendId);
    }

    @Override
    public Collection<User> getUserFriends(Integer id) {
        return friendDao.getUserFriends(id);
    }

    @Override
    public Collection<User> getMutualFriends(Integer id, Integer otherId) {
        return friendDao.getMutualFriends(id, otherId);
    }

    private User makeUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        HashSet<Integer> friends = new HashSet<>(jdbcTemplate.query(SQL_GET_FRIENDS,
                (rs1, rowNum) -> (rs1.getInt("friend_id")), id));
        HashMap<Integer, Boolean> friendStatus = new HashMap<>();

        SqlRowSet friendsRows = jdbcTemplate.queryForRowSet(SQL_GET_FRIEND_STATUS, id);
        if (friendsRows.next()) {
            friendStatus.put(friendsRows.getInt("friend_id"), friendsRows.getBoolean("status"));
        }
        HashSet<Integer> likedFilms = new HashSet<>(jdbcTemplate.query(SQL_GET_LIKED_FILMS,
                (rs3, rowNum) -> (rs3.getInt("film_id")), id));
        return new User(id, login, name, email, birthday, friends, friendStatus, likedFilms);
    }
}
