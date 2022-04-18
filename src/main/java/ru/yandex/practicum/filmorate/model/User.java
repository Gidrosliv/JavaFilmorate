package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.controllers.UserController.userIdGen;


@Getter
@Setter
@ToString
@Validated
public class User {
    @NotBlank
    @NotNull(message = "login cannot be empty")
    private String login;
    @NotBlank//TODO
    @NotNull(message = "name cannot be empty")
    private String name;
    @Email
    @NotNull
    private String email;
    @Past
    @NotNull
    private LocalDate birthday;
    private int id;


    public User(String login, String name, String email, LocalDate birthday) {
        this.login = login;
        if (name == null || name.isEmpty()){
            this.name = name;
        }else{
            this.name = login;
        }
        this.email = email;
        this.birthday = birthday;
        this.id = userIdGen();
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


