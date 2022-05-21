package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService{
    InMemoryUserStorage inMemoryUserStorage;
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User create(User user) {
        return inMemoryUserStorage.users.put(user.getId(), user);
    }
    public User update(User user) {
        return inMemoryUserStorage.update(user);
    }
    public void delete(int id) {
        inMemoryUserStorage.delete(id);
    }
    public User getUser(int id) {
        return inMemoryUserStorage.getUser(id);
    }
    public Collection<User> list() {
        return inMemoryUserStorage.list();
    }


    public void addFriend(int id, int friendId) {
        if (id <= 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        if (friendId <= 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        inMemoryUserStorage.users.values()
                .stream()
                .filter(user -> user.getId() == id)
                .forEach(user -> user.setFriends(friendId));
        log.info("user with friendId={} has been added as a friend", friendId);
    }

    public void deleteFriend(int id, int friendId) {
        if (id <= 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        if (friendId <= 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        inMemoryUserStorage.users.values().stream()
                .filter(user -> user.getId() == id)
                .forEach(user -> user.removeFriend(friendId));
        log.info("user with friendId={} has been deleted from a friend", friendId);
    }

    public List<User> getFriends(int id) {
        if (id <= 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        List<User> friends = new ArrayList<>();
        Set<Integer> friendIdList;
        for (User x : inMemoryUserStorage.users.values()) {
            if (x.getId() == id) {
                friendIdList = x.getFriends();
                for (Integer y : friendIdList) {
                    for (User friend : inMemoryUserStorage.users.values()) {
                        if (friend.getId() == y) {
                            friends.add(friend);
                        }
                    }
                }
            }
        }
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
        List<User> mutualFriends = new ArrayList<>();
        Set<Integer> idFriends = new HashSet<>();
        Set<Integer> otherIdFriends = new HashSet<>();
        for (User x : inMemoryUserStorage.users.values()) {
            if (x.getId() == id) {
                idFriends = x.getFriends();
            }
        }

        for (User x : inMemoryUserStorage.users.values()) {
            if (x.getId() == otherId) {
                otherIdFriends = x.getFriends();
            }
        }

        for (Integer x : idFriends) {
            for (Integer y : otherIdFriends) {
                if (x == y) {
                    for (User friend : inMemoryUserStorage.users.values()) {
                        if (friend.getId() == x) {
                            mutualFriends.add(friend);
                        }
                    }
                }
            }
        }
        log.info("list of mutual friends");
        return mutualFriends;
    }

    public int getUsersSize() {
        log.info("got a list of sizes");
        return inMemoryUserStorage.users.size();
    }

    public Set<Integer> getFriendList(int id) {
        return inMemoryUserStorage.users.get(id).getFriends();
    }

    public void addToFriends(int id, int friendId) {
        inMemoryUserStorage.addToFriends(id, friendId);
    }

    public Integer removeFromFriends(Integer id, Integer friendId) {
        return inMemoryUserStorage.removeFromFriends(id, friendId);
    }

    public Collection<User> getUserFriends(Integer id) {
        return inMemoryUserStorage.getUserFriends(id);
    }

    public Collection<User> getMutualFriends(Integer id, Integer otherId) {
        return inMemoryUserStorage.getMutualFriends(id, otherId);
    }
}



