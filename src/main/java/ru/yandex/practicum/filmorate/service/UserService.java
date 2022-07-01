package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Service
public class UserService{
    private final UserStorage userStorage;
    public UserService(@Qualifier("userDbStorage")UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        return userStorage.create(user);
    }
    public User update(User user) {
        if (user.getId() < 0) {
            throw new InvalidUserIdException("cannot be negative");
        }
        return userStorage.update(user);
    }
    public void delete(int id) {
        userStorage.delete(id);
    }
    public User getUser(int id) {
        if(id < 0) {
            throw new InvalidUserIdException("cannot be negative");
        }
        return userStorage.getUser(id);
    }
    public Collection<User> list() {
        return userStorage.list();
    }

    public void addFriend(int id, int friendId) {
        if(id <0 || friendId <0) {
            throw new InvalidUserIdException("cannot be negative");
        }
        userStorage.addFriend(id, friendId);
    }

    public Integer removeFromFriends(Integer id, Integer friendId) {
        return userStorage.removeFromFriends(id, friendId);
    }

    public Collection<User> getUserFriends(Integer id) {
        return userStorage.getUserFriends(id);
    }

    public Collection<User> getMutualFriends(Integer id, Integer otherId) {
        return userStorage.getMutualFriends(id, otherId);
    }
    public Map<Integer, User> getAllUsers() {
        return userStorage.getAllUsers();
    }
}



