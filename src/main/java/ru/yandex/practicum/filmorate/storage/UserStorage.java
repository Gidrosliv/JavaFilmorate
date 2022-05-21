package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

public interface UserStorage {
    User create(User user);

    User update(User user);

    User getUser(int id);

    void delete(int id);

//    Collection<User> list();

    Map<Integer, User> getAllUsers();

    User addToFriends(int id, int friendId);

    Integer removeFromFriends(Integer id, Integer friendId);

    Collection<User> getUserFriends(Integer id);

    Collection<User> getMutualFriends(Integer id, Integer otherId);
}
