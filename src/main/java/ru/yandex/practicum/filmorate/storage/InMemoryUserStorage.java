package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    public static final Map<Integer, User> users = new HashMap<>();
    UserService userService;

    InMemoryUserStorage(UserService userService) {
        this.userService = userService;
    }

    public User create(User user) {
        users.put(user.getId(), user);
        log.info("field with type {} and id={} added.", user.getClass(), user.getId());
        return user;
    }

    public User update(User user) {
        for(User x: users.values()){
            if(x.getId() == user.getId()){
                x.setName(user.getName());
                x.setBirthday(user.getBirthday());
                x.setEmail(user.getEmail());
                x.setLogin(user.getLogin());
            }
        }
        log.info("field  with id={} updated: {}", user.getId(), user.getClass());
        return user;
    }

    public User getUser(int id) {
        if (id < 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        log.info("field  with id={}", id);
        return users.get(id);
    }

    public User delete(User user) {
        users.remove(user.getId());
        log.info("field  with id={} deleted", user.getId());
        return user;
    }

    public Collection<User> list() {
        log.info("users list size {}", users.size());
        return users.values();
    }

    public void addFriend(int id, int friendId) {
        if (id <= 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        if (friendId <= 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        userService.addFriend(id, friendId);
        log.info("user with friendId={} has been added as a friend", friendId);
    }

    public void deleteFriend(int id, int friendId) {
        if (id <= 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        if (friendId <= 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        userService.deleteFriend(id, friendId);
        log.info("user with friendId={} has been deleted from a friend", friendId);
    }

    public List<User> getFriends(int id) {
        if (id <= 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        List<User> friends = userService.getFriends(id);
        log.info("user id={} friends list", id);
        return friends;
    }

    public List<User> mutualFriends(int id, int otherId) {
        if (id <= 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        if (otherId <= 0) {
            throw new InvalidUserIdException("The otherId ID cannot be negative.");
        }
        log.info("list of mutual friends");
        return userService.mutualFriends(id, otherId);
    }

}
