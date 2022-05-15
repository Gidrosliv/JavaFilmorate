package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.controllers.UserController;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@Validated
public class User implements Entities {
    @NotBlank(message = "cannot be empty")
    @Pattern(regexp = "\\S+")
    private String login;
    private String name;
    @Email
    @NotBlank
    private String email;
    @Past(message = "cannot be in the future")
    private LocalDate birthday;
    @Positive(message = "must be greater than 0")
    private int id;
    Set<Integer> friends = new HashSet<>();

    public User(String login, String name, String email, LocalDate birthday) {
        this.login = login;
        if (name == null || name.isEmpty() || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.email = email;
        this.birthday = birthday;
        this.id = UserController.idGenerator();
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


    public void setFriends(int friendId) {
        this.friends.add(friendId);
    }

    public void removeFriend(int friendId) {
        this.friends.remove(friendId);
    }

}


