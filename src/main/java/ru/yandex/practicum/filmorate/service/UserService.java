package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    public void addFriend(int id, int friendId) {
        InMemoryUserStorage.users.values()
                .stream()
                .filter(user -> user.getId() == id)
                .forEach(user -> user.setFriends(friendId));
    }

    public void deleteFriend(int id, int friendId) {
        InMemoryUserStorage.users.values().stream()
                .filter(user -> user.getId() == id)
                .forEach(user -> user.removeFriend(friendId));
    }

    public List<User> getFriends(int id) {
        List<User> friends = new ArrayList<>();
        Set<Integer> friendIdList;
        for (User x : InMemoryUserStorage.users.values()) {
            if (x.getId() == id) {
                friendIdList = x.getFriends();
                for (Integer y : friendIdList) {
                    for (User friend : InMemoryUserStorage.users.values()) {
                        if (friend.getId() == y) {
                            friends.add(friend);
                        }
                    }
                }
            }
        }

        return friends;
    }

    public List<User> mutualFriends(int id, int otherId) {
        List<User> mutualFriends = new ArrayList<>();
        Set<Integer> idFriends = new HashSet<>();
        Set<Integer> otherIdFriends = new HashSet<>();
        for (User x : InMemoryUserStorage.users.values()) {
            if (x.getId() == id) {
                idFriends = x.getFriends();
            }
        }

        for (User x : InMemoryUserStorage.users.values()) {
            if (x.getId() == otherId) {
                otherIdFriends = x.getFriends();
            }
        }

        for (Integer x : idFriends) {
            for (Integer y : otherIdFriends) {
                if (x == y) {
                    for (User friend : InMemoryUserStorage.users.values()) {
                        if (friend.getId() == x) {
                            mutualFriends.add(friend);
                        }
                    }
                }
            }
        }
        return mutualFriends;
    }

}

