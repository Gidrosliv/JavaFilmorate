package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    public static final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    public User create(@NotNull User user) {
        User NewUser = new User(id, user.getLogin(),user.getName(),user.getEmail(), user.getBirthday());
        users.put(NewUser.getId(), NewUser);
        id++;
        log.info("field with type {} and id={} added.", user.getClass(), user.getId());
        return users.get(user.getId());
    }

    public User getUser(int id) {
        if (id < 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        log.info("field  with id={}", id);
        return users.get(id);
    }

    public User update(User user) {
        for (User x : users.values()) {
            if (x.getId() == user.getId()) {
                x.setName(user.getName());
                x.setBirthday(user.getBirthday());
                x.setEmail(user.getEmail());
                x.setLogin(user.getLogin());
            }
        }
        log.info("field  with id={} updated: {}", user.getId(), user.getClass());
        return users.get(user.getId());
    }

    public void delete(int id) {
        users.remove(id);
        log.info("field  with id={} deleted", id);
    }

    public Map<Integer, User> getAllUsers() {
        return users;
    }


    public Collection<User> list() {
        log.info("users list size {}", users.size());
        return users.values();
    }

    @Override
    public User addToFriends(int id, int friendId) {
        users.get(id).getFriends().add(friendId);
        users.get(friendId).getFriends().add(id);
        return users.get(friendId);
    }
    @Override
    public Integer removeFromFriends(Integer id, Integer removeFromId) {
        users.get(id).getFriends().remove(removeFromId);
        users.get(removeFromId).getFriends().remove(id);
        return id;
    }
    @Override
    public Collection<User> getUserFriends(Integer id) {
        List<User> friends = new ArrayList<>();
        Set<Integer> userSet = users.get(id).getFriends();
        for (Integer user : userSet) {
            friends.add(users.get(user));
        }
        return friends;
    }

    public Collection<User> getMutualFriends(Integer id, Integer id1) {
        List<User> friendsNames = new ArrayList<>();
        Set<Integer> userSet = users.get(id).getFriends();
        Set<Integer> userSet1 = users.get(id1).getFriends();
        for (Integer user : userSet) {
            if (userSet1.contains(user)) {
                friendsNames.add(users.get(user));
            }
        }
        return friendsNames;
    }

}
