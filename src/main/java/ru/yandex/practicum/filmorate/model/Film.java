package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.annotation.CheckData;
import ru.yandex.practicum.filmorate.controllers.FilmController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Validated
public class Film implements Entities {
    private int id;
    @NonNull
    @NotBlank(message = "cannot be empty")
    private String name;
    @Size(max = 200)
    @NonNull
    @NotBlank(message = "cannot be empty")
    private String description;
    @CheckData
    private LocalDate releaseDate;
    @Positive(message = "must be greater than 0")
    private int duration;
    Set<String> likes = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.id = FilmController.idGenerator();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

//    public void setLikes(int id) {
//        this.likes.add(id);
//    }

    public void setLikes(String id) {
        this.likes.add(id);
    }

    public void removeLikes(String id) {
        this.likes.remove(id);
    }

//    public void removeLikes(int id) {
//        this.likes.remove(id);
//    }

}

