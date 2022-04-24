package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.controllers.UserController;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@Validated
public class User implements Entities {
    @NotBlank(message = "cannot be empty")
    @NonNull
    private String login;
    @NonNull
    private String name;
    @Email
    @NonNull
    private String email;
    @Past(message = "cannot be in the future")
    @NonNull
    private LocalDate birthday;
    private int id;


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


}


