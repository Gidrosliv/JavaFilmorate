package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    public static final Map<Integer, User> users = new HashMap<>();

    public User create(User user) {
        users.put(user.getId(), user);
        log.info("field with type {} and id={} added.", user.getClass(), user.getId());
        return users.get(user.getId());
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

    public void delete(User user) {
        users.remove(user.getId());
        log.info("field  with id={} deleted", user.getId());
    }

    public User getUser(int id) {
        if (id < 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        log.info("field  with id={}", id);
        return users.get(id);
    }

    public Collection<User> list() {
        log.info("users list size {}", users.size());
        return users.values();
    }
}
