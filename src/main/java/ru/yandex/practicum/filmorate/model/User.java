package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Getter
@Setter
@ToString
@Validated
public class User {
    @NonNull
    private String login;
    private String name;
    @Email
    @NonNull
    private String email;
    @NonNull
    @Past
    private LocalDate birthday;
    private int id;
    Set<Integer> friends = new HashSet<>();
    private Map<Integer, Boolean> friendStatus;
    private Set<Integer> likedFilms;

    public User() {
        super();
    }

    public User(String login, String name, String email, LocalDate birthday) {
        this.login = login;
        if (name == null || name.isEmpty() || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.email = email;
        this.birthday = birthday;
    }

    public User(int id, String login, String name, String email, LocalDate birthday) {
        this.login = login;
        if (name == null || name.isEmpty() || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.email = email;
        this.birthday = birthday;
        this.id = id;
    }

    public User(int id, String login, String name, String email, LocalDate birthday, Set<Integer> friends, Map<Integer, Boolean> friendStatus, Set<Integer> likedFilms) {
        this.login = login;
        if (name == null || name.isEmpty() || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.email = email;
        this.birthday = birthday;
        this.id = id;
        this.friends = new HashSet<>();
        this.friendStatus = new HashMap<>();
        this.likedFilms = new HashSet<>();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User user = (User) obj;
        if (email != user.email)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = Integer.parseInt(prime * result + getEmail());
        return result;
    }

    public String getEmail() {
        return email;
    }

    public Set<Integer> getFriends() {
        return friends;
    }

    public void setFriends(int friendId) {
        this.friends.add(friendId);
    }

    public void removeFriend(int friendId) {
        this.friends.remove(friendId);
    }

}


